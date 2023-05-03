package payroll;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
public abstract class Processing {
	protected ArrayList<Employee> _employees;
	private Calendar calendarNow;
	private String day;
	private String month;
	private String year;
	abstract void print();
	public void printDate() {
		System.out.println(getDate());
	}
	private String getDate() {
		calendarNow = Calendar.getInstance();
		day = (calendarNow.get(calendarNow.DAY_OF_MONTH) > 9) ? calendarNow.get(calendarNow.DAY_OF_MONTH) + "" :
			"0" + calendarNow.get(calendarNow.DAY_OF_MONTH);
		month = ((calendarNow.get(calendarNow.MONTH) + 1) > 9) ? calendarNow.get((calendarNow.MONTH) + 1) + "" :
			"0" + (calendarNow.get(calendarNow.MONTH) + 1);
		year = calendarNow.get(calendarNow.YEAR) + "";
		return  (year + "-" + month + "-" + day);
	}
}
