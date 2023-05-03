package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class BurdenDisplay implements Display {
	private EmployeeList _employeeList;
	public BurdenDisplay(EmployeeList employeeList) {
		_employeeList = employeeList;
	}
	public void display() {
		Employee current = _employeeList.GetFirst();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.print(dateFormat.format(date));
		String line2 = current.PrintPeriod();
		System.out.print("\n" + line2);
		double totalBurden = 0;
		while (current != null) {
			totalBurden += current.Gross();
			current = current.GetNext();
		}
		System.out.print("\nTotal Burden: $" + Employee.StringRound(totalBurden));
	}
}
