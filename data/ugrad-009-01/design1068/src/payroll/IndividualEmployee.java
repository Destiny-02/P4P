package payroll;
public class IndividualEmployee {
	private String _stringEmployee, _hourly,_firstName, _lastName, _startDate, _endDate;
	private int _tid, _startYear, _startMonth, _startDay, _endYear, _endMonth, _endDay;
	private double _rate, _ytd, _deduction, _hours;
	private boolean _isItHourly;
	private double _gross, _paye, _nett;
	private ComputingEmployeeData _processData;
	private CheckDataForErrors _checking;
	public IndividualEmployee(String stringEmployee){
		_stringEmployee = stringEmployee;
		_processData = new ComputingEmployeeData();
		_checking = new CheckDataForErrors();
	}
	public void separateLineToData() throws PayrollException{
		String[] splitData = _stringEmployee.split("\t");
		_checking.checkInitialData(splitData);
		_tid = _processData.changeToInt(splitData[0]);
		String[] name = _processData.separateFullName(splitData[1]);
		_lastName = name[0];
		_firstName = name[1];
		_hourly = splitData[2];
		_isItHourly = _processData.isItHourly(_hourly);
		_rate = _processData.goAwayDollarSign(splitData[3]);
		_ytd = _processData.goAwayDollarSign(splitData[4]);
		_startDate = splitData[5];
		int[] startDate = _processData.separateDate(_startDate);
		_startYear = startDate[0];
		_startMonth = startDate[1];
		_startDay = startDate[2];
		_endDate = splitData[6];
		int[] endDate = _processData.separateDate(_endDate);
		_endYear = endDate[0];
		_endMonth = endDate[1];
		_endDay = endDate[2];
		_hours = _processData.nearestQuarterHour(splitData[7]);
		_deduction = _processData.goAwayDollarSign(splitData[8]);
	}
	public void checkErrors() throws PayrollException{
		_checking.deductionsCompareEarning(_deduction, _gross);
	}
	public void calculatingNeededData(){
		_gross = _processData.calculateGross(_hours, _rate, _isItHourly);
		_paye = _processData.calculatePaye(_gross);
		_nett = _processData.calculateNett(_gross, _paye, _deduction);
		_ytd = _processData.calculateYtd(_ytd, _gross);
	}
	public void printIndividualEmployeePayslip(){
		System.out.println(_tid + ". " + _firstName + " " + _lastName + ", Period: " + _startDate +
				" to " + _endDate + ". Gross: $" + _processData.keepTwoDecimal(_gross) + ", PAYE: $" +
				_processData.keepTwoDecimal(_paye) + ", Deductions: $" +
				_processData.keepTwoDecimal(_deduction) + " Nett: $" + _processData.keepTwoDecimal(_nett) +
				" YTD: $" + _processData.keepTwoDecimal(_ytd));
	}
	public void printIndividualEmployeeData(){
		System.out.println(_lastName + ", " + _firstName +  " (" + _tid + ") " +
				_hourly + ", $" + _processData.keepTwoDecimal(_rate) + " YTD:$" + _processData.keepTwoDecimal(_ytd));
	}
	public void printEmployeeStartToEndDate(){
		System.out.println(_startDate + " to " + _endDate);
	}
	public int getTid(){
		return _tid;
	}
	public String getFirstName(){
		return _firstName;
	}
	public String getLastName(){
		return _lastName;
	}
	public double getGross(){
		return _gross;
	}
	public double getPaye(){
		return _paye;
	}
	public double getDeduction(){
		return _deduction;
	}
}
