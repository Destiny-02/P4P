package payroll;
import java.util.List;
public class ProcessingFactory extends AbstractProcessingMethods {
	String _reportType;
	public ProcessingFactory(String reportType) {
		_reportType = reportType;
	}
	public void initialise(List<Employee> employeeList) {
		if (employeeList.size() < 1) {
			System.out.println("Error: No valid employees in input file");
			return;
		}
		Calculation calcValue = null;
		if (this._reportType.equals("Payslips")) {
			calcValue = new Payslips();
		} else if (this._reportType.equals("Employees")) {
			calcValue = new EmployeeList();
		} else if (this._reportType.equals("Burden")) {
			calcValue = new Burden();
		} else if (this._reportType.equals("PAYE")) {
			calcValue = new PAYE();
		}
		getDate();
		calcValue.operationPrep(employeeList);
		for (Employee nextEmployee : employeeList) {
			calcValue.getData(nextEmployee);
		}
		calcValue.dispTotal();
	}
}
