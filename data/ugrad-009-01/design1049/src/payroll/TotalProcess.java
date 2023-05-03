package payroll;
public class TotalProcess {
	public double totalBurden(EmployeeInfo[] employeeData) {
		double burden = 0.0;
		for(int i = 0; i < employeeData.length; i++) {
			burden = burden + employeeData[i].getGross();
		}
		return burden;
	}
	public double totalPAYE(EmployeeInfo[] employeeData) {
		double paye = 0.0;
		for(int i = 0; i < employeeData.length; i++) {
			paye = paye + employeeData[i].getTax();
		}
		return paye;
	}
}
