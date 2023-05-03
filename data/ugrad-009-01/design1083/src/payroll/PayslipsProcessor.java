package payroll;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import payroll.Employee.EmployeeDataComputation;
public class PayslipsProcessor extends Processor{
	private List<String> _output = new Vector<String>();
	public void process(EmployeeList employeeList){
		_output.add(currentDate());
		List<Employee> listLOfEmployees = employeeList.getListOfEmployees();
		Collections.sort(listLOfEmployees, Employee.TIDOrder());
		for (Employee employee : listLOfEmployees){
			EmployeeDataComputation computePayslip= employee.new EmployeeDataComputation();
			_output.add(computePayslip.payslip());
		}
	}
	public void displayProcessedList(){
		for (String printing : _output){
			System.out.println(printing);
		}
	}
}
