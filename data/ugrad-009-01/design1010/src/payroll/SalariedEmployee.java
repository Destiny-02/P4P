package payroll;
import java.text.DecimalFormat;
public class SalariedEmployee extends Employee {
	public SalariedEmployee(String stringInput) {
		super(stringInput);
	}
	public float calculatePay() {
		DecimalFormat rounder = new DecimalFormat("#.##");
		float pay = Float.parseFloat(rounder.format(_rate/52));
		return pay;
	}
}
