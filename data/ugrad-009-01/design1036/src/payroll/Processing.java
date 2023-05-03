package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class Processing {
	private String iso8601;
	final private String PAYSLIPS_COMMAND = "Payslips";
	final private String EMPLOYEES_COMMAND = "Employees";
	final private String BURDEN_COMMAND = "Burden";
	final private String PAYE_COMMAND = "PAYE";
	public Processing() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		iso8601 = dateFormat.format(calendar.getTime());
		System.out.println(iso8601);
	}
	public void determineProcess(String process, EmployeeRecord eR) {
		if (process.equals(PAYSLIPS_COMMAND)) {
			eR.printPayslips();
		} else if (process.equals(EMPLOYEES_COMMAND)) {
			eR.printEmployees();
		} else if (process.equals(BURDEN_COMMAND)) {
			eR.printBurden();
		} else if (process.equals(PAYE_COMMAND)) {
			eR.printPaye();
		} else {
			System.out
					.println("Please specify the kind of processing to be done (case sensitive).");
		}
	}
}
