package chocan;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Member class
 * Written by Wheeler Knight on 12/4/2025
 */
public class MemberTest {
    
    private Member member;
    
    @Before
    public void setUp() {
        member = new Member("John", "Doe", "555-555-5555", "123 Main St", 
                           "Tuscaloosa", "Alabama", "35405", "john@email.com", "12345");
    }
    
    // ==================== Constructor Tests ====================
    
    @Test
    public void testMemberCreation() {
        assertNotNull("Member should not be null after creation", member);
    }
    
    @Test
    public void testMemberFirstName() {
        assertEquals("First name should be John", "John", member.getFirstName());
    }
    
    @Test
    public void testMemberLastName() {
        assertEquals("Last name should be Doe", "Doe", member.getLastName());
    }
    
    @Test
    public void testMemberFullName() {
        assertEquals("Full name should be John Doe", "John Doe", member.getFullName());
    }
    
    @Test
    public void testMemberPhoneNumber() {
        assertEquals("Phone should be 555-555-5555", "555-555-5555", member.getPhoneNumber());
    }
    
    @Test
    public void testMemberAddress() {
        assertEquals("Address should be 123 Main St", "123 Main St", member.getAddress());
    }
    
    @Test
    public void testMemberCity() {
        assertEquals("City should be Tuscaloosa", "Tuscaloosa", member.getCity());
    }
    
    @Test
    public void testMemberState() {
        assertEquals("State should be Alabama", "Alabama", member.getState());
    }
    
    @Test
    public void testMemberZipCode() {
        assertEquals("Zip code should be 35405", "35405", member.getZipCode());
    }
    
    @Test
    public void testMemberEmail() {
        assertEquals("Email should be john@email.com", "john@email.com", member.getEmail());
    }
    
    // ==================== MemberCard Tests ====================
    
    @Test
    public void testMemberCardNotNull() {
        assertNotNull("Member card should not be null", member.getCard());
    }
    
    @Test
    public void testMemberCardNumber() {
        assertEquals("Member card number should be 12345", "12345", member.getCard().getMemberNumber());
    }
    
    @Test
    public void testMemberCardFirstName() {
        assertEquals("Card first name should match member", "John", member.getCard().getFirstName());
    }
    
    @Test
    public void testMemberCardLastName() {
        assertEquals("Card last name should match member", "Doe", member.getCard().getLastName());
    }
    
    // ==================== Setter Tests ====================
    
    @Test
    public void testSetFirstName() {
        member.setFirstName("Jane");
        assertEquals("First name should be updated to Jane", "Jane", member.getFirstName());
    }
    
    @Test
    public void testSetLastName() {
        member.setLastName("Smith");
        assertEquals("Last name should be updated to Smith", "Smith", member.getLastName());
    }
    
    @Test
    public void testSetEmail() {
        member.setEmail("newemail@test.com");
        assertEquals("Email should be updated", "newemail@test.com", member.getEmail());
    }
    
    @Test
    public void testSetAddress() {
        member.setAddress("456 New St");
        assertEquals("Address should be updated", "456 New St", member.getAddress());
    }
    
    @Test
    public void testSetCity() {
        member.setCity("Birmingham");
        assertEquals("City should be updated", "Birmingham", member.getCity());
    }
    
    @Test
    public void testSetState() {
        member.setState("Georgia");
        assertEquals("State should be updated", "Georgia", member.getState());
    }
    
    @Test
    public void testSetZipCode() {
        member.setZipCode("30301");
        assertEquals("Zip code should be updated", "30301", member.getZipCode());
    }
    
    // ==================== returnInfo Tests ====================
    
    @Test
    public void testReturnInfo() {
        String info = member.returnInfo();
        assertNotNull("returnInfo should not return null", info);
        assertTrue("returnInfo should contain first name", info.contains("John"));
        assertTrue("returnInfo should contain last name", info.contains("Doe"));
        assertTrue("returnInfo should contain member number", info.contains("12345"));
    }
    
    @Test
    public void testReturnInfoFormat() {
        String info = member.returnInfo();
        String[] parts = info.split("_");
        assertEquals("returnInfo should have 9 parts separated by underscore", 9, parts.length);
    }
}

