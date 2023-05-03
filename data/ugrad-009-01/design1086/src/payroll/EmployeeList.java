package payroll;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
public class EmployeeList {
	private ArrayList<Employee> _employeeList;
	public EmployeeList (){
		_employeeList = new ArrayList<Employee>();
	}
	public void insertInformation(Employee employee) {
		_employeeList.add(employee);
	}
	public void printPayslipReport(){
		sortByTaxID();
		Iterator<Employee> iterator = _employeeList.iterator();
		while(iterator.hasNext()){
			iterator.next().aEmployeePayslip();
		}
	}
	public void printEmployeesReport(){
		sortByName();
		Iterator<Employee> iterator = _employeeList.iterator();
		while(iterator.hasNext()){
			iterator.next().aEmployeeEmployeesReport();
		}
	}
	public void printBurden(){
		Iterator<Employee> iterator = _employeeList.iterator();
		double burden = 0;
		while(iterator.hasNext()){
			burden = iterator.next().calculateBurden(burden);
		}
		Employee aEmployee = _employeeList.get(0);
		aEmployee.printpayPeriod();
		System.out.println("Total Burden: $" + String.format("%.2f", burden));
	}
	public void printPAYE(){
		Iterator<Employee> iterator = _employeeList.iterator();
		double PAYE = 0;
		while(iterator.hasNext()){
			PAYE = iterator.next().calculatePAYE(PAYE);
		}
		Employee aEmployee = _employeeList.get(0);
		aEmployee.printpayPeriod();
		System.out.println("Total PAYE: $" + String.format("%.2f", PAYE));
	}
	public void sortByTaxID(){
		Collections.sort(_employeeList);
	}
	public void sortByName(){
		Employee.NameComparator nameCompare = new Employee.NameComparator();
		Collections.sort(_employeeList, nameCompare);
	}
}
