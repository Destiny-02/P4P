package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class PAYEDisplay implements Display {
	private EmployeeList _employeeList;
	public PAYEDisplay(EmployeeList employeeList) {
		_employeeList = employeeList;
	}
	public void display() {
		Employee current = _employeeList.GetFirst();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.print(dateFormat.format(date));
		String line2 = current.PrintPeriod();
		System.out.print("\n" + line2);
		double totalPAYE = 0;
		while (current != null) {
			totalPAYE += current.PAYE();
			current = current.GetNext();
		}
		System.out.print("\nTotal PAYE: $" + Employee.StringRound(totalPAYE));
	}
}
