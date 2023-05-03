package payroll;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class HourlyPay extends Pay {
	public HourlyPay(Employee Employee, BigDecimal hours, BigDecimal deductions, BigDecimal rate, BigDecimal ytd) throws Exception {
		super(Employee, hours, deductions, rate, ytd);
	}
	@Override
	public BigDecimal calculateGrossPay() {
		BigDecimal grossPay = BigDecimal.ZERO;
		BigDecimal hours = _hours;
		if (hours.compareTo(new BigDecimal(43)) > 0){
			BigDecimal add = hours.subtract(new BigDecimal(43)).multiply(new BigDecimal(2)).setScale(2, RoundingMode.HALF_UP);
			add.multiply(_rate).setScale(2, RoundingMode.HALF_UP);
			grossPay.add(add);
			hours = BigDecimal.valueOf(43);
		}
		if (hours.compareTo(new BigDecimal(40)) > 0){
			BigDecimal add = hours.subtract(new BigDecimal(40)).multiply(new BigDecimal(1.5)).setScale(2, RoundingMode.HALF_UP);
			add = add.multiply(_rate).setScale(2, RoundingMode.HALF_UP);
			grossPay = grossPay.add(add);
			hours = BigDecimal.valueOf(40);
		}
		BigDecimal add = hours.multiply(_rate).setScale(2, RoundingMode.HALF_UP);
		grossPay = grossPay.add(add);
		return grossPay;
	}
	@Override
	public BigDecimal calculateGrossAnnualPay() {
		return calculateGrossPay().multiply(new BigDecimal(52)).setScale(2, RoundingMode.HALF_UP);
	}
}
