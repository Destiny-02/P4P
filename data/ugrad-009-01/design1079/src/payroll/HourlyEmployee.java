package payroll;
import java.text.ParseException;
public class HourlyEmployee extends Employee{
	public HourlyEmployee(int tid, String fullName, double rate, double ytd, String start, String end, double hours,
			double deduction) throws ParseException {
		super(tid, fullName, rate, ytd, start, end, hours, deduction);
	}
	public double getGrossPay() {
		double hours = getHours();
		if(hours <= 40)
			return getRate() * hours;
		else if(hours <= 43)
			return (getRate() * 40) + (getRate() * 1.5  * ( hours - 40));
		else
			return getRate() * 40 + getRate() * 1.5  * 3 + getRate() * 2 * (hours - 43);
	}
	public void processEmployee() {
		System.out.printf("%s, %s (%d) %s, $%.2f YTD:$%.2f\n",
				getFamilyName(), getFirstName(), getTid(), "Hourly", getRate(), getFinalYTD());
	}
}
