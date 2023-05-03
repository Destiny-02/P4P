package payroll;
public class HourlyPaidEmployee extends Employee {
	private double _rate;
	private double _hours;
	private double _pay;
	public static final double std_overtime = 40;
	public static final double std_multiplier = 1.5;
	public static final double double_overtime = 43;
	public static final double double_multiplier = 2;
	public HourlyPaidEmployee(int tid, String name, String employment, String rate, String ytd, String startDate,
			String endDate, double hours, String deduction) {
		super(tid, name, employment, rate, ytd, startDate, endDate, hours, deduction);
		_rate = Double.parseDouble(rate);
		_hours = hours;
	}
	public String computeGross() {
		if (_hours <= std_overtime) {
			_pay = _hours * _rate;
		} else if (std_overtime <= _hours && _hours <= double_overtime) {
			_pay =  std_overtime * _rate + (_hours - std_overtime) *
					_rate * std_multiplier;
		} else {
			_pay = std_overtime * _rate + (_hours - std_overtime) *
					_rate * std_multiplier + (_hours - double_overtime) * _rate * double_multiplier;
		}
		String roundedPay = formatDouble(_pay);
		return roundedPay;
	}
}

