
package employee;
public abstract class GeneralEmployee
{
	protected int _TID;
	protected String _fullName;
	protected String _employment;
	protected double _rate;
	protected double _ytd;
	protected String _dateStart;
	protected String _dateEnd;
	protected double _hoursWorked;
	protected double _deduction;
	protected double _gross;
	protected double _paye;
	protected double _nett;
	protected String _firstName;
	protected String _familyName;
	GeneralEmployee(int TID, String fullName, String employment, double rate, double ytd, String dateStart, String dateEnd, double hoursWorked, double deduction)
	{
		_TID = TID;
		_fullName = fullName;
		_employment = employment;
		_rate = rate;
		_dateStart = dateStart;
		_dateEnd = dateEnd;
		_hoursWorked = hoursWorked;
		_deduction = deduction;
		_gross = calcGross();
		_paye = calcPaye();
		_nett = calcNett();
		_ytd = calcYTD(ytd);
		splitFullNameIntoFirstAndLast();
	}
	protected abstract double calcGross();
	private double calcPaye()
	{
		double paye = 0;
		double annualGross = _gross * 52;
		if (annualGross <= 14000)
		{
			paye = (annualGross * 10.5 / 100) / 52;
		}
		else if ((annualGross > 14000) && (annualGross <= 48000))
		{
			paye = ((14000 * 10.5 / 100 ) + ((annualGross - 14000) * 17.5/100) ) / 52;
		}
		else if ((annualGross > 48000) && (annualGross <= 70000))
		{
			paye = ((14000 * 10.5 / 100 ) + (34000 * 17.5/100) + ((annualGross - 48000) * 30/100)) / 52;
		}
		else
		{
			paye = ((14000 * 10.5 / 100 ) + (34000 * 17.5/100) + (22000 * 30/100) + ((annualGross - 70000) * 33/100)) / 52;
		}
		return paye;
	}
	private double calcNett()
	{
		double nett = _gross - _paye - _deduction;
		return nett;
	}
	private double calcYTD(double inputYTD)
	{
		double outputYTD = inputYTD + _gross;
		return outputYTD;
	}
	private void splitFullNameIntoFirstAndLast()
	{
		String[] tempFullName = _fullName.split(", ");
		_familyName = tempFullName[0];
		_firstName = tempFullName[1];
	}
	public void printPayslip()
	{
		System.out.printf("%d. %s %s, Period: %s to %s. Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f Nett: $%.2f YTD: $%.2f\n", _TID, _firstName, _familyName, _dateStart, _dateEnd, _gross, _paye, _deduction, _nett, _ytd);
	}
	public void printEmployeeDetails()
	{
		System.out.printf("%s (%d) %s, $%.2f YTD:$%.2f\n", _fullName, _TID, _employment, _rate, _ytd);
	}
	public int getTID()
	{
		return _TID;
	}
	public String getFullName()
	{
		return _fullName;
	}
	public String getDateStart()
	{
		return _dateStart;
	}
	public String getDateEnd()
	{
		return _dateEnd;
	}
	public double getGross()
	{
		return _gross;
	}
	public double getPaye()
	{
		return _paye;
	}
}
