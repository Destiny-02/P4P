package payroll;
public class Employees {
	private int _taxid;
	private String _firstname, _lastname, _employment, _startperiod, _endperiod;
	private double _rate, _yeartodate, _hours, _deduction, _gross, _paye, _nett, _currentYTD;
	public Employees (int taxid, String lastname, String firstname, String employment, double rate,
			double yeartodate, String startperiod, String endperiod, double hours, double deduction) {
		_taxid = taxid;
		_firstname = firstname;
		_lastname = lastname;
		_employment = employment;
		_rate = rate;
		_yeartodate = yeartodate;
		_startperiod = startperiod;
		_endperiod = endperiod;
		_hours = hours;
		_deduction = deduction;
	}
	public Employees (int taxid, String lastname, String firstname, String employment, double rate,
			double yeartodate, String startperiod, String endperiod, double hours, double deduction,
			double gross, double paye, double nett, double currentYTD) {
		_taxid = taxid;
		_firstname = firstname;
		_lastname = lastname;
		_employment = employment;
		_rate = rate;
		_yeartodate = yeartodate;
		_startperiod = startperiod;
		_endperiod = endperiod;
		_hours = hours;
		_deduction = deduction;
		_gross = gross;
		_paye = paye;
		_nett = nett;
		_currentYTD = currentYTD;
	}
	public int getTaxid() { return _taxid; }
	public String getFirstname() { return _firstname; }
	public String getLastname() { return _lastname; }
	public String getEmployment() { return _employment; }
	public double getRate() { return _rate; }
	public void setGross(double gross) { _gross = gross; }
	public double getGross() { return _gross; }
	public double getHours() { return _hours; }
	public void setPaye(double paye) { _paye = paye; }
	public double getPaye() { return _paye; }
	public double getDeduction() { return _deduction; }
	public void setNett(double nett) { _nett = nett; }
	public double getNett() { return _nett; }
	public double getYTD() { return _yeartodate; }
	public String getStart() { return _startperiod; }
	public String getEnd() { return _endperiod; }
	public void setCurrentYTD(double ytd) { _currentYTD = ytd; }
	public double getCurrentYTD() { return _currentYTD; }
}
