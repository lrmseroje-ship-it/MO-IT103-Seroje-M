package Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.Duration;
import java.time.LocalDate;
// This class is responsible for loading attendance records from a CSV file and computing hours worked based on login and logout times.
public class Attendance {
    public static Map<String, List<String[]>> loadAttendance() {

	    Map<String, List<String[]>> attendanceMap = new HashMap<>();

	    try (BufferedReader reader = new BufferedReader(
	            new FileReader("resources/MotorPH_Employee Data - Attendance Record.csv"))) {

	        String line;
	        reader.readLine();

	        while ((line = reader.readLine()) != null) {

	            if (line.trim().isEmpty()) continue;

	            String[] data = line.split(",");

	            String empId = data[0];
	            // this creates list if employee that does not exist yet, then add record
	            attendanceMap.putIfAbsent(empId, new ArrayList<>());
	            attendanceMap.get(empId).add(data);
	        }

	    } catch (Exception e) {
	        System.out.println("Error loading attendance.");
	    }

	    return attendanceMap;
	}
	// Compute hours worked between login and logout times
	// - 1 hour lunch break is deducted
	public static double computeHours(LocalTime login, LocalTime logout) {
		
		LocalTime startTime = LocalTime.of(8, 0);
		LocalTime graceTime = LocalTime.of(8, 10);
		LocalTime cutoffTime = LocalTime.of(17, 0);
		//Adjust login time if the employee started earlier than 8
		if(login.isBefore(startTime)) {
			login = startTime;
		}
		//this limits the log out time to official cutoff
		if (logout.isAfter(cutoffTime)) {
			logout = cutoffTime;
		}
		
		long minutesWorked = Duration.between(login, logout).toMinutes();
		// 1 hour Lunch break deduction
		if (minutesWorked > 60) {
			minutesWorked -= 60; 
		}
		
		double hours = minutesWorked / 60.0;
		//return full 8 hours if employee is on time
		if (!login.isAfter(graceTime) && logout.equals(cutoffTime)) {
			return 8.0; // Full 8 hours if on time
			}
		return Math.min(hours, 8.0);
		
	}
	// This method calculates the total hours worked for an employee in the first and second cutoff periods of a given month, based on their attendance records.
	public static double[] getCutoffHours(String employeeId, int month) { 

    Map<String, List<String[]>> attendanceMap = loadAttendance();

    double firstCutoffHours = 0;
    double secondCutoffHours = 0;

    List<String[]> records = attendanceMap.get(employeeId);

    if(records == null) {
        return new double[]{0,0};
    }

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
	
	for (String[] record : records) {

    try {

        LocalDate date = LocalDate.parse(record[3].trim(), dateFormatter);

        if (date.getMonthValue() != month) {
            continue;
        }

        LocalTime login = LocalTime.parse(record[4].trim(), timeFormatter);
        LocalTime logout = LocalTime.parse(record[5].trim(), timeFormatter);
        double hours = computeHours(login, logout);

        if (date.getDayOfMonth() <= 15) {
            firstCutoffHours += hours;
        } else {
            secondCutoffHours += hours;
        }

    } catch (Exception e) {

        System.out.println(
            "Error processing attendance record for Employee "
            + employeeId);

    }
}

    return new double[]{
        firstCutoffHours,
        secondCutoffHours
    };
	}
}
	
