package payroll.employees;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
public class EmployeeList extends ArrayList<EmployeeRecord> {
	private static final long serialVersionUID = -4979207783885207356L;
	public void sortByName() {
		Collections.sort(this, new NameComparator());
	}
	public void sortByTID() {
		Collections.sort(this, new TIDComparator());
	}
}
class TIDComparator implements Comparator<EmployeeRecord> {
	public int compare(EmployeeRecord employeeOne, EmployeeRecord employeeTwo) {
		if (employeeOne.taxID() > employeeTwo.taxID()) {
			return 1;
		} else if (employeeOne.taxID() < employeeTwo.taxID()) {
			return -1;
		}
		return 0;
	}
	public boolean equals(EmployeeRecord employeeOne, EmployeeRecord employeeTwo) {
		return (compare(employeeOne, employeeTwo) == 0);
	}
}
class NameComparator implements Comparator<EmployeeRecord> {
	public int compare(EmployeeRecord employeeOne, EmployeeRecord employeeTwo) {
		return employeeOne.lastFirstName().compareTo(employeeTwo.lastFirstName());
	}
	public boolean equals(EmployeeRecord employeeOne, EmployeeRecord employeeTwo) {
		return (compare(employeeOne, employeeTwo) == 0);
	}
}