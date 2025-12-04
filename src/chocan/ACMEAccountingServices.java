package chocan;

public class ACMEAccountingServices{
	
	private Member[] members;
	private Member[] suspendedMembers; //ACME's list of suspended members, should talk to VerifyMember
    
    public ACMEAccountingServices(Member[] members, Member[] suspendedMembers) {
    	
    	this.members = members;
    	this.suspendedMembers = suspendedMembers;
    	
    }
    
<<<<<<< HEAD
    public void setMembers(Member[] members) {
    	this.members = members;
=======

    public void addMember(String firstName, String lastName, String phoneNumber, String address, String city, String state, String zipCode, String email, String number) {
    	members.add(new Member(firstName, lastName, phoneNumber, address, city, state, zipCode, email, number));
>>>>>>> 690588c0fb117292166d5d821f7878aec369dc9c
    }
    
    
    public Member[] getMembers() {
    	return members;
    }
    
    public Member[] getSuspendedMembers() {
    	return suspendedMembers;
    }
    
<<<<<<< HEAD
    public void setSuspendedMembers(Member[] members) {
    	this.members = members;
    }
    
    public void suspendMember(String number) {
    	for(int i = 0; i < members.length; i++) {
    		if(members[i].getCard().getMemberNumber() == number) ;
=======
    public void suspendMember() { 
    	for(int i = 0; i < members.size(); i++) {
    		
>>>>>>> 690588c0fb117292166d5d821f7878aec369dc9c
    	}
    }
    
    public void unsuspendMember(String number) {
    	
    }
 
}
