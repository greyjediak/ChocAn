// Provider class written by Lindsey Bowen
//Providers store information about the provider name and location
//Should each provider have an ID number, or is it based off of name lookup?
// Providers are responsible for "Request Health Services", "Fill out Provider Forms", and can also "Request Provider Directory".

package chocan;

public class Provider extends Person {
    public Provider(String firstName, String lastName, String number, String address, String city, String state, String zipCode)
    {
        super(firstName, lastName, number, address, city, state, zipCode);
    }

    public boolean checkCard(MemberCard card)
    {
        //Needs to talk with verify functions from ACME
        // if card bad, return false, if good, return true
        return false; //temporary
    }

    public ProviderForm fillForm() 
    {
        //TODO
        //Needs to use ProviderForm.java
        return null;
    }

    public void requestProviderDirectory()
    {
        //TODO
        // Needs to use ProviderDirctory.java
    }

}
