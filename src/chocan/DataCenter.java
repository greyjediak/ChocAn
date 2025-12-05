package chocan;

import java.util.*;

public class DataCenter extends readAndWritable{
	public final String[] SERVICE_NAMES = {"CONSULTATION", "CONVERSATION", "EMERGENCY"};
	public final Double[] SERVICE_FEE_RATES = {12.99, 8.99, 29.99};

    private Vector<Member> members = new Vector<>();
    private Vector<Member> suspendedMembers = new Vector<>();
    private Vector<Provider> providers = new Vector<>();
    private Vector<Manager> managers = new Vector<>();
    private Vector<Operator> operators = new Vector<>();

    // Edited by Wheeler Knight on 12/4/2025 - Initialized Vector fields to prevent NullPointerException
    private Vector<ProviderForm> weeklyProviderForms = new Vector<>();
    private Vector<MemberServiceReport> allMemberServiceReports = new Vector<>();
    private Vector<ServiceRequest> pendingServiceRequest = new Vector<>();
    private Vector<ServiceRecord> serviceRecords = new Vector<>();
    
    // Edited by Wheeler Knight on 12/5/2025 - Switched to JSON file format using Gson
    // Edited by Wheeler Knight on 12/5/2025 - Added Operator support
    public DataCenter() {
    	Vector<Member> m = readMembers("members.json");
    	if (m != null) members = m;
    	
    	Vector<Member> sm = readMembers("suspendedmembers.json");
    	if (sm != null) suspendedMembers = sm;
    	
    	Vector<Provider> p = readProviders("providers.json");
    	if (p != null) providers = p;
    	
    	Vector<Manager> mg = readManagers("managers.json");
    	if (mg != null) managers = mg;
    	
    	Vector<Operator> op = readOperators("operators.json");
    	if (op != null) operators = op;
    	
    	// Load service records from JSON file
    	Vector<ServiceRecord> sr = readServiceRecords("servicerecords.json");
    	if (sr != null) serviceRecords = sr;
    }
    
    // Edited by Wheeler Knight on 12/5/2025 - Added operators persistence
    public void saveInfo() {
    	 writeInfo("members.json");
    	 writeInfo("suspendedmembers.json");
    	 writeInfo("providers.json");
    	 writeInfo("managers.json");
    	 writeInfo("operators.json");
    	 writeInfo("servicerecords.json");
    }
    
    // Edited by Wheeler Knight on 12/5/2025 - Added operators write support
    public void writeInfo(String fileName) {
    	if(fileName.equals("members.json")) {
    		writeMember(fileName, members);
    	}
    	
    	if(fileName.equals("providers.json")) {
    		writeProvider(fileName, providers);	
    	}
    	
    	if(fileName.equals("managers.json")) {
    		writeManager(fileName, managers);	
    	}
    	
    	if(fileName.equals("suspendedmembers.json")) {
    		writeMember(fileName, suspendedMembers);
    	}
    	
    	if(fileName.equals("operators.json")) {
    		writeOperator(fileName, operators);
    	}
    	
    	if(fileName.equals("servicerecords.json")) {
    		writeServiceRecords(fileName, serviceRecords);
    	}
    }
    
    // ==================== GETTERS ====================
    
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
    
