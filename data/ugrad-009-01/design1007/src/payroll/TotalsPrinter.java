package payroll;
import java.util.List;
public class TotalsPrinter extends Printer{
	public TotalsPrinter(List<Employee> employeeRecord, String processType) {
		super(employeeRecord, processType);
	}
	public void print() {
		Employee employee = _employeeRecord.get(0);
		double total = 0;
		for (Employee emp: _employeeRecord) {
			total += emp.getTotalsPrintInfo(_processType);
		}
		System.out.println(employee.getPayperiod());
		System.out.println("Total " + _processType + ": $" + total);
	}
}
