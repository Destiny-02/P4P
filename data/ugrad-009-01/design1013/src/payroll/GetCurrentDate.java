package payroll;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class GetCurrentDate {
	public void printCurrentDate(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String formatted = format1.format(cal.getTime());
		System.out.println(formatted);
	}
}
