package payroll;
public class HourlyEmployee extends Employee {
	private boolean _hourly = true;
	private Double _rate;
	public HourlyEmployee(String name, Double YTD, Integer taxid, Double wage) {
		super(name, YTD, taxid);
		_rate = wage;
	}
	public boolean isHourly() {
		return _hourly;
	}
	public Double getRate() {
		return _rate;
	}
}
