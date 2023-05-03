package payroll;
import java.text.SimpleDateFormat;
import java.util.Date;
public class DateInfo {
	public static String getDate() {
		Date dateToday = new Date( );
		SimpleDateFormat ft =
				new SimpleDateFormat ("yyyy-MM-dd");
		return ft.format(dateToday);
	}
}

