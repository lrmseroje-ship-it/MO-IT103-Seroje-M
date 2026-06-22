package Main;

import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
// This class is responsible for processing the payroll for employees by calculating gross salary, deductions, and net salary based on attendance records and employee details.
public class PayrollProcessing {

    private EmployeeData employeeData;
    private Deduction deduction;

    public PayrollProcessing() {
        employeeData = new EmployeeData();
        deduction = new Deduction();
    }

    public String processPayroll(
            String employeeNumber,
            String employeeName,
            String month,
            String payCoverage) {

        Month monthObj = Month.valueOf(month.toUpperCase());
        int monthNumber = monthObj.getValue();

        Map<String, List<String[]>> attendanceMap = Attendance.loadAttendance();
    
        StringBuilder result = new StringBuilder();

        String firstName = "";
        String lastName = "";
        String birthday = "";

        double basicSalary = 0;
        double hourlyRate = 0;

        double firstCutOff = 0;
        double secondCutOff = 0;

        String[] data = employeeData.getEmployee(employeeNumber);

        if (data == null) {
            return "Employee number does not exist.";
        }

        lastName = data[1];
        firstName = data[2];
        birthday = data[3];

        try {
            basicSalary = Double.parseDouble(
                    data[13].replace(",", "")
                            .replace("\"", "")
                            .trim());
        } catch (NumberFormatException e) {
            basicSalary = 0;
        }

        try {
            hourlyRate = Double.parseDouble(
                    data[18].replace(",", "")
                            .replace("\"", "")
                            .trim());
        } catch (NumberFormatException e) {
            hourlyRate = 0;
        }

        String monthName = monthObj.getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");

        int daysInMonth = YearMonth.of(2024, monthNumber).lengthOfMonth();

        List<String[]> records = attendanceMap.get(employeeNumber);

        if (records != null) {

            for (String[] record : records) {

                String[] dateParts = record[3].split("/");
                int recordMonth = Integer.parseInt(dateParts[0]);
                int day = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);

                if (year != 2024 || recordMonth != monthNumber) {
                continue;
                }

                LocalTime login = LocalTime.parse(record[4].trim(), timeFormat);
                LocalTime logout = LocalTime.parse( record[5].trim(), timeFormat);
                double hours = Attendance.computeHours(login, logout);

                if (day <= 15) { firstCutOff += hours;
                } else {
                    secondCutOff += hours;
                }
            }
        }

        double firstGrossSalary = firstCutOff * hourlyRate;
        double secondGrossSalary = secondCutOff * hourlyRate;
        double monthlyGrossSalary = firstGrossSalary + secondGrossSalary;
        double sss = deduction.computeSSS(basicSalary);
        double philHealth = deduction.computePhilHealth(basicSalary);
        double pagibig = deduction.computePagibig(basicSalary);
        double taxableIncome = monthlyGrossSalary - (sss + philHealth + pagibig);
        double tax = deduction.computeTax(taxableIncome);
        double totalDeductions = sss + philHealth + pagibig + tax;
        double firstNetSalary = firstGrossSalary;
        double secondNetSalary = secondGrossSalary - totalDeductions;

        result.append("================================================\n");
        result.append("PAYROLL SUMMARY\n");
        result.append("================================================\n");

        result.append("Employee Number : ")
              .append(employeeNumber)
              .append("\n");

        result.append("Employee Name   : ")
              .append(lastName)
              .append(", ")
              .append(firstName)
              .append("\n");

        result.append("Birthday        : ")
              .append(birthday)
              .append("\n");

      result.append("Hourly Rate      : ₱")
              .append(String.format("%.2f", hourlyRate))
              .append("\n");  

        result.append("Month           : ")
              .append(monthName)
              .append("\n\n");

        result.append("[ FIRST CUTOFF ]\n");
        result.append("Period          : ")
              .append(monthName)
              .append(" 1 - ")
              .append(monthName)
              .append(" 15\n");

        result.append("Hours Worked    : ")
              .append(firstCutOff)
              .append("\n");

        result.append("Gross Salary    : ₱")
              .append(String.format("%.2f", firstGrossSalary))
              .append("\n");

        result.append("Net Salary      : ₱")
              .append(String.format("%.2f", firstNetSalary))
              .append("\n\n");

        result.append("[ SECOND CUTOFF ]\n");

        result.append("Period          : ")
              .append(monthName)
              .append(" 16 - ")
              .append(monthName)
              .append(" ")
              .append(daysInMonth)
              .append("\n");

        result.append("Hours Worked    : ")
              .append(secondCutOff)
              .append("\n");

        result.append("Gross Salary    : ₱")
              .append(String.format("%.2f", secondGrossSalary))
              .append("\n\n");

        result.append("Government Deductions\n");
        result.append("SSS             : ₱")
              .append(String.format("%.2f", sss))
              .append("\n");

        result.append("PhilHealth      : ₱")
              .append(String.format("%.2f", philHealth))
              .append("\n");

        result.append("Pag-IBIG        : ₱")
              .append(String.format("%.2f", pagibig))
              .append("\n");

        result.append("Tax             : ₱")
              .append(String.format("%.2f", tax))
              .append("\n");

        result.append("Net Salary      : ₱")
              .append(String.format("%.2f", secondNetSalary))
              .append("\n\n");

        result.append("[ MONTHLY SUMMARY ]\n");

        result.append("Total Gross     : ₱")
              .append(String.format("%.2f", monthlyGrossSalary))
              .append("\n");

        result.append("Deductions      : ₱")
              .append(String.format("%.2f", totalDeductions))
              .append("\n");

        result.append("Total Net       : ₱")
              .append(String.format("%.2f",
                      firstNetSalary + secondNetSalary))
              .append("\n");

        result.append("================================================");

        return result.toString();
    }
}