package payroll;
import java.util.List;
public class PAYE extends AbstractProcessingMethods implements Calculation{
	private double _PAYE;
	public void operationPrep(List<Employee> employeeList) {
		displayPayPeriod(employeeList);
	}
	public void getData(Employee currentEmployee) {
		_PAYE += getPAYE(currentEmployee);
	}
	public void dispTotal() {
		System.out.printf("Total PAYE: $%.2f\n", _PAYE);
	}
}

