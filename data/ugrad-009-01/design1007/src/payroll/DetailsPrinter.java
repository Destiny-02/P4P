package payroll;
import java.util.List;
public class DetailsPrinter extends Printer{
	public DetailsPrinter(List<Employee> employeeRecord, String processType) {
		super(employeeRecord, processType);
	}
	public void print() {
		for (Employee emp: _employeeRecord) {
			System.out.println(emp.getDetailedPrintInfo(_processType));
		}
	}
}
