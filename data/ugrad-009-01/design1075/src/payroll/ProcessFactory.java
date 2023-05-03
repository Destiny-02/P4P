package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class ProcessFactory {
		public void process(EmployeeList list, String processCMD) throws EmployeeException{
		DateFormat format = new SimpleDateFormat ("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		System.out.println(format.format(cal.getTime()));
		ProcessType p = fromString(processCMD);
		switch(p) {
		case PAYSLIPS:
			new Process(list).Payslips();
			break;
		case EMPLOYEES:
			new Process(list).Employees();
			break;
		case BURDEN:
			new Process(list).Burden();
			break;
		case PAYE:
			new Process(list).PAYE();
			break;
		default:
			System.out.println("%%Invalid processing command: (" + processCMD + ")");
			break;
		}
	}
		private ProcessType fromString (String userInput) {
			if (userInput != null) {
				for (ProcessType p: ProcessType.values()) {
					if (userInput.equals(p.getProcess())) {
						return p;
					}
				}
			}
			throw new IllegalArgumentException("(" + userInput + ") is not a valid process");
		}
}

