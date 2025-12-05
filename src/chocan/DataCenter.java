package chocan;

import java.util.*;

public class DataCenter extends readAndWritable{
	public final String[] SERVICE_NAMES = {"CONSULTATION", "CONVERSATION", "EMERGENCY"};
	public final Double[] SERVICE_FEE_RATES = {12.99, 8.99, 29.99};

    private Vector<Member> members = new Vector<>();
    private Vector<Member> suspendedMembers = new Vector<>();
    private Vector<Provider> providers = new Vector<>();
    private Vector<Manager> managers = new Vector<>();

    private Vector<ProviderForm> weeklyProviderForms;
    private Vector<MemberServiceReport> allMemberServiceReports;
    private Vector<ServiceRequest> pendingServiceRequest;
    private Vector<ServiceRecord> serviceRecords = new Vector<>();
    
    public DataCenter() {
    	Vector<Member> m = readMembers("members.txt");
    	if (m != null) members = m;
    	
    	Vector<Member> sm = readMembers("suspendedmembers.txt");
    	if (sm != null) suspendedMembers = sm;
    	
    	Vector<Provider> p = readProviders("provider.txt");
    	if (p != null) providers = p;
    	
    	Vector<Manager> mg = readManagers("manager.txt");
    	if (mg != null) managers = mg;    }
    
    public void saveInfo() {
    	
    	 writeInfo("members.txt");
    	 writeInfo("suspendedmembers.txt");
    	 writeInfo("providers.txt");
    	 writeInfo("manager.txt");
    	 
    }
    
    public void writeInfo(String fileName) {
    	
    	if(fileName.equals("members.txt")) {
    		writeMember("src/chocan/" + fileName, members);
    	}
    	
    	if(fileName.equals("providers.txt")) {
    		writeProvider("src/chocan/" + fileName, providers);	
    	}
    	
    	if(fileName.equals("manager.txt")) {
    		writeManager("src/chocan/" + fileName, managers);	
    	}
    	if(fileName.equals("suspendedmembers.txt")) {
    		writeMember("src/chocan/" + fileName, suspendedMembers);
    	}
    	
    }
    
    public Member[] getMembers() {
    	
    	return members.toArray(new Member[members.size()]);
    	
    }
    
    public Member[] getSuspendedMembers() {
    	
    	return suspendedMembers.toArray(new Member[suspendedMembers.size()]);
    	
    }
    
    public Provider[] getProviders() {
    	
    	return providers.toArray(new Provider[providers.size()]);
    	
    }
    
    public Manager[] getManagers() {
    	
    	return managers.toArray(new Manager[managers.size()]);
    	
    }
    
    public ProviderForm[] getWeeklyProviderForm() {
    	
    	return weeklyProviderForms.toArray(new ProviderForm[weeklyProviderForms.size()]);
    	
    }
    
    public MemberServiceReport[] getAllMemberServiceReport() {
    	
    	return allMemberServiceReports.toArray(new MemberServiceReport[allMemberServiceReports.size()]);
    	
    }
    
    public ServiceRequest[] getPendingServiceRequest() {
    	
    	return pendingServiceRequest.toArray(new ServiceRequest[pendingServiceRequest.size()]);
    	
    }
    
    public void addMember(String firstName, String lastName, String phoneNumber, String address, String city, String state, String zipCode, String email, String number) {
    	members.add(new Member(firstName, lastName, phoneNumber, address, city, state, zipCode, email, number));
    }
    
    public void addPendingServiceRequest(Member member, String providerName, String serviceType) {
    	pendingServiceRequest.add(new ServiceRequest(member, providerName, serviceType));
    }
    
    public boolean validMember(String name, String number) {
    	for(int i = 0;  i < members.size(); i++) {
    		if((members.get(i).getFirstName() + " " + members.get(i).getLastName()).equals(name) && members.get(i).getCard().getMemberNumber().equals(number)) return true;
    	}
		return false;
    }
    public boolean validProvider(String name, String number) {
    	for(int i = 0;  i < providers.size(); i++) {
    		if((providers.get(i).getFirstName() + " " + providers.get(i).getLastName()).equals(name) && providers.get(i).getProviderNumber().equals(number)) return true;
    	}
		return false;
    }
    
    public boolean validManager(String name, String number) {
    	for(int i = 0;  i < managers.size(); i++) {
    		if((managers.get(i).getFirstName() + " " + managers.get(i).getLastName()).equals(name) && managers.get(i).getManagerNumber().equals(number)) return true;
    	}
		return false;
    }
    
    public java.util.List<ServiceRecord> getServiceRecordsForLastWeek() {
        java.time.LocalDate oneWeekAgo = java.time.LocalDate.now().minusWeeks(1);
        java.util.List<ServiceRecord> result = new java.util.ArrayList<>();
        for (ServiceRecord sr : serviceRecords) {
            if (sr.getServiceDate() != null && !sr.getServiceDate().isBefore(oneWeekAgo)) {
                result.add(sr);
            }
        }
        return result;
    }
    
    public Provider getProviderByNumber(String providerNumber) {
        for (Provider p : providers) {
            if (p.getProviderNumber().equals(providerNumber)) {
                return p;
            }
        }
        return null;
    }
    
    public void addServiceRecord(ServiceRecord record) {
        serviceRecords.add(record);
    }
    
    public java.util.List<ServiceRecord> getServiceRecords() {
        return new java.util.ArrayList<>(serviceRecords);
    }
    
    public void addProvider(String firstName, String lastName, String phoneNumber, String address, String city, String state, String zipCode, String number) {
        providers.add(new Provider(firstName, lastName, phoneNumber, address, city, state, zipCode, number));
    }
}
