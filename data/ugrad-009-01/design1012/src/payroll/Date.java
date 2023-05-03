package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class Date {
	void printDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime()));
	}
	void startAndEndDate(Employee employee){
		String[] datesWorked = employee.getDatesWorked();
		System.out.print(datesWorked[0] + " to " + datesWorked[1]);
	}
}
