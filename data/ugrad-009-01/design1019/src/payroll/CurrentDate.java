package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
public class CurrentDate implements Display {
	private String _currentDate;
	public CurrentDate() {
		TimeZone tz = TimeZone.getTimeZone("Pacific/Auckland");
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    df.setTimeZone(tz);
	    _currentDate = df.format(new Date());
	}
	public String getCurrentDate() {
		String temp = _currentDate;
		return temp;
	}
	public void display() {
	    System.out.println(this.getCurrentDate() );
	}
}