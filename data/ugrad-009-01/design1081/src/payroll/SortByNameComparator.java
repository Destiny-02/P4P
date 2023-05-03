package payroll;
import java.util.Comparator;
public class SortByNameComparator implements Comparator<Employee> {
	@Override
	public int compare(Employee e1, Employee e2) {
		if (e1.getName().compareTo(e2.getName()) < 0) {
			return -1;
		} else if (e1.getName().compareTo(e2.getName()) > 0) {
			return 1;
		} else {
			return 0;
		}
	}
}
