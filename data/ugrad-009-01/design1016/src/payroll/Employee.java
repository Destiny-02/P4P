package payroll;
public class Employee {
	final private int _TID;
	final private String _firstName;
	final private String _lastName;
	final private String _type;
	final private double _rate;
	final private double _YTDIncome;
	final private String _startDate;
	final private String _endDate;
	final private double _hoursWorked;
	final private double _deductions;
	final private double _grossIncome;
	final private double _PAYEAmount;
	final private double _nettIncome;
	final private double _updatedYTD;
	Employee(String input){
		String[] inputArraySplit = input.split("\t");
		_TID = Integer.parseInt(inputArraySplit[0]);
		String[] nameReversed = inputArraySplit[1].split(",");
		String firstName = nameReversed[1].trim();
		_firstName = firstName;
		_lastName = nameReversed[0];
		_type = inputArraySplit[2];
		_rate = Double.parseDouble(inputArraySplit[3].replace("$", ""));
		_YTDIncome = Double.parseDouble(inputArraySplit[4].replace("$", ""));
		_startDate = inputArraySplit[5];
		_endDate = inputArraySplit[6];
		_hoursWorked = Double.parseDouble(inputArraySplit[7]);
		_deductions = Double.parseDouble(inputArraySplit[8].replace("$", ""));
		_grossIncome = this.calculateGrossIncome();
		_PAYEAmount = this.calculatePAYE();
		_nettIncome = _grossIncome - _PAYEAmount - _deductions;
		_updatedYTD = _YTDIncome + _grossIncome;
	}
	public double calculateGrossIncome(){
		double unroundedWeeklyIncome;
		double roundedWeeklyIncome;
		if(_type.equals("Salaried")){
			unroundedWeeklyIncome = (_rate)/52.0;
		} else {
			double doubleTimeAmount =0;
			double timeHalfAmount =0;
			double timeNormalAmount =0;
			if(_hoursWorked > 43){
				double doubleTimeHours = _hoursWorked - 43;
				doubleTimeAmount = doubleTimeHours*(2*_rate);
				timeHalfAmount = 3*(1.5*_rate);
				timeNormalAmount = 40*_rate;
			} else if(_hoursWorked > 40){
				double timeHalfHours = _hoursWorked - 40;
				timeHalfAmount = timeHalfHours*(1.5*_rate);
				timeNormalAmount = 40*_rate;
			} else {
				timeNormalAmount = _hoursWorked*_rate;
			}
			unroundedWeeklyIncome = (doubleTimeAmount+timeHalfAmount+timeNormalAmount);
		}
		roundedWeeklyIncome = roundTo2dp(unroundedWeeklyIncome);
		return roundedWeeklyIncome;
	}
	public double calculatePAYE(){
		double PAYEUnrounded=0, PAYERounded=0, taxBracketAmount;
		double salaryTemp;
		if(_type.equals("Salaried")){
			salaryTemp = _rate;
		}
		else {
			double grossWeekIncome = this.calculateGrossIncome();
			double grossAnnualIncome = grossWeekIncome*52.0;
			salaryTemp = grossAnnualIncome;
		}
		if(salaryTemp >70000){
			taxBracketAmount = salaryTemp -70000;
			PAYEUnrounded = PAYEUnrounded + taxBracketAmount*(0.33);
			salaryTemp = salaryTemp - taxBracketAmount;
		}if(salaryTemp >= 48000){
			taxBracketAmount = salaryTemp -48000;
			PAYEUnrounded = PAYEUnrounded + taxBracketAmount*(0.30);
			salaryTemp = salaryTemp - taxBracketAmount;
		}if(salaryTemp >= 14000){
			taxBracketAmount = salaryTemp -14000;
			PAYEUnrounded = PAYEUnrounded + taxBracketAmount*(0.175);
			salaryTemp = salaryTemp - taxBracketAmount;
		}
		PAYEUnrounded = PAYEUnrounded + salaryTemp*(0.105);
		double PAYEWeekly = PAYEUnrounded/52.0;
		PAYERounded = roundTo2dp(PAYEWeekly);
		return PAYERounded;
	}
	private double roundTo2dp(double input){
		double inputTemp = input*100;
		inputTemp = Math.round(inputTemp);
		return inputTemp/100;
	}
	public int getTID(){
		return _TID;
	}
	public String getFirstName(){
		return _firstName;
	}
	public String getLastName(){
		return _lastName;
	}
	public String getType(){
		return _type;
	}
	public double getYTD(){
		return _updatedYTD;
	}
	public double getDeductions(){
		return _deductions;
	}
	public double getNettIncome(){
		return _nettIncome;
	}
	public double getGrossIncome(){
		return _grossIncome;
	}
	public String getStartDate(){
		return _startDate;
	}
	public String getEndDate(){
		return _endDate;
	}
	public double getPAYE(){
		return _PAYEAmount;
	}
	public double getRate() {
		return _rate;
	}
}
