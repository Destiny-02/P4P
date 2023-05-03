package payroll;
import java.util.Comparator;
public class NameCompare implements Comparator<Employee> {
	public int compare(Employee employeeA, Employee employeeB){
		return employeeA.name().compareTo(employeeB.name());
	}
}