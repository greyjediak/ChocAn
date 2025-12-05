# ChocAn System - Complete Demo Script

## Overview
This document provides a complete walkthrough of all ChocAn system features for demonstration and testing purposes. Follow this script sequentially to showcase every requirement.

---

## 🚀 Pre-Demo Setup

### 1. Start the Application
```bash
cd /home/wheeler/cs200/project4/ChocAn
./run_tests.sh      # Verify everything compiles
./run_app.sh        # Launch the GUI
```

### 2. Verify Database Files Exist
```
database/
├── members.json         (11 members)
├── suspendedmembers.json (2 suspended)
├── providers.json       (7 providers)
├── managers.json        (3 managers)
├── operators.json       (3 operators)
└── servicerecords.json  (20 service records)
```

---

## 📋 REQUIREMENT 2: User Login with PIN/Password Validation

### Demo Steps:

#### Step 1: Test Invalid Login
1. On the login screen, enter:
   - **Name:** `Invalid User`
   - **Number:** `99999`
   - **PIN:** `wrong`
2. Click **Login**
3. ✅ **Expected:** Error message "Invalid Login. Please check your credentials."

#### Step 2: Test Wrong Password
1. Enter:
   - **Name:** `Jon Doe`
   - **Number:** `100001`
   - **PIN:** `wrongpin`
2. Click **Login**
3. ✅ **Expected:** Error message (wrong PIN)

#### Step 3: Test Correct Login (Member)
1. Enter:
   - **Name:** `Jon Doe`
   - **Number:** `100001`
   - **PIN:** `1234`
2. Click **Login**
3. ✅ **Expected:** Member Portal opens
4. Click **Logout** to return to login screen

---

## 📋 REQUIREMENT 2: Operator Menu (Member & Provider Management)

### Demo Steps:

#### Step 1: Login as Operator
1. Enter:
   - **Name:** `System Operator`
   - **Number:** `OP001`
   - **PIN:** `1234`
2. Click **Login**
3. ✅ **Expected:** Operator Portal opens with Member Management and Provider Management sections

---

## 📋 REQUIREMENT 3: Member Management (Add, Edit, Delete)

### Demo Steps:

#### Step 1: Add Member - Jane Doe
1. In Operator Portal, click **Add Member**
2. Fill in the form:
   - **First Name:** `Jane`
   - **Last Name:** `Newmember`
   - **Phone:** `555-123-4567`
   - **Address:** `123 Test Street`
   - **City:** `Tuscaloosa`
   - **State:** `Alabama`
   - **Zip Code:** `35401`
   - **Email:** `jane.new@email.com`
   - **Member Number:** `200001`
   - **PIN:** `1234`
3. Click **OK**
4. ✅ **Expected:** "Member added successfully!" message

#### Step 2: View All Members (Verify Addition)
1. Click **View All Members**
2. ✅ **Expected:** Jane Newmember appears in the list with #200001

#### Step 3: Edit Member - Change Jane Newmember to John Newmember
1. Click **Edit Member**
2. Enter member number: `200001`
3. Change:
   - **First Name:** `John` (was Jane)
   - **Last Name:** `Newmember`
4. Click **OK**
5. ✅ **Expected:** "Member updated successfully!" message

#### Step 4: View All Members (Verify Edit)
1. Click **View All Members**
2. ✅ **Expected:** Now shows "John Newmember" instead of "Jane Newmember"

#### Step 5: Suspend Member
1. Click **Suspend Member**
2. Enter member number: `100005` (Mike Brown)
3. ✅ **Expected:** "Member suspended successfully!" message

#### Step 6: View Suspended Members
1. Click **View Suspended Members**
2. ✅ **Expected:** Shows Mike Brown in suspended list (along with pre-existing suspended users)

#### Step 7: Unsuspend Member
1. Click **Unsuspend Member**
2. Enter member number: `100005`
3. ✅ **Expected:** "Member unsuspended successfully!" message

#### Step 8: Delete Member (Will do at end for Persistence test)
*Note: We'll delete John Newmember (#200001) later during the Persistence test*

---

## 📋 REQUIREMENT 4: Provider Management (Add, Edit, Delete)

### Demo Steps:

