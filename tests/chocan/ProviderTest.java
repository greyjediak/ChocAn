package chocan;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Provider class
 * Written by Wheeler Knight on 12/4/2025
 */
public class ProviderTest {
    
    private Provider provider;
    
    @Before
    public void setUp() {
        provider = new Provider("Dr", "Smith", "555-555-5555", "456 Medical Dr", 
                               "Birmingham", "Alabama", "35201", "P12345");
    }
    
    // ==================== Constructor Tests ====================
    
    @Test
    public void testProviderCreation() {
        assertNotNull("Provider should not be null after creation", provider);
    }
    
    @Test
    public void testProviderFirstName() {
        assertEquals("First name should be Dr", "Dr", provider.getFirstName());
    }
    
    @Test
    public void testProviderLastName() {
        assertEquals("Last name should be Smith", "Smith", provider.getLastName());
    }
    
    @Test
    public void testProviderFullName() {
        assertEquals("Full name should be Dr Smith", "Dr Smith", provider.getFullName());
    }
    
    @Test
    public void testProviderNumber() {
        assertEquals("Provider number should be P12345", "P12345", provider.getProviderNumber());
    }
    
    @Test
    public void testProviderPhoneNumber() {
        assertEquals("Phone should be 555-555-5555", "555-555-5555", provider.getPhoneNumber());
    }
    
    @Test
    public void testProviderAddress() {
        assertEquals("Address should be 456 Medical Dr", "456 Medical Dr", provider.getAddress());
    }
    
    @Test
    public void testProviderCity() {
        assertEquals("City should be Birmingham", "Birmingham", provider.getCity());
    }
    
    @Test
    public void testProviderState() {
        assertEquals("State should be Alabama", "Alabama", provider.getState());
    }
    
    @Test
    public void testProviderZipCode() {
        assertEquals("Zip code should be 35201", "35201", provider.getZipCode());
    }
    
    // ==================== Password Tests ====================
    
    @Test
    public void testPasswordInitiallyNull() {
        assertNull("Password should be null initially", provider.getPassword());
    }
    
    @Test
    public void testSetPassword() {
        provider.setPassword("secret123");
        assertEquals("Password should be set correctly", "secret123", provider.getPassword());
    }
    
    // ==================== returnInfo Tests ====================
    
    @Test
    public void testReturnInfo() {
        String info = provider.returnInfo();
        assertNotNull("returnInfo should not return null", info);
        assertTrue("returnInfo should contain first name", info.contains("Dr"));
        assertTrue("returnInfo should contain last name", info.contains("Smith"));
        assertTrue("returnInfo should contain provider number", info.contains("P12345"));
    }
    
    @Test
    public void testReturnInfoFormat() {
        String info = provider.returnInfo();
        String[] parts = info.split("_");
        assertEquals("returnInfo should have 8 parts separated by underscore", 8, parts.length);
    }
    
    // ==================== Setter Tests ====================
    
    @Test
    public void testSetFirstName() {
        provider.setFirstName("Doc");
        assertEquals("First name should be updated to Doc", "Doc", provider.getFirstName());
    }
    
    @Test
    public void testSetLastName() {
        provider.setLastName("Jones");
        assertEquals("Last name should be updated to Jones", "Jones", provider.getLastName());
    }
    
    @Test
    public void testSetAddress() {
        provider.setAddress("789 Health Blvd");
        assertEquals("Address should be updated", "789 Health Blvd", provider.getAddress());
    }
    
    @Test
    public void testSetCity() {
        provider.setCity("Montgomery");
        assertEquals("City should be updated", "Montgomery", provider.getCity());
    }
    
    @Test
    public void testSetState() {
        provider.setState("Georgia");
        assertEquals("State should be updated", "Georgia", provider.getState());
    }
    
    @Test
    public void testSetZipCode() {
        provider.setZipCode("36101");
        assertEquals("Zip code should be updated", "36101", provider.getZipCode());
    }
}

