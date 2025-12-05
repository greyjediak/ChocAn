# ChocAn Data Processing System

## Overview

ChocAn (Chocoholics Anonymous) is a healthcare services management system designed to manage member subscriptions, provider services, and billing operations for a fictional organization that helps people addicted to chocolate. The system facilitates interactions between three main user types: **Members**, **Providers**, and **Managers**.

## Table of Contents

- [Features](#features)
- [System Architecture](#system-architecture)
- [Getting Started](#getting-started)
- [User Guide](#user-guide)
- [Data Files](#data-files)
- [Contributors](#contributors)

---

## Features

### Member Features
- Login with member name and number
- View service fees and billing status
- Request health services from providers
- Member card validation

### Provider Features
- Login with provider credentials
- Check member card validity
- Fill out provider forms after services
- Approve or decline service requests
- Request provider directory

### Manager Features
- Login with manager credentials
- Request weekly member service reports
- Request summary reports (provider consultations and fees)
- View overall system statistics

### System Features
- Persistent data storage (members, providers, managers)
- Service record tracking
- Weekly billing and reporting
- ACME Accounting Services integration for member validation and billing

---

## System Architecture

```
ChocAn/
├── src/
│   └── chocan/
│       ├── Main.java                 # Application entry point
│       ├── Terminal.java             # GUI terminal interface (Swing)
│       ├── DataCenter.java           # Central data management
│       ├── Person.java               # Base class for all actors
│       ├── Member.java               # Member entity
│       ├── MemberCard.java           # Member identification card
│       ├── Provider.java             # Provider entity
│       ├── Manager.java              # Manager entity
│       ├── ServiceRequest.java       # Service request object
│       ├── ServiceRecord.java        # Completed service record
│       ├── ProviderForm.java         # Provider billing form
│       ├── ProviderDirectory.java    # Service directory
│       ├── MemberServiceReport.java  # Member report generator
│       ├── SummaryReport.java        # Summary report generator
│       ├── ACMEAccountingServices.java # External accounting integration
│       └── readAndWritable.java      # File I/O utilities
├── docs/                             # Documentation
├── tests/                            # Test files
└── bin/                              # Compiled classes
```

### Class Hierarchy

```
Person (base)
├── Member
├── Provider
└── Manager

readAndWritable (abstract)
└── DataCenter
```

---

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- A Java IDE (optional) or command line tools

### Compilation

From the project root directory:

```bash
# Navigate to project directory
cd ChocAn

# Compile all Java files
javac -d bin src/chocan/*.java
```

### Running the Application

```bash
# Run the application
java -cp bin chocan.Main
```

This will launch the GUI terminal interface.

---

## User Guide

### Logging In

1. Launch the application
2. Enter your full name (format: `FirstName LastName`)
3. Enter your user number
4. Click "Login"

The system will automatically detect your user type (Member, Provider, or Manager) and display the appropriate interface.

### Member Operations

**Viewing Fees:**
- After login, your current service fees are displayed
- "No Fees!" indicates no outstanding charges

**Requesting a Service:**
1. Click "Request Service"
2. Select a provider from the dropdown
3. Select the service type (CONSULTATION, CONVERSATION, EMERGENCY)
4. Click "Submit"

### Provider Operations

**Checking Member Cards:**
1. Click "Approve/Decline Request"
2. Click "Check" to verify member card validity
3. Approve or Decline the service request

**Filling Provider Forms:**
- After providing a service, the system prompts for:
  - Date of service (day, month, year)
  - Member number
  - 6-digit service code

### Manager Operations

**Requesting Service Reports:**
- Enter week number (1-53) and year
- System generates reports for all members

**Requesting Summary Reports:**
- Enter week number and year
- Displays provider statistics including:
  - Provider names and numbers
  - Number of consultations
  - Total fees per provider
  - Grand totals

---

## Data Files

The system uses underscore-delimited text files for persistent storage:

| File | Description |
|------|-------------|
| `members.txt` | Active member records |
| `suspendedmembers.txt` | Suspended member records |
| `provider.txt` | Provider records (read) |
| `providers.txt` | Provider records (write) |
| `manager.txt` | Manager records |

### Data Format

**Members:** `FirstName_LastName_Phone_Address_City_State_Zip_Email_Number`

**Providers:** `FirstName_LastName_Phone_Address_City_State_Zip_Number`

**Managers:** `FirstName_LastName_Phone_Address_City_State_Zip_Number`

---

## Service Codes and Fees

| Service | Code | Fee |
|---------|------|-----|
| CONSULTATION | 000001 | $12.99 |
| CONVERSATION | 000002 | $8.99 |
| EMERGENCY | 000003 | $29.99 |

---

## Contributors

- **Lindsey B.** - Person class, Member class, MemberCard class
- **Wheeler Knight** - Provider methods (checkCard, fillForm, EnterPassword, RequestProviderDirectory), Manager methods (RequestServiceReport, RequestSummaryReport), Member RequestHealthService, ACMEAccountingServices methods, MemberServiceReport, SummaryReport classes
-- **Rossy Hollinger**
-- **Dylan Stokes**
-- **Logan Hernandez**
-- **Esmeralda Gomez**


---

## License

This project was developed for CS200 - Project 4.

---

## Support

For issues or questions, please refer to the project documentation or contact the development team.

