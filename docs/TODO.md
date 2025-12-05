# ChocAn Development TODO List

## Priority Legend
- 🔴 **Critical** - Must fix before any testing/deployment
- 🟠 **High** - Important for core functionality
- 🟡 **Medium** - Should be done for complete feature set
- 🟢 **Low** - Nice to have / polish

---

## Critical Bug Fixes 🔴

### DataCenter.java
- [ ] **Fix string comparisons in `writeInfo()` method** (lines 43-57)
  - Change `if(fileName == "members.txt")` to `if(fileName.equals("members.txt"))`
  - Same for all other string comparisons in this method
  
- [ ] **Fix string comparisons in validation methods** (lines 110-128)
  - `validMember()`, `validProvider()`, `validManager()` all use `==` for strings
  
- [ ] **Fix file path inconsistency**
  - Reading from `"provider.txt"` but writing to `"providers.txt"`
  - Decide on one naming convention and update both read/write operations
  - Same issue with `"manager.txt"` vs `"managers.txt"`

- [ ] **Initialize Vector fields in constructor**
  ```java
  private Vector<ProviderForm> weeklyProviderForms = new Vector<>();
  private Vector<MemberServiceReport> allMemberServiceReports = new Vector<>();
  private Vector<ServiceRequest> pendingServiceRequest = new Vector<>();
  ```

### readAndWritable.java
- [ ] **Fix `readProviderLine()` array bounds error** (line 169)
  - Change `parts.length == 7` to `parts.length == 8`
  
- [ ] **Fix `readManagerLine()` array bounds error** (line 182)
  - Change `parts.length == 7` to `parts.length == 8`

### Terminal.java
- [ ] **Fix provider name typo** (line 98)
  - Change `providers[i].getFirstName() + providers[i].getFirstName()` 
  - To `providers[i].getFirstName() + " " + providers[i].getLastName()`

- [ ] **Fix NullPointerException in constructor** (lines 140-146, 216-222)
  - Move `fetchServiceReportCount()` and `fetchServiceRequestCount()` calls to after login

### ACMEAccountingServices.java
- [ ] **Fix `setSuspendedMembers()` method** (line 30)
  - Change `this.members = members` to `this.suspendedMembers = members`

---

## High Priority Features 🟠

### Terminal.java
- [ ] **Implement Manager Screen**
  - Currently commented out (line 231)
  - Add UI components for:
    - Request Service Report button
    - Request Summary Report button
    - View All Members button
    - View All Providers button
    - Logout button

### ACMEAccountingServices.java
- [ ] **Complete `suspendMember()` method**
  - Remove member from `members` array
  - Add member to `suspendedMembers` array
  - Update data files
  
- [ ] **Implement `unsuspendMember()` method**
  - Remove member from `suspendedMembers` array
  - Add member to `members` array
  - Update data files

### Provider.java
- [ ] **Fix `returnInfo()` to include provider number**
  - Add `+ "_" + providerNumber` to the return string

### DataCenter.java
- [ ] **Implement `getMemberByNumber()` method**
  - Mirror `getProviderByNumber()` functionality
  
- [ ] **Add `getServiceFeeByCode()` utility method**
  - Centralize service fee lookup logic

---

## Medium Priority Features 🟡

### Service Request Workflow
- [ ] **Implement actual service approval logic** (Terminal.java)
  - When provider clicks "Approve":
    - Create a ServiceRecord
    - Add to DataCenter.serviceRecords
    - Remove from pendingServiceRequest
  - When provider clicks "Decline":
    - Remove from pendingServiceRequest
    - Optionally notify member

### Data Persistence
- [ ] **Add ServiceRecord persistence**
  - Create `readServiceRecords()` method in readAndWritable
  - Create `writeServiceRecords()` method in readAndWritable
  - Add service records file (e.g., `servicerecords.txt`)
  - Load/save in DataCenter constructor/saveInfo()

- [ ] **Add ProviderForm persistence**
  - Create read/write methods for provider forms
  - Store weekly forms for reporting

- [ ] **Add ServiceRequest persistence**
  - Save pending requests between sessions
  - Utilize existing `servicerequest.txt` file

