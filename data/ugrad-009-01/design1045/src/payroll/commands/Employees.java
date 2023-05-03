package payroll.commands;
import java.text.DecimalFormat;
import payroll.Payroll;
import payroll.employees.EmployeeList;
import payroll.employees.EmployeeRecord;
public class Employees {
	EmployeeList _employeeList;
	public Employees(EmployeeList employeeList) {
		_employeeList = employeeList;
		sortList();
		print();
	}
	void sortList() {
		_employeeList.sortByName();
	}
	void print() {
		System.out.println(Payroll.dateToday());
		DecimalFormat df = new DecimalFormat("0.00");
		for (EmployeeRecord er : _employeeList) {
			String emp = "" + er.lastFirstName() + " (" + er.taxID() + ") " + er.paidBy() + ", $" + df.format(er.rate())
					+ " YTD:$" + df.format(er.calcYTD());
			System.out.println(emp);
		}
	}
}