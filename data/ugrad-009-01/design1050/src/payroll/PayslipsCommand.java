package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
public class PayslipsCommand implements Command{
	public void display(EmployeeList employeeList){
		payslipsCalculations(employeeList);
		payslipsDisplay(employeeList);
	}
	private void payslipsCalculations(EmployeeList employeeList){
		ArrayList<Employee> employees = employeeList.getEmployeeList();
		for(Employee employee:employees){
			employee.calculateGross();
			employee.calculateYearlyGross();
			employee.calculatePAYE();
			employee.calculateNett();
			employee.calculateYTD();
		}
	}
	private void payslipsDisplay(EmployeeList employeeList){
		ArrayList<Employee> employees = employeeList.getEmployeeList();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.print(dateFormat.format(date));
		Collections.sort(employees, new TIDComparator());
		for(Employee employee: employees){
			String str = employee.forPayslipsCommand();
			System.out.print("\n" + str);
		}
	}
}