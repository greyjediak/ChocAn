package chocan;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for ServiceRequest class
 * Written by Wheeler Knight on 12/4/2025
 */
public class ServiceRequestTest {
    
    private ServiceRequest request;
    private Member member;
    
    @Before
    public void setUp() {
        member = new Member("John", "Doe", "555-1111", "123 St", "City", "ST", "12345", "john@test.com", "M001");
        request = new ServiceRequest(member, "Dr Smith", "CONSULTATION");
    }
    
    // ==================== Constructor Tests ====================
    
    @Test
    public void testServiceRequestCreation() {
        assertNotNull("ServiceRequest should not be null after creation", request);
    }
    
    @Test
    public void testMember() {
        assertNotNull("Member should not be null", request.member);
        assertEquals("Member should match", member, request.member);
    }
    
    @Test
    public void testProviderName() {
        assertEquals("Provider name should be Dr Smith", "Dr Smith", request.providerName);
    }
    
    @Test
    public void testServiceType() {
        assertEquals("Service type should be CONSULTATION", "CONSULTATION", request.serviceType);
    }
    
    // ==================== getInfo Tests ====================
    
    @Test
    public void testGetInfoNotNull() {
        String info = request.getInfo();
        assertNotNull("getInfo should not return null", info);
    }
    
    @Test
    public void testGetInfoContainsMemberNumber() {
        String info = request.getInfo();
        assertTrue("getInfo should contain member number", info.contains("M001"));
    }
    
    @Test
    public void testGetInfoContainsProviderName() {
        String info = request.getInfo();
        assertTrue("getInfo should contain provider name", info.contains("Dr Smith"));
    }
    
    @Test
    public void testGetInfoContainsServiceType() {
        String info = request.getInfo();
        assertTrue("getInfo should contain service type", info.contains("CONSULTATION"));
    }
    
    @Test
    public void testGetInfoFormat() {
        String info = request.getInfo();
        String[] parts = info.split("_");
        assertEquals("getInfo should have 3 parts separated by underscore", 3, parts.length);
    }
    
    // ==================== Different Service Types ====================
    
    @Test
    public void testConversationServiceType() {
        ServiceRequest convRequest = new ServiceRequest(member, "Dr Jones", "CONVERSATION");
        assertEquals("Service type should be CONVERSATION", "CONVERSATION", convRequest.serviceType);
    }
    
    @Test
    public void testEmergencyServiceType() {
        ServiceRequest emergRequest = new ServiceRequest(member, "Dr Emergency", "EMERGENCY");
        assertEquals("Service type should be EMERGENCY", "EMERGENCY", emergRequest.serviceType);
    }
}

