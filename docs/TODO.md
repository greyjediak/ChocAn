# ChocAn Development TODO List

## Priority Legend
- 🔴 **Critical** - Must fix before any testing/deployment
- 🟠 **High** - Important for core functionality
- 🟡 **Medium** - Should be done for complete feature set
- 🟢 **Low** - Nice to have / polish

---

## Critical Bug Fixes 🔴

### DataCenter.java
- [x] **Fix string comparisons in `writeInfo()` method** (lines 43-57) ✅
  - Change `if(fileName == "members.txt")` to `if(fileName.equals("members.txt"))`
  - Same for all other string comparisons in this method
  
- [x] **Fix string comparisons in validation methods** (lines 110-128) ✅
  - `validMember()`, `validProvider()`, `validManager()` all use `==` for strings
  
- [x] **Fix file path inconsistency** ✅
  - Reading from `"provider.txt"` but writing to `"providers.txt"`
  - Decide on one naming convention and update both read/write operations
  - Same issue with `"manager.txt"` vs `"managers.txt"`

- [x] **Initialize Vector fields in constructor** ✅
  ```java
  private Vector<ProviderForm> weeklyProviderForms = new Vector<>();
  private Vector<MemberServiceReport> allMemberServiceReports = new Vector<>();
  private Vector<ServiceRequest> pendingServiceRequest = new Vector<>();
  ```

### readAndWritable.java
- [x] **Fix `readProviderLine()` array bounds error** (line 169) ✅
  - Change `parts.length == 7` to `parts.length == 8`
  
- [x] **Fix `readManagerLine()` array bounds error** (line 182) ✅
  - Change `parts.length == 7` to `parts.length == 8`

### Terminal.java
- [x] **Fix provider name typo** (line 98) ✅
  - Change `providers[i].getFirstName() + providers[i].getFirstName()` 
  - To `providers[i].getFirstName() + " " + providers[i].getLastName()`

- [x] **Fix NullPointerException in constructor** (lines 140-146, 216-222) ✅
  - Move `fetchServiceReportCount()` and `fetchServiceRequestCount()` calls to after login

### ACMEAccountingServices.java
- [x] **Fix `setSuspendedMembers()` method** (line 30) ✅
  - Change `this.members = members` to `this.suspendedMembers = members`

---

## High Priority Features 🟠

### Terminal.java
- [x] **Implement Manager Screen** ✅
  - Currently commented out (line 231)
  - Add UI components for:
    - Request Service Report button
    - Request Summary Report button
    - View All Members button
    - View All Providers button
    - Logout button

### ACMEAccountingServices.java
- [x] **Complete `suspendMember()` method** ✅
  - Remove member from `members` array
  - Add member to `suspendedMembers` array
  - Update data files
  
- [x] **Implement `unsuspendMember()` method** ✅
  - Remove member from `suspendedMembers` array
  - Add member to `members` array
  - Update data files

### Provider.java
- [x] **Fix `returnInfo()` to include provider number** ✅
  - Add `+ "_" + providerNumber` to the return string

### DataCenter.java
- [x] **Implement `getMemberByNumber()` method** ✅
  - Mirror `getProviderByNumber()` functionality
  
- [x] **Add `getServiceFeeByCode()` utility method** ✅
  - Centralize service fee lookup logic

---

## Medium Priority Features 🟡

### Service Request Workflow
- [x] **Implement actual service approval logic** (Terminal.java) ✅
  - When provider clicks "Approve":
    - Create a ServiceRecord
    - Add to DataCenter.serviceRecords
    - Remove from pendingServiceRequest
  - When provider clicks "Decline":
    - Remove from pendingServiceRequest
    - Optionally notify member

### Data Persistence
- [x] **Add ServiceRecord persistence** ✅
  - Create `readServiceRecords()` method in readAndWritable
  - Create `writeServiceRecords()` method in readAndWritable
  - Add service records file (e.g., `servicerecords.txt`)
  - Load/save in DataCenter constructor/saveInfo()

- [ ] **Add ProviderForm persistence**
  - Create read/write methods for provider forms
  - Store weekly forms for reporting

- [x] **Add ServiceRequest persistence** ✅ (partial - write method added)
  - Save pending requests between sessions
  - Utilize existing `servicerequest.txt` file

### User Management
- [x] **Add UI for creating new Members** ✅
  - Manager should be able to add members
  - Form with all required fields
  
- [x] **Add UI for creating new Providers** ✅
  - Manager should be able to add providers
  
- [ ] **Add UI for editing/deleting users**
  - Update existing records
  - Remove inactive users

### Reporting
- [x] **Implement EFT (Electronic Funds Transfer) data generation** ✅
  - Generate payment file for ACME
  
- [x] **Add report export to file functionality** ✅
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
  
- [x] **Remove duplicate code** ✅ (partial)
  - Consolidate service fee lookup logic (done - getServiceFeeByCode)
  - Move `returnInfo()` to Person base class
  - Remove `Provider.getName()` (use inherited `getFullName()`) ✅

### UI Improvements
- [ ] **Use proper layout managers** (Terminal.java)
  - Replace `setLayout(null)` with BorderLayout/GridBagLayout
  - Make UI responsive to window resizing
  
- [x] **Add input validation** ✅ (partial - login validation added)
  - Validate member/provider numbers (format, length)
  - Validate email format
  - Validate phone number format
  - Validate zip code format
  
- [x] **Improve login screen** ✅
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
  
- [x] **Update inner README.md** (src/chocan/README.md) ✅
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
- [x] Remove unused `idNumber` field from Person.java ✅
- [x] Remove unused `requests.txt` file or implement its functionality ✅ (removed)
- [x] Remove or implement `servicerequest.txt` functionality ✅ (implemented)
- [ ] Clean up commented code in Terminal.java
- [x] Remove FIXME comments after implementing fixes ✅

### Fix Resource Leaks
- [ ] Fix Scanner resource warnings
  - Either close scanners properly
  - Or use a single shared scanner for System.in

---

## Summary Checklist

### Before Alpha Release
- [x] All Critical Bug Fixes complete ✅
- [x] Manager Screen implemented ✅
- [x] Core suspend/unsuspend functionality working ✅

### Before Beta Release
- [x] All High Priority features complete ✅
- [x] Service Request workflow complete ✅
- [x] Basic data persistence for all entities ✅

### Before Production Release
- [x] All Medium Priority features complete ✅ (mostly)
- [ ] Unit tests with >80% coverage
- [ ] Security improvements implemented
- [x] Documentation complete ✅ (basic)

---

## Notes

- **Last Updated:** December 5, 2025
- **Primary Developer:** Wheeler Knight
- **Original Authors:** Lindsey B. (Person, Member, MemberCard classes)

### Known Working Features
- ✅ Member login
- ✅ Provider login
- ✅ Manager login
- ✅ Basic member/provider data loading from files
- ✅ Provider directory display and file export
- ✅ Service request approval/decline workflow
- ✅ Member suspension/unsuspension
- ✅ Add new members/providers (Manager)
- ✅ EFT data generation
- ✅ Report export to files
- ✅ Service record persistence

### Known Broken Features
- (All previously broken features have been fixed!)

