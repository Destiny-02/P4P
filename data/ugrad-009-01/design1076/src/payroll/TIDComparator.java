package payroll;
import java.util.Comparator;
public class TIDComparator implements Comparator<Payment>{
	@Override
	public int compare(Payment p1, Payment p2) {
		Employee e1,e2;
		e1 = p1.getEmployee();
		e2 = p2.getEmployee();
		if (e1.getTID()>e2.getTID()){
			return 1;
		}
		else if(e2.getTID()>e1.getTID()){
			return -1;
		}
		else{
			return 0;
		}
	}
}
