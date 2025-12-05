package chocan;

/* Written by Wheeler Knight 12/04/2025*/

public class SummaryReport {
    /**
     * Generates and prints a summary report for management including:
     *  - List of providers to be paid
     *  - Number of consultations per provider
     *  - Fee owed to each provider
     *  - Total providers who serviced members
     *  - Total consultations
     *  - Overall fees to be distributed
     *
     * @param dataCenter The DataCenter object containing provider and service data.
     */
    public static void printSummaryReport(DataCenter dataCenter) {
        // Assume DataCenter has methods/data:
        // - List<Provider> getProviders()
        // - List<ServiceRecord> getServiceRecordsForLastWeek()
        // - Each ServiceRecord has: getProviderNumber(), getServiceFee()
        // - Provider has: getProviderNumber(), getName()
        java.util.Map<String, ProviderSummary> providerMap = new java.util.HashMap<>();

        java.util.List<ServiceRecord> serviceRecords = dataCenter.getServiceRecordsForLastWeek();
        // Tally per-provider info
        for (ServiceRecord sr : serviceRecords) {
            String providerNumber = sr.getProviderNumber();
            double fee = sr.getServiceFee();
            Provider provider = dataCenter.getProviderByNumber(providerNumber);
            if (provider == null) continue; // skip if provider not found

            ProviderSummary summary = providerMap.get(providerNumber);
            if (summary == null) {
                // Edited by Wheeler Knight on 12/4/2025 - Changed to use getFullName()
                summary = new ProviderSummary(provider.getFullName(), providerNumber);
                providerMap.put(providerNumber, summary);
            }
            summary.consultCount += 1;
            summary.feeOwed += fee;
        }

        // Print report header
        System.out.println("Summary Report (This Week)");
        System.out.printf("%-20s %-15s %-20s %-15s\n", "Provider Name", "Provider Number", "Number of Consultations", "Fee Owed ($)");
        double totalFeeAll = 0;
        int totalConsults = 0;

        for (ProviderSummary ps : providerMap.values()) {
            System.out.printf("%-20s %-15s %-20d $%-14.2f\n", ps.name, ps.number, ps.consultCount, ps.feeOwed);
            totalFeeAll += ps.feeOwed;
            totalConsults += ps.consultCount;
        }

        System.out.println("\nTotal Providers Who Serviced Members: " + providerMap.size());
        System.out.println("Total Number of Consultations: " + totalConsults);
        System.out.printf("Overall Fee to be distributed to providers: $%.2f\n", totalFeeAll);
    }

    // Helper class (inner, package-private) for provider info aggregation
    static class ProviderSummary {
        String name;
        String number;
        int consultCount;
        double feeOwed;

        ProviderSummary(String name, String number) {
            this.name = name;
            this.number = number;
            this.consultCount = 0;
            this.feeOwed = 0.0;
        }
    }
}