#### Step 1: Add Provider - John Smith
1. Still in Operator Portal, click **Add Provider**
2. Fill in the form:
   - **First Name:** `John`
   - **Last Name:** `Smith`
   - **Phone:** `555-999-8888`
   - **Address:** `500 Medical Plaza`
   - **City:** `Birmingham`
   - **State:** `Alabama`
   - **Zip Code:** `35203`
   - **Provider Number:** `P999`
   - **Password:** `newprovider`
3. Click **OK**
4. ✅ **Expected:** "Provider added successfully!" message

#### Step 2: View All Providers (Verify Addition)
1. Click **View All Providers**
2. ✅ **Expected:** John Smith appears in the list with #P999

#### Step 3: Edit Provider - Change John Smith to Mary Smith
1. Click **Edit Provider**
2. Enter provider number: `P999`
3. Change:
   - **First Name:** `Mary` (was John)
   - **Last Name:** `Smith`
4. Click **OK**
5. ✅ **Expected:** "Provider updated successfully!" message

#### Step 4: View All Providers (Verify Edit)
1. Click **View All Providers**
2. ✅ **Expected:** Now shows "Mary Smith" instead of "John Smith"

#### Step 5: Delete Provider (Will do at end for Persistence test)
*Note: We'll delete Mary Smith (#P999) later during the Persistence test*

#### Step 6: Logout from Operator Portal
1. Click **Logout**
2. ✅ **Expected:** Returns to login screen

---

## 📋 REQUIREMENT 5: Add Service Records + Member Validation (Provider Menu)

### Demo Steps:

#### Step 1: Login as Provider
1. Enter:
   - **Name:** `Dr Smith`
   - **Number:** `P001`
   - **PIN:** `provider`
2. Click **Login**
3. ✅ **Expected:** Provider Portal opens

