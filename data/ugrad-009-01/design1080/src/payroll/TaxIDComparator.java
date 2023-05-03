package payroll;
import java.util.Comparator;
public class TaxIDComparator implements Comparator<Employee> {
	@Override
	public int compare(Employee employee1, Employee employee2) {
		return (employee1.getTid() < employee2.getTid()) ? -1 :
			(employee1.getTid() > employee2.getTid()) ? 1 : 0;
	}
}
