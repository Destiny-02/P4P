package payroll;
import java.text.DecimalFormat;
public class Employees implements Display {
	private EmployeeRecords _employee;
	public Employees (EmployeeRecords employee) {
		_employee = employee;
	}
	public void display() {
		DecimalFormat TwoDP = new DecimalFormat("#.00");
		System.out.println(_employee.getFamilyName()+", "+_employee.getGivenName()+" ("+_employee.getTaxID()+") "+_employee.getEmployment()+", $"+TwoDP.format(_employee.getRate())+" YTD:$"+TwoDP.format(_employee.getCurrentYTD()) );
	}
	public void checkNegative() throws PayrollException {
		DecimalFormat TwoDP = new DecimalFormat("#.00");
		if (_employee.getCurrentYTD() < 0) {
			throw new PayrollException("Negative YTD: "+TwoDP.format(_employee.getCurrentYTD())+" for "+_employee.getGivenName()+", "+_employee.getFamilyName() );
		}
	}
}
