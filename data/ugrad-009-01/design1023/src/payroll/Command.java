package payroll;
public class Command {
	private Process process = null;
	public Process determineProcess(String[] args){
		if(args[1].equals("Payslips")){
			process = new PayslipsProcess();
		}
		else if(args[1].equals("Employees")){
			process = new EmployeesProcess();
		}
		else if(args[1].equals("Burden")){
			process = new BurdenProcess();
		}
		else if(args[1].equals("PAYE")){
			process = new PayeProcess();
		}
		return process;
	}
}
