package payroll;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
public class AllEmployees{
	private HashMap<Integer,Employee> _hashID;
	public AllEmployees(){
		_hashID = new HashMap<Integer, Employee>();
	}
	public Employee lookup(int tid){
		Employee employee;
		if(_hashID.get(tid)==null){return null;}
		return ((employee = _hashID.get(tid)).getTID()==tid)?employee:null;
	}
	public void storeInHashtable(Employee employee, String fullName, int tid){
		if(lookup(tid)==null){
			_hashID.put(tid, employee);
		}else{
			_hashID.replace(tid, employee);
		}
	}
	public Employee[] getEmployeesByName(){
		Integer[] orderedList = _hashID.keySet().toArray(new Integer[0]);
		Arrays.sort(orderedList);
		Employee[] employeeList = new Employee[orderedList.length];
		int i=0;
		for(Integer key : orderedList){
			employeeList[i] = _hashID.get(key);
			i++;
		}
		Arrays.sort(employeeList, new Comparator<Employee>(){
				public int compare(Employee e1, Employee e2){
					return e1.compareLastThenFirstName(e2);
				}
		}
		);
		return employeeList;
	}
	public Employee[] getEmployeesByTID(){
		Integer[] orderedList = _hashID.keySet().toArray(new Integer[0]);
		Arrays.sort(orderedList);
		Employee[] employeeList = new Employee[orderedList.length];
		int i=0;
		for(Integer key : orderedList){
			employeeList[i] = _hashID.get(key);
			i++;
		}
		return employeeList;
	}
	public void storeRecordFromString(String lineIn) throws ParseException{
		String[] line = lineIn.replaceAll(" ", "").split("\\t+");
		Employee employee;
		int tid;
		try{
			tid = Integer.parseInt(line[0]);
		}catch(NumberFormatException ex){
			throw new NumberFormatException("TID "+line[0]+" is not formatted correctly.");
		}
		if(lookup(tid)!=null){
			if(lookup(tid).getNameWithDelimiter(',',Order.LAST_TO_FIRST)==line[1]){
				employee = lookup(tid);
				employee.storeDetails(line);
			}else{
				throw new ParseException("Error: TID "+tid+" matches another TID of at least one other employee!");
			}
		}else{
		employee = new Employee(line);
		this.storeInHashtable(employee,line[1],tid);
		}
	}
}
class Employee{
	enum EmployeeConstants {
		SALARY("salaried"),
		HOURLY("hourly"),
		WEEKSINYEAR(52),
		HOURSINYEAR(52*40);
		private String _salaryType;
		private double _unitsInYear;
		EmployeeConstants(String type){
			_salaryType = type;
		}
		EmployeeConstants(double howManyUnits){
			_unitsInYear = howManyUnits;
		}
		public double getUnits(){
			return _unitsInYear;
		}
		public String getType(){
			return _salaryType;
		}
	}
	private TaxID _tid;
	private Name _name;
	private String _employment;
	private double _rate;
	private double _ytd;
	private LocalDate _start;
	private LocalDate _end;
	private double _hours;
	private double _deduct;
	public Employee(String[] details) throws ParseException{
		if(details.length!=9){throw new RuntimeException("Employee record is missing fields.");}
		try{
			_tid = new TaxID(Integer.parseInt(details[0]));
		}catch(NumberFormatException ex){
			throw new NumberFormatException("TID "+details[0]+" is not formatted correctly.");
		}
		this.storeDetails(details);
	}
	public void storeDetails(String[] details) throws ParseException{
		try{
		if(details.length!=9){
			throw new ParseException("Employee record is missing fields.");
		}
		String[] name;
		if((name=details[1].split(",")).length==2){
		_name = new Name(name[1],name[0]);
		}
		_employment = details[2];
		_rate =  currencyFormatter(details[3]);
		_ytd = currencyFormatter(details[4]);
		_start = new Date(details[5], "ymd").getDate();
		_end = new Date(details[6], "ymd").getDate();
		_hours = Double.parseDouble(details[7]);
		if(_hours<0){throw new NumberFormatException("Hours cannot be negative in Employee TID "+_tid.getTID());}
		_deduct = currencyFormatter(details[8]);
		}catch(DateTimeParseException dtex){
			throw new DateTimeParseException("Date could not be parsed. Employee TID: "+_tid.getTID()+". "+dtex.getLocalizedMessage()+dtex.getParsedString(),dtex.getParsedString(),0);
		}
	}
	private double currencyFormatter(String currency){
		if(currency.indexOf('$')==0&&currency.indexOf('-')==-1){
			return Double.parseDouble(currency.substring(1));
		}else{
			throw new NumberFormatException("Rate is not of a correct format in Employee TID "+_tid.getTID());
		}
	}
	public final String getNameWithDelimiter(char delimiter,Order order){
		return getNameWithDelimiter(String.valueOf(delimiter), order);
	}
	public final String getNameWithDelimiter(String delimiter, Order order){
		switch(order){
		case LAST_TO_FIRST:
		return (delimiter!=null)?_name.getLastName()+delimiter+_name.getFirstName():_name.getLastName()+_name.getFirstName();
		case FIRST_TO_LAST:
		return (delimiter!=null)?_name.getFirstName()+delimiter+_name.getLastName():_name.getFirstName()+_name.getLastName();
		default:
		throw new RuntimeException("No order specified for getName.");
		}
	}
	public final String getName(Order order){
		return getNameWithDelimiter(null,order);
	}
	public final int getTID(){
		return _tid.getTID();
	}
	public String getWorkingPeriod(){
		return _start.toString()+" to "+_end.toString();
	}
	public double getRate(){
		return _rate;
	}
	public double getHoursWorked(){
		return _hours;
	}
	public double getYTD(){
		return _ytd;
	}
	public double getDeduction(){
		return _deduct;
	}
	public String getEmploymentType(){
		return _employment;
	}
	public int compareLastThenFirstName(Employee e2) {
		int compared = this.getName(Order.LAST_TO_FIRST).compareTo(e2.getName(Order.LAST_TO_FIRST));
		return compared;
	}
}
class Name{
	private String _firstName;
	private String _lastName;
	public Name(String firstName, String lastName){
		_firstName = firstName;
		_lastName = lastName;
	}
	public String getFirstName(){
		return _firstName;
	}
	public String getLastName(){
		return _lastName;
	}
}
class TaxID{
	private int _tid;
	public TaxID(int tid){
		_tid = tid;
	}
	public int getTID(){
		return _tid;
	}
}