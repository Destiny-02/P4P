package payroll;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public abstract class Processor {
	abstract public void process(EmployeeList employeeList);
	protected String currentDate(){
		Date date = new Date();
	    SimpleDateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd");
	    return iso8601.format(date);
	}
	protected String convertToTwoDP(double doubleValue){
		DecimalFormat df2 = new DecimalFormat(".00");
		return df2.format(doubleValue);
	}
	abstract public void displayProcessedList();
}
