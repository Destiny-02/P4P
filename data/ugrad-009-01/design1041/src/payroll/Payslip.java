package payroll;
public class Payslip implements Comparable<Payslip> {
	private int _taxid;
	private String _firstName;
	private String _lastName;
	private String _employment;
	private double _rate;
	private double _ytd;
	private String _startdate;
	private String _enddate;
	private double _hours;
	private double _deduction;
	private double _gross = 0;
	private double _nett = 0;
	private double _paye = 0;
	public Payslip(String inputString){
		String[] fields = inputString.split("\t");
		_taxid = Integer.parseInt(fields[0]);
		String[] name = fields[1].split(", ");
		_firstName = name[1];
		_lastName = name[0];
		_employment = fields[2];
		StringManipulation string = new StringManipulation(fields[3]);
		_rate = Double.parseDouble(string.removeFirstChar());
		string = new StringManipulation(fields[4]);
		_ytd = Double.parseDouble(string.removeFirstChar());
		_startdate = fields[5];
		_enddate = fields[6];
		_hours = Double.parseDouble(fields[7]);
		string = new StringManipulation(fields[8]);
		_deduction = Double.parseDouble(string.removeFirstChar());
		calculateValues();
	}
	private void calcGross() {
		if (_employment.equals("Salaried")) {
			_gross = _rate / 52;
		} else if (_employment.equals("Hourly")) {
			_gross = _rate * 40;
			if (_hours > 40) {
				_gross += (_rate * (_hours - 40)) * 1.5;
			}
			if (_hours > 43) {
				_gross += (_rate * (_hours - 43)) * 2.0;
			}
		} else {
			throw new RuntimeException("Employment type incorrect: ");
		}
	}
	private void calcIncomeTax (){
		double annualgross = 0;
		annualgross = _gross * 52;
		if (annualgross < 14000) {
			_paye = annualgross * 10.5 / 100;
		} else if (annualgross > 14000) {
			_paye = 14000 * 10.5 / 100;
		}
		if (annualgross > 14000 && annualgross < 48000) {
			_paye += (annualgross - 14000) * 17.5 / 100;
		} else if (annualgross > 48000){
			_paye += (48000 - 14000) * 17.5 / 100;
		}
		if (annualgross > 48000 && annualgross < 70000) {
			_paye += (annualgross - 48000) * 30 / 100;
		} else if (annualgross > 70000){
			_paye += (70000 - 48000) * 30 / 100;
		}
		if (annualgross > 70000) {
			_paye += (annualgross - 70000) * 33 / 100;
		}
		_paye = _paye / 52;
	}
	private void calcYTD (){
		_ytd = _gross + _ytd;
		if (_ytd < 0) {
			throw new RuntimeException("Negative YTD value: ");
		}
	}
	private void calcNett () {
		_nett = _gross - _paye - _deduction;
		if (_nett < 0) {
			throw new RuntimeException("Negative Nett value: ");
		}
	}
	private void calculateValues(){
		this.calcGross();
		this.calcIncomeTax();
		this.calcYTD();
		this.calcNett();
	}
	public void printPayslip(){
		System.out.format("%d. %s %s, Period: %s to %s. Gross:"
				+ " $%.2f, PAYE: $%.2f, Deductions: $%.2f"
				+ " Nett: $%.2f YTD: $%.2f%n", _taxid, _firstName,
				_lastName, _startdate, _enddate, _gross, _paye,
				_deduction, _nett, _ytd);
	}
	public String getStartDate() {
		return _startdate;
	}
	public String getEndDate() {
		return _enddate;
	}
	public double getPaye() {
		return _paye;
	}
	public int compareTo(Payslip other) {
		if (_taxid < other._taxid) {
			return -1;
		} else if (_taxid > other._taxid) {
			return 1;
		} else {
			return 0;
		}
	}
}
