package Main;
import javax.swing.*;
import java.awt.*;
// This class is responsible for creating the graphical user interface (GUI) for updating employee details, allowing users to modify an employee's position and hourly rate, as well as delete the employee record if necessary.
public class UpdateEmployeeGUI extends JFrame {

    private JTextField employeeNumberField;
    private JTextField employeeNameField;
    private JTextField positionField;
    private JTextField hourlyRateField;

    private JButton saveButton;
    private JButton deleteButton;
    private JButton cancelButton;
    // This constructor initializes the update employee GUI with the provided employee number and name, and sets up the form fields and buttons for updating employee details.
    public UpdateEmployeeGUI(
            String employeeNumber,
            String employeeName) {

        setTitle("Employee Management");
        setSize(500, 320);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6,2,10,10));

        panel.add(new JLabel("Employee Number"));
        employeeNumberField = new JTextField(employeeNumber);
        employeeNumberField.setEditable(true);
        panel.add(employeeNumberField);

        panel.add(new JLabel("Employee Name"));
        employeeNameField = new JTextField(employeeName);
        employeeNameField.setEditable(true);
        panel.add(employeeNameField);
        
        panel.add(new JLabel("Position"));

        EmployeeData employeeData = new EmployeeData();
        String[] data = employeeData.getEmployee(employeeNumber);

        String position = "";
        String hourlyRate = "";

        if(data != null) {
            position = data[11];
            hourlyRate = data[18];
        }
        // Pre-fills the position and hourly rate fields with the current values from the employee data, allowing the user to see the existing details before making updates.
        positionField = new JTextField(position);
        panel.add(positionField);

        panel.add(new JLabel("Hourly Rate"));

        hourlyRateField = new JTextField(hourlyRate);
        panel.add(hourlyRateField);

        saveButton = new JButton("Save Changes");
        JButton addButton = new JButton("Add Employee");
        deleteButton = new JButton("Delete Employee");
        cancelButton = new JButton("Cancel");

        panel.add(saveButton);
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(cancelButton);

        add(panel);
        
        saveButton.addActionListener(e -> updateEmployee());
        addButton.addActionListener(e -> addEmployee());
        deleteButton.addActionListener(e -> deleteEmployee());
        cancelButton.addActionListener( e -> dispose());
    }  
    // Update employee record
    private void updateEmployee() {

    String employeeId = employeeNumberField.getText();
    String position = positionField.getText();
    String hourlyRate = hourlyRateField.getText();
  
    if(position.trim().isEmpty()) {

    JOptionPane.showMessageDialog(
            this,
            "Position cannot be empty.");

    return;
}

    if(hourlyRate.trim().isEmpty()) {

    JOptionPane.showMessageDialog(
            this,
            "Hourly Rate cannot be empty.");

    return;
}

    try {
        double rate = Double.parseDouble(hourlyRate);
        if(rate < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Hourly Rate cannot be negative.");
            return;
        }
    } catch(NumberFormatException ex) {
        JOptionPane.showMessageDialog(
                this,
                "Invalid Hourly Rate. Please enter a valid number.");
        return;
    }
    //confirm update
    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Save changes to this employee?",
        "Confirm Update",
        JOptionPane.YES_NO_OPTION);

        if(confirm != JOptionPane.YES_OPTION) {
        return;
        }

        //Save changes to CSV
    EmployeeData employeeData = new EmployeeData();
        boolean updated = employeeData.updateRecord(
            employeeId,
            position,
            hourlyRate);
        // Displays a success or failure message based on whether the employee record was successfully updated, providing feedback to the user about the outcome of their action.
    if(updated) {
        //employeeData.saveChangesToFile();
        JOptionPane.showMessageDialog(
                this,
                "Employee record updated successfully.");
    } else {
        JOptionPane.showMessageDialog(
                this,
                "Failed to update employee record.");
        }
}
private void addEmployee() {

    String employeeId = employeeNumberField.getText().trim();
    String employeeName = employeeNameField.getText().trim();
    String position = positionField.getText().trim();
    String hourlyRate = hourlyRateField.getText().trim();

    if (employeeId.isEmpty() || employeeName.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Employee Number and Name are required.");
        return;
    }

    EmployeeData employeeData = new EmployeeData();

    if (employeeData.getEmployee(employeeId) != null) {
        JOptionPane.showMessageDialog(this,
                "Employee already exists.");
        return;
    }

    String[] employee = new String[19];

    employee[0] = employeeId;

    String[] name = employeeName.split(" ", 2);

    if (name.length == 2) {
        employee[2] = name[0];
        employee[1] = name[1];
    } else {
        employee[2] = employeeName;
        employee[1] = "";
    }

    employee[11] = position;
    employee[18] = hourlyRate;

    // Fill remaining fields so there are no null values
    for (int i = 0; i < employee.length; i++) {
        if (employee[i] == null) {
            employee[i] = "";
        }
    }

    if (employeeData.addRecord(employee)) {
        JOptionPane.showMessageDialog(this,
                "Employee added successfully.");
    } else {
        JOptionPane.showMessageDialog(this,
                "Failed to add employee.");
    }
}

// This method deletes the employee record when the "Delete Employee" button is clicked, after confirming the action with the user, and provides feedback on whether the deletion was successful.
    private void deleteEmployee() {

    String employeeId = employeeNumberField.getText();

    int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete employee "
            + employeeId + "?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);
// If the user confirms the deletion, the employee record is removed from the data and the changes are saved to the file, with a success message displayed. If the deletion fails, an error message is shown.
    if (confirm == JOptionPane.YES_OPTION) {
        EmployeeData employeeData = new EmployeeData();
        boolean deleted = employeeData.deleteRecord(employeeId);
        if(deleted) {
            //employeeData.saveChangesToFile();
            JOptionPane.showMessageDialog(
                    this,
                    "Employee record deleted successfully.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to delete employee record.");
                }
        }
    }
}
