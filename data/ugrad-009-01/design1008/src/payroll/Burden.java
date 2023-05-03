package payroll;
import java.text.DecimalFormat;
import java.util.ArrayList;
public class Burden extends Processing {
	public Burden (ArrayList<Employees> empList) {
		super(empList);
	}
	public void printDate() {
		System.out.println(dateFormat.format(date));
	}
	public void print() {
		DecimalFormat df = new DecimalFormat("0.00");
		for (int i = 0; i <_empList.size(); i++) {
			result += _empList.get(i).getGross();
		}
		System.out.println(_empList.get(0).getStart() + " to " + _empList.get(0).getEnd() + "\nTotal Burden: $" + df.format(result));
	}
}
