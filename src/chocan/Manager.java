package chocan;

public class Manager extends Person {
	
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

	//getters

	public String getFirstName() {
        return firstName;
    }
	public String getLastName() {
		return lastName;
	}
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public String getZipCode() {
        return zipCode;
    }
	public String getNumber() {
		return managerNumber;
	}

	// DataCenter object 
	//request service report and summary report

	public ServiceRecord requestServiceReport(String providerNumber, String startDate, String endDate) {
		return null;
	}

	public SummaryReport requestSummaryReport(String startDate, String endDate) {
		return null;
	}

	public void suspendProvider(String providerNumber) {
		return;
	}

}
