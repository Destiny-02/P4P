package payroll;
import java.util.ArrayList;
public class PayeProcessing extends Processing {
	private double _sum = 0;
	private String _startDate;
	private String _endDate;
	private ArrayList<Employee> _employees;
	public PayeProcessing(ArrayList<Employee> employees) {
		_employees = employees;
		_startDate = employees.get(0).getStartDate();
		_endDate = employees.get(0).getEndDate();
		print();
	}
	public void process() {
		for (Employee employee : _employees) {
			_sum += employee.payeProcessing();
		}
		System.out.println(_startDate + " to " + _endDate);
		System.out.println("Total PAYE: " + _employees.get(0).printDouble(_sum));
	}
	@Override
	void print() {
		printDate();
		process();
	}
}
