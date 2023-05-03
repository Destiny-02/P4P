package payroll.commands;
import java.text.DecimalFormat;
import payroll.Payroll;
import payroll.employees.EmployeeList;
import payroll.employees.EmployeeRecord;
public class Burden {
	double _burden = 0;
	EmployeeList _employeeList;
	public Burden(EmployeeList employeeList) {
		_employeeList = employeeList;
		sumGross();
		printOut();
	}
	void sumGross() {
		for (EmployeeRecord er : _employeeList) {
			_burden += Payroll.roundCent(er.calcGross());
		}
	}
	void printOut() {
		System.out.println(Payroll.dateToday());
		System.out.println(_employeeList.get(0).periodStart() + " to " + _employeeList.get(0).periodEnd());
		System.out.print("Total Burden: $");
		DecimalFormat df = new DecimalFormat("0.00");
		System.out.print(df.format(_burden));
	}
}