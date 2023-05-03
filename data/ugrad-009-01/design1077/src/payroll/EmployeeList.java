package payroll;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
public class EmployeeList {
	private ArrayList<Employee> _employees = new ArrayList<>();
	private Store _store;
	private String _startDate = "";
	private String _endDate = "";
	public EmployeeList(Store store) {
		_store = store;
	}
	public void addEmployee(String[] employeeInfo) {
		setDates(employeeInfo);
		Employee newEmp = new Employee(employeeInfo, _store);
		_employees.add(newEmp);
	}
	public Iterator<Employee> getEmployeeInfo() {
		Iterator<Employee> employeeItr = _employees.iterator();
		return employeeItr;
	}
	public double getTotalBurden() {
		double totalBurden = 0;
		for (Employee emp : _employees) {
			totalBurden  += emp.getGross();
		}
		return totalBurden;
	}
	public double getTotalPAYE() {
		double totalPAYE = 0;
		for (Employee emp : _employees) {
			totalPAYE += emp.getPAYE();
		}
		return totalPAYE;
	}
	public void sortEmployeesByName() {
		Collections.sort(_employees, sortByName());
	}
	public void sortEmployeesByTID() {
		Collections.sort(_employees, sortByTID());
	}
	public void printPayPeriod() {
		System.out.print(_startDate + " to " + _endDate + "\n");
	}
	public Comparator<Employee> sortByName() {
		return new Comparator<Employee>() {
			public int compare(Employee emp1, Employee emp2) {
				String emp1Name = emp1.getFamilyName().toLowerCase();
				String emp2Name = emp2.getFamilyName().toLowerCase();
				if (emp1Name.equals(emp2Name)) {
					emp1Name = emp1.getGivenName().toLowerCase();
					emp2Name = emp2.getGivenName().toLowerCase();
				}
				if (emp1Name.equals(emp2Name)) {
					int emp1TID = emp1.getTID();
					int emp2TID = emp2.getTID();
					return emp1TID - emp2TID;
				}
				return emp1Name.compareTo(emp2Name);
			}
		};
	}
	public Comparator<Employee> sortByTID() {
		return new Comparator<Employee>() {
			public int compare(Employee emp1, Employee emp2) {
				int emp1TID = emp1.getTID();
				int emp2TID = emp2.getTID();
				return emp1TID - emp2TID;
			}
		};
	}
	private void setDates(String[] employeeInfo) {
		if (_startDate.isEmpty() && _endDate.isEmpty()) {
			_startDate = employeeInfo[5];
			_endDate = employeeInfo[6];
		} else if (!_startDate.equals(employeeInfo[5]) || !_endDate.equals(employeeInfo[6])) {
			throw new PayrollException("Inconsistant Dates for: " + employeeInfo[1] + " or the first employee in the list.");
		}
	}
}
