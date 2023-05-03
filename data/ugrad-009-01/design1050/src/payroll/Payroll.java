package payroll;
public class Payroll {
	public static void main(String[] args){
		EmployeeList employeeList;
		try{
			employeeList = new EmployeeList(args[0]);
		} catch(ArrayIndexOutOfBoundsException arrayex){
			System.out.print("Please provide an input (you bozo)");
			return;
		}
		Command cmd;
		try{
			if(args[1].equals("Payslips")){
				cmd = new PayslipsCommand();
			} else if (args[1].equals("Employees")){
				cmd = new EmployeesCommand();
			} else if (args[1].equals("Burden")){
				cmd = new BurdenCommand();
			} else if (args[1].equals("PAYE")){
				cmd = new PAYECommand();
			}else{
				cmd = new UnrecognisedProcessingCommand();
			}
		} catch(ArrayIndexOutOfBoundsException arrayex){
			cmd = new UnrecognisedProcessingCommand();
		}
		cmd.display(employeeList);
	}
}