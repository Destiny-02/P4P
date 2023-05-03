package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Dates {
	public Dates() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String theDate = dateFormat.format(date);
		System.out.println(theDate);
	}
}
