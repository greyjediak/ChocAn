package chocan;

public class Provider extends Person {
	
	private String providerNumber;
	
    public Provider(String firstName, String lastName, String phoneNumber, String address, String city, String state, String zipCode, String number)
    {
        super(firstName, lastName, phoneNumber, address, city, state, zipCode);
        providerNumber = number;
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
    public String getProviderNumber()
    {
        return providerNumber;
    }
    
    public String returnInfo() {
    	return firstName + "_" + lastName + "_" + phoneNumber  + "_" + address  + "_" + city + "_" +  state  + "_" + zipCode;
    }

}
