package payroll;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Utility {
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
	public static double round(double x){
		return Math.round(x * 100.0) / 100.0;
	}
	public static  Date stringToDate(String date) throws ParseException{
		return dateFormatter.parse(date);
	}
	public static String dateToString(Date date){
		return dateFormatter.format(date);
	}
}
