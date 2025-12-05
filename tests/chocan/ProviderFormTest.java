package chocan;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for ProviderForm class
 * Written by Wheeler Knight on 12/4/2025
 */
public class ProviderFormTest {
    
    private ProviderForm form;
    
    @Before
    public void setUp() {
        form = new ProviderForm();
    }
    
    // ==================== No-arg Constructor Tests ====================
    
    @Test
    public void testNoArgConstructor() {
        assertNotNull("ProviderForm should not be null after creation", form);
    }
    
    // ==================== Full Constructor Tests ====================
    
    @Test
    public void testFullConstructor() {
        ProviderForm fullForm = new ProviderForm(
            (byte) 10, (byte) 30, (byte) 45,  // hours, minutes, seconds
            (byte) 4, (byte) 12, (short) 2025, // day, month, year
            "Dr Provider", "P001",             // provider name, number
            "John Doe", "M001",                 // member name, number
            "000001", "CONSULTATION",           // service code, name
            12.99,                              // fee
            "Test comments"                     // comments
        );
        
        assertNotNull("ProviderForm with full constructor should not be null", fullForm);
        assertEquals("Hours should be 10", 10, fullForm.getHours());
        assertEquals("Minutes should be 30", 30, fullForm.getMinutes());
        assertEquals("Provider name should match", "Dr Provider", fullForm.getProviderName());
        assertEquals("Fee should be 12.99", 12.99, fullForm.getFee(), 0.01);
    }
    
    // ==================== Time Setters/Getters ====================
    
    @Test
    public void testSetGetHours() {
        form.setHours((byte) 14);
        assertEquals("Hours should be 14", 14, form.getHours());
    }
    
    @Test
    public void testSetGetMinutes() {
        form.setMinutes((byte) 45);
        assertEquals("Minutes should be 45", 45, form.getMinutes());
    }
    
    @Test
    public void testSetGetSeconds() {
        form.setSeconds((byte) 30);
        assertEquals("Seconds should be 30", 30, form.getSeconds());
    }
    
    // ==================== Date Setters/Getters ====================
    
    @Test
    public void testSetGetDay() {
        form.setDay((byte) 15);
        assertEquals("Day should be 15", 15, form.getDay());
    }
    
    @Test
    public void testSetGetMonth() {
        form.setMonth((byte) 6);
        assertEquals("Month should be 6", 6, form.getMonth());
    }
    
    @Test
    public void testSetGetYear() {
        form.setYear((short) 2025);
        assertEquals("Year should be 2025", 2025, form.getYear());
    }
    
    // ==================== Provider Info Setters/Getters ====================
    
    @Test
    public void testSetGetProviderName() {
        form.setProviderName("Dr Smith");
        assertEquals("Provider name should be Dr Smith", "Dr Smith", form.getProviderName());
    }
    
    @Test
    public void testSetGetProviderNumber() {
        form.setProviderNumber("P123");
        assertEquals("Provider number should be P123", "P123", form.getProviderNumber());
    }
    
    // ==================== Member Info Setters/Getters ====================
    
    @Test
    public void testSetGetMemberName() {
        form.setMemberName("John Doe");
        assertEquals("Member name should be John Doe", "John Doe", form.getMemberName());
    }
    
    @Test
    public void testSetGetMemberNumber() {
        form.setMemberNumber("M123");
        assertEquals("Member number should be M123", "M123", form.getMemberNumber());
    }
    
    // ==================== Service Info Setters/Getters ====================
    
    @Test
    public void testSetGetServiceCode() {
        form.setServiceCode("000001");
        assertEquals("Service code should be 000001", "000001", form.getServiceCode());
    }
    
    @Test
    public void testSetGetServiceName() {
        form.setServiceName("CONSULTATION");
        assertEquals("Service name should be CONSULTATION", "CONSULTATION", form.getServiceName());
    }
    
    @Test
    public void testSetGetFee() {
        form.setFee(29.99);
        assertEquals("Fee should be 29.99", 29.99, form.getFee(), 0.01);
    }
    
    @Test
    public void testSetGetComments() {
        form.setComments("Test comment");
        assertEquals("Comments should match", "Test comment", form.getComments());
    }
    
    // ==================== Legacy Fields ====================
    
    @Test
    public void testSetGetName() {
        form.setName("Test Name");
        assertEquals("Name should be Test Name", "Test Name", form.getName());
    }
    
    @Test
    public void testSetGetNumber() {
        form.setNumber("12345");
        assertEquals("Number should be 12345", "12345", form.getNumber());
    }
    
    // ==================== getInfo Tests ====================
    
    @Test
    public void testGetInfo() {
        form.setName("John Doe");
        form.setNumber("M001");
        form.setHours((byte) 10);
        form.setMinutes((byte) 30);
        form.setSeconds((byte) 0);
        form.setDay((byte) 4);
        form.setMonth((byte) 12);
        form.setYear((short) 2025);
        form.setFee(12.99);
        
        String info = form.getInfo();
        assertNotNull("getInfo should not return null", info);
        assertTrue("getInfo should contain name", info.contains("John Doe"));
        assertTrue("getInfo should contain number", info.contains("M001"));
    }
}

