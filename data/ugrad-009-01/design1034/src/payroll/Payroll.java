package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class Payroll {
	public static void main(String[] args) {
		Payroll payroll = new Payroll();
		payroll.getDateOfReport();
		FileContents file = new FileContents();
		ArrayList<Employee> employees = file.getFileContents(args[0]);
		Display display = new Display(employees, args[1]);
		display.ASCIIDisplayForProcess();
	}
	private void getDateOfReport() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
	}
}
