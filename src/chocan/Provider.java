package chocan;

import java.util.List;

public class Provider extends Person {
	
	private String providerNumber;
	private String password;

    // Default constructor for Gson deserialization - Wheeler Knight 12/5/2025
    public Provider() {
        super();
    }
	
    public Provider(String firstName, String lastName, String phoneNumber, String address, String city, String state, String zipCode, String number){
        super(firstName, lastName, phoneNumber, address, city, state, zipCode);
        providerNumber = number;
    }

    // Written by Wheeler Knight 12/04/2025
    /**
     * Checks if the given member card is valid.
     * Validity means:
     *   - The member exists in the system as an active member (not suspended)
     *   - The card number and name match the member record
     *
     * NOTE: This implementation assumes access to a DataCenter or ACMEAccountingServices
     * instance is provided to the Provider object, or passed as an argument.
     * For demonstration, we use a static reference to DataCenter, but in
     * production you'd inject or pass it in for unit-testability.
     */
    public boolean checkCard(MemberCard card) {
        // You'll need an instance of DataCenter or ACMEAccountingServices to check
        // We'll use a static DataCenter for example, but you should refactor as needed!
        DataCenter dataCenter = new DataCenter(); // Replace with dependency injection in actual use
        // Check if card matches an active (not suspended) member
        for (Member member : dataCenter.getMembers()) {
            MemberCard mcard = member.getCard();
            if (mcard.getMemberNumber().equals(card.getMemberNumber()) &&
                mcard.getFirstName().equalsIgnoreCase(card.getFirstName()) &&
                mcard.getLastName().equalsIgnoreCase(card.getLastName())) {
                // Now check that member is NOT suspended
                boolean isSuspended = false;
                for (Member sm : dataCenter.getSuspendedMembers()) {
                    if (sm.getCard().getMemberNumber().equals(card.getMemberNumber())) {
                        isSuspended = true;
                        break;
                    }
                }
                return !isSuspended;
            }
        }
        // Not found, or is suspended
        return false;
    };
    
