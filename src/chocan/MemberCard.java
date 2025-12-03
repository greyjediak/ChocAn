//MemberCard.java written by Lindsey Bowen
// MemberCard should point to a specific member
//A member card should automatically be created upon creating a new user

package chocan;

public class MemberCard {
    private String memberFirstName;
    private String memberLastName;
    private String memberNumber;

    //Main constructor
    public MemberCard(String memberFirstName, String memberLastName, String memberNumber)
    {
        this.memberFirstName = memberFirstName;
        this.memberLastName = memberLastName;
        this.memberNumber = memberNumber;

    }

    // Getters
    public String getFirstName() { return memberFirstName;}
    public String getLastName() { return memberLastName;}
    public String getMemberNumber() { return memberNumber;}
}
