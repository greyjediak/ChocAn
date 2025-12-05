package chocan;

public class Provider extends Person {
	
	private String providerNumber;
	
    public Provider(String firstName, String lastName, String phoneNumber, String address, String city, String state, String zipCode, String number){
        super(firstName, lastName, phoneNumber, address, city, state, zipCode);
        providerNumber = number;
    }

    //AMCE Accounting Service Object 

    public boolean checkCard(MemberCard card) {
        //if card bad, return false, if good, return true

        if (card.getMemberNumber() == null || card.getMemberNumber().isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }

    public ProviderForm fillForm() {
        return null;
    }
    public String getProviderNumber() {
        return providerNumber;
    }
    
    public String returnInfo() {
    	return firstName + "_" + lastName + "_" + phoneNumber  + "_" + address  + "_" + city + "_" +  state  + "_" + zipCode;
    }

     // pull getSuspendedMembers from ACMEAccountingServices

    public void checkSuspended(ACMEAccountingServices acmeAccounting) {
    	Member[] suspendedMembers = acmeAccounting.getSuspendedMembers();
    	for(int i = 0; i < suspendedMembers.length; i++) {
    		if(suspendedMembers[i].getCard().getMemberNumber() == this.getProviderNumber()) {
    			//provider is suspended
    		}
    	}
    }

    public ProviderDirectory requestProviderDirectory() {
        // TODO Auto-generated method stub
        return null;
    }

    public ServiceRecord requestServiceRecord(String providerNumber, String startDate, String endDate) {
        // TODO Auto-generated method stub
        return null;
    }

    public SummaryReport requestSummaryReport(String startDate, String endDate) {
        // TODO Auto-generated method stub
        return null;
    }

}
