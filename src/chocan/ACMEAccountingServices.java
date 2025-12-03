package chocan;

import java.util.Vector;

public class ACMEAccountingServices extends readAndWritable{
	
	private Vector<Member> members = new Vector<>();
	private Vector<Member> suspendedMembers = new Vector<>(); //ACME's list of suspended members, should talk to VerifyMember
    
    public ACMEAccountingServices() {
    	
    	members = readMembers("members.txt");
        suspendedMembers = readMembers("suspendedembers.txt");  
    	
    }
    

    public void addMember(String firstName, String lastName, String phoneNumber, String address, String city, String state, String zipCode, String email, String number) {
    	members.add(new Member(firstName, lastName, phoneNumber, address, city, state, zipCode, email, number));
    }
    
    public void addMember(Member newMember) {
    	members.add(newMember);
    }
    
    public Member[] getMembers() {
    	return members.toArray(new Member[members.size()]);
    }
    
    public Member[] getSuspendedMembers() {
    	return suspendedMembers.toArray(new Member[suspendedMembers.size()]);
    }
    
    public void suspendMember() { 
    	for(int i = 0; i < members.size(); i++) {
    		
    	}
    }
    
    public void unsuspendMember(String number) {
    	
    }
    
    public void saveInfo() {
    	
    	 writeInfo("members.txt");
    	 writeInfo("suspendedmembers.txt");
    }
    
    public void writeInfo(String fileName) {
    	
    	Vector<Member> currMembers = null;
    	
    	if(fileName == "members.txt") {
    		currMembers = members;
    		writeMember("src/chocan/" + fileName, currMembers);
    	}
    	
    	if(fileName == "suspendedmembers.txt") {
    		currMembers = suspendedMembers;
    		writeMember("src/chocan/" + fileName, currMembers);
    	}
    }
}
