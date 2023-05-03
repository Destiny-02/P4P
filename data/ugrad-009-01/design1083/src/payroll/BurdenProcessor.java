package payroll;
import java.util.List;
import java.util.Vector;
import payroll.Employee.EmployeeDataComputation;
public class BurdenProcessor extends Processor{
	private List<String> _output = new Vector<String>();
	public void process(EmployeeList employeeList){
		_output.add(currentDate());
		double totalBurden = 0;
		boolean printPeriod = true;
		List<Employee> listLOfEmployees = employeeList.getListOfEmployees();
		for (Employee employee : listLOfEmployees){
			while (printPeriod){
				_output.add(employee.getPayPeriod());
				printPeriod = false;
			}
			EmployeeDataComputation calculator= employee.new EmployeeDataComputation();
			totalBurden += calculator.calculateGross();
		}
		_output.add("Total Burden: $" + convertToTwoDP(totalBurden));
	}
	public List<String> getProcessedList(){
		return _output;
	}
	public void displayProcessedList(){
		for (String printing : _output){
			System.out.println(printing);
		}
	}
}
