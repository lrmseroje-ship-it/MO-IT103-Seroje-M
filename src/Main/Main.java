package Main;
// This is the main class that serves as the entry point of the MotorPH Employee Payroll System application, responsible for launching the graphical user interface (GUI) for employee management and payroll processing.	
public class Main {
	public static void main(String[] args) {
		EmployeeGUI gui = new EmployeeGUI();
		gui.setVisible(true);
	}
}