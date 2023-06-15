package payroll;
import java.util.List;
public interface Calculation {
	public void operationPrep(List<Employee> employeeList);
	public void getData(Employee currentEmployee);
	public void dispTotal();
}
