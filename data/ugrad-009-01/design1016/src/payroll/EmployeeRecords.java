package payroll;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
public class EmployeeRecords {
	private ArrayList<Employee> _employeeData;
	private ArrayList<Integer> _employeeTIDData;
	private ArrayList<String> _employeeNameData;
	private OutputFormatting _outputFormat;
	private String _startDate;
	private String _endDate;
	EmployeeRecords(){
		_employeeData = new ArrayList<Employee>();
		_employeeTIDData = new ArrayList<Integer>();
		_employeeNameData = new ArrayList<String>();
		_outputFormat = new OutputFormatting();
	}
	public void setEmployee(Employee currentEmployee){
		int currentTID = currentEmployee.getTID();
		String currentEmployeeName = currentEmployee.getLastName() + currentEmployee.getFirstName();
		_employeeData.add(currentEmployee);
		_employeeTIDData.add(currentTID);
		_employeeNameData.add(currentEmployeeName);
	}
	public void runPAYEProccessing(){
		double totalPAYE=0;
		this.setDates();
		for(Employee currentEmployee : _employeeData){
			totalPAYE = totalPAYE + currentEmployee.getPAYE();
		}
		_outputFormat.printFullPAYEProcessing(totalPAYE, _startDate, _endDate);
	}
	public void runBurdenProccessing(){
		double totalBurden=0;
		this.setDates();
		for(Employee currentEmployee : _employeeData){
			totalBurden = totalBurden + currentEmployee.getGrossIncome();
		}
		_outputFormat.printFullBurdenProcessing(totalBurden, _startDate, _endDate);
	}
	public void runEmployeeProcessing(){
		_outputFormat.printCurrentDate();
		HashMap<String, Employee> mapData = new HashMap<String, Employee>();
		for(Employee currentEmployee : _employeeData){
			String currentEmployeeName = currentEmployee.getLastName() + currentEmployee.getFirstName();
			mapData.put(currentEmployeeName, currentEmployee);
		}
		Collections.sort(_employeeNameData);
		for(String name : _employeeNameData){
			Employee currentEmployee = mapData.get(name);
			_outputFormat.printLineEmployeeProcessing(currentEmployee);
		}
	}
	public void runPayslipProcessing(){
		_outputFormat.printCurrentDate();
		HashMap<Integer,Employee> mapData = new HashMap<Integer,Employee>();
		for(Employee currentEmployee : _employeeData){
			mapData.put(currentEmployee.getTID(), currentEmployee);
		}
		Collections.sort(_employeeTIDData);
		for(int TID : _employeeTIDData){
			Employee currentEmployee = mapData.get(TID);
			_outputFormat.printLinePayslipProcessing(currentEmployee);
		}
	}
	private void setDates(){
		Employee e = returnSingleEmployee(0);
		_startDate = e.getStartDate();
		_endDate = e.getEndDate();
	}
	private Employee returnSingleEmployee(int pos){
		return _employeeData.get(pos);
	}
}
