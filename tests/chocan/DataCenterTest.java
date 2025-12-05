package chocan;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for DataCenter class
 * Written by Wheeler Knight on 12/4/2025
 */
public class DataCenterTest {
    
    private DataCenter dataCenter;
    
    @Before
    public void setUp() {
        dataCenter = new DataCenter();
    }
    
    // ==================== Member Tests ====================
    
    @Test
    public void testGetMembersNotNull() {
        Member[] members = dataCenter.getMembers();
        assertNotNull("getMembers() should not return null", members);
    }
    
    @Test
    public void testAddMember() {
        int initialCount = dataCenter.getMembers().length;
        dataCenter.addMember("Test", "User", "555-555-5555", "123 Test St", 
                            "TestCity", "TS", "12345", "test@test.com", "999");
        int newCount = dataCenter.getMembers().length;
        assertEquals("Member count should increase by 1", initialCount + 1, newCount);
    }
    
    @Test
    public void testValidMemberWithValidCredentials() {
        // Add a test member first
        dataCenter.addMember("John", "Doe", "555-555-5555", "123 Test St", 
                            "TestCity", "TS", "12345", "john@test.com", "100");
        assertTrue("Valid member should return true", 
                   dataCenter.validMember("John Doe", "100"));
    }
    
    @Test
    public void testValidMemberWithInvalidCredentials() {
        assertFalse("Invalid member should return false", 
                    dataCenter.validMember("Nonexistent User", "999"));
    }
    
    @Test
    public void testGetMemberByNumber() {
        dataCenter.addMember("Jane", "Smith", "555-555-5556", "456 Test Ave", 
                            "TestCity", "TS", "12345", "jane@test.com", "101");
        Member member = dataCenter.getMemberByNumber("101");
        assertNotNull("getMemberByNumber should find added member", member);
        assertEquals("Member first name should match", "Jane", member.getFirstName());
    }
    
    @Test
    public void testGetMemberByNumberNotFound() {
        Member member = dataCenter.getMemberByNumber("99999");
        assertNull("getMemberByNumber should return null for non-existent member", member);
    }
    
    // ==================== Provider Tests ====================
    
    @Test
    public void testGetProvidersNotNull() {
        Provider[] providers = dataCenter.getProviders();
        assertNotNull("getProviders() should not return null", providers);
    }
    
    @Test
    public void testAddProvider() {
        int initialCount = dataCenter.getProviders().length;
        dataCenter.addProvider("Dr", "Test", "555-555-5557", "789 Provider St", 
                              "TestCity", "TS", "12345", "P100");
        int newCount = dataCenter.getProviders().length;
        assertEquals("Provider count should increase by 1", initialCount + 1, newCount);
    }
    
    @Test
    public void testValidProviderWithValidCredentials() {
        dataCenter.addProvider("Dr", "Provider", "555-555-5558", "123 Medical St", 
                              "TestCity", "TS", "12345", "P200");
        assertTrue("Valid provider should return true", 
                   dataCenter.validProvider("Dr Provider", "P200"));
    }
    
    @Test
    public void testValidProviderWithInvalidCredentials() {
        assertFalse("Invalid provider should return false", 
                    dataCenter.validProvider("Fake Doctor", "P999"));
    }
    
    @Test
    public void testGetProviderByNumber() {
        dataCenter.addProvider("Dr", "Smith", "555-555-5559", "456 Health Ave", 
                              "TestCity", "TS", "12345", "P300");
        Provider provider = dataCenter.getProviderByNumber("P300");
        assertNotNull("getProviderByNumber should find added provider", provider);
        assertEquals("Provider last name should match", "Smith", provider.getLastName());
    }
    
    @Test
    public void testGetProviderByNumberNotFound() {
        Provider provider = dataCenter.getProviderByNumber("P99999");
        assertNull("getProviderByNumber should return null for non-existent provider", provider);
    }
    
    // ==================== Manager Tests ====================
    
    @Test
    public void testGetManagersNotNull() {
        Manager[] managers = dataCenter.getManagers();
        assertNotNull("getManagers() should not return null", managers);
    }
    
    @Test
    public void testValidManagerWithInvalidCredentials() {
        assertFalse("Invalid manager should return false", 
                    dataCenter.validManager("Fake Manager", "M999"));
    }
    
    // ==================== Service Record Tests ====================
    
    @Test
    public void testAddServiceRecord() {
        java.time.LocalDate today = java.time.LocalDate.now();
        ServiceRecord record = new ServiceRecord("P100", "M100", "1", 12.99, today);
        dataCenter.addServiceRecord(record);
        
        java.util.List<ServiceRecord> records = dataCenter.getServiceRecords();
        assertTrue("Service records should contain added record", records.size() > 0);
    }
    
