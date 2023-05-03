package payroll;
import java.text.DecimalFormat;
import java.util.Comparator;
public class Employee implements Comparable<Employee> {
	private DecimalFormat dp2 = new DecimalFormat(".00");
	public String round(double unrounded){
		return dp2.format(unrounded);
	}
	private static final int WEEKS_IN_YEAR = 52;
	private static final double TOP_TAX_PERCENTAGE = 0.33;
	private static final int TOP_TAX_BRACKET = 70000;
	private static final double SECOND_TAX_PERCENTAGE = 0.3;
	private static final int SECOND_TAX_BRACKET = 48000;
	private static final double THIRD_TAX_PERCENTAGE = 0.175;
	private static final int THIRD_TAX_BRACKET = 14000;
	private static final double LOWEST_TAX_PERCENTAGE = 0.105;
	private int _tid;
	private String _familyCommaGiven;
	private String _familyName;
	private String _givenName;
	private String _givenSpaceFamily;
	private String _employment;
	private double _rate;
	private String _rateString;
	private double _ytd;
	private String _ytdString;
	private String _start;
	private String _end;
	private double _hours;
	private double _hourlyGross;
	private double _gross;
	private String _grossString;
	private double _annualRate;
	private double _paye;
	private String _payeString;
	private double _deduction;
	private String _deductionString;
	private double _nett;
	private String _nettString;
	public Employee (String[] recordArray) {
		setRecords(recordArray);
	}
	private void setRecords(String[] recordArray) {
		_tid = Integer.parseInt(recordArray[0]);
		setName(recordArray[1]);
		setEmployment(recordArray[2]);
		setRate(recordArray[3]);
		setYtd(recordArray[4]);
		_start = recordArray[5];
		_end = recordArray[6];
		setHours(recordArray[7]);
		calcHourlyGross();
		calcGross();
		calcAnnualRate();
		calcPaye();
		setDeduction(recordArray[8]);
		updateYtd();
	}
	private void setName(String fromRecordArray){
		if (!fromRecordArray.contains(",")){
			throw new RuntimeException("Employee name '" + fromRecordArray + "' is in the wrong format. Should be 'familyname comma givenname'.");
		}
		else {
			_familyCommaGiven = fromRecordArray;
			_familyName = fromRecordArray.split(",")[0];
			_givenName = fromRecordArray.split(",")[1].trim();
			_givenSpaceFamily = _givenName + " " + _familyName;
		}
	}
	private void setEmployment(String fromRecordArray){
		String employmentEntered = fromRecordArray;
		if (!(employmentEntered.equals("Salaried") || employmentEntered.equals("Hourly"))) {
			throw new RuntimeException("Invalid employment type for " + _givenSpaceFamily + ". Must be 'Salaried' or 'Hourly' (case sensitive).");
		} else {
			_employment = employmentEntered;
		}
	}
	private void setRate(String fromRecordArray){
		if (fromRecordArray.charAt(0)!='$'){
			throw new RuntimeException(_givenSpaceFamily + "'s Rate is in the wrong format. Should be start with a $ sign.");
		}
		else {
			double rateEntered = Double.parseDouble(fromRecordArray.replace('$', '\0'));
			if (rateEntered < 0) {
				throw new RuntimeException(_givenSpaceFamily + " earns a negative rate. This is incorrect.");
			} else {
				_rate = rateEntered;
				_rateString = round(_rate);
			}
		}
	}
	private void setYtd(String fromRecordArray){
		if (fromRecordArray.charAt(0)!= '$'){
			throw new RuntimeException(_givenSpaceFamily + "'s YTD is in the wrong format. Should be start with a $ sign.");
		}
		else {
			double ytdEntered = Double.parseDouble(fromRecordArray.replace('$', '\0'));
			if (ytdEntered < 0) {
				throw new RuntimeException(_givenSpaceFamily + " has a negative YTD value. This is incorrect.");
			} else {
				_ytd = ytdEntered;
			}
		}
	}
	private void setHours(String fromRecordArray){
		double hoursEntered = Double.parseDouble(fromRecordArray);
		if (hoursEntered > 168) {
			throw new RuntimeException(_givenSpaceFamily + " worked more hours than there are in a week. This is incorrect.");
		} else if (hoursEntered < 0){
			throw new RuntimeException(_givenSpaceFamily + " worked a negative number of hours. This is incorrect.");
		} else {
			_hours = hoursEntered;
		}
	}
	private void calcHourlyGross() {
		if (_hours > 43) {
			_hourlyGross =((_hours-43)*2 + 3*1.5 + 40)*_rate;
		} else if (_hours > 40){
			_hourlyGross =((_hours-40)*1.5 + 40)*_rate;
		} else {
			_hourlyGross = _hours*_rate;
		}
	}
	private void calcGross() {
		if (_employment.equals("Salaried")) {
			_gross = _rate/WEEKS_IN_YEAR;
		} else {
			_gross = _hourlyGross;
		}
		_grossString = round(_gross);
	}
	private void calcAnnualRate() {
		if (_employment.equals("Salaried")) {
			_annualRate = _rate;
		} else {
			_annualRate = WEEKS_IN_YEAR*Double.parseDouble(_grossString);
		}
	}
	private void calcPaye() {
		if (_annualRate > TOP_TAX_BRACKET) {
			_paye=((_annualRate-TOP_TAX_BRACKET)*TOP_TAX_PERCENTAGE + (TOP_TAX_BRACKET-SECOND_TAX_BRACKET)*SECOND_TAX_PERCENTAGE + (SECOND_TAX_BRACKET-THIRD_TAX_BRACKET)*THIRD_TAX_PERCENTAGE + THIRD_TAX_BRACKET*LOWEST_TAX_PERCENTAGE)/WEEKS_IN_YEAR;
		} else if (_annualRate > SECOND_TAX_BRACKET){
			_paye=((_annualRate-SECOND_TAX_BRACKET)*SECOND_TAX_PERCENTAGE + (SECOND_TAX_BRACKET-THIRD_TAX_BRACKET)*THIRD_TAX_PERCENTAGE + THIRD_TAX_BRACKET*LOWEST_TAX_PERCENTAGE)/WEEKS_IN_YEAR;
		} else if (_annualRate > THIRD_TAX_BRACKET) {
			_paye=((_annualRate-THIRD_TAX_BRACKET)*THIRD_TAX_PERCENTAGE + THIRD_TAX_BRACKET*LOWEST_TAX_PERCENTAGE)/WEEKS_IN_YEAR;
		} else {
			_paye=(_annualRate*LOWEST_TAX_PERCENTAGE)/WEEKS_IN_YEAR;
		}
		_payeString = round(_paye);
	}
	private void setDeduction(String fromRecordArray){
		double deductionEntered = Double.parseDouble(fromRecordArray.replace('$', '\0'));
		double resultingNett = Double.parseDouble(_grossString) - deductionEntered - Double.parseDouble(_payeString);
		if (resultingNett < 0) {
			throw new RuntimeException("The deduction of $" + deductionEntered + " is greater than what " + _givenSpaceFamily + " earns, and will result in a negative nett for this pay period. Something in the input records is wrong. E.g. deductions is too large, wrote 'Salaried' for an hourly employee, etc.");
		} else {
			_deduction = deductionEntered;
			_deductionString = round(_deduction);
			_nett = resultingNett;
			_nettString = round(_nett);
		}
	}
	private void updateYtd() {
		_ytd = _ytd + Double.parseDouble(_grossString);
		_ytdString = round(_ytd);
	}
	public String getStart() {
		String startDate = _start + " ";
		return startDate;
	}
	public String getEnd() {
		String endDate = " " + _end;
		return endDate;
	}
	public int compareTo (Employee aEmployee) {
		int comparedToTID = aEmployee._tid;
		if(this._tid == comparedToTID) {
			return 0;
		} else {
			return this._tid < comparedToTID ? -1 : 1;
		}
	}
	public static class FamilyNameComparator implements Comparator<Employee> {
		public int compare(Employee employee1, Employee employee2) {
			String employee1Name = employee1._familyCommaGiven;
			String employee2Name = employee2._familyCommaGiven;
			int nameComp = employee1Name.compareTo(employee2Name);
			if (nameComp != 0) {
				return nameComp;
			} else {
				return (employee1._tid < employee2._tid ? -1 : 1);
			}
		}
	};
	public String payslipsLine() {
		String line = _tid + ". " + _givenSpaceFamily + ", Period: " + _start + " to " + _end + ". Gross: $" + _grossString+ ", PAYE: $" + _payeString + ", Deductions: $" + _deductionString + " Nett: $" + _nettString + " YTD: $" + _ytdString;
		return line;
	}
	public String employeesLine() {
		String line = _familyCommaGiven + " (" + _tid + ") " + _employment + ", $" + _rateString + " YTD:$" + _ytdString;
		return line;
	}
	public double getGrossCopy() {
		double grossCopy = Double.parseDouble(_grossString);
		return grossCopy;
	}
	public double getPayeCopy() {
		double payeCopy = Double.parseDouble(_payeString);
		return payeCopy;
	}
}