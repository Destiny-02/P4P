package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public abstract class Processing {
	protected ArrayList<Employees> _empList;
	protected double result;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date();
	public Processing(ArrayList<Employees> empList) {
		_empList = empList;
	}
	public void printDate() {
		System.out.println(dateFormat.format(date));
	}
	public abstract void print();
}
