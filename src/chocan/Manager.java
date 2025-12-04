package chocan;

public class Manager extends Person{
	
	private String managerNumber;

	public Manager(String firstName, String lastName, String phoneNumber, String address, String city, String state,String zipCode, String number) {
		super(firstName, lastName, phoneNumber, address, city, state, zipCode);
		managerNumber = number;
		
	}
	public String getManagerNumber() {
		return managerNumber;
	}
	
	public String returnInfo() {
    	return firstName + "_" + lastName + "_" + phoneNumber  + "_" + address  + "_" + city + "_" +  state  + "_" + zipCode;
    }

}