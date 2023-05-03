package payroll;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
public class PayslipProcessing extends Processing {
	public PayslipProcessing(ArrayList<Employee> employees) {
		_employees = employees;
		print();
	}
	void process() {
		for (Employee employee : _employees) {
			System.out.print(employee.payslipProcessing() + "\n");
		}
	}
	@Override
	void print() {
		printDate();
		process();
	}
}
