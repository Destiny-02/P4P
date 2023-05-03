package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class PayrollDate {
	private int _day;
	private int _month;
	private int _year;
	public PayrollDate() {
	}
	@SuppressWarnings("deprecation")
	public PayrollDate(int year, int month, int day) throws PayrollDateFormatException {
		_day = day;
		_month = month;
		_year = year;
		if ((day > 31) || (day < 1) || (month > 12) || (month < 1) || (year > (new Date().getYear() + 1900))
				|| (year < 1900)) {
			throw new PayrollDateFormatException(toString());
		}
	}
	@Override
	public String toString() {
		return String.format("%s-%s-%s", String.format("%04d", _year), String.format("%02d", _month),
				String.format("%02d", _day));
	}
	public String currentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
