package payroll;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Employee implements Comparable<Employee> {
	private Integer _taxID;
	private String _firstName;
	private String _lastName;
	private BigDecimal _rate;
	private Pay _empPayslip;
	private PayType _payType;
	private Date _start;
	private Date _end;
	public final static DateFormat _dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public Employee(Integer taxID, String lastName, String firstName, String payType, BigDecimal rate, BigDecimal ytd,
			Date start, Date end, BigDecimal hours, BigDecimal deductions) throws Exception {
		_taxID = taxID;
		_firstName = firstName;
		_lastName = lastName;
		_rate = rate;
		_start = start;
		_end = end;
		_payType = PayType.getPaytypeForToken(payType);
		if(_payType == PayType.Salary) {
				_empPayslip = new SalariedPay(this, hours, deductions, rate, ytd);
		} else if(_payType == PayType.Hourly){
			_empPayslip = new HourlyPay(this, hours, deductions, rate, ytd);
		} else {
			throw new Exception("Paytype invalid");
		}
	}
	@Override
	public String toString() {
		StringBuilder EmpString = new StringBuilder(_lastName);
		EmpString.append(", ").append(_firstName);
		EmpString.append(" (").append(_taxID).append(") ");
		EmpString.append(_payType.GetToken()).append(", ");
		EmpString.append("$").append(String.format("%.2f", _rate));
		EmpString.append(" YTD:$").append(String.format("%.2f", _empPayslip.calculateYTD()));
		return EmpString.toString();
	}
	@Override
	public int compareTo(Employee CompEmp) {
		int comparison = _lastName.compareTo(CompEmp._lastName);
		if(comparison == 0){
			return _firstName.compareTo(CompEmp._firstName);
		}
		return comparison;
	}
	public String formatPayPeriod() {
		StringBuilder payPeriod = new StringBuilder(_dateFormat.format(_start));
		payPeriod.append(" to ").append(_dateFormat.format(_end));
		return payPeriod.toString();
	}
	public String getPayslip() throws Exception {
		return _empPayslip.formatPayslip();
	}
	public BigDecimal getGross() {
		return _empPayslip.calculateGrossPay();
	}
	public BigDecimal getPAYE() {
		return _empPayslip.calculatePAYE();
	}
	public Integer getTaxID() {
		return _taxID;
	}
	public String getFirstName() {
		return _firstName;
	}
	public String getLastName() {
		return _lastName;
	}
}