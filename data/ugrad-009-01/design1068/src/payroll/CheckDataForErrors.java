package payroll;
import java.util.List;
public class CheckDataForErrors  {
	private ComputingEmployeeData _processData;
	public CheckDataForErrors(){
		_processData = new ComputingEmployeeData();
	}
	public void checkInitialData(String[] employeeData) throws PayrollException{
		if(employeeData.length != 9){
			throw new PayrollException("Not enough data or in wrong format.");
		}
		checkIfNegative(employeeData[0]);
		if(!employeeData[1].contains(", ")){
			throw new PayrollException("Name is in wrong format.");
		}
		missingDollarSign(employeeData[3]);
		checkIfNegative(employeeData[3]);
		missingDollarSign(employeeData[4]);
		checkIfNegative(employeeData[4]);
		checkDateFormat(employeeData[5]);
		checkDateFormat(employeeData[6]);
		startDateEndDateCompare(employeeData[5],employeeData[6]);
		checkIfNegative(employeeData[7]);
		missingDollarSign(employeeData[8]);
		checkIfNegative(employeeData[8]);
	}
	public void deductionsCompareEarning(double deductions, double gross) throws PayrollException{
		if(deductions > gross){
			throw new PayrollException("Deduction is greater than the Employee's earning!");
		}
	}
	public void checkUniqueTID(IndividualEmployee[] listOfEmployees) throws PayrollException{
		for(int i = 0; i < listOfEmployees.length-1; i++){
			for(int j = i+1; j < listOfEmployees.length; j++){
				if(listOfEmployees[i].getTid() == listOfEmployees[j].getTid()){
					throw new PayrollException("Duplicate TID found!");
				}
			}
		}
	}
	private void missingDollarSign(String money) throws PayrollException{
		String firstCharacter = String.valueOf(money.charAt(0));
		if(!firstCharacter.equals("$")){
			throw new PayrollException("Money needs to begin with '$'");
		}
	}
	private void startDateEndDateCompare(String startDate, String endDate) throws PayrollException{
		int[] intStartDate = _processData.separateDate(startDate);
		int[] intEndDate = _processData.separateDate(endDate);
		if(intStartDate[0] > intEndDate[0]){
			throw new PayrollException("Invalid date");
		} else if(intStartDate[0] == intEndDate[0]){
			if(intStartDate[1] > intEndDate[1]){
				throw new PayrollException("Invalid date");
			} else if(intStartDate[1] == intEndDate[1]){
				if(intStartDate[2] > intEndDate[2]){
					throw new PayrollException("Invalid date");
				}
			}
		}
	}
	private void checkIfNegative(String data) throws PayrollException{
		String numWithoutDollarSign = data.replace("$", "");
		double num = Double.parseDouble(numWithoutDollarSign);
		if(num < 0){
			throw new PayrollException("Some value is negative!");
		}
	}
	private void checkDateFormat(String date) throws PayrollException{
		int[] intDate = _processData.separateDate(date);
		int month = intDate[1];
		int day = intDate[2];
		if( (month > 12 || month < 1) || (day > 31 || day < 1)){
			throw new PayrollException("Date is in wrong format.");
		}
	}
}
