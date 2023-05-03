package payroll;
public class Employees implements Comparable<Employees> {
	private int _taxid;
	private String _firstName;
	private String _lastName;
	private String _employment;
	private double _rate;
	private double _ytd;
	private String _startdate;
	private String _enddate;
	private double _hours;
	private double _gross = 0;
	public Employees(String inputString){
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
	private void calcYTD (){
		_ytd = _gross + _ytd;
		if (_ytd < 0) {
			throw new RuntimeException("Negative YTD value: ");
		}
	}
	private void calculateValues(){
		this.calcGross();
		this.calcYTD();
	}
	public void printEmployees(){
		System.out.format("%s, %s (%d) %s, $%.2f YTD:$%.2f%n",
				_lastName, _firstName, _taxid, _employment, _rate,
				_ytd);
	}
	public String getStartDate() {
		return _startdate;
	}
	public String getEndDate() {
		return _enddate;
	}
	public double getGross() {
		return _gross;
	}
	public int compareTo(Employees other) {
		int val = _lastName.compareToIgnoreCase(other._lastName);
		if (val < 0) {
			return -1;
		} else if (val > 0) {
			return 1;
		} else {
			val = _firstName.compareToIgnoreCase(other._firstName);
			if (val < 0) {
				return -1;
			} else if (val > 0) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}