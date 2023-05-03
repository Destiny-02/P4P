package payroll;
import java.text.DecimalFormat;
public class SalariedEmployee extends Employee {
	public SalariedEmployee(int TID, String lastName, String firstName, String employment, double rate, double YTD, String periodStart, String periodEnd, double hours, double deduction) {
		super(TID, lastName, firstName, employment, rate, YTD, periodStart, periodEnd, hours, deduction);
	}
	public double Gross() throws EmployeeException{
		double gross = 0;
		DecimalFormat df = new DecimalFormat("###.##");
		gross = Double.parseDouble((df.format(this.giveRate() / 52)));
		if (gross < 0) {
			throw new EmployeeException("Negative gross");
		}
		else {
			return gross;
		}
	}
}
