# ChocAn Codebase Autopsy

## Overview

This document provides a comprehensive analysis of the ChocAn codebase, identifying bugs, design issues, redundancies, missing functionality, and areas for improvement.

---

## Table of Contents

- [Critical Bugs](#critical-bugs)
- [Design Issues](#design-issues)
- [Redundancies](#redundancies)
- [Missing Functionality](#missing-functionality)
- [Unused/Incomplete Code](#unusedincomplete-code)
- [Code Quality Issues](#code-quality-issues)
- [Security Concerns](#security-concerns)
- [Recommendations](#recommendations)

---

## Critical Bugs

### 1. String Comparison Using `==` Instead of `.equals()`

**Location:** `DataCenter.java` (lines 43, 47, 51, 54, 111-112, 117-118, 124-125)

**Problem:** String comparisons use `==` which compares references, not values.

```java
// WRONG - compares object references
if(fileName == "members.txt") { ... }

// WRONG - compares object references  
if((members.get(i).getFirstName() + " " + members.get(i).getLastName()) == name ...
```

**Impact:** Login validation and file writing will fail unpredictably.

**Fix:** Use `.equals()` for string comparison:
```java
if(fileName.equals("members.txt")) { ... }
```

---

### 2. File Path Mismatch Between Read and Write Operations

**Location:** `DataCenter.java`

**Problem:** 
- Reading uses: `"provider.txt"` (line 26)
- Writing uses: `"providers.txt"` (line 36, 48)
- Also writes to `"manager.txt"` but checks for `"managers.txt"` (line 51)

**Impact:** Data written will never be read on next load; providers/managers appear lost.

---

### 3. Array Index Out of Bounds in `readProviderLine`

**Location:** `readAndWritable.java` (line 169-170)

**Problem:** Checks for `parts.length == 7` but accesses `parts[7]` (8th element).

```java
if(parts.length == 7){
    Provider returnProvider = new Provider(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]);
```

**Impact:** ArrayIndexOutOfBoundsException when reading providers.

---

### 4. Same Bug in `readManagerLine`

**Location:** `readAndWritable.java` (line 182-184)

**Problem:** Same issue - checks for 7 parts but accesses 8 indices.

---

### 5. Typo in Provider Name Display

**Location:** `Terminal.java` (line 98)

**Problem:** Displays first name twice instead of first + last name:
```java
providerNames[i] = providers[i].getFirstName() + providers[i].getFirstName();
```

**Fix:** Should be:
```java
providerNames[i] = providers[i].getFirstName() + " " + providers[i].getLastName();
```

---

### 6. Incomplete `suspendMember` Method

**Location:** `ACMEAccountingServices.java` (lines 33-37)

**Problem:** Method body does nothing - has an empty statement after if condition:
```java
public void suspendMember(String number) {
    for(int i = 0; i < members.length; i++) {
        if(members[i].getCard().getMemberNumber() == number) ;  // Empty statement!
    }
}
```

---

### 7. NullPointerException Risk in Terminal

**Location:** `Terminal.java` (lines 140-146, 216-222)

**Problem:** `currMember` and `currProvider` are null when these lines execute during constructor initialization, causing NPE when calling methods on them.

---

### 8. Bug in `setSuspendedMembers`

**Location:** `ACMEAccountingServices.java` (lines 29-31)

**Problem:** Sets `members` instead of `suspendedMembers`:
```java
public void setSuspendedMembers(Member[] members) {
    this.members = members;  // WRONG - should be this.suspendedMembers
}
```

---

## Design Issues

### 1. DataCenter Instantiation Anti-Pattern

**Problem:** Multiple methods create new `DataCenter()` instances instead of using dependency injection:

- `Provider.checkCard()` - creates new DataCenter
- `Provider.fillForm()` - creates new DataCenter
- `Provider.RequestProviderDirectory()` - creates new DataCenter
- `Manager.RequestServiceReport()` - creates new DataCenter
- `Manager.RequestSummaryReport()` - creates new DataCenter
- `ACMEAccountingServices.BillMembers()` - creates new DataCenter

**Impact:** 
- Each instantiation re-reads all data files
- Changes made in one DataCenter instance are lost in others
- Poor performance and potential data inconsistency

---

### 2. Mixing GUI and Business Logic

**Location:** `Terminal.java`

**Problem:** The Terminal class handles both Swing GUI and core business logic. This violates separation of concerns.

**Recommendation:** Implement MVC (Model-View-Controller) pattern.

---

### 3. Scanner Resource Leak

**Location:** Multiple files

**Problem:** `new Scanner(System.in)` is created but never closed in:
- `Member.RequestHealthService()`
- `Provider.fillForm()`
- `Provider.EnterPassword()`
- `Manager.RequestServiceReport()`
- `Manager.RequestSummaryReport()`

**Impact:** Resource leak warning; closing System.in scanner can cause issues.

---

### 4. Use of Deprecated `Vector` Class

**Location:** `DataCenter.java`, `readAndWritable.java`

**Problem:** Uses `Vector` instead of `ArrayList`. Vector is synchronized and slower.

**Recommendation:** Use `ArrayList` unless thread safety is explicitly required.

---

### 5. Hardcoded File Paths

**Location:** Various files

**Problem:** File paths like `"src/chocan/members.txt"` are hardcoded, making deployment difficult.

---

### 6. No Interface Segregation

**Problem:** `readAndWritable` abstract class forces `DataCenter` to implement all read/write methods even if not needed.

---

## Redundancies

### 1. Duplicate Name Methods

- `Person.getFullName()` returns `firstName + " " + lastName`
- `Provider.getName()` does the same thing
- `Member` has commented-out `getFullName()` 

**Fix:** Remove `Provider.getName()` and use inherited `getFullName()`.

---

### 2. Duplicate `returnInfo()` Pattern

All three classes (`Member`, `Provider`, `Manager`) have nearly identical `returnInfo()` methods. This should be in the `Person` base class or use a common interface.

---

### 3. Duplicate Service Fee Logic

Service fee lookup code is duplicated in:
- `Provider.fillForm()`
- `Manager.RequestServiceReport()`
- `Manager.RequestSummaryReport()`
- `ACMEAccountingServices.BillMembers()`
- `MemberServiceReport.ReportGenerator.printMemberReport()`

**Fix:** Create a utility method in DataCenter: `getServiceFeeByCode(int code)`.

---

### 4. Duplicate Week Calculation Logic

Week start/end date calculation is duplicated in:
- `Manager.RequestServiceReport()`
- `Manager.RequestSummaryReport()`

---

### 5. Redundant Fields in `ProviderForm`

The class has both:
- `name` and `memberName`
- `number` and `memberNumber`

These appear to serve the same purpose.

---

## Missing Functionality

### 1. Manager Screen Not Implemented

**Location:** `Terminal.java` (line 231)

```java
//        this.add(managerScreen);
```

The manager screen is commented out and never initialized with any components.

---

### 2. Empty `unsuspendMember` Method

**Location:** `ACMEAccountingServices.java` (lines 39-41)

```java
public void unsuspendMember(String number) {
    // Completely empty
}
```

---

### 3. No Provider/Manager Addition Through UI

While `DataCenter.addMember()` and `DataCenter.addProvider()` exist, there's no UI to add new providers or managers.

---

### 4. No Edit/Delete Functionality

Cannot edit or delete existing members, providers, or managers.

---

### 5. No Service Record Persistence

`ServiceRecord` objects are stored in memory but never saved to/loaded from files.

---

### 6. No Provider Form Persistence

`ProviderForm` objects are created but never persisted.

---

### 7. No Actual Service Approval Workflow

The approve/decline buttons in `Terminal.java` just switch screens without recording the decision or creating service records.

---

### 8. Missing Provider Number in Provider File Format

`Provider.returnInfo()` doesn't include the provider number:
```java
return firstName + "_" + lastName + "_" + phoneNumber + "_" + address + "_" + city + "_" + state + "_" + zipCode;
// Missing: + "_" + providerNumber
```

---

### 9. No Password Storage for Providers

Provider password is checked but never persisted to file.

---

### 10. No Email Validation

No validation for email format in Member class.

---

## Unused/Incomplete Code

### 1. `ServiceRequest.servicerequest.txt`

File exists but is never read or written to.

---

### 2. `requests.txt`

File exists but is never used.

---

### 3. `idNumber` Field in Person

**Location:** `Person.java` (line 17)

```java
protected String idNumber;
```

Declared but never used or set.

---

### 4. `weeklyProviderForms` Never Initialized

**Location:** `DataCenter.java` (line 14)

```java
private Vector<ProviderForm> weeklyProviderForms;
```

Declared but never initialized, causing NPE when accessed.

---

### 5. `allMemberServiceReports` Never Initialized

**Location:** `DataCenter.java` (line 15)

Same issue as above.

---

### 6. `pendingServiceRequest` Never Initialized

**Location:** `DataCenter.java` (line 16)

Same issue - calling `addPendingServiceRequest()` will throw NPE.

---

### 7. Unused `Person` Parameter in `EnterPassword`

**Location:** `Provider.java` (line 142)

```java
public boolean EnterPassword(Person person) {
```

The `person` parameter is never used in the method.

---

### 8. Inner README.md

**Location:** `src/chocan/README.md`

Contains only `# ChocAn` - essentially empty.

---

## Code Quality Issues

### 1. Inconsistent Naming Conventions

- Methods: Mix of camelCase (`getMembers`) and PascalCase (`RequestHealthService`, `BillMembers`)
- Classes: `readAndWritable` should be `ReadAndWritable`

---

### 2. Missing JavaDoc Comments

Most methods lack proper documentation.

---

### 3. Magic Numbers

Service codes (1, 2, 3) and array indices are used without constants.

---

### 4. Raw Type Usage

**Location:** Various files

Using raw `Vector` instead of parameterized `Vector<Type>` in some places.

---

### 5. Inconsistent Null Checking

Some methods check for null, others don't, leading to inconsistent behavior.

---

### 6. GUI Not Using Layout Managers Properly

**Location:** `Terminal.java`

Uses `null` layout and absolute positioning:
```java
this.setLayout(null);
```

This makes the UI not responsive to window resizing.

---

### 7. No Input Validation

User input from text fields is not validated before use.

---

## Security Concerns

### 1. Plaintext Password Storage

Provider passwords would be stored in plaintext if implemented.

---

### 2. No Authentication Security

Simple string matching for login without any security measures.

---

### 3. No Input Sanitization

User input is directly used without sanitization.

---

## Recommendations

### High Priority

1. Fix all `==` string comparisons to use `.equals()`
2. Fix array index bug in `readProviderLine` and `readManagerLine`
3. Initialize all Vector fields in DataCenter constructor
4. Fix file path mismatch between read/write operations
5. Implement singleton or dependency injection for DataCenter

### Medium Priority

1. Implement manager screen in Terminal
2. Add persistence for ServiceRecords
3. Complete suspendMember and unsuspendMember methods
4. Add proper null checking throughout
5. Refactor to separate UI from business logic

### Low Priority

1. Rename classes/methods to follow Java conventions
2. Add comprehensive JavaDoc documentation
3. Replace Vector with ArrayList
4. Implement proper input validation
5. Add unit tests

---

## Summary Statistics

| Category | Original | Fixed | Remaining |
|----------|----------|-------|-----------|
| Critical Bugs | 8 | 8 | 0 |
| Design Issues | 6 | 2 | 4 |
| Redundancies | 5 | 3 | 2 |
| Missing Functionality | 10 | 9 | 1 |
| Unused/Incomplete Code | 8 | 6 | 2 |
| Code Quality Issues | 7 | 4 | 3 |
| Security Concerns | 3 | 0 | 3 |

---

## Update: December 5, 2025 - Wheeler Knight

**Fixes Applied:**
- ✅ All critical bugs fixed (string comparisons, array bounds, NPE issues, file paths)
- ✅ Manager screen fully implemented
- ✅ Service request approval workflow complete
- ✅ Member suspend/unsuspend working
- ✅ ServiceRecord persistence added
- ✅ EFT data generation implemented
- ✅ Report export functionality added
- ✅ User management UI (add members/providers)
- ✅ Login screen improved with labels and validation
- ✅ Removed duplicate code (getName() -> getFullName())
- ✅ Removed unused idNumber field
- ✅ Cleaned up FIXME comments
- ✅ Package documentation added

**Overall Assessment:** The codebase is now functional for basic operations. All critical bugs have been resolved. Remaining work includes unit testing, security improvements (password hashing), and some UI polish (layout managers).

