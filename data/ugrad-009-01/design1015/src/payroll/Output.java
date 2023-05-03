package payroll;
import java.util.List;
public final class Output {
	private static List<Employee> _list;
	private String _operation;
	public Output(List<Employee> list, String operation){
		_operation = operation;
		_list = list;
	}
	public void printOutput() throws Error{
		List<Employee> sortedList;
		switch(_operation){
		case "Payslips":
			sortedList = Employee.sortList(_list, "Payslips");
			for (int i=0; i<sortedList.size(); i++){
				Payslips payslip = _list.get(i).createPayslip();
				payslip.printOutput();
			}
			break;
		case "Employees":
			sortedList = Employee.sortList(_list,"Employees");
			for (int i=0; i<_list.size(); i++){
				Employees employees = _list.get(i).createEmployees();
				employees.printOutput();
			}
			break;
		case "Burden":
			Burden totalBurden = _list.get(0).createBurden();
			totalBurden.printOutput();
			break;
		case "PAYE":
			PAYE totalPAYE = _list.get(0).createPAYE();
			totalPAYE.printOutput();
			break;
		default:
			throw new Error("Invalid operation requested (" + _operation + ").");
		}
	}
}

