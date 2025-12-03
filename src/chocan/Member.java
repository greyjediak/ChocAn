package chocan;

public class Member extends Person {
    private String email;  //
    private String memberNumber; // Members unique ID

    //Main Constructor
    public Member(String firstName, String lastName, String phoneNumber, String address, String city, String state, String zipCode, String email, String memberNumber)
    {
        super(firstName, lastName, phoneNumber, address, city, state, zipCode);
        this.email = email;
        this.memberNumber = memberNumber;
    }

    //Getters
    public String getEmail() {return email;}
    //public String getMemberNumber(){ return idNumber;}

    // Setters
    public void setEmail(String email) {this.email = email;}
    public void setMemberNumber (String idNumber) { this.idNumber = idNumber;}
    
    // returnInfo returns member info for ACMEAccoutingServices.writeInfo()
    public String returnInfo() {
    	return String.join("_", firstName, lastName, phoneNumber, address, city, state, zipCode, email, idNumber);
    }

    public String returnMember ()
    {
        return memberNumber;
    }

}
