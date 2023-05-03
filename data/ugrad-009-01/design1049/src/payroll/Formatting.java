package payroll;
import java.text.DecimalFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
public abstract class Formatting {
	public String twoDP(double number) {
		DecimalFormat df = new DecimalFormat("#.00");
		String output = df.format(number);
		return output;
	}
	public String todaysDate() {
		TimeZone tz = TimeZone.getTimeZone("NZ");
		DateFormat today = new SimpleDateFormat("yyyy-MM-dd");
		today.setTimeZone(tz);
		String todayFormated = today.format(new Date());
		return todayFormated;
	}
	public String periodFormat(String startDate, String endDate) {
		String periodString = startDate + " to " + endDate;
		return periodString;
	}
}
