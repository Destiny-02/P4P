package payroll;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
public final class Input {
	private Scanner scan = new Scanner(System.in);
	public static List<Employee> list = new Vector<Employee>();
	public List<Employee> takeDetails(String filename) throws Error{
		File file = new File(filename);
		try {
			scan = new Scanner(file);
			while (scan.hasNextLine()) {
	        	String line = scan.nextLine();
	        	if (!line.contains("#") && (!line.equals(""))){
	          		storeValues(line.split("\t"),list);
	        	}
	        }
	        scan.close();
	        return list;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	private void storeValues(String[] employeeDetails, List<Employee> list) throws Error, NumFormatError{
		int taxID;
		try{
			taxID = Integer.parseInt(employeeDetails[0]);
		} catch (NumberFormatException e){
			throw new NumFormatError("At least one employee has a blank or incorrectly formatted tax ID");
		}
		String name = employeeDetails[1];
		if (!name.matches("(.*), (.*)")){
			throw new NumFormatError("The employee with the tax ID " + taxID + " has a name in the wrong format (" + name + ")");
		}
		String wageType = employeeDetails[2];
		float rate;
		float YTD;
		try{
			rate = Float.parseFloat(employeeDetails[3].replaceAll("[^\\d.]+", ""));
			YTD = Float.parseFloat(employeeDetails[4].replaceAll("[^\\d.]+", ""));
		} catch (NumberFormatException e){
			throw new NumFormatError(name + " (" + taxID + ") has a missing or incorrectly formatted rate and/or YTD");
		}
		String startDate = employeeDetails[5];
		String endDate = employeeDetails[6];
		float hours;
		float deductions;
		try{
			hours = Float.parseFloat(employeeDetails[7]);
			deductions = Float.parseFloat(employeeDetails[8].replaceAll("[^\\d.]+", ""));
		} catch (NumberFormatException e){
			throw new NumFormatError(name + " (" + taxID + ") has a missing or incorrectly formatted number of hours worked and/or deduction");
		}
		list.add( new Employee(taxID, name, wageType, rate, YTD, startDate, endDate,hours, deductions));
	}
}
