package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Payroll {
	public static void main (String[] args) {
		ProcessInput input = new ProcessInput(args[0], args[1]);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		input.processEmployees();
	}
}