package payroll;
import java.util.List;
public class Burden extends AbstractProcessingMethods implements Calculation {
	double _Burden;
	public void operationPrep(List<Employee> employeeList) {
		displayPayPeriod(employeeList);
	}
	public void getData(Employee currentEmployee) {
		_Burden += currentEmployee.getGross();
	}
	public void dispTotal() {
		System.out.printf("Total Burden: $%.2f\n", _Burden);
	}
}
