package payroll;
public class UnrecognisedProcessingCommand implements Command{
	public void display(EmployeeList employeeList){
	System.out.print("Please input a valid processing command-"
			+ "either \n\t\"Payslips\" for pay slips, \n\t\"Employees\" "
			+ "for a list of employees and their YTD, \n\t\"Burden\" "
			+ "for the amount of wages to be paid out this week, "
			+ "or \n\t\"PAYE\", for the amount of your hard-earned money "
			+ "that's going to the government."
			+ "\nNote: All commands are case sensitive");
	}
}