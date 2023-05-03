package payroll;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;
public class Employee {
	private final int _taxID;
	private final String _familyName;
	private final String _givenName;
	private final String _employment;
	private final double _rate;
	private double _YTD;
	private final String _startDate;
	private final String _endDate;
	private final double _hours;
	private final double _deductions;
	private double _PAYE = 0;
	private double _grossWeeklyIncome;
	public Employee(){
		_taxID = 0;
		_familyName = null;
		_givenName = null;
		_employment = null;
		_rate = 0;
		_YTD = 0;
		_startDate = null;
		_endDate = null;
		_hours = 0;
		_deductions = 0;
	}
	public Employee (int taxID, String familyName, String givenName, String employment, double rate, double YTD, String startDate, String endDate, double hours, double deductions){
		_taxID = taxID;
		_familyName = familyName;
		_givenName = givenName;
		_employment = employment;
		_rate = rate;
		_YTD = YTD;
		_startDate = startDate;
		_endDate = endDate;
		_hours = hours;
		_deductions = deductions;
	}
	public void updateYTD(double grossWeeklyIncome){
		_YTD += grossWeeklyIncome;
	}
	public double calculateGrossWeeklyIncome(double rate, double hours) {
		return rate/Payroll.WEEKS_IN_A_YEAR;
	}
	double returnNettIncome(){
		return _grossWeeklyIncome - _PAYE - _deductions;
	}
	boolean hasNegativeField(){
		boolean negativeField = false;
		if (_rate < 0 || _YTD < 0 || _hours < 0 || _deductions < 0 ){
			negativeField = true;
		}
		return negativeField;
	}
	public class EmployeeList {
		private TreeMap<Integer,Employee> _employeeList = null;
		public EmployeeList (){
			_employeeList = new TreeMap<Integer,Employee> ();
		}
		public void addNewEmployee(String[] splitEmployeeDetails){
			Employee newEmployee;
			if (Double.parseDouble(splitEmployeeDetails[4].substring(1)) < 0|| Double.parseDouble(splitEmployeeDetails[5].substring(1)) < 0|| Double.parseDouble(splitEmployeeDetails[8]) < 0|| Double.parseDouble(splitEmployeeDetails[9].substring(1)) < 0){
				System.out.println("WARNING: Negative value provided for  " + splitEmployeeDetails[1] + " " + splitEmployeeDetails[2] + " (TID " + Integer.parseInt(splitEmployeeDetails[0]) + ")." );
			}
			if(splitEmployeeDetails[3].equals("Salaried")){
				newEmployee = new SalariedEmployee(Integer.parseInt(splitEmployeeDetails[0]), splitEmployeeDetails[1], splitEmployeeDetails[2], splitEmployeeDetails[3], Double.parseDouble(splitEmployeeDetails[4].substring(1)), Double.parseDouble(splitEmployeeDetails[5].substring(1)), splitEmployeeDetails[6], splitEmployeeDetails[7], Double.parseDouble(splitEmployeeDetails[8]), Double.parseDouble(splitEmployeeDetails[9].substring(1)));
			} else {
				newEmployee = new HourlyEmployee(Integer.parseInt(splitEmployeeDetails[0]), splitEmployeeDetails[1], splitEmployeeDetails[2], splitEmployeeDetails[3], Double.parseDouble(splitEmployeeDetails[4].substring(1)), Double.parseDouble(splitEmployeeDetails[5].substring(1)), splitEmployeeDetails[6], splitEmployeeDetails[7], Double.parseDouble(splitEmployeeDetails[8]), Double.parseDouble(splitEmployeeDetails[9].substring(1)));
			}
			newEmployee._grossWeeklyIncome = newEmployee.calculateGrossWeeklyIncome(newEmployee._rate, newEmployee._hours);
			ProcessTax taxProcessor = new ProcessTax();
			if (newEmployee._employment.equals("Salaried")){
				newEmployee._PAYE = taxProcessor.calculateSalariedTax(newEmployee._rate);
			} else{
				newEmployee._PAYE = taxProcessor.calculateHourlyTax(newEmployee._grossWeeklyIncome);
			}
			newEmployee.updateYTD(newEmployee._grossWeeklyIncome);
			addEmployeeToTreeMap(newEmployee);
			if (splitEmployeeDetails[4].charAt(0) != '$'||splitEmployeeDetails[5].charAt(0) != '$' || splitEmployeeDetails[9].charAt(0) != '$'){
				System.out.println("WARNING: Value missing $ sign in  " + splitEmployeeDetails[1] + " " + splitEmployeeDetails[2] + " (TID " + Integer.parseInt(splitEmployeeDetails[0]) + "). Incorrect input value.");
			}
			if (newEmployee.hasNegativeField()== true){
				System.out.println("WARNING: Negative value found for  " + splitEmployeeDetails[1] + " " + splitEmployeeDetails[2] + " (TID " + Integer.parseInt(splitEmployeeDetails[0]) + ")." );
			}
		}
		public TreeMap<Integer, Employee> returnEmployeeList (){
			return _employeeList;
		}
		private void addEmployeeToTreeMap( Employee employee){
			if(_employeeList.containsKey(employee._taxID) == false){
				_employeeList.put(employee._taxID, employee);
			} else {
				System.out.println("WARNING	: TID " + employee._taxID + " not unique- overwriting " + _employeeList.get(employee._taxID)._familyName + " " + _employeeList.get(employee._taxID)._givenName + " (previous employee with TID entry)");
			}
		}
	}
	public class PrintInformation {
		public DecimalFormat rounding2DP = new DecimalFormat(".00");
		public void output(String processingType, TreeMap <Integer, Employee> employeeDetailsMap) throws IOException{
			if (processingType.equals("Payslips")){
				payslipProcessing(employeeDetailsMap);
			} else if (processingType.equals("Employees")){
				employeeProcessing(employeeDetailsMap);
			} else if (processingType.equals("Burden")){
				burdenProcessing(employeeDetailsMap);
			} else if(processingType.equals("PAYE")){
				PAYEProcessing(employeeDetailsMap);
			} else  {
				throw new IOException();
			}
		}
		private void payslipProcessing(TreeMap <Integer, Employee> employeeDetailsMap){
			Employee currentEmployee;
			for(Entry<Integer, Employee> entry : employeeDetailsMap.entrySet()){
				currentEmployee = entry.getValue();
				System.out.println(currentEmployee._taxID + ". " + currentEmployee._givenName + " " + currentEmployee._familyName + " Period: " + currentEmployee._startDate + " to " + currentEmployee._endDate + ". Gross: $" + rounding2DP.format(currentEmployee._grossWeeklyIncome) + ", PAYE: $" + rounding2DP.format(currentEmployee._PAYE) + ", Deductions: $" + rounding2DP.format(currentEmployee._deductions) + " Nett: $" + rounding2DP.format(currentEmployee.returnNettIncome()) + " YTD: $" + rounding2DP.format(currentEmployee._YTD));
			}
		}
		private void employeeProcessing(TreeMap <Integer, Employee> employeeDetailsMap){
			List<Employee> employeeDetailsList = new ArrayList<Employee>(employeeDetailsMap.values());
			Collections.sort(employeeDetailsList, new FamilyNameComparator());
			Employee currentEmployee;
			for (int i = 0; i < employeeDetailsList.size(); i++) {
				currentEmployee = employeeDetailsList.get(i);
				System.out.println(currentEmployee._familyName + " " + currentEmployee._givenName + " (" + currentEmployee._taxID + ") " + currentEmployee._employment + ", $" + rounding2DP.format(currentEmployee._rate) + " YTD:$" + rounding2DP.format(currentEmployee._YTD));
			}
		}
		private void burdenProcessing(TreeMap <Integer, Employee> employeeDetailsMap){
			double totalBurden = 0;
			for(Entry<Integer, Employee> entry : employeeDetailsMap.entrySet()) {
				totalBurden += entry.getValue()._grossWeeklyIncome;
			}
			System.out.println(employeeDetailsMap.firstEntry().getValue()._startDate + " to " +employeeDetailsMap.firstEntry().getValue()._endDate);
			System.out.println("Total Burden: $" + rounding2DP.format(totalBurden));
		}
		private void PAYEProcessing(TreeMap <Integer, Employee> employeeDetailsMap){
			double totalPAYE = 0;
			for(Entry<Integer, Employee> entry : employeeDetailsMap.entrySet()){
				totalPAYE += entry.getValue()._PAYE;
			}
			System.out.println(employeeDetailsMap.firstEntry().getValue()._startDate + " to " +employeeDetailsMap.firstEntry().getValue()._endDate);
			System.out.println("Total PAYE: $" + rounding2DP.format(totalPAYE));
		}
	}
	public class FamilyNameComparator implements Comparator<Employee> {
		public int compare(Employee e1, Employee e2){
			int compareFamilyValue = e1._familyName.toLowerCase().compareTo(e2._familyName.toLowerCase());
			if (compareFamilyValue > 0){
				return 1;
			} else if (compareFamilyValue < 0){
				return -1;
			} else {
				int compareGivenValue = e1._givenName.toLowerCase().compareTo(e2._givenName.toLowerCase());
				if (compareGivenValue > 0){
					return 1;
				} else if (compareGivenValue < 0){
					return -1;
				} else {
					return 0;
				}
			}
		}
	}
}
