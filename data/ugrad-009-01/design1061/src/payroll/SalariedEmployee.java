package payroll;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class SalariedEmployee extends Employee implements EmployeeFunctions{
	private final static String _workType = "Salaried";
	private double _weeklyWage;
	private double _weeklyTax;
	private double _weeklyGross;
	private boolean _validEmployee;
	public SalariedEmployee(int tid, String name, double rate, double ytd, double hours, double deduction){
		super(tid, name, rate, ytd, hours, deduction);
		_weeklyGross = annualToWeeklyGross(rate);
		_weeklyTax = getWeeklyTax(rate);
		_weeklyWage = _weeklyGross - _weeklyTax - deduction;
		setYTD(this.getYTD() + _weeklyGross);
		if (_weeklyWage <= 0){ _validEmployee = false;}
		else {_validEmployee = true;}
	}
	public double getWeeklyWage(){return _weeklyWage;}
	public double getWeeklyTax(){return _weeklyTax;}
	public double getWeeklyGross(){return _weeklyGross;}
	public String getWorkType(){ return _workType;}
	public boolean getValidity(){ return _validEmployee;}
	public int getTID()		{return super.getTID();}
	public String getName()	{return super.getName();}
	public double getRate()	{return super.getRate();}
	public double getYTD()		{return super.getYTD();}
	public double getHours()	{return super.getHours();}
	public double getDeduct()	{return super.getDeduct();}
	private double annualToWeeklyGross(double annualGross){
		BigDecimal weeklyGross = new BigDecimal(annualGross/52).setScale(2, RoundingMode.HALF_UP);
		return (weeklyGross.doubleValue());
	}
}
