package payroll;
import java.text.DecimalFormat;
public class PAYE implements Display {
	private EmployeeRecords _employee;
	private double _sumPAYE;
	public PAYE (double sumPAYE, EmployeeRecords employee) {
		_employee = employee;
		_sumPAYE = sumPAYE;
	}
	public void display() {
		DecimalFormat TwoDP = new DecimalFormat("#.00");
		System.out.println(_employee.getStartDate()+" to "+ _employee.getEndDate());
		System.out.println("Total PAYE: $"+TwoDP.format(_sumPAYE) );
	}
}
