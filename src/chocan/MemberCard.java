package chocan;

public class MemberCard {
    private String memberFirstName;
    private String memberLastName;
    private String memberNumber;

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
