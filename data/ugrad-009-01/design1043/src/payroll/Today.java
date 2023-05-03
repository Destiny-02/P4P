package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class Today {
	public void printTodayDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		System.out.print(dateFormat.format(cal.getTime()));
	}
}
