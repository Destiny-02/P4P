package payroll;
import java.util.Collections;
import java.util.List;
public class operationControl {
	private static List<Employee> _employeeList;
	public operationControl(List<Employee> employeeList){
		_employeeList = employeeList;
	}
	public void paySlips(){
		System.out.println(DateInfo.getDate());
		if(_employeeList.size() > 0 ){
			Collections.sort(_employeeList, new TaxIDCompare());
			for(Employee employee: _employeeList){
				employee.printEmployeePayslip();
			}
		} else {
		System.out.println("No Employees in File: No Payslips to create.");
		}
	}
	public void employeesList(){
		System.out.println(DateInfo.getDate());
		if(_employeeList.size() > 0 ){
			Collections.sort(_employeeList, new NameCompare());
		for(Employee employee: _employeeList){
			employee.printEmployeeInfo();
		}
		} else {
			System.out.println("Not Employees in File: Employee List is empty.");
		}
	}
	public void burden(){
		double burden = 0;
		String timeRange = null;
		for(Employee employee: _employeeList){
			burden = burden + employee.employeeBurden();
			timeRange = employee.getTimeRange();
		}
		System.out.println(DateInfo.getDate());
		System.out.println(timeRange);
		Printer printer = new Printer(burden, "Burden");
		printer.printSimpleReport();
	}
	public void paye(){
		double payeTotal = 0;
		String timeRange = null;
		for(Employee employee: _employeeList){
			payeTotal = payeTotal + employee.employeeTax();
			timeRange = employee.getTimeRange();
		}
		System.out.println(DateInfo.getDate());
		System.out.println(timeRange);
		Printer printer = new Printer(payeTotal, "PAYE");
		printer.printSimpleReport();
	}
}

