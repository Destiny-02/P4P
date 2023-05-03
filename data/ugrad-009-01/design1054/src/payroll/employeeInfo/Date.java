package payroll.employeeInfo;
import java.util.Calendar;
public class Date {
	private final int _year;
	private final int _month;
	private final int _day;
	public int GetYear() {
		return _year;
	}
	public int GetMonth() {
		return _month;
	}
	public int GetDay() {
		return _day;
	}
	public Date(int year, int month, int day) {
		_year = year;
		_month = month;
		_day = day;
	}
	public Date() {
		Calendar cal = Calendar.getInstance();
		_year = cal.get(Calendar.YEAR);
		_month = cal.get(Calendar.MONTH) + 1;
		_day = cal.get(Calendar.DAY_OF_MONTH);
	}
	public String toString() {
		return String.format("%1$04d-%2$02d-%3$02d", _year, _month, _day);
	}
}
