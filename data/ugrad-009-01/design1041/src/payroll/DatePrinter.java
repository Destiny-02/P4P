package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class DatePrinter {
	private DateFormat _dateformat;
	private Date _date;
	public DatePrinter() {
		_dateformat = new SimpleDateFormat("yyyy-MM-dd");
		_date = new Date();
		printDate();
	}
	private void printDate() {
		System.out.println(_dateformat.format(_date));
	}
}
