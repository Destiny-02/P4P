package payroll;
import java.util.ArrayList;
public class Display {
	private ArrayList<Employees> _empList = new ArrayList<Employees>();
	private String _arg;
	public Display(ArrayList<Employees> empList, String arg) {
		_empList = empList;
		_arg = arg;
	}
	public void display() {
		if ((_arg.toLowerCase()).equals("payslips")) {
			Payslips payslip = new Payslips(_empList);
			payslip.sort();
			payslip.printDate();
			payslip.print();
		} else if ((_arg.toLowerCase()).equals("employees")) {
			EmployeesProcessed emp= new EmployeesProcessed(_empList);
			emp.sort();
			emp.printDate();
			emp.print();
		} else if ((_arg.toLowerCase()).equals("burden")) {
			Burden burd = new Burden(_empList);
			burd.printDate();
			burd.print();
		} else if ((_arg.toLowerCase()).equals("paye")) {
			PayeProcessed payeP = new PayeProcessed(_empList);
			payeP.printDate();
			payeP.print();
		} else {
			System.out.println("Invalid Processing Type");
		}
	}
}