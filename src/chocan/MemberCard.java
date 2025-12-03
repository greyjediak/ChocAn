// MemberCard written by Lindsey Bowen
// Small object linked to Member class
// TODO: tie to Member in ACMEAccountingServices

package chocan;

public class MemberCard {
    private String memberNumber;

    public MemberCard(String memberNumber)
    {
        this.memberNumber = memberNumber;

    }

    // Getters
    public String getMemberNumber() { return memberNumber;}

}
