package Main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
// This class is responsible for loading employee details from a CSV file and providing access to employee information based on employee number.
public class EmployeeData {
    private Map<String, String[]> employeeMap;

    public EmployeeData() {
        employeeMap = loadEmployees();
    }

    private Map<String, String[]> loadEmployees() {

        Map<String, String[]> employeeMap =
                new HashMap<>();

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(
                                     "resources/MotorPH_Employee Data - Employee Details.csv"))) {

            String line;

            reader.readLine();

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty())
                    continue;

                String[] data =
                        line.split(
                                ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                employeeMap.put(
                        data[0],
                        data);
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
}
