package Main;
import javax.swing.*;
import java.awt.*;
// This class is for the employee details window
public class EmployeeDetailsGUI extends JFrame {
    public EmployeeDetailsGUI(
            String employeeNumber) {

        setTitle("Employee Details");
        setSize(700,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        EmployeeData employeeData = new EmployeeData();

        String[] data = employeeData.getEmployee( employeeNumber);

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                0,2,10,10));

        if(data != null) {

            panel.add(new JLabel("Employee Number"));
            panel.add(new JLabel(data[0]));
            panel.add(new JLabel("Last Name"));
            panel.add(new JLabel(data[1]));
            panel.add(new JLabel("First Name"));
            panel.add(new JLabel(data[2]));
            panel.add(new JLabel("Birthday"));
            panel.add(new JLabel(data[3]));
            panel.add(new JLabel("Address"));
            panel.add(new JLabel(data[4]));
            panel.add(new JLabel("Phone Number"));
            panel.add(new JLabel(data[5]));
            panel.add(new JLabel("SSS #"));
            panel.add(new JLabel(data[6]));
            panel.add(new JLabel("PhilHealth #"));
            panel.add(new JLabel(data[7]));
            panel.add(new JLabel("TIN #"));
            panel.add(new JLabel(data[8]));
            panel.add(new JLabel("Pag-IBIG #"));
            panel.add(new JLabel(data[9]));
            panel.add(new JLabel("Status"));
            panel.add(new JLabel(data[10]));
            panel.add(new JLabel("Position"));
            panel.add(new JLabel(data[11]));
            panel.add(new JLabel("Immediate Supervisor"));
            panel.add(new JLabel(data[12]));
            panel.add(new JLabel("Basic Salary"));
            panel.add(new JLabel(data[13]));
            panel.add(new JLabel("Hourly Rate"));
            panel.add(new JLabel(data[18]));
        }

        add(new JScrollPane(panel));
    }
}
