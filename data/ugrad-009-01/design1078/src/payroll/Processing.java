package payroll;
import java.util.ArrayList;
import java.util.Collections;
import payroll.Employee.EmployeeComparator;
public class Processing {
	public enum Processes {
		PAYSLIPS, EMPLOYEES, PAYE, BURDEN;
	}
	public void displayProcess(ArrayList <Employee> employeeList, Processes processes) {
		switch (processes) {
		case PAYSLIPS:
			displayPayslips(employeeList);
			break;
		case EMPLOYEES:
			displayEmployees(employeeList);
			break;
		case PAYE:
			employeeList.get(0).dispPaye(employeeList);
			break;
		case BURDEN:
			employeeList.get(0).dispBurden(employeeList);
			break;
		}
	}
	private void displayPayslips(ArrayList <Employee> employeeList) {
		Collections.sort(employeeList, EmployeeComparator.employeeTIDComparator);
		for (Employee e: employeeList){
			System.out.println(e.generatePayslip(e));
		}
	}
	private void displayEmployees(ArrayList <Employee> employeeList){
		Collections.sort(employeeList, EmployeeComparator.employeeTIDComparator);
		Collections.sort(employeeList, EmployeeComparator.employeeGivenName);
		Collections.sort(employeeList, EmployeeComparator.employeeFamilyName);
		for (Employee e: employeeList){
			System.out.println(e);
		}
	}
	public boolean isValidProcess(String args){
		for (Processes p: Processes.values()){
			if (p.name().equals(args)){
				return true;
			}
		}
		return false;
	}
	protected String formatCurrency(Double currency){
		return String.format("%.02f", currency);
	}
	protected String formatName(String name) {
		String[] nameTokens = name.split(",");
		if (nameTokens[1].substring(0, 1).equals(" ")){
			nameTokens[1] = nameTokens[1].substring(1);
		}
		return " " + nameTokens[1] + " " + nameTokens[0] + ", ";
	}
	protected String formatPeriod(String start, String end){
		return start + " to " + end;
	}
}
