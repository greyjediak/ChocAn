package chocan;

import java.util.*;

public class DataCenter extends readAndWritable{
    private String[] ServiceNames;
    private int[] ServiceCodes;
    private String[] ServiceFees;

    private Vector<Member> members = new Vector<>();
    private Vector<Provider> providers = new Vector<>();

    private ProviderForm[] WeeklyProviderForms;
    private MemberServiceReport[] AllMemberServiceReport;
    
    public DataCenter() {
    	
    	members = readMembers("members.txt");
    	providers = readProviders("members.txt");
 
    }
    
    public void saveInfo() {
    	
    	 writeInfo("members.txt");
    	 writeInfo("providers.txt");
    	 
    }
    
    public void writeInfo(String fileName) {
    	
    	if(fileName == "members.txt") {
    		writeMember("src/chocan/" + fileName, members);
    	}
    	
    	if(fileName == "providers.txt") {
    		writeProvider("src/chocan/" + fileName, providers);	
    	}
    	
    }
    
}
