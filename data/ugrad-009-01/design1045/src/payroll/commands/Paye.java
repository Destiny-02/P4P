package payroll.commands;
import java.text.DecimalFormat;
import payroll.Payroll;
import payroll.employees.EmployeeList;
import payroll.employees.EmployeeRecord;
public class Paye {
	EmployeeList _employeeList;
	double _payeSum = 0;
	public Paye(EmployeeList employeeList) {
		_employeeList = employeeList;
		sumPAYE();
		print();
	}
	void print() {
		System.out.println(Payroll.dateToday());
		System.out.println(_employeeList.get(0).periodStart() + " to " + _employeeList.get(0).periodEnd());
		System.out.print("Total PAYE: $");
		DecimalFormat df = new DecimalFormat("0.00");
		System.out.print(df.format(_payeSum));
	}
	void sumPAYE() {
		for (EmployeeRecord er : _employeeList) {
			_payeSum += Payroll.roundCent(er.calcPAYE());
		}
	}
}
