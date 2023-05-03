package payroll;
import java.util.ArrayList;
public class Payroll {
	public static void main(String[] args){
		FileScanner scan = new FileScanner();
		ArrayList<Employee> _employeeList= scan.fileReader(args[0]);
		if(args[1].equals("Payslips")){
			PayslipsProc pay = new PayslipsProc();
			pay.payslips(_employeeList);
		}
		else if(args[1].equals("Employees")){
			EmployeesProc emp = new EmployeesProc();
			emp.employeeProcess(_employeeList);
		}
		else if(args[1].equals("Burden")){
			BurdenProc burd = new BurdenProc();
			burd.burden(_employeeList);
		}
		else if(args[1].equals("PAYE")){
			PAYEProc paye = new PAYEProc();
			paye.payeProcess(_employeeList);
		}
	}
}
