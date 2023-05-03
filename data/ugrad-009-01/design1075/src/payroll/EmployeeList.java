package payroll;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
public class EmployeeList implements Iterable<Employee> {
	private List<Employee> _employees;
	public EmployeeList(File file) throws IOException, EmployeeException{
		_employees = new ArrayList<Employee>();
		readAndProcessFile(file);
	}
	private void storeEmployeeInfo (String[] employeeInfo) throws EmployeeException {
		if (employeeInfo.length != 10) {
			throw new EmployeeException("Wrong number of info: either missing or unexpected info");
		}
		int TID = Integer.parseInt(employeeInfo[0]);
		String lastName = employeeInfo[1];
		String firstName = employeeInfo[2];
		String employment = employeeInfo[3];
		double rate = Double.parseDouble(employeeInfo[4]);
		double YTD = Double.parseDouble(employeeInfo[5]);
		String periodStart = employeeInfo[6];
		String periodEnd = employeeInfo[7];
		double hours = Double.parseDouble(employeeInfo[8]);
		double deduction = Double.parseDouble(employeeInfo[9]);
		if (employment.equals("Salaried")) {
			_employees.add(new SalariedEmployee(TID, lastName, firstName, employment, rate, YTD, periodStart, periodEnd, hours, deduction));
		}
		else if (employment.equals("Hourly")) {
			_employees.add(new HourlyEmployee(TID, lastName, firstName, employment, rate, YTD, periodStart, periodEnd, hours, deduction));
		}
	}
	private void readAndProcessFile(File file) throws IOException, EmployeeException {
		   FileReader fileReader = new FileReader(file);
	       BufferedReader bufferedReader  = new BufferedReader(fileReader);
	       String line;
	       while((line = bufferedReader.readLine()) != null){
	       	   if (line.isEmpty() || line.substring(0,1).equals("#"))
	       		   	continue;
	       	   if ((line.length() - line.replace("$", "").length()) < 3) {
	       		   throw new EmployeeException("Wrong money format: missing $");
	       	   }
	       	   else if ((line.length() - line.replace("-", "").length()) < 4) {
	       		   throw new EmployeeException("Wrong date format: missing -");
	       	   }
	       	   line = line.replaceAll("[,$]", "");
	           String[] employeeInfo  = line.split("\\s+");
	       	   storeEmployeeInfo(employeeInfo);
	       }
	       bufferedReader.close();
	       fileReader.close();
	}
	@Override
	public Iterator<Employee> iterator() {
		return _employees.iterator();
	}
	public void sortEmployees (String order) {
		if (order == "TID") {
			Collections.sort(_employees);
		}
		else if (order == "Name") {
			Collections.sort(_employees, new Employee.FamilyNameComparator());
		}
		else{
		}
	}
	public Employee giveRandomEmployee () {
		return _employees.get((int) (Math.random()*(_employees.size() - 1)));
	}
}
