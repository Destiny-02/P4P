package payroll;
import java.text.DecimalFormat;
import java.util.Map;
import payroll.Command;
import payroll.Employee;
;
public class BurdenCommand implements Command {
	DecimalFormat formator = new DecimalFormat("#.00");
	public void operation(Information info) {
		double gross = 0;
		int counter = 0;
		for ( Map.Entry<Integer, Employee> entry : info.getEmployeeInfo().entrySet()) {
			if ( counter == 0) {
				entry.getValue().getDate();
				System.out.println(entry.getValue().getPeriod());
				counter++;
			}
			gross = gross + Double.parseDouble(entry.getValue().getGross());
			formator.format(gross);
		}
		System.out.print("Total Burden: $" + gross);
	}
}