    public Operator[] getOperators() {
    	return operators.toArray(new Operator[operators.size()]);
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
    
    // ==================== MEMBER CRUD ====================
    
    public void addMember(String firstName, String lastName, String phoneNumber, String address, 
                         String city, String state, String zipCode, String email, String number) {
    	members.add(new Member(firstName, lastName, phoneNumber, address, city, state, zipCode, email, number));
    	System.out.println("Member added: " + firstName + " " + lastName);
    }
    
    // Written by Wheeler Knight on 12/5/2025 - Edit member by number
    public boolean editMember(String memberNumber, String firstName, String lastName, String phoneNumber, 
                             String address, String city, String state, String zipCode, String email) {
        for (int i = 0; i < members.size(); i++) {
            Member m = members.get(i);
            if (m.getCard().getMemberNumber().equals(memberNumber)) {
                // Create new member with updated info (keeping same number)
                members.set(i, new Member(firstName, lastName, phoneNumber, address, city, state, zipCode, email, memberNumber));
                System.out.println("Member updated: " + firstName + " " + lastName);
                return true;
            }
        }
        System.out.println("Member not found with number: " + memberNumber);
        return false;
    }
    
    // Written by Wheeler Knight on 12/5/2025 - Delete member by number
    public boolean deleteMember(String memberNumber) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getCard().getMemberNumber().equals(memberNumber)) {
                Member removed = members.remove(i);
                System.out.println("Member deleted: " + removed.getFullName());
                return true;
            }
        }
        System.out.println("Member not found with number: " + memberNumber);
        return false;
    }
    
    // Written by Wheeler Knight on 12/5/2025 - Suspend member
    public boolean suspendMember(String memberNumber) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getCard().getMemberNumber().equals(memberNumber)) {
                Member m = members.remove(i);
                suspendedMembers.add(m);
                System.out.println("Member suspended: " + m.getFullName());
                return true;
            }
        }
        System.out.println("Member not found with number: " + memberNumber);
        return false;
    }
    
    // Written by Wheeler Knight on 12/5/2025 - Unsuspend member
    public boolean unsuspendMember(String memberNumber) {
        for (int i = 0; i < suspendedMembers.size(); i++) {
            if (suspendedMembers.get(i).getCard().getMemberNumber().equals(memberNumber)) {
                Member m = suspendedMembers.remove(i);
                members.add(m);
                System.out.println("Member unsuspended: " + m.getFullName());
                return true;
            }
        }
        System.out.println("Suspended member not found with number: " + memberNumber);
        return false;
    }
    
    // ==================== PROVIDER CRUD ====================
    
    public void addProvider(String firstName, String lastName, String phoneNumber, String address, 
                           String city, String state, String zipCode, String number) {
        providers.add(new Provider(firstName, lastName, phoneNumber, address, city, state, zipCode, number));
        System.out.println("Provider added: " + firstName + " " + lastName);
    }
    
    // Written by Wheeler Knight on 12/5/2025 - Edit provider by number
    public boolean editProvider(String providerNumber, String firstName, String lastName, String phoneNumber, 
                               String address, String city, String state, String zipCode) {
        for (int i = 0; i < providers.size(); i++) {
            Provider p = providers.get(i);
            if (p.getProviderNumber().equals(providerNumber)) {
                providers.set(i, new Provider(firstName, lastName, phoneNumber, address, city, state, zipCode, providerNumber));
                System.out.println("Provider updated: " + firstName + " " + lastName);
                return true;
            }
        }
        System.out.println("Provider not found with number: " + providerNumber);
        return false;
    }
    
    // Written by Wheeler Knight on 12/5/2025 - Delete provider by number
    public boolean deleteProvider(String providerNumber) {
        for (int i = 0; i < providers.size(); i++) {
            if (providers.get(i).getProviderNumber().equals(providerNumber)) {
                Provider removed = providers.remove(i);
                System.out.println("Provider deleted: " + removed.getFullName());
                return true;
            }
        }
        System.out.println("Provider not found with number: " + providerNumber);
        return false;
    }
    
    // ==================== SERVICE REQUESTS ====================
    
    public void addPendingServiceRequest(Member member, String providerName, String serviceType) {
    	pendingServiceRequest.add(new ServiceRequest(member, providerName, serviceType));
    }
    
    public void removePendingServiceRequest(int index) {
    	if (index >= 0 && index < pendingServiceRequest.size()) {
    		pendingServiceRequest.remove(index);
    	}
    }
    
    // ==================== VALIDATION ====================
    
    public boolean validMember(String name, String number) {
    	for(int i = 0;  i < members.size(); i++) {
    		if((members.get(i).getFirstName() + " " + members.get(i).getLastName()).equals(name) && 
    		   members.get(i).getCard().getMemberNumber().equals(number)) return true;
    	}
		return false;
    }
    
    // Written by Wheeler Knight on 12/5/2025 - Check if member is valid (not suspended)
    public String validateMemberCard(String memberNumber) {
        // Check if member exists and is active
        for (Member m : members) {
            if (m.getCard().getMemberNumber().equals(memberNumber)) {
                return "VALID";
            }
        }
        // Check if member is suspended
        for (Member m : suspendedMembers) {
            if (m.getCard().getMemberNumber().equals(memberNumber)) {
                return "SUSPENDED";
            }
        }
        return "INVALID";
    }
    
    public boolean validProvider(String name, String number) {
    	for(int i = 0;  i < providers.size(); i++) {
    		if((providers.get(i).getFirstName() + " " + providers.get(i).getLastName()).equals(name) && 
    		   providers.get(i).getProviderNumber().equals(number)) return true;
    	}
		return false;
    }
    
    public boolean validManager(String name, String number) {
    	for(int i = 0;  i < managers.size(); i++) {
    		if((managers.get(i).getFirstName() + " " + managers.get(i).getLastName()).equals(name) && 
    		   managers.get(i).getManagerNumber().equals(number)) return true;
    	}
		return false;
    }
    
    // Written by Wheeler Knight on 12/5/2025 - Operator validation
    public boolean validOperator(String name, String number) {
    	for(int i = 0;  i < operators.size(); i++) {
    		if((operators.get(i).getFirstName() + " " + operators.get(i).getLastName()).equals(name) && 
    		   operators.get(i).getOperatorNumber().equals(number)) return true;
    	}
		return false;
    }
    
    // Written by Wheeler Knight on 12/5/2025 - Get operator by name and number
    public Operator getOperator(String name, String number) {
        for (Operator op : operators) {
            if (op.getFullName().equals(name) && op.getOperatorNumber().equals(number)) {
                return op;
            }
        }
        return null;
    }
    
    // ==================== LOOKUP METHODS ====================
    
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
    
    public Member getMemberByNumber(String memberNumber) {
        for (Member m : members) {
            if (m.getCard().getMemberNumber().equals(memberNumber)) {
                return m;
            }
        }
        return null;
    }
    
    public double getServiceFeeByCode(int code) {
        if (code >= 1 && code <= SERVICE_FEE_RATES.length) {
            return SERVICE_FEE_RATES[code - 1];
        }
        return 0.0;
    }
    
    // ==================== SERVICE RECORDS ====================
    
    public void addServiceRecord(ServiceRecord record) {
        serviceRecords.add(record);
    }
    
    public java.util.List<ServiceRecord> getServiceRecords() {
        return new java.util.ArrayList<>(serviceRecords);
    }
    
    // Written by Wheeler Knight on 12/5/2025 - Get service records for a specific provider
    public java.util.List<ServiceRecord> getServiceRecordsForProvider(String providerNumber) {
        java.util.List<ServiceRecord> result = new java.util.ArrayList<>();
        for (ServiceRecord sr : serviceRecords) {
            if (sr.getProviderNumber().equals(providerNumber)) {
                result.add(sr);
            }
        }
        return result;
    }
    
    // Written by Wheeler Knight on 12/5/2025 - Get service records for a specific member
    public java.util.List<ServiceRecord> getServiceRecordsForMember(String memberNumber) {
        java.util.List<ServiceRecord> result = new java.util.ArrayList<>();
        for (ServiceRecord sr : serviceRecords) {
            if (sr.getMemberNumber().equals(memberNumber)) {
                result.add(sr);
            }
        }
        return result;
    }
    
    // Written by Wheeler Knight on 12/5/2025 - Check if member number already exists
    public boolean memberNumberExists(String number) {
        for (Member m : members) {
            if (m.getCard().getMemberNumber().equals(number)) return true;
        }
        for (Member m : suspendedMembers) {
            if (m.getCard().getMemberNumber().equals(number)) return true;
        }
        return false;
    }
    
    // Written by Wheeler Knight on 12/5/2025 - Check if provider number already exists
    public boolean providerNumberExists(String number) {
        for (Provider p : providers) {
            if (p.getProviderNumber().equals(number)) return true;
        }
        return false;
    }
}
