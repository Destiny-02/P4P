package payroll;
public class BurdenProcessing implements Processing {
	public void printToConsole(EmployeeList employeesList) {
		double burden = 0;
		System.out.println(employeesList.getDatePeriod());
		for(Employee employee:employeesList){
			burden += employee._pay;
		}
		System.out.printf("Total Burden: $%.2f\n", burden);
	}
}
