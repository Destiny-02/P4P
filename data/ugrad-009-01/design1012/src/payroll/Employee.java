package payroll;
import java.util.HashSet;
public class Employee{
	private String _taxID;
	private String[] _givenNames;
	private String _startDate;
	private String _endDate;
	private EmployeeType _employeeType;
	private double _rate;
	private double _YTD;
	private double _hoursWorked;
	private double _deductions;
	EmployeeType getEmployeeType(){
		return _employeeType;
	}
	String getTaxID(){
		return _taxID;
	}
	String[] getGivenNames(Boolean firstNameLastName){
		if(firstNameLastName){
			String[] name = new String[2];
			name[0] = _givenNames[1];
			name[1] = _givenNames[0];
			return name;
		} else {
			return _givenNames;
		}
	}
	double getRate(){
		return _rate;
	}
	double getHoursWorked(){
		return _hoursWorked;
	}
	double getYTD(){
		return _YTD;
	}
	void updateYTD(double updatedYTD){
		_YTD = updatedYTD;
	}
	String[] getDatesWorked(){
		String[] datesWorked = new String[2];
		datesWorked[0] = _startDate;
		datesWorked[1] = _endDate;
		return datesWorked;
	}
	double getDeductions(){
		return _deductions;
	}
	void fieldInitialiser(String[] splitLine, EmployeeList employeeList, Employee employee, HashSet<String> tidChecker) throws DuplicateEmployeeException, InputFormatException{
		boolean isDoubleUp = tidChecker.contains(splitLine[0]);
		if(isDoubleUp){
			throw new DuplicateEmployeeException(splitLine[0]);
		} else {
			tidChecker.add(splitLine[0]);
			_taxID = (splitLine[0]);
			if(!(splitLine[1].matches("([a-zA-Z]+), ([a-zA-Z]+)"))){
				throw new InputFormatException(_taxID);
			}
			_givenNames = splitLine[1].split(", ");
			if(splitLine[2].equals("Salaried")){
				_employeeType = EmployeeType.SALARY;
			} else if (splitLine[2].equals("Hourly")){
				_employeeType = EmployeeType.HOURLY;
			}
			String rateWithoutDollarSign = splitLine[3].replaceAll("[$,]", "");
			String YTDWithoutDollarSign = splitLine[4].replaceAll("[$,]", "");
			_rate = Double.parseDouble(rateWithoutDollarSign);
			_YTD = Double.parseDouble(YTDWithoutDollarSign);
			String hoursWorkedWithoutDollarSign = splitLine[7].replaceAll("[$,]", "");
			_hoursWorked = (Double.parseDouble(hoursWorkedWithoutDollarSign));
			if (!(splitLine[5].matches("([0-9]{4})-([0-9]{2})-([0-9]{2})") && splitLine[6].matches("([0-9]{4})-([0-9]{2})-([0-9]{2})"))){
				throw new InputFormatException(_taxID);
			}
			String[] splitStartDate = splitLine[5].split("-");
			String[] splitEndDate = splitLine[6].split("-");
			int startDay = Integer.parseInt(splitStartDate[2]);
			int startMonth = Integer.parseInt(splitStartDate[1]);
			int startYear = Integer.parseInt(splitStartDate[0]);
			int endDay = Integer.parseInt(splitEndDate[2]);
			int endMonth = Integer.parseInt(splitEndDate[1]);
			int endYear = Integer.parseInt(splitEndDate[0]);
			if(endDay < startDay || endMonth < startMonth || endYear < startYear){
				throw new InputFormatException(_taxID);
			}
			_startDate = splitLine[5];
			_endDate = splitLine[6];
			String deductions = splitLine[8].replaceAll("[$,]", "");
			_deductions = Double.parseDouble(deductions);
			employeeList.addToList(employee);
		}
	}
}
