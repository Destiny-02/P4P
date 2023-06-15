package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
abstract public class Operations {
	public static void printDate(){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = format.format(new Date());
		System.out.println(currentDate);
	}
	abstract public void printOutput();
	public static String roundToTwoDec(float currencyValue){
		return String.format("%.2f", currencyValue);
	}
}
