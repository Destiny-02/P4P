
package payroll;
import java.math.BigDecimal;
import java.math.RoundingMode;
abstract class Employee{
	protected static final double WEEKS_IN_YEAR = 52.0;
	private static final double[] TAX_BRACKET_AMOUNTS = 	{14000.0,  	48000.0, 	70000.0};
	private static final double[] TAX_BRACKET_PERCENTS = 	{0.105, 	0.175, 		0.3, 		0.33};
	protected int _id;
	protected String _firstName, _lastName, _employment;
	protected double _rate, _ytd, _hours, _deductions;
	protected double _paye, _grossWeeklyIncome, _nettIncome;
	public int getID()					{return _id;}
	public String getFirstName()		{return _firstName;}
	public String getLastName()			{return _lastName;}
	public String getEmploymentType()	{return _employment;}
	public double getCurrentYTD()		{return _ytd;}
	public double getUpdatedYTD()		{return calculateUpdatedYTD();}
	public double getThisWeeksHours()	{return _hours;}
	public double getWeeklyDeductions()	{return _deductions;}
	public double getWeeksPAYE()		{return _paye;}
	public double getWeeksGross()		{return _grossWeeklyIncome;}
	public double getNettIncome()		{return _nettIncome;}
	public double getRate()				{return _rate;}
	protected double calculateWeeklyPAYE(){
		double runningTaxAmount = 0;
		double grossAnnualIncome = _grossWeeklyIncome * WEEKS_IN_YEAR;
		if (grossAnnualIncome <= TAX_BRACKET_AMOUNTS[0]){
			runningTaxAmount = grossAnnualIncome * TAX_BRACKET_PERCENTS[0];
		} else {
			runningTaxAmount += TAX_BRACKET_AMOUNTS[0] * TAX_BRACKET_PERCENTS[0];
			for(int i = 1; i < TAX_BRACKET_AMOUNTS.length; i++){
				if(grossAnnualIncome > TAX_BRACKET_AMOUNTS[i-1] && grossAnnualIncome <= TAX_BRACKET_AMOUNTS[i]){
					runningTaxAmount += (grossAnnualIncome - TAX_BRACKET_AMOUNTS[i-1]) * TAX_BRACKET_PERCENTS[i];
				} else if(grossAnnualIncome > TAX_BRACKET_AMOUNTS[i]){
					runningTaxAmount += (TAX_BRACKET_AMOUNTS[i] - TAX_BRACKET_AMOUNTS[i-1]) * TAX_BRACKET_PERCENTS[i];
				}
			}
			if(grossAnnualIncome > TAX_BRACKET_AMOUNTS[TAX_BRACKET_AMOUNTS.length - 1]){
				runningTaxAmount += (grossAnnualIncome - TAX_BRACKET_AMOUNTS[TAX_BRACKET_AMOUNTS.length - 1]) * TAX_BRACKET_PERCENTS[TAX_BRACKET_AMOUNTS.length];
			}
		}
		double annualTax = twoDecimalPlaceRound(runningTaxAmount);
		return twoDecimalPlaceRound(annualTax / WEEKS_IN_YEAR);
	}
	protected double calculateWeeklyNettIncome(){
		return _grossWeeklyIncome - (_deductions + _paye);
	}
	protected double calculateUpdatedYTD(){
		return _ytd + _grossWeeklyIncome;
	}
	protected static double twoDecimalPlaceRound(double number) {
	    BigDecimal numberToRound = new BigDecimal(number);
	    numberToRound = numberToRound.setScale(2, RoundingMode.HALF_UP);
	    return numberToRound.doubleValue();
	}
}
