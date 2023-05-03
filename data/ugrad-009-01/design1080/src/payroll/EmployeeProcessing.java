package payroll;
import java.util.ArrayList;
public class EmployeeProcessing extends Processing {
	private ArrayList<Employee> _employees;
	public EmployeeProcessing(ArrayList<Employee> employees) {
		_employees = employees;
		print();
	}
	public void process() {
		for (Employee employee : _employees) {
			System.out.println(employee.employeeProcessing());
		}
	}
	@Override
	void print() {
		printDate();
		process();
	}
}
