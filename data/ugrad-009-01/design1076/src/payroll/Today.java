package payroll;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Today {
	public String getDate(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return (dateFormat.format(date));
	}
}
