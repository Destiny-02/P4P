package payroll;
public final class PAYE extends WeekReport{
	private float _PAYE;
	public PAYE(String startDate, String endDate, float PAYE){
		super(startDate,endDate);
		_PAYE = PAYE;
	}
	public void printOutput(){
		printPeriod();
		System.out.println("Total PAYE: $" + roundToTwoDec(_PAYE));
	}
}
