package employee;
public class SalariedEmployee extends GeneralEmployee {
	public SalariedEmployee(int TID, String fullName, String employment, double rate, double ytd, String dateStart, String dateEnd, double hoursWorked, double deduction)
	{
		super(TID, fullName, employment, rate, ytd, dateStart, dateEnd, hoursWorked,
				deduction);
	}
	@Override
	protected double calcGross() {
		double gross = _rate/52;
		return gross;
	}
}
