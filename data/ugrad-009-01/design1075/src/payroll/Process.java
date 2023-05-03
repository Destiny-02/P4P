package payroll;
public class Process {
	private EmployeeList _list;
	public Process(EmployeeList list) {
		_list = list;
	}
	public void Payslips() throws EmployeeException{
		_list.sortEmployees("TID");
		for (Employee employee: _list) {
			System.out.println(employee.Payslip());
			}
		}
	public void Employees () throws EmployeeException{
		_list.sortEmployees("Name");
		for (Employee employee: _list) {
			System.out.println(employee.EmployeeInfo());
		}
	}
	public void Burden() throws EmployeeException{
		double totalBurden = 0;
		System.out.println(_list.giveRandomEmployee().givePayPeriod());
		for (Employee employee: _list) {
			totalBurden = totalBurden + employee.Gross();
		}
		System.out.println("Total Burden: " + new Money(totalBurden).moneyFormat2dp());
	}
	public void PAYE() throws EmployeeException{
			double totalPAYE = 0;
			System.out.println(_list.giveRandomEmployee().givePayPeriod());
			for (Employee employee: _list) {
				double indiPAYE;
				indiPAYE = employee.PAYE();
				totalPAYE = totalPAYE + indiPAYE;
			}
			System.out.println("Total PAYE: " + new Money(totalPAYE).moneyFormat2dp());
		}
}
