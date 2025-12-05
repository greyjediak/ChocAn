package chocan;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for ACMEAccountingServices class
 * Written by Wheeler Knight on 12/4/2025
 */
public class ACMEAccountingServicesTest {
    
    private ACMEAccountingServices accounting;
    private Member[] members;
    private Member[] suspendedMembers;
    
    @Before
    public void setUp() {
        // Create test members
        members = new Member[] {
            new Member("John", "Doe", "555-1111", "123 St", "City", "ST", "12345", "john@test.com", "M001"),
            new Member("Jane", "Smith", "555-2222", "456 Ave", "Town", "ST", "12346", "jane@test.com", "M002")
        };
        
        suspendedMembers = new Member[] {
            new Member("Bob", "Suspended", "555-3333", "789 Rd", "Village", "ST", "12347", "bob@test.com", "M003")
        };
        
        accounting = new ACMEAccountingServices(members, suspendedMembers);
    }
    
    // ==================== Initialization Tests ====================
    
    @Test
    public void testAccountingServicesCreation() {
        assertNotNull("ACMEAccountingServices should not be null", accounting);
    }
    
    @Test
    public void testGetMembers() {
        Member[] result = accounting.getMembers();
        assertNotNull("getMembers should not return null", result);
        assertEquals("Should have 2 members", 2, result.length);
    }
    
    @Test
    public void testGetSuspendedMembers() {
        Member[] result = accounting.getSuspendedMembers();
        assertNotNull("getSuspendedMembers should not return null", result);
        assertEquals("Should have 1 suspended member", 1, result.length);
    }
    
    // ==================== Add Member Tests ====================
    
    @Test
    public void testAddMembers() {
        int initialCount = accounting.getMembers().length;
        Member newMember = new Member("New", "Member", "555-4444", "New St", "New City", "NC", "11111", "new@test.com", "M004");
        accounting.AddMembers(newMember);
        assertEquals("Member count should increase by 1", initialCount + 1, accounting.getMembers().length);
    }
    
    @Test
    public void testAddMembersNull() {
        int initialCount = accounting.getMembers().length;
        accounting.AddMembers(null);
        assertEquals("Member count should not change when adding null", initialCount, accounting.getMembers().length);
    }
    
    @Test
    public void testAddDuplicateMember() {
        int initialCount = accounting.getMembers().length;
        // Try to add member with same number as existing member
        Member duplicate = new Member("Dup", "Member", "555-5555", "Dup St", "Dup City", "DC", "22222", "dup@test.com", "M001");
        accounting.AddMembers(duplicate);
        assertEquals("Member count should not change when adding duplicate", initialCount, accounting.getMembers().length);
    }
    
    // ==================== Suspend Member Tests ====================
    
    @Test
    public void testSuspendMember() {
        int initialMemberCount = accounting.getMembers().length;
        int initialSuspendedCount = accounting.getSuspendedMembers().length;
        
        accounting.suspendMember("M001");
        
        assertEquals("Member count should decrease by 1", initialMemberCount - 1, accounting.getMembers().length);
        assertEquals("Suspended count should increase by 1", initialSuspendedCount + 1, accounting.getSuspendedMembers().length);
    }
    
    @Test
    public void testSuspendNonexistentMember() {
        int initialMemberCount = accounting.getMembers().length;
        int initialSuspendedCount = accounting.getSuspendedMembers().length;
        
        accounting.suspendMember("NONEXISTENT");
        
        assertEquals("Member count should not change", initialMemberCount, accounting.getMembers().length);
        assertEquals("Suspended count should not change", initialSuspendedCount, accounting.getSuspendedMembers().length);
    }
    
    // ==================== Unsuspend Member Tests ====================
    
    @Test
    public void testUnsuspendMember() {
        int initialMemberCount = accounting.getMembers().length;
        int initialSuspendedCount = accounting.getSuspendedMembers().length;
        
        accounting.unsuspendMember("M003");
        
        assertEquals("Member count should increase by 1", initialMemberCount + 1, accounting.getMembers().length);
        assertEquals("Suspended count should decrease by 1", initialSuspendedCount - 1, accounting.getSuspendedMembers().length);
    }
    
    @Test
    public void testUnsuspendNonexistentMember() {
        int initialMemberCount = accounting.getMembers().length;
        int initialSuspendedCount = accounting.getSuspendedMembers().length;
        
        accounting.unsuspendMember("NONEXISTENT");
        
        assertEquals("Member count should not change", initialMemberCount, accounting.getMembers().length);
        assertEquals("Suspended count should not change", initialSuspendedCount, accounting.getSuspendedMembers().length);
    }
    
    // ==================== Validation Toggle Tests ====================
    
    @Test
    public void testToggleValidation() {
        // Just verify it doesn't throw an exception
        accounting.ToggleValidation();
        accounting.ToggleValidation();
        // If we reach here without exception, test passes
        assertTrue(true);
    }
    
    // ==================== Setter Tests ====================
    
    @Test
    public void testSetMembers() {
        Member[] newMembers = new Member[] {
            new Member("New", "One", "555-6666", "St", "City", "ST", "11111", "one@test.com", "N001")
        };
        accounting.setMembers(newMembers);
        assertEquals("Should have 1 member after setMembers", 1, accounting.getMembers().length);
    }
    
    @Test
    public void testSetSuspendedMembers() {
        Member[] newSuspended = new Member[] {
            new Member("Sus", "One", "555-7777", "St", "City", "ST", "11111", "sus1@test.com", "S001"),
            new Member("Sus", "Two", "555-8888", "St", "City", "ST", "11111", "sus2@test.com", "S002")
        };
        accounting.setSuspendedMembers(newSuspended);
        assertEquals("Should have 2 suspended members after setSuspendedMembers", 2, accounting.getSuspendedMembers().length);
    }
}

