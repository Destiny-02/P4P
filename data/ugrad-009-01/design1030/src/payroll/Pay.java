package payroll;
import java.math.BigDecimal;
import java.math.RoundingMode;
public abstract class Pay{
	private Employee _employee;
	protected BigDecimal _hours;
	private BigDecimal _deductions;
	protected BigDecimal _rate;
	private BigDecimal _ytd;
	public Pay(Employee Employee, BigDecimal hours, BigDecimal deductions, BigDecimal rate, BigDecimal ytd) throws Exception{
		_employee = Employee;
		_hours = hours;
		_deductions = deductions;
		_rate = rate;
		_ytd = ytd;
	}
	public String formatPayslip() throws Exception {
		StringBuilder PayString = new StringBuilder(_employee.getTaxID().toString());
		PayString.append(". ").append(_employee.getFirstName()).append(" ").append(_employee.getLastName()).append(", ");
		PayString.append("Period: ").append(_employee.formatPayPeriod());
		PayString.append(". Gross: $").append(calculateGrossPay()).append(", ");
		PayString.append("PAYE: $").append(calculatePAYE()).append(", ");
		PayString.append("Deductions: $").append(_deductions);
		PayString.append(" Nett: $").append(calculateNettPay());
		PayString.append(" YTD: $").append(calculateYTD());
		return PayString.toString();
	}
	public BigDecimal calculateNettPay() throws Exception {
		BigDecimal nett = calculateGrossPay().subtract(calculatePAYE()).subtract(_deductions);
		if(nett.compareTo(new BigDecimal(0)) < 0){
			throw new Exception("Nett Pay is less than $0");
		}
		return nett;
	}
	public BigDecimal calculateYTD() {
		return _ytd.add(calculateGrossPay());
	}
	public  BigDecimal calculatePAYE(){
		BigDecimal annualPay = calculateGrossAnnualPay();
		BigDecimal partTax1 = BigDecimal.ZERO, partTax2 = BigDecimal.ZERO, partTax3 = BigDecimal.ZERO, partTax4 = BigDecimal.ZERO;
		if (annualPay.compareTo(new BigDecimal(70000)) > 0) {
			partTax1 = annualPay.subtract(new BigDecimal(70000)).multiply(new BigDecimal(0.33)).setScale(2, RoundingMode.HALF_UP);
			annualPay = BigDecimal.valueOf(70000);
		}
		if (annualPay.compareTo(new BigDecimal(48000)) > 0) {
			partTax2 = annualPay.subtract(new BigDecimal(48000)).multiply(new BigDecimal(0.30)).setScale(2, RoundingMode.HALF_UP);
			annualPay = BigDecimal.valueOf(48000);
		}
		if (annualPay.compareTo(new BigDecimal(14000)) > 0) {
			partTax3 = annualPay.subtract(new BigDecimal(14000)).multiply(new BigDecimal(0.175)).setScale(2, RoundingMode.HALF_UP);
			annualPay = BigDecimal.valueOf(14000);
		}
		partTax4 = annualPay.multiply(new BigDecimal(0.105)).setScale(2, RoundingMode.HALF_UP);
		return partTax1.add(partTax2).add(partTax3).add(partTax4).divide(new BigDecimal(52), 2, RoundingMode.HALF_UP);
	}
	public abstract BigDecimal calculateGrossPay();
	public abstract BigDecimal calculateGrossAnnualPay();
}
