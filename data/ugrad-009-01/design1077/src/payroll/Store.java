package payroll;
import java.util.Date;
import java.util.Iterator;
public class Store {
	private final double _normalHours;
	private final double _timeAndAHalf;
	private final double[] _taxBrackets;
	private final double[] _taxRate;
	public Store() {
		_taxBrackets = new double[]{14000, 34000, 22000};
		_taxRate = new double[]{0.105, 0.175, 0.3, 0.33};
		_normalHours = 40;
		_timeAndAHalf = 43;
	}
	public Store(double[] taxBrackets, double[] taxRate, double normalHours, double timeAndAHalf) {
		_taxBrackets = taxBrackets;
		_taxRate = taxRate;
		_normalHours = normalHours;
		_timeAndAHalf = timeAndAHalf;
	}
	public double[] getTaxBrackets() {
		return _taxBrackets;
	}
	public double[] getTaxRate() {
		return _taxRate;
	}
	public double getNormalHours() {
		return _normalHours;
	}
	public double getTimeAndAHalf() {
		return _timeAndAHalf;
	}
	public void runProcess(String processToDo, EmployeeList employees) {
		checkTIDs(employees);
		switch(processToDo.toLowerCase())
		{
		case "payslips" :
			paySlips(employees);
			break;
		case "employees" :
			employees(employees);
			break;
		case "burden" :
			burden(employees);
			break;
		case "paye" :
			PAYE(employees);
			break;
		default :
			throw new PayrollException("Process not recognized: " + processToDo);
		}
	}
	private void paySlips(EmployeeList employees) {
		employees.sortEmployeesByTID();
		Iterator<Employee> employeeItr = employees.getEmployeeInfo();
		printCurrentDate();
		while (employeeItr.hasNext()) {
			Employee tempEmp = employeeItr.next();
			tempEmp.displayPayslip();
		}
	}
	private void employees(EmployeeList employees) {
		employees.sortEmployeesByName();
		Iterator<Employee> employeeItr = employees.getEmployeeInfo();
		printCurrentDate();
		while (employeeItr.hasNext()) {
			Employee tempEmp = employeeItr.next();
			tempEmp.displayEmployee();
		}
	}
	private void burden(EmployeeList employees) {
		printCurrentDate();
		employees.printPayPeriod();
		System.out.print("Total Burden: $" + String.format("%1$.2f", employees.getTotalBurden()) + "\n");
	}
	private void PAYE(EmployeeList employees) {
		printCurrentDate();
		employees.printPayPeriod();
		System.out.print("Total PAYE: $" + String.format("%1$.2f", employees.getTotalPAYE()) + "\n");
	}
	private void printCurrentDate() {
		Date currentDate = new Date();
		System.out.printf("%tF", currentDate);
		System.out.print("\n");
	}
	private void checkTIDs(EmployeeList employees) {
		employees.sortEmployeesByTID();
		Iterator<Employee> employeeItr = employees.getEmployeeInfo();
		int previousTID = -1;
		while (employeeItr.hasNext()) {
			Employee tempEmp = employeeItr.next();
			if (previousTID == -1) {
				previousTID = tempEmp.getTID();
			} else if (previousTID == tempEmp.getTID()){
				throw new PayrollException("Non-unique Tax ID.");
			}
		}
	}
}
