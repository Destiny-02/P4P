package payroll;
import java.text.DecimalFormat;
import java.util.Map;
import payroll.Command;
import payroll.Employee;
public class PAYECommand implements Command {
	DecimalFormat formator = new DecimalFormat("#.00");
	public void operation(Information info) {
		double PAYE = 0;
		int counter = 0;
		for ( Map.Entry<Integer, Employee> entry : info.getEmployeeInfo().entrySet()) {
			if ( counter == 0) {
				entry.getValue().getDate();
				System.out.println(entry.getValue().getPeriod());
				counter++;
			}
			PAYE = PAYE + Double.parseDouble(entry.getValue().getPAYE());
			formator.format(PAYE);
		}
		System.out.print("Total PAYE: $" + PAYE);
	}
}
