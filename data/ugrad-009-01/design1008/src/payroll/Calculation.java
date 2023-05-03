package payroll;
import java.util.ArrayList;
public abstract class Calculation {
	protected final int WEEKS_IN_YEAR = 52;
	protected ArrayList<Employees> _empList;
	protected double result;
	public Calculation(ArrayList<Employees> empList) {
		_empList = empList;
	}
	public abstract void calculate();
}
