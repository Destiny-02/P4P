package employee;
public class HourlyEmployee extends GeneralEmployee
{
	public HourlyEmployee(int TID, String fullName, String employment, double rate,
			double ytd, String dateStart, String dateEnd, double hoursWorked,
			double deduction) {
		super(TID, fullName, employment, rate, ytd, dateStart, dateEnd, hoursWorked,
				deduction);
	}
	@Override
	protected double calcGross() {
		double gross = 0;
		if (_hoursWorked > 43)
		{
			gross = (_rate * 40) + (3 * 1.5 * _rate) + ((_hoursWorked - 43) * 2 * _rate);
		}
		else if ((_hoursWorked > 40) && (_hoursWorked <= 43))
		{
			gross = (_rate * 40) + ((_hoursWorked - 40) * 1.5 * _rate);
		}
		else
		{
			gross = (_rate * _hoursWorked);
		}
		return gross;
	}
}
