package payroll;
import java.util.Comparator;
public class EmployeeComparator implements Comparator<Employee>{
	private String _processType;
	public EmployeeComparator(String processType) {
		_processType = processType;
	}
	public int compare(Employee emp1, Employee emp2) {
		if (_processType.equals("Payslips")) {
			return emp1.compareTaxID(emp2);
		} else {
			return emp1.compareName(emp2);
		}
	}
}
