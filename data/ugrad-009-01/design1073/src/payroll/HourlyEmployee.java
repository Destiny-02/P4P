
package payroll;
public class HourlyEmployee extends Employee{
	private static final String EMPLOYMENT = "Hourly";
	public HourlyEmployee(int id, String fName,String lName, double rate, double yearToDate, double hours, double deductions){
		_id = id;
		_firstName = fName;
		_lastName = lName;
		_employment = EMPLOYMENT;
		_rate = rate;
		_ytd = yearToDate;
		_hours = hours;
		_deductions = deductions;
		_grossWeeklyIncome = calculateWeeklyGross();
		_paye = calculateWeeklyPAYE();
		_nettIncome = calculateWeeklyNettIncome();
	}
	private double calculateWeeklyGross(){
		double rateMultiplier = 0;
		double weeklyGross;
		double payMultiplier0 = 1.0;
		double multiplier0Hours = 40.0;
		double payMultiplier1 = 1.5;
		double multiplier1Hours = 43.0;
		double payMultiplier2 = 2.0;
		if (_hours <= multiplier0Hours){
			rateMultiplier = _hours * payMultiplier0;
		} else {
			rateMultiplier += multiplier0Hours * payMultiplier0;
			if (_hours > multiplier0Hours && _hours <= multiplier1Hours){
				rateMultiplier +=((_hours - multiplier0Hours) * payMultiplier1);
			} else {
				rateMultiplier += ((multiplier1Hours - multiplier1Hours) * payMultiplier1) + ((_hours - multiplier1Hours) * payMultiplier2);
			}
		}
		weeklyGross = twoDecimalPlaceRound(_rate * rateMultiplier);
		return weeklyGross;
	}
}