package payroll;
public class PayslipsFormat extends Formatting{
	public void idSort(EmployeeInfo[] employeeData) {
		EmployeeInfo temp = new EmployeeInfo();
		for (int i = 0; i < employeeData.length - 1; i++) {
			for (int j = i; j < employeeData.length; j++) {
				if (employeeData[i].getTID() > employeeData[j].getTID()) {
					temp = employeeData[i];
					employeeData[i] = employeeData[j];
					employeeData[j] = temp;
				}
			}
		}
	}
	public String tidFormat(int tid) {
		return tid + ". ";
	}
	public String firstNameFirst(String name) {
		String[] splitName = name.split(", ");
		String firstLast = splitName[1] + " " + splitName[0] + ", Period: ";
		return firstLast;
	}
	public String grossFormat(double gross) {
		String grossString = ". Gross: $" + twoDP(gross) + ", ";
		return grossString;
	}
	public String payeFormat(double paye) {
		String payeString = "PAYE: $" + twoDP(paye) + ", ";
		return payeString;
	}
	public String deductionsFormat(double deductions) {
		String deductionsString = "Deductions: $" + twoDP(deductions) + " ";
		return deductionsString;
	}
	public String nettFormat(double nett) {
		String nettString = "Nett: $" + twoDP(nett) + " ";
		return nettString;
	}
	public String ytdFormat(double ytd) {
		String ytdString = "YTD: $" + twoDP(ytd);
		return ytdString;
	}
	public void printPayslip(EmployeeInfo[] employeeData) {
		idSort(employeeData);
		System.out.println(todaysDate());
		for (int i = 0; i < employeeData.length; i++) {
			System.out.print(tidFormat(employeeData[i].getTID()));
			System.out.print(firstNameFirst(employeeData[i].getName()));
			System.out.print(periodFormat(employeeData[i].getStartDate(),employeeData[i].getEndDate()));
			System.out.print(grossFormat(employeeData[i].getGross()));
			System.out.print(payeFormat(employeeData[i].getTax()));
			System.out.print(deductionsFormat(employeeData[i].getDeductions()));
			System.out.print(nettFormat(employeeData[i].getNett()));
			System.out.println(ytdFormat(employeeData[i].getNewYTD()));
		}
	}
}
