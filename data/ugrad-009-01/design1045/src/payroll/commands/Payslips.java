package payroll.commands;
import java.text.DecimalFormat;
import payroll.Payroll;
import payroll.employees.EmployeeList;
import payroll.employees.EmployeeRecord;
public class Payslips {
	EmployeeList _employeeList;
	String[] printout;
	public Payslips(EmployeeList employeeList) {
		_employeeList = employeeList;
		printout = new String[_employeeList.size()];
		sortList();
		printOut();
		printToConsole();
	}
	void sortList() {
		_employeeList.sortByTID();
	}
	void printOut() {
		DecimalFormat df = new DecimalFormat("0.00");
		for (int i = 0; i < _employeeList.size(); i++) {
			EmployeeRecord er = _employeeList.get(i);
			String payslip = "" + er.taxID() + ". " + er.firstLastName() + ", Period: " + er.periodStart() + " to "
					+ er.periodEnd() + ". Gross: $" + df.format(er.calcGross()) + ", PAYE: $" + df.format(er.calcPAYE())
					+ ", Deductions: $" + df.format(er.deductions()) + " NETT: $" + df.format(er.calcNett()) + " YTD: $"
					+ df.format(er.calcYTD());
			printout[i] = payslip;
		}
	}
	void printToConsole() {
		System.out.println(Payroll.dateToday());
		for (String s : printout) {
			System.out.println(s);
		}
	}
}
