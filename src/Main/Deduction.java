package Main;

import java.io.BufferedReader;
import java.io.FileReader;
// This class is responsible for computing various deductions such as SSS, PhilHealth, PagIbig, and tax based on the employee's salary and taxable income.
public class Deduction {
    //compute SSS contribution based on salary range from the SSS CSV file
	public double computeSSS(double basicSalary) {
		//maximum SSS salary cap
		if (basicSalary > 24750) {
	        basicSalary = 24750;
	    }
		
		try (
			//reads the SSS contribution details based on salary range
		        BufferedReader reader = new BufferedReader(new FileReader("resources/SSS Contribution.csv"))){
		        
		        String line;
		        reader.readLine();
		        
		        while ((line = reader.readLine()) != null) {
		        	
		            if (line.trim().isEmpty()) continue;
		           // used to ignore non-numeric rows, as in the CSV there is below the salary then over the salary range
		            //if this is not added, error would occur
		            if (line.toLowerCase().contains("below") || line.toLowerCase().contains("over")) {
		                continue;
		            }
		            line = line.replace(",", "");
		            //extract numeric values from line
		            String cleaned = line.replaceAll("[^0-9. ]", " ").trim();
		            String[] numbers = cleaned.split("\\s+");

		            if (numbers.length < 3) continue;

		            double minSalary = Double.parseDouble(numbers[0]);
		            double maxSalary = Double.parseDouble(numbers[1]);
		            double contribution = Double.parseDouble(numbers[2]);
		            //return contribution if employee salary falls within range
		            if (basicSalary >= minSalary && basicSalary <= maxSalary) {
		                return contribution;
		            }
		            
		        }
		       
		 } catch (Exception e) {
		     System.out.println("Error reading SSS Contribution file.");
		    //e.printStackTrace(); // used for debugging to find where the issue is at
		 	}
		 	
		    return 0;
		}
        //compute PhilHealth contribution based on employee's salary
	public double computePhilHealth(double basicSalary) {
		// PhilHealth contribution thresholds for 2024
		double premiumRate = 0.03;
	    double minContribution = 300;
	    double maxContribution = 1800;

	    double premium;

	    // this is where we apply range rules
	    if (basicSalary < 10000) {
	        premium = minContribution;
	    } else if (basicSalary <= 60000) {
	        premium = basicSalary * premiumRate;
	    } else {
	        premium = maxContribution;
	    }

	    // Apply cap (safety)
	    if (premium > maxContribution) {
	        premium = maxContribution;
	    }

	    // Employee pays half and the employers pay half as well
	    return premium / 2;
	}
	//compute PagIbig contribution based on employee's salary
	// returns fixed maximum contribution based on policy
	public double computePagibig(double basicSalary) {
		
		if (basicSalary <=1000) {
			basicSalary = 1000;
		}
		if (basicSalary >1500) {
			basicSalary = 1500;
		}
		//double employeeContribution = basicSalary * 0.01;
		double employeeMaxContribution = 100;
		
		return employeeMaxContribution;
	}
	//compute withholding based on the taxable income
	public double computeTax(double taxableIncome) {
		//Tax bracket threshold for 2024
		//computes total working hours based on company rules:
		final int TAX_BRACKET_1 = 20832;
		final int TAX_BRACKET_2 = 33333;
		final int TAX_BRACKET_3 = 66667;
		final int TAX_BRACKET_4 = 166667;
		final int TAX_BRACKET_5 = 666667;
		final int TAX_BRACKET_6 = 666667;
		
		// the condition for the tax deductions based on salary
		if (taxableIncome <= TAX_BRACKET_1) {
			return 0;
		}
		else if (taxableIncome < TAX_BRACKET_2) {
			return (taxableIncome - TAX_BRACKET_1) * 0.20;
		}
		else if (taxableIncome < TAX_BRACKET_3) {
			return 2500 + (taxableIncome - TAX_BRACKET_2) * 0.25;
		}
		else if (taxableIncome < TAX_BRACKET_4) {
			return 10833 + (taxableIncome - TAX_BRACKET_3) * 0.30;
		}
		else if (taxableIncome < TAX_BRACKET_5) {
			return 40833.33 + (taxableIncome - TAX_BRACKET_4) * 0.32;
		}
		else {
			return 200833.33 + (taxableIncome - TAX_BRACKET_6) * 0.35;
		}
		
	}
}
