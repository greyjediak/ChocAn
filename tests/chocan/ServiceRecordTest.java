package chocan;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;

/**
 * Unit tests for ServiceRecord class
 * Written by Wheeler Knight on 12/4/2025
 */
public class ServiceRecordTest {
    
    private ServiceRecord record;
    private LocalDate testDate;
    
    @Before
    public void setUp() {
        testDate = LocalDate.of(2025, 12, 4);
        record = new ServiceRecord("P001", "M001", "1", 12.99, testDate);
    }
    
    // ==================== Constructor Tests ====================
    
    @Test
    public void testServiceRecordCreation() {
        assertNotNull("ServiceRecord should not be null after creation", record);
    }
    
    @Test
    public void testProviderNumber() {
        assertEquals("Provider number should be P001", "P001", record.getProviderNumber());
    }
    
    @Test
    public void testMemberNumber() {
        assertEquals("Member number should be M001", "M001", record.getMemberNumber());
    }
    
    @Test
    public void testServiceCode() {
        assertEquals("Service code should be 1", "1", record.getServiceCode());
    }
    
    @Test
    public void testServiceFee() {
        assertEquals("Service fee should be 12.99", 12.99, record.getServiceFee(), 0.01);
    }
    
    @Test
    public void testServiceDate() {
        assertEquals("Service date should match", testDate, record.getServiceDate());
    }
    
    // ==================== Different Service Codes ====================
    
    @Test
    public void testServiceCodeConsultation() {
        ServiceRecord consultRecord = new ServiceRecord("P001", "M001", "1", 12.99, testDate);
        assertEquals("Consultation code should be 1", "1", consultRecord.getServiceCode());
    }
    
    @Test
    public void testServiceCodeConversation() {
        ServiceRecord convRecord = new ServiceRecord("P001", "M001", "2", 8.99, testDate);
        assertEquals("Conversation code should be 2", "2", convRecord.getServiceCode());
    }
    
    @Test
    public void testServiceCodeEmergency() {
        ServiceRecord emergRecord = new ServiceRecord("P001", "M001", "3", 29.99, testDate);
        assertEquals("Emergency code should be 3", "3", emergRecord.getServiceCode());
    }
    
    // ==================== Null Date Test ====================
    
    @Test
    public void testNullDate() {
        ServiceRecord nullDateRecord = new ServiceRecord("P001", "M001", "1", 12.99, null);
        assertNull("Service date can be null", nullDateRecord.getServiceDate());
    }
    
    // ==================== Different Fee Values ====================
    
    @Test
    public void testZeroFee() {
        ServiceRecord zeroFeeRecord = new ServiceRecord("P001", "M001", "1", 0.0, testDate);
        assertEquals("Fee can be zero", 0.0, zeroFeeRecord.getServiceFee(), 0.01);
    }
    
    @Test
    public void testLargeFee() {
        ServiceRecord largeFeeRecord = new ServiceRecord("P001", "M001", "1", 999.99, testDate);
        assertEquals("Large fee should be stored correctly", 999.99, largeFeeRecord.getServiceFee(), 0.01);
    }
}

