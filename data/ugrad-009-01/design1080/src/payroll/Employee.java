package payroll;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
public abstract class Employee {
	private int _tid;
	private String _firstName;
	private String _lastName;
	private double _rate;
	private double _ytd;
	private String _startDate;
	private String _endDate;
	private double _hours;
	private double _deduction;
	private double _gross;
	private double _paye;
	private double _nett;
	private String _type;
	abstract double getGross();
	abstract double getPaye();
	abstract String getType();
	public Employee(String details) throws PayrollException {
		String[] arr = details.split("\t");
		registerInformation(arr);
	}
	public void setCalculatedFields() throws PayrollException {
		_gross = getGross();
		_paye = getPaye();
		_nett = _gross - _paye - _deduction;
		_type = getType();
		if (_deduction > _gross) {
			throw new PayrollException("Higher deductions than base earnings.");
		}
	}
	private void registerInformation(String[] array) throws PayrollException {
		_tid = Integer.parseInt(array[0]);
		if (array[1].contains(",")) {
			_firstName = array[1].split(",")[1].trim();
			_lastName = array[1].split(",")[0].trim();
		}
		_rate = Double.parseDouble(array[3].replaceAll("\\$", ""));
		_ytd = Double.parseDouble(array[4].replaceAll("\\$", ""));
		_startDate = array[5];
		_endDate = array[6];
		_hours = Double.parseDouble(array[7]);
		_deduction = Double.parseDouble(array[8].replaceAll("\\$", ""));
		checkInputs(array);
	}
	private void checkInputs(String[] array) throws PayrollException {
		int startYear = (Integer.parseInt(_startDate.replaceAll("-", "").substring(0, 4)));
		int endYear = (Integer.parseInt(_endDate.replaceAll("-", "").substring(0, 4)));
		int startMonth = (Integer.parseInt(_startDate.replaceAll("-", "").substring(4, 6)));
		int endMonth = (Integer.parseInt(_endDate.replaceAll("-", "").substring(4, 6)));
		int startDay = (Integer.parseInt(_startDate.replaceAll("-", "").substring(6, 8)));
		int endDay = (Integer.parseInt(_endDate.replaceAll("-", "").substring(6, 8)));
		int startValue = ((startYear * 375) + (startMonth * 31) + startDay);
		int endValue = ((endYear  * 375) + (endMonth * 31) + endDay);
		if ((!array[3].contains("$")) || (!array[4].contains("$")) || (!array[8].contains("$"))) {
			throw new PayrollException("Missing '$' sign in the input.");
		}
		if (!array[1].contains(",")) {
			throw new PayrollException("Missing ',' sign in the name input.");
		}
		if ((!array[5].matches("^[0-9]{4}(-[0-9]{2}){2}$")) || (!array[6].matches("^[0-9]{4}(-[0-9]{2}){2}$"))) {
			throw new PayrollException("Incorrect date format.");
		}
		if (!array[1].trim().contains(" ")) {
			throw new PayrollException("No space between the name inputs.");
		}
		if ((array[0].contains("-")) || (array[3].contains("-")) || (array[4].contains("-")) || (array[7].contains("-")) || (array[8].contains("-"))) {
			throw new PayrollException("Negative numbers detected.");
		}if (startValue > endValue) {
			throw new PayrollException("Start date later than end date");
		}
	}
	public double round(double number) {
	    BigDecimal bd = new BigDecimal(number).setScale(2, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	public String printDouble(Double number) {
		DecimalFormat df = new DecimalFormat("#.00");
	    BigDecimal bd = new BigDecimal(number).setScale(2, RoundingMode.HALF_UP);
	    return ("$" + df.format(bd));
	}
	public String payslipProcessing() {
		String payslip = "";
		payslip = payslip.concat(_tid + ". ");
		payslip = payslip.concat(_firstName + " " + _lastName + ", ");
		payslip = payslip.concat("Period: "  + _startDate + " to " + _endDate + ". ");
		payslip = payslip.concat("Gross: " + printDouble(_gross) + ", ");
		payslip = payslip.concat("PAYE: " + printDouble(_paye) + ", ");
		payslip = payslip.concat("Deductions: " + printDouble(_deduction) + " ");
		payslip = payslip.concat("Nett: " + printDouble(_nett) + " ");
		payslip = payslip.concat("YTD: " + printDouble(_ytd + _gross));
		return payslip;
	}
	public String employeeProcessing() {
		String employee = "";
		employee = employee.concat(_lastName + ", " + _firstName + " ");
		employee = employee.concat("(" + _tid + ") ");
		employee = employee.concat(_type + ", " + printDouble(_rate) + " ");
		employee = employee.concat("YTD:" + printDouble(_ytd + _gross));
		return employee;
	}
	public double burdenProcessing() {
		return _gross;
	}
	public double payeProcessing() {
		return _paye;
	}
	public int getTid() {
		return this._tid;
	}
	public String getFirstName() {
		return _firstName;
	}
	public String getLastName() {
		return _lastName;
	}
	public double getRate() {
		return _rate;
	}
	public double getYtd() {
		return _ytd;
	}
	public String getStartDate() {
		return _startDate;
	}
	public String getEndDate() {
		return _endDate;
	}
	public double getHours() {
		return _hours;
	}
	public double getDeduction() {
		return _deduction;
	}
}
	