package payroll;
import java.util.Comparator;
public class FamilyNameComparator implements Comparator<Employee> {
	@Override
	public int compare(Employee employee1, Employee employee2) {
		return employee1.getLastName().compareTo(employee2.getLastName());
	}
}
