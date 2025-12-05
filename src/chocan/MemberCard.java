/*
Member Class
Responsible for being able to return membercard info relevant to a particular member
 */

package chocan;

public class MemberCard {
    private String memberFirstName;
    private String memberLastName;
    private String memberNumber;

    // Default constructor for Gson deserialization - Wheeler Knight 12/5/2025
    public MemberCard() {}

    public MemberCard(String memberFirstName, String memberLastName, String memberNumber)
    {
        this.memberFirstName = memberFirstName;
        this.memberLastName = memberLastName;
        this.memberNumber = memberNumber;

    }

    public String getFirstName() { return memberFirstName;}
    public String getLastName() { return memberLastName;}
    public String getMemberNumber() { return memberNumber;}
}
