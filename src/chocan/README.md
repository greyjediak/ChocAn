# ChocAn Package Documentation

Written by Wheeler Knight on 12/4/2025

## Package Overview

The `chocan` package contains all classes for the Chocoholics Anonymous (ChocAn) 
healthcare services management system.

## Class Descriptions

### Core Classes

| Class | Description |
|-------|-------------|
| `Main` | Application entry point - launches Terminal |
| `Terminal` | Swing GUI interface for all user interactions |
| `DataCenter` | Central data management and persistence |

### Entity Classes (Actors)

| Class | Description |
|-------|-------------|
| `Person` | Base class for all actors (abstract) |
| `Member` | Healthcare service members |
| `Provider` | Healthcare service providers |
| `Manager` | System managers/administrators |
| `MemberCard` | Member identification card data |

### Service Classes

| Class | Description |
|-------|-------------|
| `ServiceRequest` | Pending service request from member to provider |
| `ServiceRecord` | Completed service record with fees |
| `ProviderForm` | Form filled by provider after service |
| `ProviderDirectory` | Directory of available services and fees |

### Report Classes

| Class | Description |
|-------|-------------|
| `MemberServiceReport` | Weekly service report for members |
| `SummaryReport` | Management summary report |

### Utility Classes

| Class | Description |
|-------|-------------|
| `readAndWritable` | Abstract class for file I/O operations |
| `ACMEAccountingServices` | External accounting integration |

## Data Files

| File | Description |
|------|-------------|
| `members.txt` | Active member records |
| `suspendedmembers.txt` | Suspended member records |
| `provider.txt` | Provider records |
| `manager.txt` | Manager records |
| `servicerecords.txt` | Completed service records |

## Data Format

All data files use underscore (`_`) as field delimiter.

**Member Format:** `FirstName_LastName_Phone_Address_City_State_Zip_Email_Number`

**Provider Format:** `FirstName_LastName_Phone_Address_City_State_Zip_Number`

**Manager Format:** `FirstName_LastName_Phone_Address_City_State_Zip_Number`

**ServiceRecord Format:** `ProviderNumber_MemberNumber_ServiceCode_Fee_Date`

## Service Codes

| Code | Service | Fee |
|------|---------|-----|
| 1 | CONSULTATION | $12.99 |
| 2 | CONVERSATION | $8.99 |
| 3 | EMERGENCY | $29.99 |
