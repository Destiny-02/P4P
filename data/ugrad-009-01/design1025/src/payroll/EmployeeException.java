package payroll;
import java.util.ArrayList;
@SuppressWarnings("serial")
public class EmployeeException extends RuntimeException{
	private ArrayList<Employees> _employees;
	public EmployeeException(ArrayList<Employees> employees) {
		_employees = employees;
	}
	public EmployeeException() {}
	public EmployeeException(String message)
	{
		super(message);
	}
	public void checkInput(String line) throws EmployeeException {
		String[] details = line.split("\t");
		if (details.length != 9) {
			throw new EmployeeException("Incorrect input format : " + line);
		}
		if (Employees.find(Integer.valueOf(details[0]), _employees, true) != -1) {
			throw new EmployeeException("Duplicate TaxID found : " + details[0]);
		}
		if (!details[3].substring(0, 1).equals("$") || !details[4].substring(0, 1).equals("$") || !details[8].substring(0, 1).equals("$")) {
			throw new EmployeeException("Money in wrong format for employee with Tax ID : " + details[0]);
		}
	}
	public void checkEmployees() throws EmployeeException {
		if (_employees.size() == 0) {
			throw new EmployeeException("No employees found");
		}
		for (Employees employee: _employees) {
			if (employee.getName().split(", ").length != 2) {
				throw new EmployeeException("Name in wrong format : " + employee.getName());
			}
			if (!employee.getEmployment().equals("Salaried") && !employee.getEmployment().equals("Hourly")) {
				throw new EmployeeException("Invalid employment type : " + employee.getEmployment());
			}
			if (employee.getDeductions() < 0 || employee.getRate() < 0 || employee.getYTD() < 0) {
				throw new EmployeeException("Negative numbers for employee with Tax ID : " + employee.getTID());
			}
			if (employee.getTID() < 0) {
				throw new EmployeeException("Invalid TID number: " + employee.getTID());
			}
		}
	}
	public static void checkArgs(String[] args) throws EmployeeException {
		if (args.length != 2) {
			throw new EmployeeException("Incorrect number of input arguments : " + args.length + ", must be 2");
		}
	}
}
