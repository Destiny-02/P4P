package payroll;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class FormatProcessing {
	public static String decimalFormat(String input) {
		String sinput = input.substring(1);
		double iinput = Double.parseDouble(sinput);
		DecimalFormat df = new DecimalFormat("0.00");
		String sans = df.format(iinput);
		String ans = "$" + sans;
		return ans;
	}
	public static void outputdate() {
		Date date1 = Calendar.getInstance().getTime();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sdf1.format(date1));
	}
}