    @Test
    public void testGetServiceRecordsForLastWeek() {
        java.time.LocalDate today = java.time.LocalDate.now();
        ServiceRecord record = new ServiceRecord("P100", "M100", "1", 12.99, today);
        dataCenter.addServiceRecord(record);
        
        java.util.List<ServiceRecord> weeklyRecords = dataCenter.getServiceRecordsForLastWeek();
        assertTrue("Weekly records should contain today's record", weeklyRecords.size() > 0);
    }
    
    @Test
    public void testGetServiceRecordsForLastWeekExcludesOldRecords() {
        // Add a record from 2 weeks ago
        java.time.LocalDate twoWeeksAgo = java.time.LocalDate.now().minusWeeks(2);
        ServiceRecord oldRecord = new ServiceRecord("P100", "M100", "1", 12.99, twoWeeksAgo);
        dataCenter.addServiceRecord(oldRecord);
        
        // Add a record from today
        java.time.LocalDate today = java.time.LocalDate.now();
        ServiceRecord newRecord = new ServiceRecord("P101", "M101", "2", 8.99, today);
        dataCenter.addServiceRecord(newRecord);
        
        java.util.List<ServiceRecord> weeklyRecords = dataCenter.getServiceRecordsForLastWeek();
        
        // Verify only recent record is included
        boolean hasOldRecord = false;
        for (ServiceRecord sr : weeklyRecords) {
            if (sr.getServiceDate().equals(twoWeeksAgo)) {
                hasOldRecord = true;
            }
        }
        assertFalse("Weekly records should not contain records from 2 weeks ago", hasOldRecord);
    }
    
    // ==================== Service Request Tests ====================
    
    @Test
    public void testGetPendingServiceRequestNotNull() {
        ServiceRequest[] requests = dataCenter.getPendingServiceRequest();
        assertNotNull("getPendingServiceRequest() should not return null", requests);
    }
    
    @Test
    public void testAddPendingServiceRequest() {
        dataCenter.addMember("Request", "Member", "555-555-5560", "123 St", 
                            "City", "ST", "12345", "req@test.com", "R100");
        Member member = dataCenter.getMemberByNumber("R100");
        
        int initialCount = dataCenter.getPendingServiceRequest().length;
        dataCenter.addPendingServiceRequest(member, "Dr Provider", "CONSULTATION");
        int newCount = dataCenter.getPendingServiceRequest().length;
        
        assertEquals("Pending request count should increase by 1", initialCount + 1, newCount);
    }
    
    @Test
    public void testRemovePendingServiceRequest() {
        dataCenter.addMember("Remove", "Member", "555-555-5561", "456 St", 
                            "City", "ST", "12345", "rem@test.com", "R200");
        Member member = dataCenter.getMemberByNumber("R200");
        
        dataCenter.addPendingServiceRequest(member, "Dr Provider", "CONSULTATION");
        int countAfterAdd = dataCenter.getPendingServiceRequest().length;
        
        dataCenter.removePendingServiceRequest(0);
        int countAfterRemove = dataCenter.getPendingServiceRequest().length;
        
        assertTrue("Pending request count should decrease after removal", 
                   countAfterRemove < countAfterAdd);
    }
    
    // ==================== Service Fee Tests ====================
    
    @Test
    public void testGetServiceFeeByCodeConsultation() {
        double fee = dataCenter.getServiceFeeByCode(1);
        assertEquals("Consultation fee should be 12.99", 12.99, fee, 0.01);
    }
    
    @Test
    public void testGetServiceFeeByCodeConversation() {
        double fee = dataCenter.getServiceFeeByCode(2);
        assertEquals("Conversation fee should be 8.99", 8.99, fee, 0.01);
    }
    
    @Test
    public void testGetServiceFeeByCodeEmergency() {
        double fee = dataCenter.getServiceFeeByCode(3);
        assertEquals("Emergency fee should be 29.99", 29.99, fee, 0.01);
    }
    
    @Test
    public void testGetServiceFeeByCodeInvalid() {
        double fee = dataCenter.getServiceFeeByCode(99);
        assertEquals("Invalid code should return 0.0", 0.0, fee, 0.01);
    }
    
    @Test
    public void testServiceNames() {
        assertEquals("First service should be CONSULTATION", "CONSULTATION", dataCenter.SERVICE_NAMES[0]);
        assertEquals("Second service should be CONVERSATION", "CONVERSATION", dataCenter.SERVICE_NAMES[1]);
        assertEquals("Third service should be EMERGENCY", "EMERGENCY", dataCenter.SERVICE_NAMES[2]);
    }
}

