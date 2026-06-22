# MotorPH Employee Payroll System

## Overview

The MotorPH Employee Payroll System is a Java-based desktop application developed as part of the MO-IT103 course requirements. The system allows users to manage employee records, view employee details, process payroll based on attendance records, and maintain employee information through update and delete operations.

The application utilizes Java Swing for its graphical user interface (GUI) and CSV files for data storage and retrieval.

---

## Features

### Employee Record Management

* Search employees using Employee Number
* Display employee information automatically
* View complete employee details, including:

  * Employee Number
  * Name
  * Birthday
  * Address
  * Phone Number
  * SSS Number
  * PhilHealth Number
  * TIN Number
  * Pag-IBIG Number
  * Employment Status
  * Position
  * Immediate Supervisor
  * Basic Salary
  * Hourly Rate

### Payroll Processing

* Process payroll for:

  * 1st Cutoff (1–15)
  * 2nd Cutoff (16–31)
  * Whole Month
* Calculate hours worked from attendance records
* Compute Gross Salary and Net Salary
* Compute government deductions:

  * SSS
  * PhilHealth
  * Pag-IBIG
  * Withholding Tax
* Generate detailed payroll summaries

### Employee Maintenance

* Update employee Position
* Update employee Hourly Rate
* Delete employee records
* Save changes directly to the CSV file
* Confirmation dialogs before update and delete actions
* Input validation for employee data

### User Interface

* User-friendly GUI built using Java Swing
* Error handling through JOptionPane dialogs
* Read-only display for employee information where applicable
* Separate windows for:

  * Payroll Processing
  * Employee Details
  * Employee Maintenance

---

## Technologies Used

* Java
* Java Swing
* CSV File Storage
* Visual Studio Code
* Git
* GitHub

---

## Project Structure

```text
src/
 └── Main/
      ├── Main.java
      ├── EmployeeGUI.java
      ├── EmployeeData.java
      ├── EmployeeDetailsGUI.java
      ├── UpdateEmployeeGUI.java
      ├── Attendance.java
      ├── Deduction.java
      └── PayrollProcessing.java

resources/
 ├── MotorPH_Employee Data - Employee Details.csv
 ├── MotorPH_Employee Data - Attendance Record.csv
 ├── Pag-ibig Contribution.csv
 ├── Philhealth Contribution.csv
 ├── SSS Contribution.csv
 ├── Withholding Tax.csv
 └── motorph_logo.png
```

---

## How to Run

### 1. Clone the Repository

```bash
git clone https://github.com/lrmseroje-ship-it/MO-IT103-Seroje-M.git
```

### 2. Open the Project

Open the project folder using Visual Studio Code or your preferred Java IDE.

### 3. Ensure Java JDK is Installed

Verify that Java JDK is installed and configured correctly.

### 4. Run the Application

Run:

```bash
Main.java
```

### 5. Launch the System

The MotorPH Employee Payroll System window will open.

---

## Usage

### Search Employee

1. Enter an Employee Number.
2. Click **Search**.
3. Employee information will be displayed automatically.

### View Employee Details

1. Search for an employee.
2. Click **View Employee Details**.
3. Review the complete employee profile.

### Process Payroll

1. Search for an employee.
2. Select the desired Month.
3. Select the Pay Coverage:

   * 1st Cutoff
   * 2nd Cutoff
   * Whole Month
4. Click **Process Payroll**.
5. Review the generated payroll summary.

### Update Employee Information

1. Search for an employee.
2. Click **Update Employee**.
3. Modify the Position and/or Hourly Rate.
4. Click **Save Changes**.
5. Confirm the update.

### Delete Employee Record

1. Search for an employee.
2. Click **Update Employee**.
3. Click **Delete Employee**.
4. Confirm the deletion.
5. The employee record will be removed and saved to the CSV file.

---

## Validation and Error Handling

The application includes:

* Validation for empty fields
* Validation for invalid hourly rates
* Prevention of negative hourly rates
* Confirmation dialogs before updates and deletions
* Error handling for file operations
* Employee search validation

---

## Author

**Marjury Seroje**

GitHub Repository:

https://github.com/lrmseroje-ship-it/MO-IT103-Seroje-M

---

## Course Information

**Course:** MO-IT103 – Computer Programming 2

**Project:** MotorPH Employee Payroll System

**Milestone Completed:** Features 2, 3, and 4

---

## License

This project was developed for academic purposes only.
