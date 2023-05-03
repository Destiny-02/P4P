package payroll;
import java.text.DecimalFormat;
public class HourlyEmployee extends Employee {
	public HourlyEmployee(String stringInput) {
		super(stringInput);
	}
	public float calculatePay() {
		DecimalFormat rounder = new DecimalFormat("#.##");
		float hoursWorked = _hoursWorked;
		float rate = _rate;
		float pay = 0.00f;
		if (hoursWorked > 43) {
			float part = hoursWorked - 43;
			pay += Float.parseFloat(rounder.format(part * rate * 2.00));
			hoursWorked = 43;
		}
		if (hoursWorked > 40) {
			float part = hoursWorked - 40;
			pay += Float.parseFloat(rounder.format(part * rate * 1.50));
			hoursWorked = 40;
		}
		if (hoursWorked > 0) {
			pay += Float.parseFloat(rounder.format(hoursWorked * rate));
		}
		return pay;
	}
}
