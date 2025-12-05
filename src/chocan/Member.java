/*
Member class authored by Lindsey B.
Inherits from Person and stores all information relevant to a "Member"
Not really responsible for much besides being an object with member info
Contains class MemberCard which stores the name and ID of a member
 */

package chocan;

public class Member extends Person {
    private String email;  //
    private String pin; // PIN for login validation - Wheeler Knight 12/5/2025
    private MemberCard memberCard; //let all members have object member card

    // Default constructor for Gson deserialization - Wheeler Knight 12/5/2025
    public Member() {
        super();
    }

    public Member(String firstName, String lastName, String phoneNumber, String address, String city, String state, String zipCode, String email, String number)
    {
        super(firstName, lastName, phoneNumber, address, city, state, zipCode);
        this.email = email;
        this.memberCard = new MemberCard(firstName, lastName, number);
    }

    // Getters and Setters
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPin() {return pin;}
    public void setPin(String pin) {this.pin = pin;}
    public MemberCard getCard() {return memberCard;}
    /*public String getFullName()
    {
        return firstName + " " + lastName;
    }*/
    public String returnInfo() {
        return firstName + "_" + lastName + "_" + phoneNumber + "_" + address + "_" + city + "_" + state + "_" + zipCode
                + "_" + email + "_" + memberCard.getMemberNumber();
    }

    /**
     * The member requests a health service from a provider and service of their choosing.
     * This prompts the member to select a provider and the type of service requested.
     * Then, it creates a ServiceRequest and (optionally) appends it to a request queue.
     * Written by Wheeler Knight 12/04/2025
     */
    public void RequestHealthService() {
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        // Prompt for provider name (In real case, might query available providers from DataCenter)
        System.out.println("=== Request Health Service ===");
        System.out.print("Enter the provider's name: ");
        String providerName = scanner.nextLine().trim();

        // Prompt for service type (In real case, display available services from ProviderDirectory)
        System.out.print("Enter the type of service: ");
        String serviceType = scanner.nextLine().trim();

        // Create service request
        ServiceRequest request = new ServiceRequest(this, providerName, serviceType);

        System.out.println("Service request created:");
        System.out.println("  Member Number: " + this.getCard().getMemberNumber());
        System.out.println("  Provider: " + providerName);
        System.out.println("  Service Type: " + serviceType);

        // Typically, you'd add the request to a queue or notify the system:
        // DataCenter.getInstance().addServiceRequest(request); // example only
    }
}
