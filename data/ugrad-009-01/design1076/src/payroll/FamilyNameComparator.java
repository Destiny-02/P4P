package payroll;
import java.util.Comparator;
public class FamilyNameComparator implements Comparator<Payment>{
	@Override
	public int compare(Payment p1, Payment p2) {
		Employee e1, e2;
		e1 = p1.getEmployee();
		e2 = p2.getEmployee();
		String name1=e1.getName();
		String name2=e2.getName();
		return name1.compareTo(name2);
	}
}
