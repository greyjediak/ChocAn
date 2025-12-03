//Member.java written by Lindsey Bowen
//Member class should be capable of creating new members and holding information such as:
// Name, phone number, address, city, state, zip, and status (A/S)
// This class will matter for the ACMEAccountingService class
//NEEDS COMPLETION

package chocan;


public class Member extends Person {
    private String email;  //
    private MemberCard memberCard; //let all members have object member card
    private MemberStatus status; // From enum in MemberStatus.java, ACTIVE/SUSPENDED

    // Main constructor
    public Member(String firstName, String lastName, String number, String address, String city, String state, String zipCode, String email, MemberStatus status)
    {
        super(firstName, lastName, number, address, city, state, zipCode);
        this.email = email;
        this.status = status;
        this.memberCard = new MemberCard(firstName, lastName, number);
    }

    // Getters
    public String getEmail() { return email;} //getter for email
    public MemberCard getCard() { return memberCard;}
    public MemberStatus getStatus(){ return status;}

    // Setters
    public void setEmail(String email) {this.email = email;} //setter for email
    public void setStatus(MemberStatus status) // status comes in from elsewhere
    {
        this.status = status;
    }

    // Member Status
    public boolean isActive() // Check if active member
    {
        return status == MemberStatus.ACTIVE;
    } 
    public boolean isSuspended() // Check if member suspended - set by ACME Accounting
    {
        return status == MemberStatus.SUSPENDED;
    }

    

}
