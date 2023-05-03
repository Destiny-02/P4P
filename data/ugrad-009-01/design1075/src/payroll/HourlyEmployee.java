package payroll;
import java.text.DecimalFormat;
public class HourlyEmployee extends Employee {
	public HourlyEmployee(int TID, String lastName, String firstName, String employment, double rate, double YTD, String periodStart, String periodEnd, double hours, double deduction) {
		super(TID, lastName, firstName, employment, rate, YTD, periodStart, periodEnd, hours, deduction);
	}
	public double Gross() {
		DecimalFormat df = new DecimalFormat("###.##");
		if (this.giveHours() <= 40) {
			return Double.parseDouble((df.format(this.giveHours() * this.giveRate())));
		}
		else if (this.giveHours() <= 43) {
			return Double.parseDouble((df.format (40 * this.giveRate() + (this.giveHours() - 40) * 1.5 * this.giveRate())));
		}
		else {
			return Double.parseDouble((df.format (40 * this.giveRate() + 3 * 1.5 * this.giveRate() + (this.giveHours() - 43) * 2 * this.giveRate())));
		}
	}
}
