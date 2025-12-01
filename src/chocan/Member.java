//Member.java written by Lindsey Bowen
//NEEDS COMPLETION

package chocan;

public class Member extends Person {
    private String email;  //
    private MemberCard memberCard; //let all members have object member card

    public Member(String firstName, String lastName, String number, String address, String city, String state, String zipCode, String email)
    {
        super(firstName, lastName, number, address, city, state, zipCode);
        this.email = email;
        this.memberCard = new MemberCard(firstName, lastName, number);
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public MemberCard getCard() {return memberCard;}

    public void requestHealthService() 
    {
        //TODO
    }

}