    // Written by Wheeler Knight 12/04/2025
    /**
     * Prompts the provider to fill out a provider form, asks for all required fields,
     * and returns a populated ProviderForm object.
     *
     * Displays prompts for: current time (auto-generated), date of service, member number, member name,
     * service code, and fee (auto-computed from code if possible). No persistence to file is done here.
     */
    public ProviderForm fillForm(ACMEAccountingServices accounting) 
    {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        ProviderForm form = new ProviderForm();

        // Get current date/time for form
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        form.setHours((byte) now.getHour());
        form.setMinutes((byte) now.getMinute());
        form.setSeconds((byte) now.getSecond());

        // Prompt for Date of Service
        System.out.println("=== Provider Form ===");
        System.out.println("Enter the date of service:");

        System.out.print("  Day (1-31): ");
        byte day = (byte) Integer.parseInt(scanner.nextLine().trim());
        form.setDay(day);

        System.out.print("  Month (1-12): ");
        byte month = (byte) Integer.parseInt(scanner.nextLine().trim());
        form.setMonth(month);

        System.out.print("  Year (e.g. 2025): ");
        short year = (short) Integer.parseInt(scanner.nextLine().trim());
        form.setYear(year);

        // Member Number
        System.out.print("Enter the member number: ");
        String memberNumber = scanner.nextLine().trim();
        form.setNumber(memberNumber);

        // Member Name (for display, not necessary for ProviderForm but optional)
        String memberName = "";
        Member memberFound = null;
        for (Member m : accounting.getMembers()) {
            if (m.getCard().getMemberNumber().equals(memberNumber)) {
                memberName = m.getFirstName() + " " + m.getLastName();
                memberFound = m;
                break;
            }
        }
        if (memberName.isEmpty())
            System.out.println("WARNING: Member not found in system!");

        form.setName(memberName.isEmpty() ? "Unknown Member" : memberName);

        // Service Code
        System.out.print("Enter the 6-digit service code: ");
        String codeInput = scanner.nextLine().trim();
        int serviceCode;
        try {
            serviceCode = Integer.parseInt(codeInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid code, defaulting to 1.");
            serviceCode = 1;
        }

        // Set Service Fee (look up by code if available)
        double fee = 0.0;
        DataCenter dc = new DataCenter(); // Ideally this should be injected, not constructed every time.
        if (serviceCode >= 1 && serviceCode <= dc.SERVICE_FEE_RATES.length) {
            fee = dc.SERVICE_FEE_RATES[serviceCode - 1];
            System.out.printf("Service fee for code %06d is $%.2f.\n", serviceCode, fee);
        } else {
            System.out.print("Unknown code. Enter the fee manually: ");
            try {
                fee = Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                fee = 0.0;
            }
        }

        form.setFee(fee);

        System.out.println("Provider form filled successfully.");

        return form;
    }

    // Written by Wheeler Knight 12/04/2025
    public boolean EnterPassword(Person person) {
        /*
         * Prompts the provider to enter their password, checks if it matches the provider's stored password,
         * returns true if correct, false otherwise.
         * NOTE: This assumes the Provider class has a `password` field and appropriate methods to get/set it.
         * If not, you may need to add it.
         */
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.print("Enter your password: ");
        String inputPassword = scanner.nextLine();

        // For this implementation, let's assume a `password` field exists.
        // Typically you would have: private String password; and a getPassword() method.
        // For demo, we'll suppose such a method is present; else, replace with the actual logic.
        if (this.getPassword() != null && this.getPassword().equals(inputPassword)) {
            System.out.println("Password correct. Access granted.");
            // Optionally, return true explicitly and close scanner if you don't plan to reuse it.
            return true;
        } else {
            System.out.println("Incorrect password. Access denied.");
            return false;
        }

    }

    // Written by Wheeler Knight 12/04/2025
    public List<Provider> RequestProviderDirectory() {
        /*
        This should display and write to a file the ProviderDirectory if the provider asks for it.
        In practice, ProviderDirectory should be constructed using the current DataCenter so it reflects up-to-date service info.
        It will display to the console, and write to a default file path such as "provider_directory.txt".
        */

        // For demo, instantiate a DataCenter here, but in a real program,
        // this DataCenter object should be injected or made available to avoid state loss.
        DataCenter dataCenter = new DataCenter();
        ProviderDirectory directory = new ProviderDirectory(dataCenter);

        // Display to console
        directory.displayProviderDirectory();

        // Write to file
        String filePath = "provider_directory.txt";
        directory.saveProviderDirectoryToFile(filePath);
        System.out.println("Provider directory has been saved to: " + filePath);

        // The assignment says to return a List<Provider> but that doesn't make sense for a directory,
        // so we just return all providers from the data center as a best effort.
        return java.util.Arrays.asList(dataCenter.getProviders());

    
    };
    



    // Below is Written by Wheeler Knight 12/04/2025
    public String getProviderNumber()
    {
        return providerNumber;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    // Edited by Wheeler Knight on 12/4/2025 - Removed duplicate getName() method, use inherited getFullName() instead
    
    // Edited by Wheeler Knight on 12/4/2025 - Added providerNumber to output to match expected data format
    public String returnInfo() {
    	return firstName + "_" + lastName + "_" + phoneNumber  + "_" + address  + "_" + city + "_" +  state  + "_" + zipCode + "_" + providerNumber;
    }

     // pull getSuspendedMembers from ACMEAccountingServices

    public void checkSuspended(ACMEAccountingServices acmeAccounting) {
    	Member[] suspendedMembers = acmeAccounting.getSuspendedMembers();
    	for(int i = 0; i < suspendedMembers.length; i++) {
    		if(suspendedMembers[i].getCard().getMemberNumber() == this.getProviderNumber()) {
    			//provider is suspended
    		}
    	}
    }

    public ProviderDirectory requestProviderDirectory() {
        // TODO Auto-generated method stub
        return null;
    }

    public ServiceRecord requestServiceRecord(String providerNumber, String startDate, String endDate) {
        // TODO Auto-generated method stub
        return null;
    }

    public SummaryReport requestSummaryReport(String startDate, String endDate) {
        // TODO Auto-generated method stub
        return null;
    }

}
