package payroll;
public class Payroll {
	public static void main(String[] args){
		InputProcessor ip = new InputProcessor();
		CompanyRecords myCompany = new CompanyRecords();
		ip.readFile(myCompany, args[0]);
		if(args[1].equals("Payslips")){
			myCompany.TIDSort();
			myCompany.printAllPayslips();
		}else if(args[1].equals("Employees")){
			myCompany.nameSort();
			myCompany.printAllEmployees();
		}else if(args[1].equals("Burden")){
			myCompany.printTotalBurden();
		}else if(args[1].equals("PAYE")){
			myCompany.printTotalPAYE();
		}else{
			System.out.println("Sorry, invalid command.");
		}
	}
}
