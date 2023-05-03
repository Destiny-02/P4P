package payroll;
import java.text.DecimalFormat;
import java.util.Hashtable;
public abstract class Process {
	abstract void process(Hashtable<Integer, Employee> employeeList);
	public String Round(float f){
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(f);
	}
}
