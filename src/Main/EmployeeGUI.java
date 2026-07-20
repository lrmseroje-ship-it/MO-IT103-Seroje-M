package Main;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

// This class is responsible for creating the graphical user interface (GUI) for the MotorPH Employee Payroll System, allowing users to search for employees and process payroll based on selected criteria.
public class EmployeeGUI extends JFrame {
    private JButton searchButton;
    private JButton clearButton;
    private JButton updateEmployeeButton;
    private JButton viewDetailsButton;
    private JTextField employeeNumberField; // for entering the employee number to search for
    private JTextField employeeNameField; // for displaying the employee name based on the employee number entered (non-editable)
    private JButton processPayrollButton; // for processing payroll based on the employee number, month, and pay coverage entered
    private JButton payrollSummaryButton; // for viewing the payroll summary of the employee based on the employee number, month, and pay coverage entered
    private JComboBox<String> monthComboBox; // for selecting the month to process payroll for
    private JComboBox<String> payCoverageComboBox; // for selecting the pay coverage to process payroll for

    public EmployeeGUI() {
        setTitle("MotorPH Employee App");
        setSize(750, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        //title section
        JLabel titleLabel = new JLabel("MotorPH Employee Payroll System ");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        //form section
        JPanel formPanel = new JPanel();
        formPanel.setBorder(BorderFactory.createTitledBorder("Employee Information"));
        formPanel.setLayout(new GridLayout(4, 2, 10, 10));
        //employee number input
        formPanel.add(new JLabel("Employee Number"));
        employeeNumberField = new JTextField();
        formPanel.add(employeeNumberField);
        //employee name display (non-editable)
        formPanel.add(new JLabel("Employee Name"));
        employeeNameField = new JTextField();
        employeeNameField.setEditable(false); //makes the employee name field non-editable since it will be filled based on the employee number
        employeeNameField.setBackground(new Color(240,240,240)); //sets the background color of the employee name field to match the panel background, making it look like a label
        formPanel.add(employeeNameField);
       
        monthComboBox = new JComboBox<>(new String[]{ //list of months for payroll processing
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        });

        payCoverageComboBox = new JComboBox<>(new String[]{ //list of pay coverage options for payroll processing
            "1st Cutoff (1-15)",
            "2nd Cutoff (16-31)",
            "Whole Month"
        });
        //adding labels and input fields to the form panel
        formPanel.add(new JLabel("Month"));
        formPanel.add(monthComboBox);
        //adding pay coverage options to the form panel
        formPanel.add(new JLabel("Pay Coverage"));
        formPanel.add(payCoverageComboBox);
        //buttons section
        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");
        processPayrollButton = new JButton("Process Payroll");
        processPayrollButton.setEnabled(false);
        updateEmployeeButton = new JButton("Update Employee");
        viewDetailsButton = new JButton("View Details");
        payrollSummaryButton = new JButton("View Payroll Summary");

        JPanel buttonPanel = new JPanel();
        //adding buttons to the button panel
        buttonPanel.add(searchButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(processPayrollButton);
        buttonPanel.add(updateEmployeeButton);
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(payrollSummaryButton);

        //adding action listeners to the buttons
        processPayrollButton.addActionListener(e -> processPayroll());
        searchButton.addActionListener(e -> searchEmployee());
        clearButton.addActionListener(e -> clearFields());
        updateEmployeeButton.addActionListener(e -> openUpdateEmployeeWindow());
        viewDetailsButton.addActionListener(e -> openEmployeeDetailsWindow());
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    //method to process payroll based on the employee number, month, and pay coverage entered
    private void processPayroll() {
        String employeeNumber = employeeNumberField.getText();
        String employeeName = employeeNameField.getText();
        String month = (String) monthComboBox.getSelectedItem();
        String payCoverage = (String) payCoverageComboBox.getSelectedItem();
        PayrollProcessing payroll = new PayrollProcessing();
        String result = payroll.processPayroll(employeeNumber, employeeName, month, payCoverage);

        JOptionPane.showMessageDialog( this, result, "Employee Payroll Summary", JOptionPane.INFORMATION_MESSAGE);

    if(employeeNumber.trim().isEmpty()) {
        JOptionPane.showMessageDialog(
                this,
                "Please search for an employee first.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        return;
    }
}
    //method to search for an employee based on the employee number entered and display the employee name in the employee name field
    private void searchEmployee() {
        String employeeNumber = employeeNumberField.getText();
        employeeNameField.setText(employeeNameField.getText()); // Clear employee name field before searching
        processPayrollButton.setEnabled(true); // Enable process payroll button when employee is found

    if(employeeNumber.trim().isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "Please enter the employee number to proceed.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        return;
    }

    EmployeeData employeeData = new EmployeeData();
    String[] employee = employeeData.getEmployee(employeeNumber);

    if (employee != null) {
        employeeNameField.setText(employee[1] + "," + employee[2]); // Display employee name in the format "Last Name, First Name"
        processPayrollButton.setEnabled(true); // Enable process payroll button when employee is found

    } else {
        JOptionPane.showMessageDialog(
                this,
                "Employee number not found.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        processPayrollButton.setEnabled(false); // Disable process payroll button when employee is not found
    }
}
    private void clearFields() {
        employeeNumberField.setText("");
        employeeNameField.setText("");
    }
    private void openUpdateEmployeeWindow() {

    String employeeNumber =
            employeeNumberField.getText();

    if(employeeNumber.trim().isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "Please search for an employee first.",
                "Error",
                JOptionPane.ERROR_MESSAGE);

        return;
    }

    UpdateEmployeeGUI updateWindow =
        new UpdateEmployeeGUI(
                employeeNumberField.getText(),
                employeeNameField.getText());

    updateWindow.setVisible(true);
    }
    private void openEmployeeDetailsWindow() {
        String employeeNumber = employeeNumberField.getText();

        if(employeeNumber.trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please search for an employee first.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

            return;
        }

        EmployeeDetailsGUI detailsWindow = new EmployeeDetailsGUI(employeeNumber);
        detailsWindow.setVisible(true);

    }

}



