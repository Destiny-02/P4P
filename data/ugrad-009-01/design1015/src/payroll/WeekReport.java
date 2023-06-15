package payroll;
abstract public class WeekReport extends Operations{
	private final String _startDate;
	private final String _endDate;
	public WeekReport(String startDate, String endDate){
		_startDate = startDate;
		_endDate = endDate;
	}
	public void printPeriod(){
		System.out.println(_startDate + " to " + _endDate);
	}
}
