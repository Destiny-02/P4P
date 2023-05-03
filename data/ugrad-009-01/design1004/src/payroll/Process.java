package payroll;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
public abstract class Process {
	public DecimalFormat dp2 = new DecimalFormat(".00");
	public String round(double unrounded){
		return dp2.format(unrounded);
	}
	public void printDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
	}
	public abstract void process(List<Employee> employeeList);
}