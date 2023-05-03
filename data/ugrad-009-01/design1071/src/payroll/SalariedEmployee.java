package payroll;
public class SalariedEmployee implements Employable {
	private int _taxID;
	private String _lastName;
	private String _firstName;
	private String _employment;
	private double _rate;
	private double _ytd;
	private String _startDate;
	private String _endDate;
	private double _hours;
	private double _deduction;
	public SalariedEmployee(int taxID, String lastName, String firstName, String employment, double rate, double ytd, String startDate, String endDate, double hours, double deduction){
		_taxID = taxID;
		_lastName = lastName;
		_firstName = firstName;
		_employment = "Salaried";
		_rate = rate;
		_ytd = ytd;
		_startDate = startDate;
		_endDate = endDate;
		_hours = hours;
		_deduction = deduction;
	}
	public SalariedEmployee(){
		this(0, "First", "Last", "Salaried", 0, 0, "1970-01-01", "2038-12-31", 0, 0);
	}
	public double getWeekly(){
		return _rate/WEEKS_IN_YEAR;
	}
	public double getPaye(){
		TaxCalculator calc = new TaxCalculator();
		return calc.computeYearTax(_rate)/52;
	}
	public double getNett(){
		return getWeekly() - getPaye() - _deduction;
	}
	public double getNewYTD(){
		return _ytd + getWeekly();
	}
	public String generateEntry(FormatType process){
		Formatter formatter = new Formatter();
		switch(process){
		case PAYSLIPS:
			return formatter.formatPayslipEntry(_taxID, _firstName, _lastName, _startDate, _endDate, getWeekly(), getPaye(), _deduction, getNett(), getNewYTD());
		case EMPLOYEES:
			return formatter.formatEmployees(_lastName, _firstName, _taxID, _employment, _rate, getNewYTD());
		default:
			return "<Invalid case>";
		}
	}
	public double getYearSalary(){
		return _rate;
	}
	public String toString(){
		return _taxID + "\t" + _lastName + ", " + _firstName + "\t" + _employment + "\t $" + _rate + "\t $" + _ytd + "\t" + _startDate + "\t" + _endDate + "\t" + _hours + "\t $" + _deduction;
	}
	public int getID() {
		return _taxID;
	}
	public String getLastName() {
		return _lastName;
	}
	public String getStartDate() {
		return _startDate;
	}
	public String getEndDate() {
		return _endDate;
	}
}
