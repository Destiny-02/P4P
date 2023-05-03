package payroll;
import java.util.ArrayList;
import java.util.List;
public class Payroll {
	public static void main(String[] args) {
		List<Employee> employeeList = new ArrayList<Employee>();
		checkParameters(args);
		String[] lines = fileInfo.readFile(args[0]);
		for(String line: lines){
			String[] lineData = line.split("\t");
			Employee employee = new Employee(lineData);
			employeeList.add(employee);
		}
		operationControl cmd = new operationControl(employeeList);
		if(args[1].equals("PAYE")){
			cmd.paye();
		} else if (args[1].equals("Burden")){
			cmd.burden();
		}else if (args[1].equals("Employees")){
			cmd.employeesList();
		} else if (args[1].equals("Payslips")){
			cmd.paySlips();
		} else{
			throw new RuntimeException("\nInvalid process: (" + args[1] + ")\nValid processes: Payslips, Employees, PAYE, Burden");
		}
	}
	private static void checkParameters(String[] args) {
		if(args.length != 2){
			throw new RuntimeException("\nInvalid number of Arguments - Expecting: fileName process");
		}
	}
}