### User Management
- [ ] **Add UI for creating new Members**
  - Manager should be able to add members
  - Form with all required fields
  
- [ ] **Add UI for creating new Providers**
  - Manager should be able to add providers
  
- [ ] **Add UI for editing/deleting users**
  - Update existing records
  - Remove inactive users

### Reporting
- [ ] **Implement EFT (Electronic Funds Transfer) data generation**
  - Generate payment file for ACME
  
- [ ] **Add report export to file functionality**
  - Save Member Service Reports to files
  - Save Summary Reports to files
  - Include date/timestamp in filename

---

## Low Priority Improvements 🟢

### Code Refactoring
- [ ] **Implement Singleton pattern for DataCenter**
  - Prevent multiple instances
  - Ensure data consistency
  
- [ ] **Replace Vector with ArrayList**
  - Throughout DataCenter.java
  - Throughout readAndWritable.java
  
- [ ] **Fix naming conventions**
  - Rename `readAndWritable` to `ReadAndWritable`
  - Rename methods like `RequestHealthService` to `requestHealthService`
  - Rename `BillMembers` to `billMembers`
  
- [ ] **Remove duplicate code**
  - Consolidate service fee lookup logic
  - Move `returnInfo()` to Person base class
  - Remove `Provider.getName()` (use inherited `getFullName()`)

### UI Improvements
- [ ] **Use proper layout managers** (Terminal.java)
  - Replace `setLayout(null)` with BorderLayout/GridBagLayout
  - Make UI responsive to window resizing
  
- [ ] **Add input validation**
  - Validate member/provider numbers (format, length)
  - Validate email format
  - Validate phone number format
  - Validate zip code format
  
- [ ] **Improve login screen**
  - Add labels for name and number fields
  - Add "Forgot Password" functionality
  - Show login errors more clearly

- [ ] **Add loading indicators**
  - Show progress during file operations
  - Indicate when reports are generating

### Documentation
- [ ] **Add JavaDoc comments to all classes**
  - Document all public methods
  - Document parameters and return values
  
- [ ] **Update inner README.md** (src/chocan/README.md)
  - Currently just contains `# ChocAn`
  - Add package-level documentation

### Security
- [ ] **Implement password hashing**
  - Don't store plaintext passwords
  - Use secure hashing algorithm
  
- [ ] **Add input sanitization**
  - Sanitize all user input before processing
  
- [ ] **Add session timeout**
  - Auto-logout after period of inactivity

### Testing
- [ ] **Create unit tests**
  - Test DataCenter CRUD operations
  - Test validation methods
  - Test report generation
  - Test file I/O
  
- [ ] **Create integration tests**
  - Test complete workflows
  - Test login/logout cycle
  - Test service request flow

---

## Technical Debt

### Remove/Clean Up
- [ ] Remove unused `idNumber` field from Person.java
- [ ] Remove unused `requests.txt` file or implement its functionality
- [ ] Remove or implement `servicerequest.txt` functionality
- [ ] Clean up commented code in Terminal.java
- [ ] Remove FIXME comments after implementing fixes

### Fix Resource Leaks
- [ ] Fix Scanner resource warnings
  - Either close scanners properly
  - Or use a single shared scanner for System.in

---

## Summary Checklist

### Before Alpha Release
- [ ] All Critical Bug Fixes complete
- [ ] Manager Screen implemented
- [ ] Core suspend/unsuspend functionality working

### Before Beta Release
- [ ] All High Priority features complete
- [ ] Service Request workflow complete
- [ ] Basic data persistence for all entities

### Before Production Release
- [ ] All Medium Priority features complete
- [ ] Unit tests with >80% coverage
- [ ] Security improvements implemented
- [ ] Documentation complete

---

## Notes

- **Last Updated:** December 5, 2025
- **Primary Developer:** Wheeler Knight
- **Original Authors:** Lindsey B. (Person, Member, MemberCard classes)

### Known Working Features
- ✅ Member login
- ✅ Provider login  
- ✅ Basic member/provider data loading from files
- ✅ Provider directory display and file export

### Known Broken Features
- ❌ Manager login (screen not implemented)
- ❌ Service request approval (no backend logic)
- ❌ Member suspension
- ❌ Writing provider data (file path mismatch)

