package payroll;
import java.io.IOException;
public class Payroll {
	public static void main(String[] args) throws IOException{
		EmployeeList employees= new EmployeeList(args[0]);
		if(args[1].equals("Payslips")){
			employees.Payslips();
		}
		else if(args[1].equals("Employees")){
			employees.Employees();
		}
		else if(args[1].equals("Burden")){
			employees.Burden();
		}
		else if(args[1].equals("PAYE")){
			employees.PAYE();
		}
		else{
			System.out.println("Invalid input type");
		}
	}
}