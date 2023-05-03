package payroll;
public class Payroll {
	public static void main(String[] args){
		EmployeeList myEmployees = new EmployeeList(args[1]);
		getInput inputReader = new getInput(args[0], myEmployees);
		inputReader.ReadFile();
		myEmployees.FinaliseData();
		myEmployees.getOutput();
	}
}

