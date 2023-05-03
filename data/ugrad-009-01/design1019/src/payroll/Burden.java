package payroll;
import java.text.DecimalFormat;
public class Burden implements Display {
	private EmployeeRecords _employee;
	private double _sumGross;
	public Burden (double sumGross, EmployeeRecords employee) {
		_employee = employee;
		_sumGross = sumGross;
	}
	public void display() {
		DecimalFormat TwoDP = new DecimalFormat("#.00");
		System.out.println(_employee.getStartDate()+" to "+ _employee.getEndDate());
		System.out.println("Total Burden: $"+TwoDP.format(_sumGross) );
	}
}
