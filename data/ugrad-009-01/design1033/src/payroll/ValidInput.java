package payroll;
public class ValidInput {
	private String _TID;
	private String _incomeType;
	private String _rate;
	private String _YTD;
	private String _start;
	private String _end;
	private float _hoursWorked;
	private String _deductions;
	public ValidInput(String TID, String incomeType, String rate, String YTD, String start, String end, float hoursWorked, String deductions){
		_TID = TID;
		_incomeType = incomeType;
		_rate = rate;
		_YTD = YTD;
		_start = start;
		_end = end;
		_hoursWorked = hoursWorked;
		_deductions = deductions;
	}
	public boolean checkValidity(){
		checkTID();
		if (checkDollars()){
			return false;
		} else if (checkIncomeTypes()){
			return false;
		}
		else if (checkNumbers()){
			return false;
		} else if (checkDates()){
			return false;
		} else if (checkValues()){
			return false;
		} else{
			return true;
		}
	}
	public void checkTID(){
		try{
			Integer.parseInt(_TID);
		} catch (NumberFormatException e){
			throw new EmployeeException("Invalid TID must be valid numbers!");
		}
	}
	public boolean checkDollars(){
		if ((!_rate.substring(0,1).equals("$")) || (!_YTD.substring(0,1).equals("$")) || (!_deductions.substring(0,1).equals("$"))){
			return true;
		} else{
			return false;
		}
	}
	public boolean checkNumbers(){
		if ((Float.valueOf(_rate.substring(1))<0) || (Float.valueOf(_YTD.substring(1)) < 0) || (Float.valueOf(_deductions.substring(1)) < 0) || (_hoursWorked < 0)){
			return true;
		} else{
			return false;
		}
	}
	public boolean checkDates(){
		try{
			String[] storeStartDates = _start.split("-");
			String[] storeEndDates = _end.split("-");
			if (storeStartDates.length == 3 && storeEndDates.length == 3){
				float yearsDays = 364*Float.valueOf(storeEndDates[0]) - 364*Float.valueOf(storeStartDates[0]);
				float monthsDays = 364/12*Float.valueOf(storeEndDates[1]) - 364/12*Float.valueOf(storeStartDates[1]);
				float days = Float.valueOf(storeEndDates[2]) - Float.valueOf(storeStartDates[2]);
				float totalDays = yearsDays+monthsDays+days;
				if (totalDays < 0){
					throw new EmployeeException("Start date larger than end date!");
				} else {
					return false;
				}
			} else {
				throw new EmployeeException("Date is missing!");
			}
		}
		catch (NumberFormatException e){
			throw new EmployeeException("The date format is wrong! YYYY-MM-DD");
		}
	}
	public boolean checkValues(){
		if (Float.valueOf(_deductions.substring(1)) > Float.valueOf(_rate.substring(1))/52 && _incomeType.equals("Salaried")){
			return true;
		} else if (_incomeType.equals("Hourly") && _hoursWorked > 40){
			if (_hoursWorked <= 43){
				if ((_hoursWorked - 40)*Float.valueOf(_rate.substring(1))*1.5 + 40*Float.valueOf(_rate.substring(1)) < Float.valueOf(_deductions.substring(1))){
					return true;
				}
			}
			else {
				if ((_hoursWorked - 40)*Float.valueOf(_rate.substring(1))*1.5*2 + 40*Float.valueOf(_rate.substring(1)) < Float.valueOf(_deductions.substring(1))){
					return true;
				}
			}
		}
		return false;
	}
	public boolean checkIncomeTypes(){
		if ((!_incomeType.equals("Salaried"))&&(!_incomeType.equals("Hourly"))){
			throw new EmployeeException("The income type is neither a 'Salaried' or 'Hourly'");
		} else {
			return false;
		}
	}
}

