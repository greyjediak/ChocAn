package chocan;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Manager class
 * Written by Wheeler Knight on 12/4/2025
 */
public class ManagerTest {
    
    private Manager manager;
    
    @Before
    public void setUp() {
        manager = new Manager("Admin", "User", "555-555-5555", "123 Admin St", 
                             "AdminCity", "AS", "99999", "MGR001");
    }
    
    // ==================== Constructor Tests ====================
    
    @Test
    public void testManagerCreation() {
        assertNotNull("Manager should not be null after creation", manager);
    }
    
    @Test
    public void testManagerFirstName() {
        assertEquals("First name should be Admin", "Admin", manager.getFirstName());
    }
    
    @Test
    public void testManagerLastName() {
        assertEquals("Last name should be User", "User", manager.getLastName());
    }
    
    @Test
    public void testManagerFullName() {
        assertEquals("Full name should be Admin User", "Admin User", manager.getFullName());
    }
    
    @Test
    public void testManagerNumber() {
        assertEquals("Manager number should be MGR001", "MGR001", manager.getManagerNumber());
    }
    
    @Test
    public void testManagerPhoneNumber() {
        assertEquals("Phone should be 555-555-5555", "555-555-5555", manager.getPhoneNumber());
    }
    
    @Test
    public void testManagerAddress() {
        assertEquals("Address should be 123 Admin St", "123 Admin St", manager.getAddress());
    }
    
    @Test
    public void testManagerCity() {
        assertEquals("City should be AdminCity", "AdminCity", manager.getCity());
    }
    
    @Test
    public void testManagerState() {
        assertEquals("State should be AS", "AS", manager.getState());
    }
    
    @Test
    public void testManagerZipCode() {
        assertEquals("Zip code should be 99999", "99999", manager.getZipCode());
    }
    
    // ==================== returnInfo Tests ====================
    
    @Test
    public void testReturnInfo() {
        String info = manager.returnInfo();
        assertNotNull("returnInfo should not return null", info);
        assertTrue("returnInfo should contain first name", info.contains("Admin"));
        assertTrue("returnInfo should contain last name", info.contains("User"));
    }
    
    @Test
    public void testReturnInfoFormat() {
        String info = manager.returnInfo();
        String[] parts = info.split("_");
        assertEquals("returnInfo should have 7 parts separated by underscore", 7, parts.length);
    }
    
    // ==================== Setter Tests ====================
    
    @Test
    public void testSetFirstName() {
        manager.setFirstName("Super");
        assertEquals("First name should be updated to Super", "Super", manager.getFirstName());
    }
    
    @Test
    public void testSetLastName() {
        manager.setLastName("Admin");
        assertEquals("Last name should be updated to Admin", "Admin", manager.getLastName());
    }
    
    @Test
    public void testSetAddress() {
        manager.setAddress("456 Manager Blvd");
        assertEquals("Address should be updated", "456 Manager Blvd", manager.getAddress());
    }
    
    @Test
    public void testSetCity() {
        manager.setCity("NewCity");
        assertEquals("City should be updated", "NewCity", manager.getCity());
    }
    
    @Test
    public void testSetState() {
        manager.setState("NC");
        assertEquals("State should be updated", "NC", manager.getState());
    }
    
    @Test
    public void testSetZipCode() {
        manager.setZipCode("11111");
        assertEquals("Zip code should be updated", "11111", manager.getZipCode());
    }
}

