package payroll;
public class EmployeesFormat extends Formatting{
	public void nameSort(EmployeeInfo[] employeeData) {
		EmployeeInfo temp = new EmployeeInfo();
		for (int i = 0; i < employeeData.length - 1; i++) {
			for (int j = i; j < employeeData.length; j++) {
				if (((employeeData[i].getName()).compareTo(employeeData[j].getName())) > 0) {
					temp = employeeData[i];
					employeeData[i] = employeeData[j];
					employeeData[j] = temp;
				}
			}
		}
	}
	public String formatName(String name) {
		return name + " ";
	}
	public String formatTID(int tid) {
		return "(" + tid + ") ";
	}
	public String formatEmployeeType(String type) {
		return type + ", ";
	}
	public String formatRate(double rate) {
		String rateString = "$" + twoDP(rate) + " ";
		return rateString;
	}
	public String formatYTD(double ytd) {
		String ytdString = "YTD:$" + twoDP(ytd);
		return ytdString;
	}
	public void printEmployees(EmployeeInfo[] employeeData) {
		nameSort(employeeData);
		System.out.println(todaysDate());
		for (int i = 0; i < employeeData.length; i++) {
			System.out.print(formatName(employeeData[i].getName()));
			System.out.print(formatTID(employeeData[i].getTID()));
			System.out.print(formatEmployeeType(employeeData[i].getEmployeeType()));
			System.out.print(formatRate(employeeData[i].getRate()));
			System.out.println(formatYTD(employeeData[i].getNewYTD()));
		}
	}
}
