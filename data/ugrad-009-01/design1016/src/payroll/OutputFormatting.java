package payroll;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class OutputFormatting {
	private int _currentTID;
	private String _currentFirstName;
	private String _currentLastName;
	private String _currentType;
	private String _startDate;
	private String _endDate;
	private double _currentGross;
	private double _currentPAYE;
	private double _currentDeductions;
	private double _currentNett;
	private double _currentYTD;
	private double _currentRate;
	private static DecimalFormat twoDPFormatter = new DecimalFormat("0.00");
	public void printFullPAYEProcessing(double totalPAYE, String startDate, String endDate){
		this.printCurrentDate();
		System.out.println(startDate + " to " + endDate);
		System.out.println("Total PAYE: $" + totalPAYE);
	}
	public void printFullBurdenProcessing(double totalBurden, String startDate, String endDate){
		this.printCurrentDate();
		System.out.println(startDate + " to " + endDate);
		System.out.println("Total Burden: $" + totalBurden);
	}
	public void printLineEmployeeProcessing(Employee currentEmployee){
		this.retrieveAllEmployeeFields(currentEmployee);
		System.out.print(_currentLastName+", "+_currentFirstName+ " ("+_currentTID+") "+_currentType);
		System.out.println(", $"+twoDPFormatter.format(_currentRate)+" YTD:$"+twoDPFormatter.format(_currentYTD));
	}
	public void printLinePayslipProcessing(Employee currentEmployee){
		this.retrieveAllEmployeeFields(currentEmployee);
		System.out.println(_currentTID+". "+_currentFirstName+" "+_currentLastName+", Period: "+_startDate
				+" to "+_endDate+". Gross: $"+twoDPFormatter.format(_currentGross)+", PAYE: $"
				+twoDPFormatter.format(_currentPAYE)+", Deductions: $"+twoDPFormatter.format(_currentDeductions)
				+" Nett: $"+twoDPFormatter.format(_currentNett)+" YTD: $"+twoDPFormatter.format(_currentYTD));
	}
	public void printCurrentDate(){
		Date date = new Date();
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(formatDate.format(date));
	}
	private void retrieveAllEmployeeFields(Employee currentEmployee){
		_currentLastName = currentEmployee.getLastName();
		_currentFirstName = currentEmployee.getFirstName();
		_startDate = currentEmployee.getStartDate();
		_endDate = currentEmployee.getEndDate();
		_currentTID = currentEmployee.getTID();
		_currentType = currentEmployee.getType();
		_currentRate = currentEmployee.getRate();
		_currentYTD = currentEmployee.getYTD();
		_currentGross= currentEmployee.getGrossIncome();
		_currentPAYE = currentEmployee.getPAYE();
		_currentDeductions = currentEmployee.getDeductions();
		_currentNett = currentEmployee.getNettIncome();
		_currentYTD = currentEmployee.getYTD();
	}
}
