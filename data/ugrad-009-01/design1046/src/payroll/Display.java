package payroll;
import java.text.DecimalFormat;
public class Display extends Dates{
	public void payslips(Employee employee, String gross, String tax, String nett, String YTD) {
		String taxID = employee.getTaxID();
		String fullname = employee.getName();
		String start = employee.getStart();
		String end = employee.getEnd();
		String deductions = employee.getDeduction();
		double stringDeductions = Double.parseDouble(deductions);
		DecimalFormat dec = new DecimalFormat("#.00");
		String roundedDeductions = dec.format(stringDeductions);
		String[] splitNames = fullname.split(",");
		fullname = splitNames[1] + " " + splitNames[0];
		System.out.println(taxID + "." + fullname + ", Period: " + start + " to " + end + ". Gross: $" +
		gross + ", PAYE: $" + tax + ", Deductions: $" + roundedDeductions + " Nett: $" + nett + " YTD: $" + YTD);
	}
	public void Employees(Employee employee, String YTD) {
		String fullname = employee.getName();
		String taxID = employee.getTaxID();
		String employment = employee.getEmployment();
		String rate = employee.getRate();
		double stringRate = Double.parseDouble(rate);
		DecimalFormat dec = new DecimalFormat("#.00");
		String roundedrate = dec.format(stringRate);
		System.out.println(fullname + " (" + taxID + ") " + employment + ", $" + roundedrate +
				" YTD:$" + YTD);
	}
	public void Burden(Employee employee, String tGross) {
		String start = employee.getStart();
		String end = employee.getEnd();
		System.out.println(start + " to " + end + "\nTotal Burden: $" + tGross);
	}
	public void PAYE(Employee employee, String tax) {
		String start = employee.getStart();
		String end = employee.getEnd();
		System.out.println(start + " to " + end + "\nTotal PAYE: $" + tax);
	}
}
