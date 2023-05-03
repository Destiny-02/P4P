package payroll;
import java.util.Collections;
import java.util.List;
public class EmployeeList extends AbstractProcessingMethods implements Calculation {
	public void operationPrep(List<Employee> employeeList) {
		Collections.sort(employeeList, new ComparatorName());
	}
	public void getData(Employee currentEmployee) {
		String employment = currentEmployee.getEmploymentType();
		System.out.printf(currentEmployee.getName() + " ");
		System.out.printf("(" + currentEmployee.getTID() + ") ");
		System.out.printf(employment + ", ");
		System.out.printf("$%.2f ", currentEmployee.getRate());
		System.out.printf("YTD:$%.2f", getYTD(currentEmployee));
		System.out.printf("\n");
	}
	public void dispTotal() {
	}
}
