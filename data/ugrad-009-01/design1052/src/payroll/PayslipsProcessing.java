package payroll;
public class PayslipsProcessing implements Processing {
	public void printToConsole(EmployeeList employeesList) {
		this.sort(employeesList);
		for(Employee employee:employeesList){
			System.out.println(employee.toStringPayslip());
		}
	}
	public void sort(EmployeeList employeesList){
		employeesList.sortByLastName();
		employeesList.sortByFirstName();
		employeesList.sortByTaxID();
	}
}
