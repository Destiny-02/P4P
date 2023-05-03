package payroll;
import java.util.HashMap;
public class ErrorChecker {
	private HashMap<Integer,Integer>_taxIDRecord=new HashMap<Integer,Integer>();
	private String _startDate;
	private String _endDate;
	private int _taxID;
	public boolean checkArguments(String[] args){
				if (args.length != 2){
					return false;
				}
		String processingType = args[1].toLowerCase();
		if (processingType.equals("payslips") || processingType.equals("employees") || processingType.equals("burden") || processingType.equals("paye")){
			return true;
		} else {
			return false;
		}
	}
	public void checkEmployeeRecord(String employeeRecord){
		String[] employeeDetails = employeeRecord.split("\t");
		checkIfEnoughDetailsProvided(employeeDetails.length);
		_taxID = Integer.parseInt(employeeDetails[0]);
		checkTaxIDs(_taxID);
		String name = employeeDetails[1];
		checkEmployeeNames(name);
		String employment = employeeDetails[2].toLowerCase();
		checkEmployment(employment);
		String rate = employeeDetails[3];
		if (checkDollarSignAndNegativeSign(rate)){
			throw new PayrollException("Make sure all money amounts starts with $ and are not negative. For example, the rates for the employee with TID "+ _taxID +", "+ rate + " didn't start with a $ sign");
		}
		String yearToDate = employeeDetails[4];
		if (checkDollarSignAndNegativeSign(yearToDate)){
			throw new PayrollException("Make sure all money amounts starts with $ and are not negative. For example, the YTD for the employee with TID "+ _taxID +", "+ yearToDate + " didn't start with a $ sign");
		}
		String deductions = employeeDetails[8];
		if (checkDollarSignAndNegativeSign(deductions)){
			throw new PayrollException("Make sure all money amounts starts with $ and are not negative. For example, the deductions for the employee with TID "+ _taxID +", "+ deductions + " didn't start with a $ sign");
		}
		String stringHours = employeeDetails[7];
		checkHours(stringHours);
		checkIfDatesAreSame(employeeDetails[5],employeeDetails[6]);
		checkIfEndDateAndStartDatesAreValid(_startDate,_endDate);
	}
	private void checkIfEnoughDetailsProvided(int detailsAmount){
		if (detailsAmount != 9){
			throw new PayrollException("There should be 9 elements for each line of employee record, please check.");
		}
	}
	private void checkTaxIDs(int _taxID){
		if (_taxIDRecord.get(_taxID) == null){
			_taxIDRecord.put(_taxID, 1);
		} else {
			throw new PayrollException("There is more than one entry with the same taxID, " + _taxID + ", each employee has a unique taxId, so make sure that the taxID in the employee record is correct.");
		}
	}
	private void checkEmployeeNames (String name){
		int noOfCommas = 0;
		for (int i = 0; i < name.length(); i++){
		    if(name.charAt(i) == ','){
		    	noOfCommas++;
		    }
		}
		if (noOfCommas != 1){
			throw new PayrollException("The name of the employee with TID " + _taxID + ", " + name + " is in the wrong format, make sure that it is formatted as ormatted as \"Family Name, Given Names\" ");
		}
	}
	private void checkEmployment(String employment){
		if(!(employment.equals("salaried") || employment.equals("hourly"))){
			throw new PayrollException("The employee with TID " + _taxID + ", has the employment type " + employment + " which is invalid, please make sure that it is either hourly or salaried.");
		}
	}
	private boolean checkDollarSignAndNegativeSign(String moneyValue){
		if (moneyValue.charAt(0) == '$'){
			return false;
		} else {
			return true;
		}
	}
	private void checkHours(String stringHours){
		int noOfDots = 0;
		for (int i = 0; i < stringHours.length(); i++){
		    if(stringHours.charAt(i) == '.'){
		    	noOfDots++;
		    }
		    if(noOfDots > 1){
		    	throw new PayrollException("Make sure that there is only one '.' at most for the hour's value, if there is more than 1, it wouldn't be considered an hour value.");
			}
		}
		double hours = Double.parseDouble(stringHours);
		int integerHrs = (int)hours;
		if(hours - integerHrs  != 0.0 && hours - integerHrs != 0.25 && hours - integerHrs != 0.50 && hours - integerHrs != 0.75){
			throw new PayrollException("The 'fractional' part for hours worked should only be \".0\", \".25\", \".50\", or \".75\", check the employee with TID " + _taxID);
		}
	}
	private void checkIfDatesAreSame(String startDate,String endDate){
		if (_startDate != null && _endDate != null){
			if (!(_startDate.equals(startDate) && _endDate.equals(endDate))){
				throw new PayrollException("The pay period for every employee should be the same.");
			}
		} else {
			_startDate = startDate;
			_endDate = endDate;
		}
	}
	private void checkIfEndDateAndStartDatesAreValid(String _startDate,String _endDate){
		String[] startDate = _startDate.split("-");
		String[] endDate = _endDate.split("-");
		if(startDate.length != 3 || endDate.length != 3){
			throw new PayrollException("Make sure that the dates is in ISO 8601 format.");
		}
		if(startDate[0].length() != 4 ||endDate[0].length() != 4 ||startDate[1].length() != 2 ||endDate[1].length() != 2 ||startDate[2].length() != 2 ||endDate[2].length() != 2){
			throw new PayrollException("Make sure that the dates is in ISO 8601 format.");
		}
		int startYear = Integer.parseInt(startDate[0]);
		int endYear = Integer.parseInt(endDate[0]);
		int startMonth = Integer.parseInt(startDate[1]);
		int endMonth = Integer.parseInt(endDate[1]);
		int startDay = Integer.parseInt(startDate[2]);
		int endDay = Integer.parseInt(endDate[2]);
		if(endYear - startYear == 0){
			if(endMonth - startMonth == 0){
				if(endDay < startDay){
					throw new PayrollException("Make sure that the end date is after the start date");
				}
				if(endDay - startDay != 6){
					throw new PayrollException("Make sure that the pay period is exactly 7 days.");
				}
			} else if (endMonth - startMonth == 1){
				if(endMonth < startMonth){
					throw new PayrollException("Make sure that the end date is after the start date");
				}
				if(!(endDay + 31 - startDay >= 6 && endDay + 31 - startDay <= 9)){
					throw new PayrollException("Make sure that the pay period is exactly 7 days.");
				}
			} else {
				throw new PayrollException("Make sure that the pay period is exactly 7 days.");
			}
		} else if(endYear - startYear == 1){
			if(startMonth != 12 && endMonth != 1){
				throw new PayrollException("Make sure that the pay period is exactly 7 days.");
			} else {
				if(!(endDay + 31 - startDay == 6)){
					throw new PayrollException("Make sure that the pay period is exactly 7 days.");
				}
			}
		} else {
			throw new PayrollException("Make sure that the pay period is exactly 7 days.");
		}
	}
}

