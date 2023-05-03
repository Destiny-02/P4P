package payroll;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
public class Output {
	private final String _outputType;
	private EmployeeList _toGetEmployeeList;
	private ArrayList<Employee> _myFinishedEmployeeList;
	private String _startDate;
	private String _endDate;
	private double _burden;
	private double _PAYE;
	public Output(String outputType, ArrayList<Employee> toGetEmployeeList, double burden, double PAYE){
		_outputType = outputType;
		_myFinishedEmployeeList = toGetEmployeeList;
		_burden = burden;
		_PAYE = PAYE;
		printCurrentDate();
		getStartAndEndDates();
	}
	public void printCurrentDate(){
		Date date = new Date();
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(formatDate.format(date));
	}
	public void getStartAndEndDates(){
		for(Employee myEmployee: _myFinishedEmployeeList){
			_startDate = myEmployee.getStartDate();
			_endDate = myEmployee.getEndDate();
		}
	}
	public void printOutput(){
		if (_outputType.equals("Payslips")){
			printPayslips();
		}
		else if (_outputType.equals("Employees")){
			printEmployees();
		}
		else if (_outputType.equals("Burden")){
			printBurden();
		}
		else if (_outputType.equals("PAYE")){
			printPAYE();
		}
		else{
			System.out.println("Invalid input type");
			System.out.println("Input type was = " + _outputType);
		}
	}
	public void printPayslips(){
		for(Employee myEmployee: _myFinishedEmployeeList){
			String stringYTD = String.format("%.2f", myEmployee.getYTD());
			String stringGross = String.format("%.2f", myEmployee.getGross());
			String stringPAYE = String.format("%.2f", myEmployee.getPAYE());
			String stringDeductions = String.format("%.2f", myEmployee.getDeductions());
			String stringNETT = String.format("%.2f", myEmployee.getNett());
			System.out.println(myEmployee.getTaxID() + ". " + myEmployee.getFirstName() + " " + myEmployee.getLastName() + ", Period: " +
					myEmployee.getStartDate() + " to " + myEmployee.getEndDate() + ". Gross: $" + stringGross + ", PAYE: $" +
					stringPAYE + ", Deductions: $" + stringDeductions + " Nett: $" + stringNETT + " YTD: $" +
					stringYTD);
		}
	}
	public void printEmployees(){
		for(Employee myEmployee: _myFinishedEmployeeList){
			String stringYTD = String.format("%.2f", myEmployee.getYTD());
			String stringRate = String.format("%.2f", myEmployee.getRate());
			System.out.println(myEmployee.getLastName() + ", " + myEmployee.getFirstName() + " (" + myEmployee.getTaxID() + ") " +
					myEmployee.getEmployeeType() + ", $" + stringRate + " YTD:$" + stringYTD);
		}
	}
	public void printBurden(){
		String burdenString = String.format("%.2f", _burden);
		System.out.println(_startDate + " to " + _endDate);
		System.out.println("Total Burden: $" + burdenString);
	}
	public void printPAYE(){
		String stringPAYE = String.format("%.2f", _PAYE);
		System.out.println(_startDate + " to " + _endDate);
		System.out.println("Total PAYE: $" + stringPAYE);
	}
}

