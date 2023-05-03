package payroll;
import java.util.Comparator;
public class SortByTIDComparator implements Comparator<Employee>{
	@Override
	public int compare(Employee e1, Employee e2) {
		if (e1.getTID() < e2.getTID()) {
			return -1;
		} else if (e1.getTID() > e2.getTID()) {
			return 1;
		} else {
			return 0;
		}
	}
}
