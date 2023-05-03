package payroll;
import java.util.Map;
import java.util.TreeMap;
import java.util.Date;
import java.text.SimpleDateFormat;
public class EmployeeList {
	private static final String _noValidInfo = "No valid info. was found in file";
	private String _startDate;
	private String _endDate;
	private boolean _hasValidInputs;
	private EmployeeFunctions[] _sortedEmployees;
	public EmployeeList(String[] infoList, int sortType){
		if (infoList.length > 0 ){
			_hasValidInputs = true;
			String[] temp = infoList[0].split("\t");
			_startDate = temp[5];
			_endDate = temp[6];
			if (sortType == Payroll._nameAsKey){
				nameKeyMap(infoList);
			} else {
				tidKeyMap(infoList);
			}
			_hasValidInputs = ifAnyValidInfo();
		} else {
			_hasValidInputs = false;
		}
	}
	private void nameKeyMap(String[] infoList){
		String[] temp = new String[9];
		Map<String, EmployeeFunctions> employees = new TreeMap<String, EmployeeFunctions>();
		for (int i = 0; i < infoList.length; i++){
			temp = infoList[i].split("\t");
			int tid = Integer.parseInt(temp[0]);
			double rate = Double.parseDouble(temp[3].substring(1));
			double ytd = Double.parseDouble(temp[4].substring(1));
			double hours = Double.parseDouble(temp[7]);
			double deduct = Double.parseDouble(temp[8].substring(1));
			if (temp[2].toLowerCase().equals("salaried")){
				employees.put(temp[1], new SalariedEmployee(tid, temp[1], rate, ytd, hours, deduct));
			} else {
				employees.put(temp[1], new HourlyEmployee(tid, temp[1], rate, ytd, hours, deduct));
			}
		}
		_sortedEmployees = new EmployeeFunctions[infoList.length];
		int count = 0;
		for (Map.Entry<String, EmployeeFunctions> entry : employees.entrySet())
		{
			_sortedEmployees[count] = entry.getValue();
			count++;
		}
	}
	private void tidKeyMap(String[] infoList){
		String[] temp = new String[9];
		Map<Integer, EmployeeFunctions> employees = new TreeMap<Integer, EmployeeFunctions>();
		int numDuplicate = 0;
		for (int i = 0; i < infoList.length; i++){
			temp = infoList[i].split("\t");
			int tid = Integer.parseInt(temp[0]);
			double rate = Double.parseDouble(temp[3].substring(1));
			double ytd = Double.parseDouble(temp[4].substring(1));
			double hours = Double.parseDouble(temp[7]);
			double deduct = Double.parseDouble(temp[8].substring(1));
			boolean notRepeat = true;
			if (employees.containsKey(tid)){
				numDuplicate++;
				notRepeat = false;
			}
			if (notRepeat){
				if (temp[2].toLowerCase().equals("salaried")){
					employees.put(tid, new SalariedEmployee(tid, temp[1], rate, ytd, hours, deduct));
				} else {
					employees.put(tid, new HourlyEmployee(tid, temp[1], rate, ytd, hours, deduct));
				}
			}
		}
		_sortedEmployees = new EmployeeFunctions[infoList.length- numDuplicate];
		int count = 0;
		for (Map.Entry<Integer, EmployeeFunctions> entry : employees.entrySet())
		{
			_sortedEmployees[count] = entry.getValue();
			count++;
		}
	}
	private String outputHeader(String date){
		if (_hasValidInputs) {
			String todayDate = "";
			Date today = new Date();
			todayDate = new SimpleDateFormat("yyyy-MM-dd").format(today);
			return todayDate;
		} else { return _noValidInfo;}
	}
	private boolean ifAnyValidInfo(){
		for (int i = 0; i < _sortedEmployees.length; i++){
			if (_sortedEmployees[i].getValidity() == true) {return true;}
		}
		return false;
	}
	private String firstNameFirst(String name){
		String[] temp = name.split(",");
		String newName = (temp[1] + " " + temp[0]);
		return newName;
	}
	public void outputPayslips(){
		System.out.println(outputHeader(_endDate) );
		if (_hasValidInputs){
			for (int i = 0; i < _sortedEmployees.length; i++){
				if (_sortedEmployees[i].getValidity() == true){
					String output = String.format("%d. %s, Period: %s to %s. Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f Nett: $%.2f YTD: $%.2f",
							_sortedEmployees[i].getTID(), firstNameFirst(_sortedEmployees[i].getName()), _startDate, _endDate,
							_sortedEmployees[i].getWeeklyGross(), _sortedEmployees[i].getWeeklyTax(), _sortedEmployees[i].getDeduct(),
							_sortedEmployees[i].getWeeklyWage(), _sortedEmployees[i].getYTD());
					System.out.println(output);
				}
			}
		}
	}
	public void outputEmployees(){
		System.out.println(outputHeader(_endDate) );
		if (_hasValidInputs){
			for (int i = 0; i < _sortedEmployees.length; i++){
				if (_sortedEmployees[i].getValidity() == true){
				String output = String.format("%s (%d) %s, $%.2f YTD:$%.2f", _sortedEmployees[i].getName(),_sortedEmployees[i].getTID()
						, _sortedEmployees[i].getWorkType(), _sortedEmployees[i].getRate(), _sortedEmployees[i].getYTD());
				System.out.println(output);
				}
			}
		}
	}
	public void outputBurden(){
		System.out.println(outputHeader(_endDate) );
		if (_hasValidInputs){
			double netBurden = 0;
			for (int i = 0; i < _sortedEmployees.length; i++){
				if (_sortedEmployees[i].getValidity() == true){
					netBurden += _sortedEmployees[i].getWeeklyGross();
				}
			}
			System.out.println(_startDate + " to " + _endDate);
			System.out.println("Total Burden: $" + netBurden);
		}
	}
	public void outputPAYE(){
		System.out.println(outputHeader(_endDate) );
		if (_hasValidInputs){
			double netPAYE = 0;
			for (int i = 0; i < _sortedEmployees.length; i++){
				if (_sortedEmployees[i].getValidity() == true){
					netPAYE += _sortedEmployees[i].getWeeklyTax();
				}
			}
			System.out.println(_startDate + " to " + _endDate);
			System.out.println("Total PAYE: $" + netPAYE);
		}
	}
}
