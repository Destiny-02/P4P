package payroll;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class SalariedPay extends Pay {
	public SalariedPay(Employee Employee, BigDecimal hours, BigDecimal deductions, BigDecimal rate, BigDecimal ytd) throws Exception {
		super(Employee, hours, deductions, rate, ytd);
	}
	@Override
	public BigDecimal calculateGrossPay() {
		return _rate.divide(new BigDecimal(52),2,RoundingMode.HALF_UP);
	}
	@Override
	public BigDecimal calculateGrossAnnualPay() {
		return _rate;
	}
}
