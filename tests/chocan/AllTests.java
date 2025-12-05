package chocan;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite that runs all ChocAn unit tests
 * Written by Wheeler Knight on 12/4/2025
 */
@RunWith(Suite.class)
@SuiteClasses({
    DataCenterTest.class,
    MemberTest.class,
    ProviderTest.class,
    ManagerTest.class,
    ACMEAccountingServicesTest.class,
    ServiceRecordTest.class,
    ServiceRequestTest.class,
    ProviderFormTest.class
})
public class AllTests {
    // This class remains empty, it is used only as a holder for the above annotations
}

