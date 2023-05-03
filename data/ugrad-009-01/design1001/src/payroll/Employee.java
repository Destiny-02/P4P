package payroll;
import java.util.Comparator;
public class Employee implements Comparable<Employee>{
	private String[] _dataArray;
	protected final int taxID;
	protected final String name;
	protected final String employment;
	protected final double rate;
	protected final double YTD;
	protected final String start;
	protected final String end;
	protected final double hours;
	protected final double deductions;
	Employee(String inputData) throws PayrollUserException{
		_dataArray = inputData.split("\t");
		if(_dataArray.length!=9){
			throw new PayrollUserException("Record data has not been formatted correctly");
		}else{
			taxID = Integer.parseInt(_dataArray[0]);
			name = _dataArray[1];
			employment = _dataArray[2];
			rate = dollarSignRemover(_dataArray[3]);
			YTD = dollarSignRemover(_dataArray[4]);
			start = _dataArray[5];
			end = _dataArray[6];
			hours = Double.parseDouble(_dataArray[7]);
			deductions = dollarSignRemover(_dataArray[8]);
		}
	}
	private double dollarSignRemover(String value) throws PayrollUserException{
		if(value.contains("$")){
			return Double.parseDouble(value.replace("$",""));
		}else{
			throw new PayrollUserException("The value " + value + " is incorrectly formatted and missing a $");
		}
	}
	public int compareTo(Employee employee) {
		int taxIDCompare = ((Employee)employee).taxID;
		return this.taxID - taxIDCompare;
	}
	public static Comparator<Employee> employeeNameComparator = new Comparator<Employee>(){
		public int compare(Employee employee1, Employee employee2){
			String employeeName1 = employee1.name.toLowerCase();
			String employeeName2 = employee2.name.toLowerCase();
			return employeeName1.compareTo(employeeName2);
		}
	};
}
