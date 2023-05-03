package payroll;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Display {
	private final String DECIMALFORMAT = "0.00";
	private final String DATEFORMAT = "yyyy-MM-dd";
	public String getDate(){
		DateFormat df = new SimpleDateFormat(DATEFORMAT);
	    Date date = new Date();
	    return df.format(date);
	}
	public String getDecimalFormat(double number){
		DecimalFormat df = new DecimalFormat(DECIMALFORMAT);
		String decimalFormat = df.format(number);
		return decimalFormat;
	}
}
