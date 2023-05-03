package payroll;
import java.text.DecimalFormat;
import java.util.ArrayList;
public class PayeProcessed extends Processing {
	public PayeProcessed (ArrayList<Employees> empList) {
		super(empList);
	}
	public void printDate() {
		System.out.println(dateFormat.format(date));
	}
	public void print() {
		DecimalFormat df = new DecimalFormat("#.00");
		for (int i = 0; i <_empList.size(); i++) {
			result += _empList.get(i).getPaye();
		}
		System.out.println(_empList.get(0).getStart() + " to " + _empList.get(0).getEnd() + "\nTotal PAYE: $" + df.format(result));
	}
}