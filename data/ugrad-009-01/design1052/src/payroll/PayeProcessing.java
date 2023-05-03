package payroll;
public class PayeProcessing implements Processing {
	public void printToConsole(EmployeeList employeesList) {
		double paye = 0;
		System.out.println(employeesList.getDatePeriod());
		for(Employee employee:employeesList){
			paye += employee.getTax();
		}
		System.out.printf("Total PAYE: $%.2f\n", paye);
	}
}
