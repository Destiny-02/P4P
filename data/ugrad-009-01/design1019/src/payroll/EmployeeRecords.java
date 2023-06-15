package payroll;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class EmployeeRecords {
	private int _taxID;
	private String _familyName;
	private String _givenName;
	private String _employment;
	private double _rate;
	private double _yearToDate;
	private String _startDate;
	private String _endDate;
	private double _hours;
	private double _deduction;
	public EmployeeRecords(int taxID, String familyName, String givenName, String employment, double rate, double yearToDate, String startDate, String endDate, double hours, double deduction) throws PayrollException {
		_taxID = taxID;
		_familyName = familyName;
		_givenName = givenName;
		_employment = employment;
		_rate = rate;
		_yearToDate = yearToDate;
		_startDate = startDate;
		_endDate = endDate;
		_hours = hours;
		_deduction = deduction;
		if (_taxID < 0) {
			throw new PayrollException("Negative Tax ID: "+_taxID+" for "+_familyName+", "+_givenName);
		} else if (_rate < 0) {
			throw new PayrollException("Negative rate: $"+_rate+" for "+_familyName+", "+_givenName);
		} else if (_yearToDate < 0) {
			throw new PayrollException("Negative YTD: $"+_yearToDate+" for "+_familyName+", "+_givenName);
		} else if (_hours < 0) {
			throw new PayrollException("Negative hours: "+_hours+" for "+_familyName+", "+_givenName);
		} else if (_deduction < 0) {
			throw new PayrollException("Negative deduction: $"+_deduction+" for "+_familyName+", "+_givenName);
		} else {}
	}
	public int getTaxID() {
		int temp = _taxID;
		return temp;
	}
	public String getFamilyName() {
		return _familyName;
	}
	public String getGivenName() {
		String temp = _givenName;
		return temp;
	}
	public String getEmployment() {
		String temp = _employment;
		return temp;
	}
	public double getRate() {
		double temp = _rate;
		return temp;
	}
	public double getYearToDate() {
		double temp = _yearToDate;
		return temp;
	}
	public String getStartDate() {
		String temp = _startDate;
		return temp;
	}
	public String getEndDate() {
		String temp = _endDate;
		return temp;
	}
	public double getHours() {
		double temp = _hours;
		return temp;
	}
	public double getDeduction() {
		double temp = _deduction;
		return temp;
	}
	public void compareStartAndEndDate() throws PayrollException {
		try {
			Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(this._startDate);
			Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(this._endDate);
			int comparison = startDate.compareTo(endDate);
			if (comparison > 0) {
				throw new PayrollException("Start Date "+this._startDate+" after End Date "+this._endDate+" for "+_familyName+", "+_givenName);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public double getGross() {
		if ( _employment.equals("Salaried") ) {
			return _rate/52.0;
		} else {
			if (_hours <= 40.0) {
				return _rate*_hours;
			} else if (_hours > 40.0 && _hours <= 43.0) {
				return _rate*40.0+_rate*(_hours-40.0)*1.5;
			} else {
				return _rate*40.0+_rate*3.0*1.5+_rate*(_hours-43.0)*2.0;
			}
		}
	}
	public double getCurrentYTD() {
		 return this.getYearToDate() + this.getGross();
	}
}
