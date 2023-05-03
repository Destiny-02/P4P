package payroll;
public abstract class Employee {
	protected String _name;
	protected Double _gross;
	protected Double _yeartodate;
	protected Integer _taxid;
	public Employee(String name, Double YTD, Integer taxid) {
		_name = name;
		_gross = 0.00;
		_yeartodate = YTD;
		_taxid = taxid;
	}
	public Integer getTaxID() {
		return Integer.valueOf(_taxid);
	}
	public String getName() {
		String namecopy = _name;
		return namecopy;
	}
	public abstract Double getRate();
	public abstract boolean isHourly();
	public Double getGross() {
		return _gross;
	}
	public Double getYearToDate(){
		return _yeartodate;
	}
	public void addToGross(Double pay) {
		_gross += pay;
		return;
	}
	public void addToYearToDate(double d) {
		_yeartodate += d;
		return;
	}
}
