package payroll;
public class Employee implements Comparable<Employee> {
	private Integer _tid;
	private String _firstName;
	private String _lastName;
	private boolean _hourly;
	private boolean _salaried;
	private int _rate;
	private int _ytd;
	private String _startDate;
	private String _endDate;
	private double _hours;
	private int _deductions;
	private int _grossIncome;
	private int _paye;
	private int _nett;
	public void storeInformation(String info) {
		String[] strArray = info.split("\t");
		_tid = Integer.parseInt(strArray[0]);
		_lastName = strArray[1];
		_firstName = strArray[2];
		if (strArray[3].equals("Hourly")) {
			_hourly = true;
		} else {
			_salaried = true;
		}
		_rate = (int) (Double.parseDouble(strArray[4]) * 100);
		_ytd = (int) (Double.parseDouble(strArray[5]) * 100);
		_startDate = strArray[6];
		_endDate = strArray[7];
		_hours = Double.parseDouble(strArray[8]);
		_deductions = (int) (Double.parseDouble(strArray[9]) * 100);
		interperateFinancialInfo();
	}
	private void interperateFinancialInfo() {
		Finances f = new Finances();
		_grossIncome = f.calcGrossIncome(_salaried, _rate, _hours);
		_ytd = _grossIncome + _ytd;
		_paye = f.calcPaye(_grossIncome);
		_nett = (int) (_grossIncome - _paye - _deductions);
	}
	public String getLastName() {
		return _lastName;
	}
	public void displayEmployeeInfo() {
		Finances f = new Finances();
		System.out.println(_lastName + ", " + _firstName + " (" + _tid + ") "
				+ getEmployeeType() + ", " + f.currencyFormat(_rate) + " YTD:"
				+ f.currencyFormat(_ytd));
	}
	public void displayPayslip() {
		Finances f = new Finances();
		System.out.println(_tid + ". " + _firstName + " " + _lastName
				+ ", Period: " + _startDate + " to " + _endDate + ". Gross: "
				+ f.currencyFormat(_grossIncome) + ", PAYE: "
				+ f.currencyFormat(_paye) + ", Deductions: "
				+ f.currencyFormat(_deductions) + " Nett: "
				+ f.currencyFormat(_nett) + " YTD: " + f.currencyFormat(_ytd));
	}
	private String getEmployeeType() {
		if (_hourly == true) {
			return "Hourly";
		} else {
			return "Salaried";
		}
	}
	public int getGrossIncome() {
		return _grossIncome;
	}
	public void getPayPeriod() {
		System.out.println(_startDate + " to " + _endDate);
	}
	public int getPaye() {
		return _paye;
	}
	public int getTid() {
		return _tid;
	}
	public boolean vaildEmpolyeeFinancialInfo() {
		if (_hours < 0 || _rate < 0 || _ytd < 0 || _tid < 0 || _deductions < 0) {
			return false;
		} else {
			return true;
		}
	}
	@Override
	public int compareTo(Employee other) {
		if (_tid < other._tid) {
			return -1;
		} else if (_tid > other._tid) {
			return 1;
		} else {
			return 0;
		}
	}
}