#### Step 2: Test Member Validation - Invalid Member
1. Click **Add Service Record (Bill ChocAn)**
2. Enter:
   - **Member Number:** `999999999` (invalid)
   - **Service:** `CONSULTATION`
   - **Date:** (today's date)
3. Click **OK**
4. ✅ **Expected:** Error message "INVALID: Member number not found!"

#### Step 3: Test Member Validation - Suspended Member
1. Click **Add Service Record (Bill ChocAn)**
2. Enter:
   - **Member Number:** `999999` (suspended user)
   - **Service:** `CONSULTATION`
3. Click **OK**
4. ✅ **Expected:** Warning message "SUSPENDED: Member account is suspended!"

#### Step 4: Test Member Validation - Valid Member
1. Click **Add Service Record (Bill ChocAn)**
2. Enter:
   - **Member Number:** `100001` (Jon Doe - active member)
   - **Service:** `EMERGENCY`
   - **Date:** `2025-12-05`
   - **Comments:** (optional)
3. Click **OK**
4. ✅ **Expected:** 
   - Success message "VALIDATED: Member is active!"
   - Fee confirmation dialog showing "Service: EMERGENCY, Fee: $29.99"
5. Click **Yes** to confirm
6. ✅ **Expected:** "Service record added successfully!" message

---

## 📋 REQUIREMENT 8: Request Provider Directory

### Demo Steps:

#### Step 1: Request Provider Directory
1. Still in Provider Portal, click **Request Provider Directory**
2. ✅ **Expected:** 
   - Dialog showing directory with Service Name, Code, and Fee
   - Message "Also saved to provider_directory.txt"
3. ✅ **Verify:** Check that `provider_directory.txt` was created in project root

#### Step 2: View My Weekly Report
1. Click **View My Weekly Report**
2. ✅ **Expected:** 
   - Message "Provider report printed to console"
   - Console shows Dr Smith's service records for the week

#### Step 3: View/Approve Service Requests
1. Click **View/Approve Service Requests**
2. ✅ **Expected:** Shows pending requests (if any) or "No pending requests"

#### Step 4: Logout from Provider Portal
1. Click **Logout**

---

## 📋 REQUIREMENT 6: Run Main Accounting Procedure (from Main Menu)

### Demo Steps:

#### Step 1: Run Main Accounting Procedure
1. On the login screen, click **Run Main Accounting Procedure**
2. ✅ **Expected:** 
   - Dialog: "Main Accounting Procedure Complete!"
   - Lists: Summary Report, Member Service Reports, Provider Reports, EFT Data
   - Console output shows all three report types

#### Step 2: Verify Generated Files
Check that these files were created:
```
reports/
├── summary_report_[timestamp].txt
├── member_reports_[timestamp].txt
├── provider_P001_[timestamp].txt
├── provider_P002_[timestamp].txt
├── provider_P003_[timestamp].txt
├── provider_P004_[timestamp].txt
├── provider_P005_[timestamp].txt
├── provider_P006_[timestamp].txt
├── provider_8008_[timestamp].txt
└── provider_P999_[timestamp].txt (if Mary Smith had records)

eft_data.txt (in project root)
```

#### Step 3: Verify Report Contents
Open `reports/summary_report_[timestamp].txt` and verify:
- ✅ Provider names and numbers
- ✅ Consultation counts per provider
- ✅ Fees per provider
- ✅ Total providers, consultations, and fees

---

## 📋 REQUIREMENT 7: Manager Reports (Summary, Member, Provider)

### Demo Steps:

#### Step 1: Login as Manager
1. Enter:
   - **Name:** `Admin User`
   - **Number:** `M001`
   - **PIN:** `admin`
2. Click **Login**
3. ✅ **Expected:** Manager Portal opens

#### Step 2: Generate Summary Report
1. Click **Summary Report**
2. ✅ **Expected:** 
   - Message "Summary Report printed to console"
   - Console shows formatted table with:
     - Provider Name, Provider Number, Number of Consultations, Fee Owed
     - Total Providers Who Serviced Members
     - Total Number of Consultations
     - Overall Fee to be distributed

#### Step 3: Generate Member Reports
1. Click **Member Reports**
2. ✅ **Expected:**
   - Message "Member Reports printed to console"
   - Console shows for EACH member:
     - Member name, number, address, city, state, zip
     - List of services with date, provider name, service name
     - Or "No services this week" if applicable

#### Step 4: Generate Provider Reports
1. Click **Provider Reports**
2. ✅ **Expected:**
   - Message "Provider Reports printed to console"
   - Console shows for EACH provider who had consultations:
     - Provider name, number, address
     - Services: Date, Member Name, Member #, Service, Fee
     - Total Consultations and Total Fee to be Paid

#### Step 5: Export All Reports
1. Click **Export All Reports**
2. ✅ **Expected:** 
   - Message "Reports exported to reports/ folder"
   - New timestamped files appear in reports/ folder

#### Step 6: View All Members
1. Click **View All Members**
2. ✅ **Expected:** Shows all active members with name, number, email

#### Step 7: View All Providers
1. Click **View All Providers**
2. ✅ **Expected:** Shows all providers with name and number

#### Step 8: View Suspended Members
1. Click **View Suspended**
2. ✅ **Expected:** Shows suspended members list

#### Step 9: Generate EFT Data
1. Click **Generate EFT Data**
2. ✅ **Expected:** 
   - Message "EFT data generated to eft_data.txt"
   - File contains provider payments summary

#### Step 10: Logout from Manager Portal
1. Click **Logout**

---

## 📋 REQUIREMENT 9: GUI

### Verification:
✅ The entire application runs as a Swing GUI with:
- Login screen with title, labels, text fields, buttons
- Member Portal with service request and view options
- Provider Portal with service record, directory, and report options
- Manager Portal with comprehensive report and view options
- Operator Portal with full CRUD for members and providers
- Dialog boxes for all data entry operations
- Confirmation dialogs for delete operations
- Message dialogs for success/error feedback

---

## 📋 REQUIREMENT 10: Persistence Test

### Demo Steps:

#### Step 1: Exit the Application
1. Close the application window
2. When prompted "Save data before exiting?", click **Yes**
3. ✅ **Expected:** Application saves all data and exits

#### Step 2: Verify Data Saved
Check database files were updated:
```bash
cat database/members.json | grep "John Newmember"
cat database/providers.json | grep "Mary Smith"
```

#### Step 3: Restart the Application
```bash
./run_app.sh
```

#### Step 4: Verify Data Persisted
1. Login as Operator (System Operator / OP001 / 1234)
2. Click **View All Members**
3. ✅ **Expected:** John Newmember (#200001) still exists
4. Click **View All Providers**
5. ✅ **Expected:** Mary Smith (#P999) still exists

#### Step 5: Delete Member - John Newmember
1. Click **Delete Member**
2. Enter member number: `200001`
3. Confirm deletion
4. ✅ **Expected:** "Member deleted successfully!"
5. Click **View All Members** to verify removal

#### Step 6: Delete Provider - Mary Smith
1. Click **Delete Provider**
2. Enter provider number: `P999`
3. Confirm deletion
4. ✅ **Expected:** "Provider deleted successfully!"
5. Click **View All Providers** to verify removal

#### Step 7: Save and Exit
1. Close the application
2. Click **Yes** to save
3. ✅ **Expected:** Deletions are persisted

#### Step 8: Final Verification
1. Restart the application
2. Login as Operator
3. Verify John Newmember and Mary Smith are PERMANENTLY deleted

---

## 📊 Complete Test Credentials Reference

### Members
| Name | Number | PIN |
|------|--------|-----|
| Jon Doe | 100001 | 1234 |
| Jane Doe | 100002 | 1234 |
| Wyatt Knight | 567252 | 5678 |
| Bob Wilson | 100003 | 4321 |
| Sarah Johnson | 100004 | 9999 |
| Mike Brown | 100005 | 1111 |
| Emily Davis | 100006 | 2222 |
| Chris Martinez | 100007 | 3333 |
| Lisa Garcia | 100008 | 4444 |
| David Lee | 100009 | 5555 |
| Amanda Taylor | 100010 | 6666 |

### Suspended Members
| Name | Number |
|------|--------|
| Suspended User | 999999 |
| Overdue Member | 888888 |

### Providers
| Name | Number | Password |
|------|--------|----------|
| Dr Smith | P001 | provider |
| Dr Johnson | P002 | health123 |
| Dr Williams | P003 | medical |
| Dr Brown | P004 | care456 |
| Rossy Hollinger | 8008 | rossy |
| Dr Davis | P005 | therapy |
| Dr Miller | P006 | wellness |

### Managers
| Name | Number | Password |
|------|--------|----------|
| Admin User | M001 | admin |
| Regional Manager | M002 | manager |
| District Supervisor | M003 | super123 |

### Operators
| Name | Number | Password |
|------|--------|----------|
| System Operator | OP001 | 1234 |
| Data Entry | OP002 | operator |
| Support Staff | OP003 | admin123 |

---

## ✅ Requirements Checklist

| # | Requirement | Points | Demo Section |
|---|-------------|--------|--------------|
| 2 | User Login with PIN/Password | ✅ | Login Tests |
| 2 | Operator Menu | ✅ | Requirement 2 |
| 2 | Provider Menu | ✅ | Requirement 5 |
| 2 | Manager Menu | ✅ | Requirement 7 |
| 3 | Add Member | ✅ | Requirement 3, Step 1 |
| 3 | Edit Member | ✅ | Requirement 3, Step 3 |
| 3 | Delete Member | ✅ | Requirement 10, Step 5 |
| 4 | Add Provider | ✅ | Requirement 4, Step 1 |
| 4 | Edit Provider | ✅ | Requirement 4, Step 3 |
| 4 | Delete Provider | ✅ | Requirement 10, Step 6 |
| 5 | Add Service Record + Validation | ✅ | Requirement 5 |
| 6 | Main Accounting Procedure | ✅ | Requirement 6 |
| 6 | Summary Report | ✅ | Requirement 6 & 7 |
| 6 | Member Report | ✅ | Requirement 6 & 7 |
| 6 | Provider Report | ✅ | Requirement 6 & 7 |
| 7 | Manager Reports | ✅ | Requirement 7 |
| 8 | Request Provider Directory | ✅ | Requirement 8 |
| 9 | GUI | ✅ | Entire Application |
| 10 | Persistence | ✅ | Requirement 10 |

---

## 🎬 Quick Demo (5-Minute Version)

For a quick demo, follow this abbreviated path:

1. **Login as Operator** → Add Member "Jane Doe" → Edit to "John Doe"
2. **Same session** → Add Provider "John Smith" → Edit to "Mary Smith"
3. **Logout** → **Login as Provider** → Add Service Record (validate member 100001)
4. **Logout** → **Login as Manager** → Generate all 3 reports
5. **From Login Screen** → Click "Run Main Accounting Procedure"
6. **Exit & Restart** → Login as Operator → Delete John Doe → Delete Mary Smith
7. **Exit & Restart** → Verify deletions persisted

---

