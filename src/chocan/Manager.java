package chocan;

public class Manager extends Person{
	
	private String managerNumber;
	private String password; // Password for login validation - Wheeler Knight 12/5/2025

	// Default constructor for Gson deserialization - Wheeler Knight 12/5/2025
	public Manager() {
		super();
	}

	public Manager(String firstName, String lastName, String phoneNumber, String address, String city, String state, String zipCode, String number) {
		super(firstName, lastName, phoneNumber, address, city, state, zipCode);
		managerNumber = number;
	}
	
	public String getManagerNumber() {
		return managerNumber;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String returnInfo() {
		return firstName + "_" + lastName + "_" + phoneNumber + "_" + address + "_" + city + "_" + state + "_" + zipCode;
	}
		
	// Written by Wheeler Knight 12/04/2025
	public void RequestServiceReport(ACMEAccountingServices accounting) {
		// Prompts for week number and year, then generates and prints member service reports for that week.
		java.util.Scanner scanner = new java.util.Scanner(System.in);
		System.out.println("=== Member Service Report Request ===");
		System.out.print("Enter the week number (1-53): ");
		int weekNumber = 1;
		try {
			weekNumber = Integer.parseInt(scanner.nextLine().trim());
		} catch (Exception e) {
			System.out.println("Invalid input. Using week 1.");
		}

		System.out.print("Enter the year (e.g. 2025): ");
		int year = java.time.LocalDate.now().getYear();
		try {
			year = Integer.parseInt(scanner.nextLine().trim());
		} catch (Exception e) {
			System.out.println("Invalid input. Using current year: " + year);
		}

		// Retrieve all service records for that week (simulate DataCenter access)
		DataCenter dataCenter = new DataCenter();
		java.util.List<ServiceRecord> allRecords = dataCenter.getServiceRecords();

		// Determine the start and end dates of the requested week
		java.time.LocalDate firstDayOfYear = java.time.LocalDate.of(year, 1, 1);
		java.time.temporal.WeekFields weekFields = java.time.temporal.WeekFields.ISO;
		java.time.LocalDate weekStart = firstDayOfYear.with(weekFields.weekOfYear(), weekNumber).with(weekFields.dayOfWeek(), 1);
		java.time.LocalDate weekEnd = weekStart.plusDays(6);

		// Gather records in the selected time window
		java.util.List<ServiceRecord> weeklyRecords = new java.util.ArrayList<>();
		for (ServiceRecord r : allRecords) {
			java.time.LocalDate date = null;
			try {
				if (r.getServiceDate() instanceof java.time.LocalDate) {
					date = (java.time.LocalDate) r.getServiceDate();
				} else if (r.getServiceDate() != null) {
					date = java.time.LocalDate.parse(r.getServiceDate().toString());
				}
			} catch (Exception ignore) {}
			if (date != null && (date.compareTo(weekStart) >= 0 && date.compareTo(weekEnd) <= 0)) {
				weeklyRecords.add(r);
			}
		}

		// For each member, print their service report for the week
		System.out.println("\n=== Weekly Service Reports (Week " + weekNumber + ", " + year + ") ===\n");
		for (Member m : accounting.getMembers()) {
			MemberServiceReport.ReportGenerator.printMemberReport(m, weeklyRecords, dataCenter);
			System.out.println("------------------------------------------\n");
		}
	};

	// Written by Wheeler Knight 12/04/2025
	public void RequestSummaryReport(ACMEAccountingServices accounting) {
		/*
		 * This method generates and prints the summary report.
		 * The summary report should contain, for each provider who provided services this week:
		 *   - Provider name and number
		 *   - Number of consultations performed
		 *   - Total fee to be paid
		 * At the end, print the total number of providers who provided service, and the total consultations and total fees for all.
		 * The summary should be for the current (or prompted) week.
		 */
		java.util.Scanner scanner = new java.util.Scanner(System.in);

		// Prompt for week number and year (default: current week and year)
		System.out.print("Enter the week number for the report (1-53): ");
		int weekNumber = 1;
		try {
			weekNumber = Integer.parseInt(scanner.nextLine().trim());
		} catch (Exception e) {
			System.out.println("Invalid input. Using week 1.");
		}

		System.out.print("Enter the year (e.g. 2025): ");
		int year = java.time.LocalDate.now().getYear();
		try {
			year = Integer.parseInt(scanner.nextLine().trim());
		} catch (Exception e) {
			System.out.println("Invalid input. Using current year: " + year);
		}

		// Access all service records
		DataCenter dataCenter = new DataCenter();
		java.util.List<ServiceRecord> allRecords = dataCenter.getServiceRecords();

		// Find range for the week
		java.time.LocalDate firstDayOfYear = java.time.LocalDate.of(year, 1, 1);
		java.time.temporal.WeekFields weekFields = java.time.temporal.WeekFields.ISO;
		java.time.LocalDate weekStart = firstDayOfYear.with(weekFields.weekOfYear(), weekNumber).with(weekFields.dayOfWeek(), 1);
		java.time.LocalDate weekEnd = weekStart.plusDays(6);

		// Filter records in the selected week
		java.util.List<ServiceRecord> weeklyRecords = new java.util.ArrayList<>();
		for (ServiceRecord r : allRecords) {
			java.time.LocalDate date = null;
			try {
				if (r.getServiceDate() instanceof java.time.LocalDate) {
					date = (java.time.LocalDate) r.getServiceDate();
				} else if (r.getServiceDate() != null) {
					date = java.time.LocalDate.parse(r.getServiceDate().toString());
				}
			} catch (Exception ignore) {}
			if (date != null && (date.compareTo(weekStart) >= 0 && date.compareTo(weekEnd) <= 0)) {
				weeklyRecords.add(r);
			}
		}

		// Map by providerNumber: summary of consultations and total fees
		java.util.Map<String, Integer> providerToConsults = new java.util.HashMap<>();
		java.util.Map<String, Double> providerToFee = new java.util.HashMap<>();
		// Map provider number to provider object for quick lookup
		java.util.Map<String, Provider> providerMap = new java.util.HashMap<>();
		for (Provider p : dataCenter.getProviders()) {
			providerMap.put(p.getProviderNumber(), p);
		}

		for (ServiceRecord r : weeklyRecords) {
			String providerNum = r.getProviderNumber();
			double fee = 0.0;
			try {
				int code = Integer.parseInt(r.getServiceCode());
				if (code >= 1 && code <= dataCenter.SERVICE_FEE_RATES.length) {
					fee = dataCenter.SERVICE_FEE_RATES[code - 1];
				}
			} catch (Exception e) {
				// fallback: fee = 0
			}
			providerToConsults.put(providerNum, providerToConsults.getOrDefault(providerNum, 0) + 1);
			providerToFee.put(providerNum, providerToFee.getOrDefault(providerNum, 0.0) + fee);
		}

		System.out.println("\n=== Weekly Summary Report (Week " + weekNumber + ", " + year + ") ===\n");
		System.out.printf("%-20s %-15s %-25s %-15s\n", "Provider Name", "Number", "Consultations Performed", "Total Fee");

		int grandConsults = 0;
		double grandFees = 0;
		int providerWithConsults = 0;

		for (String providerNum : providerToConsults.keySet()) {
			Provider p = providerMap.get(providerNum);
			// Edited by Wheeler Knight on 12/4/2025 - Changed to use getFullName()
			String providerName = (p != null) ? p.getFullName() : "Unknown";
			int consults = providerToConsults.get(providerNum);
			double fee = providerToFee.getOrDefault(providerNum, 0.0);
			System.out.printf("%-20s %-15s %-25d $%-14.2f\n", providerName, providerNum, consults, fee);
			grandConsults += consults;
			grandFees += fee;
			providerWithConsults++;
		}

		System.out.println("\n--------------------------------------------");
		System.out.println("Total providers who provided service: " + providerWithConsults);
		System.out.println("Total consultations: " + grandConsults);
		System.out.printf("Total fees: $%.2f\n", grandFees);
	};




}