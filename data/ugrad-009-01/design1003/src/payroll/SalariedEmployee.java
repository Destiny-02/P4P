package payroll;
public class SalariedEmployee extends Employee {
	private boolean _hourly = false;
	private Double _rate;
	public SalariedEmployee(String name, Double YTD, Integer taxid, Double salary) {
		super(name, YTD, taxid);
		_rate = salary;
	}
	public Double getRate() {
		return _rate;
	}
	public boolean isHourly() {
		return _hourly;
	}
}
