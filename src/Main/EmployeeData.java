package Main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import java.io.BufferedWriter;
import java.io.FileWriter;
// This class is responsible for loading employee details from a CSV file and providing access to employee information based on employee number.
public class EmployeeData {
    private HashMap<String, String[]> employeeMap;

    public EmployeeData() {
        employeeMap = loadEmployees();
    }

    private HashMap<String, String[]> loadEmployees() {

        HashMap<String, String[]> employeeMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader( new FileReader( "resources/MotorPH_Employee Data - Employee Details.csv"))) {
        String line;
        reader.readLine();

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty())
                    continue;

                String[] data = line.split( ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                employeeMap.put( data[0], data);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error loading employee file.");
        }

        return employeeMap;
    }

    public String[] getEmployee(
            String employeeNumber) {

        return employeeMap.get(employeeNumber);
    }
    public ArrayList<String[]> getAllEmployees() {
        return new ArrayList<>(employeeMap.values());
    }

    public boolean updateRecord(
        String employeeId,
        String position,
        String hourlyRate) {

    String[] employee = employeeMap.get(employeeId);

    if (employee == null) {
        return false;
    }

    employee[11] = position;
    employee[18] = hourlyRate;

    saveChangesToFile();

    return true;
}

public boolean addRecord(String[] employee) {

    String employeeId = employee[0];

    if (employeeMap.containsKey(employeeId)) {
        return false;
    }

    employeeMap.put(employeeId, employee);

    saveChangesToFile();

    return true;
}
    public boolean deleteRecord(
        String employeeId) {

    if(employeeMap.containsKey(employeeId)) {
        employeeMap.remove(employeeId);

        saveChangesToFile();
        
        return true;
    }

    return false;
}
//this method is used to save changes
public void saveChangesToFile() {

    try {

        BufferedWriter writer = new BufferedWriter( new FileWriter("resources/MotorPH_Employee Data - Employee Details.csv"));

        writer.write(
                "Employee #,Last Name,First Name,Birthday,Address,"
                + "Phone Number,SSS #,Philhealth #,TIN #,Pag-ibig #,"
                + "Status,Position,Immediate Supervisor,Basic Salary,"
                + "Rice Subsidy,Phone Allowance,Clothing Allowance,"
                + "Gross Semi-monthly Rate,Hourly Rate");

        writer.newLine();

        for(String[] employee :
                employeeMap.values()) {

            writer.write(
                    String.join(",",
                            employee));
            writer.newLine();
        }

        writer.close();

    }
    catch(Exception e) {

        JOptionPane.showMessageDialog(
            null,
            "Error saving employee file: "
            + e.getMessage(),
            "File Error",
            JOptionPane.ERROR_MESSAGE);
    }
}
public HashMap<String, String[]> getEmployeeMap() {
    return employeeMap; 
}
}
