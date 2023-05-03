package payroll;
import java.util.ArrayList;
public class Nett extends Calculation {
	public Nett(ArrayList<Employees> empList) {
		super(empList);
	}
	public void calculate() {
		for (int i = 0; i < _empList.size(); i++) {
			double gross = _empList.get(i).getGross();
			double paye = _empList.get(i).getPaye();
			double deduction = _empList.get(i).getDeduction();
			result = gross - paye - deduction;
			_empList.get(i).setNett(result);
		}
	}
}
