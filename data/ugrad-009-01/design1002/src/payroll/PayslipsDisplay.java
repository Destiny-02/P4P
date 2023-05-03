package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class PayslipsDisplay implements Display {
	private EmployeeList _employeeList;
	public PayslipsDisplay(EmployeeList employeeList) {
		_employeeList = employeeList;
	}
	public void display() {
		Employee current = _employeeList.FindNextSmallest(-2147483648);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.print(dateFormat.format(date));
		while (current != null) {
			String line2 = current.PrintPayslip();
			System.out.print("\n" + line2);
			current = _employeeList.FindNextSmallest(current.GetTaxID());
		}
	}
}
