package payroll;
public final class Payslips extends Operations{
	private int _taxID;
	private String _startDate;
	private String _endDate;
	private float _grossEarnings;
	private float _PAYE;
	private float _deductions;
	private float _NETT;
	private float _YTD;
	private String _givenNames;
	private String _familyName;
	public Payslips(
			int taxID, String givenNames, String familyName,
			String startDate, String endDate,
			float PAYE, float deductions, float grossEarnings, float YTD, float NETT
			){
		_taxID = taxID;
		_givenNames = givenNames;
		_familyName = familyName;
		_startDate = startDate;
		_endDate = endDate;
		_grossEarnings = grossEarnings;
		_PAYE = PAYE;
		_deductions = deductions;
		_YTD = YTD;
		_NETT = NETT;
	}
	public void printOutput(){
		System.out.println(
				_taxID + ". " +
				_givenNames + " " + _familyName + ", Period: " +
				_startDate + " to " +
				_endDate + ". " + "Gross: $" +
				roundToTwoDec(_grossEarnings) + ", PAYE: $" +
				roundToTwoDec(_PAYE) + ", Deductions: $" +
				roundToTwoDec(_deductions) + " Nett: $" +
				roundToTwoDec(_NETT) + " YTD: $" +
				roundToTwoDec(_YTD)
				);
	}
}
