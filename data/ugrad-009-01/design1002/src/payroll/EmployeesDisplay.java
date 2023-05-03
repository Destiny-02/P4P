package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class EmployeesDisplay implements Display {
	private EmployeeList _employeeList;
	public EmployeesDisplay(EmployeeList employeeList) {
		_employeeList = employeeList;
	}
	public void display() {
		Employee current = _employeeList.GetFirst();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.print(dateFormat.format(date));
		while (current != null) {
			String line2 = current.PrintEmployee();
			System.out.print("\n" + line2);
			current = current.GetNext();
		}
	}
}
