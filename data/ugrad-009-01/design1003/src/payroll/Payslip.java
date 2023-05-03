package payroll;
public class Payslip {
	private Employee _employee;
	private String _startdate;
	private String _enddate;
	private Double _hours;
	private Double _deductions;
	private Double _paye;
	private int _tid;
	private Double _nettpay;
	public Payslip(int tid, Employee employee, String startdate, String enddate, Double hours, Double deductions) {
		_employee = employee;
		_startdate = startdate;
		_hours = hours;
		_deductions = deductions;
		_paye = 0.00;
		_enddate = enddate;
		_nettpay = 0.00;
	}
	public Double getTax() {
		return Double.valueOf(_tid);
	}
	public Employee getEmployee() {
		return _employee;
	}
	public String getStartDate() {
		return _startdate;
	}
	public String getEndDate() {
		return _enddate;
	}
	public Double getHours() {
		return Double.valueOf(_hours);
	}
	public int getTaxID() {
		return _tid;
	}
	public Double getDeductions() {
		return Double.valueOf(_deductions);
	}
	public Double getPAYE() {
		return Double.valueOf(_paye);
	}
	public Double getNett() {
		return Double.valueOf(_nettpay);
	}
	public void payEmployee() {
		TaxCalculator taxman = new TaxCalculator();
		if(_employee.isHourly()){
			Double pay = _hours * _employee.getRate();
			_paye = taxman.calculateTax(pay);
			_employee.addToGross(pay);
			_nettpay = pay-_deductions;
			_employee.addToYearToDate(_nettpay);
		}
		else {
			Double pay = (_employee.getRate()/52);
			_paye = taxman.calculateTax(pay);
			_employee.addToGross(pay);
			_employee.addToYearToDate(pay-_deductions);
		}
	}
}
