package chocan;

public class Provider extends Person {
    public Provider(String firstName, String lastName, String number, String address, String city, String state, String zipCode)
    {
        super(firstName, lastName, number, address, city, state, zipCode);
    }

    public boolean checkCard(MemberCard card)
    {
        //Needs to talk with verify functions
        // if card bad, return false, if good, return true
        return false; //temporary
    }
    public ProviderForm fillForm() 
    {
        //TODO
        return null;
    }
    public void requestProviderDirectory()
    {
        //TODO
    }

}
