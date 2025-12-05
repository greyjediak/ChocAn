package chocan;

import java.util.*;
import java.time.LocalDate;

/**
 * Provider Report Generator
 * Written by Wheeler Knight on 12/5/2025
 * 
 * Generates weekly reports for individual providers showing:
 * - Provider name, number, address
 * - List of services provided (date, member name, service code, fee)
 * - Total consultations and total fee
 */
public class ProviderReport {

    /**
     * Print a weekly service report for a specific provider
     */
    public static void printProviderReport(Provider provider, DataCenter dataCenter) {
        if (provider == null) {
            System.out.println("Error: Provider is null");
            return;
        }

        // Get service records for last week for this provider
        List<ServiceRecord> allRecords = dataCenter.getServiceRecordsForLastWeek();
        List<ServiceRecord> providerRecords = new ArrayList<>();
        
        for (ServiceRecord sr : allRecords) {
            if (sr.getProviderNumber().equals(provider.getProviderNumber())) {
                providerRecords.add(sr);
            }
        }

        // Sort by date
        Collections.sort(providerRecords, (r1, r2) -> {
            LocalDate d1 = r1.getServiceDate();
            LocalDate d2 = r2.getServiceDate();
            if (d1 == null && d2 == null) return 0;
            if (d1 == null) return 1;
            if (d2 == null) return -1;
            return d1.compareTo(d2);
        });

        // Print header
        System.out.println("=========================================");
        System.out.println("       PROVIDER SERVICE REPORT          ");
        System.out.println("=========================================");
        System.out.println();
        System.out.println("Provider Name   : " + provider.getFullName());
        System.out.println("Provider Number : " + provider.getProviderNumber());
        System.out.println("Address         : " + provider.getAddress());
        System.out.println("City            : " + provider.getCity());
        System.out.println("State           : " + provider.getState());
        System.out.println("ZIP Code        : " + provider.getZipCode());
        System.out.println();

        if (providerRecords.isEmpty()) {
            System.out.println("No services provided during this week.");
            return;
        }

        // Print services table
        System.out.printf("%-12s  %-20s  %-15s  %-20s  %-10s\n", 
            "Date", "Member Name", "Member #", "Service", "Fee");
        System.out.println("----------------------------------------------------------------------------------");

        double totalFees = 0;
        int totalConsultations = 0;

        for (ServiceRecord sr : providerRecords) {
            String date = (sr.getServiceDate() != null) ? sr.getServiceDate().toString() : "N/A";
            
            // Get member info
            Member member = dataCenter.getMemberByNumber(sr.getMemberNumber());
            String memberName = (member != null) ? member.getFullName() : "Unknown";
            
            // Get service name
            String serviceName = "Unknown";
            try {
                int code = Integer.parseInt(sr.getServiceCode());
                if (code >= 1 && code <= dataCenter.SERVICE_NAMES.length) {
                    serviceName = dataCenter.SERVICE_NAMES[code - 1];
                }
            } catch (Exception e) {
                serviceName = "Code: " + sr.getServiceCode();
            }

            double fee = sr.getServiceFee();
            totalFees += fee;
            totalConsultations++;

            System.out.printf("%-12s  %-20s  %-15s  %-20s  $%-9.2f\n",
                date, memberName, sr.getMemberNumber(), serviceName, fee);
        }

        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Total Consultations: " + totalConsultations);
        System.out.printf("Total Fee to be Paid: $%.2f\n", totalFees);
        System.out.println("=========================================");
    }

    /**
     * Print provider reports for ALL providers who had consultations this week
     */
    public static void printAllProviderReports(DataCenter dataCenter) {
        System.out.println("\n");
        System.out.println("*****************************************");
        System.out.println("*       ALL PROVIDER REPORTS            *");
        System.out.println("*****************************************");
        System.out.println();

        // Find all providers who have records this week
        List<ServiceRecord> weeklyRecords = dataCenter.getServiceRecordsForLastWeek();
        Set<String> providerNumbers = new HashSet<>();
        
        for (ServiceRecord sr : weeklyRecords) {
            providerNumbers.add(sr.getProviderNumber());
        }

        if (providerNumbers.isEmpty()) {
            System.out.println("No provider activity this week.");
            return;
        }

        for (String providerNum : providerNumbers) {
            Provider p = dataCenter.getProviderByNumber(providerNum);
            if (p != null) {
                printProviderReport(p, dataCenter);
                System.out.println();
            }
        }
    }

    /**
     * Export provider report to file
     */
    public static void exportProviderReportToFile(Provider provider, DataCenter dataCenter, String filePath) {
        try (java.io.PrintWriter out = new java.io.PrintWriter(new java.io.FileWriter(filePath))) {
            List<ServiceRecord> allRecords = dataCenter.getServiceRecordsForLastWeek();
            List<ServiceRecord> providerRecords = new ArrayList<>();
            
            for (ServiceRecord sr : allRecords) {
                if (sr.getProviderNumber().equals(provider.getProviderNumber())) {
                    providerRecords.add(sr);
                }
            }

            out.println("=========================================");
            out.println("       PROVIDER SERVICE REPORT          ");
            out.println("=========================================");
            out.println("Generated: " + java.time.LocalDateTime.now());
            out.println();
            out.println("Provider Name   : " + provider.getFullName());
            out.println("Provider Number : " + provider.getProviderNumber());
            out.println("Address         : " + provider.getAddress());
            out.println("City            : " + provider.getCity());
            out.println("State           : " + provider.getState());
            out.println("ZIP Code        : " + provider.getZipCode());
            out.println();

            if (providerRecords.isEmpty()) {
                out.println("No services provided during this week.");
            } else {
                out.printf("%-12s  %-20s  %-15s  %-20s  %-10s\n", 
                    "Date", "Member Name", "Member #", "Service", "Fee");
                out.println("----------------------------------------------------------------------------------");

                double totalFees = 0;
                int totalConsultations = 0;

                for (ServiceRecord sr : providerRecords) {
                    String date = (sr.getServiceDate() != null) ? sr.getServiceDate().toString() : "N/A";
                    Member member = dataCenter.getMemberByNumber(sr.getMemberNumber());
                    String memberName = (member != null) ? member.getFullName() : "Unknown";
                    
                    String serviceName = "Unknown";
                    try {
                        int code = Integer.parseInt(sr.getServiceCode());
                        if (code >= 1 && code <= dataCenter.SERVICE_NAMES.length) {
                            serviceName = dataCenter.SERVICE_NAMES[code - 1];
                        }
                    } catch (Exception e) {}

                    totalFees += sr.getServiceFee();
                    totalConsultations++;

                    out.printf("%-12s  %-20s  %-15s  %-20s  $%-9.2f\n",
                        date, memberName, sr.getMemberNumber(), serviceName, sr.getServiceFee());
                }

                out.println("----------------------------------------------------------------------------------");
                out.println("Total Consultations: " + totalConsultations);
                out.printf("Total Fee to be Paid: $%.2f\n", totalFees);
            }

            System.out.println("Provider report exported to: " + filePath);
        } catch (java.io.IOException e) {
            System.err.println("Error exporting provider report: " + e.getMessage());
        }
    }
}

