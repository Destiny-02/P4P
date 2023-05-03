
package payroll;
public class SalariedEmployee extends Employee{
	private static final String EMPLOYMENT = "Salaried";
	public SalariedEmployee(int id, String fName,String lName, double rate, double yearToDate, double hours, double deductions){
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
		return twoDecimalPlaceRound(_rate / WEEKS_IN_YEAR);
	}
}
