package payroll;
public final class Burden extends WeekReport{
	private float _burden;
	public Burden(String startDate, String endDate, float burden){
		super(startDate,endDate);
		_burden = burden;
	}
	public void printOutput(){
		printPeriod();
		System.out.println("Total Burden: $" + roundToTwoDec(_burden));
	}
}
