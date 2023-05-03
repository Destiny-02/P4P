package payroll;
import java.text.ParseException;
public class SalariedEmployee extends Employee {
	public SalariedEmployee(int tid, String fullName, double rate, double ytd, String start, String end, double hours,
			double deduction) throws ParseException {
		super(tid, fullName, rate, ytd, start, end, hours, deduction);
	}
	public double getGrossPay() {
		return Utility.round(getRate()/WEEKS_IN_YEAR);
	}
	public void processEmployee() {
		System.out.printf("%s, %s (%d) %s, $%.2f YTD:$%.2f\n",
				getFamilyName(), getFirstName(), getTid(), "Salaried", getRate(), getFinalYTD());
	}
}
