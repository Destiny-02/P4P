package payroll;
public class Person {
	final private int _tid;
	final private String _lastName;
	final private String _firstName;
	final private String _employment;
	final private double _rate;
	final private double _ytd;
	final private String _startDate;
	final private String _endDate;
	final private double _hours;
	final private double _deduction;
	final private double _gross;
	final private double _paye;
	final private double _nett;
	private Person _nextPerson;
	public Person(int tid, String lastName, String firstName, String employment, double rate, double ytd,
			    String startDate, String endDate, double hours, double deduction) {
		_tid = tid;
		_lastName = lastName;
		_firstName = firstName;
		_employment = correctingEmploymentCase(employment);
		_rate = rate;
		_startDate = startDate;
		_endDate = endDate;
		_hours = hours;
		_deduction = deduction;
		_gross = Math.round(calculateGross() * 100.0)/100.00;
		_paye = Math.round(calculatePaye() * 100.00)/100.00;
		_nett = _gross - _paye - deduction;
		_ytd = _gross + ytd;
	}
	private String correctingEmploymentCase(String employment) {
		if (employment.equalsIgnoreCase("Salaried")) {
			return "Salaried";
		} else if (employment.equalsIgnoreCase("Hourly")) {
			return "Hourly";
		} else {
			return employment;
		}
	}
	private double calculateGross() {
		if (_employment.equals("Salaried")) {
			return _rate/52;
		} else if (_hours <= 40) {
			return _rate * _hours;
		} else if (_hours <= 43) {
			return _rate * 40 + (_hours - 40) * 1.5 * _rate;
		} else {
			return _rate * 40 + 3 * 1.5 * _rate + (_hours - 43) * 2 * _rate;
		}
	}
	private double calculatePaye() {
		double total;
		if (_employment.equals("Salaried")) {
			total = _rate;
		} else {
			total = _gross * 52;
		}
		if (total == 0) {
			return 0;
		} else if (total <= 14000) {
			return (14000 * 0.105 / 52);
		} else if (total <= 48000) {
			return (14000 * 0.105 + (total - 14000) * 0.175)/52;
		} else if (total <= 70000) {
			return (14000 * 0.105 + 34000 * 0.175 + (total - 48000) * 0.3)/52;
		} else {
			return (14000 * 0.105 + 34000 * 0.175 + 22000 * 0.3 + (total - 70000) * 0.33)/52;
		}
	}
	public int getTid() {
		return _tid;
	}
	public String getFirstName() {
		return _firstName;
	}
	public String getLastName() {
		return _lastName;
	}
	public String getEmployment() {
		return _employment;
	}
	public double getRate() {
		return _rate;
	}
	public double getYtd() {
		return _ytd;
	}
	public String getStart() {
		return _startDate;
	}
	public String getEnd() {
		return _endDate;
	}
	public double getDeduction() {
		return _deduction;
	}
	public double getGross() {
		return _gross;
	}
	public double getPaye () {
		return _paye;
	}
	public double getNett() {
		return _nett;
	}
	public Person getNextPerson() {
		return _nextPerson;
	}
	public void setNextPerson(Person person) {
		_nextPerson = person;
	}
}
