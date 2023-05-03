package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Payroll {
	public static void main(String[] args){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println(format.format(date));
		Processing p = new Processing(args[0],args[1]);
		p.createList();
		p.processList();
	}
}