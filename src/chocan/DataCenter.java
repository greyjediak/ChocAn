package chocan;

import java.util.*;

public class DataCenter extends readAndWritable{
	public final String[] SERVICENAMES = {"CONSULTATION", "CONVERSATION", "EMERGENCY"};
	public final Double[] ServiceFeesRate = {12.99, 8.99, 29.99};

    private Vector<Member> members = new Vector<>();
    private Vector<Member> suspendedMembers = new Vector<>();
    private Vector<Provider> providers = new Vector<>();
    private Vector<Manager> managers = new Vector<>();

    private Vector<ProviderForm> weeklyProviderForms;
    private Vector<MemberServiceReport> allMemberServiceReports;
    
    public DataCenter() {
    	
    	members = readMembers("members.txt");
    	suspendedMembers = readMembers("suspendedmembers.txt");
    	providers = readProviders("provider.txt");
    	managers = readManagers("manager.txt");
    
    }
    
    public void saveInfo() {
    	
    	 writeInfo("members.txt");
    	 writeInfo("suspendedmembers.txt");
    	 writeInfo("providers.txt");
    	 writeInfo("manager.txt");
    	 
    }
    
    public void writeInfo(String fileName) {
    	
    	if(fileName == "members.txt") {
    		writeMember("src/chocan/" + fileName, members);
    	}
    	
    	if(fileName == "providers.txt") {
    		writeProvider("src/chocan/" + fileName, providers);	
    	}
    	
    	if(fileName == "managers.txt") {
    		writeManager("src/chocan/" + fileName, managers);	
    	}
    	if(fileName == "suspendedmembers.txt") {
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
    
    public void addMember(String firstName, String lastName, String phoneNumber, String address, String city, String state, String zipCode, String email, String number) {
    	members.add(new Member(firstName, lastName, phoneNumber, address, city, state, zipCode, email, number));
    }
    
    public boolean validMember(String name, String number) {
    	for(int i = 0;  i < members.size(); i++) {
    		if((members.get(i).getFirstName() + " " + members.get(i).getLastName()) == name && members.get(i).getCard().getMemberNumber() == number) return true;
    	}
		return false;
    }
    public boolean validProvider(String name, String number) {
    	for(int i = 0;  i < providers.size(); i++) {
    		if((providers.get(i).getFirstName() + " " + providers.get(i).getLastName()) == name && providers.get(i).getProviderNumber() == number) return true;
    	}
		return false;
    }
    
    public boolean validManager(String name, String number) {
    	for(int i = 0;  i < managers.size(); i++) {
    		if((managers.get(i).getFirstName() + " " + managers.get(i).getLastName()) == name && managers.get(i).getManagerNumber() == number) return true;
    	}
		return false;
    }
    
}
