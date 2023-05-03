package payroll;
public class EmployeesProcessing implements Processing {
	public void printToConsole(EmployeeList employeesList) {
		this.sort(employeesList);
		for(Employee employee:employeesList){
			System.out.println(employee.toStringEmployees());
		}
	}
	public void sort(EmployeeList employeesList){
		employeesList.sortByTaxID();
		employeesList.sortByFirstName();
		employeesList.sortByLastName();
	}
}
