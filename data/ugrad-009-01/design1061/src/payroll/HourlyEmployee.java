package payroll;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class HourlyEmployee extends Employee implements EmployeeFunctions{
	private final static int[] _hourRange = new int[]{40, 3, -1};
	private final static double[] _payMulti = new double[]{1, 1.5, 2};
	private final static String _workType = "Hourly";
	private double _weeklyWage;
	private double _weeklyTax;
	private double _weeklyGross;
	private boolean _validEmployee;
	public HourlyEmployee(int tid, String name, double rate, double ytd, double hours, double deduction){
		super(tid, name, rate, ytd, hours, deduction);
		_weeklyGross = getGrossForWeek(this.getHours(), this.getRate());
		_weeklyTax = getWeeklyTax(_weeklyGross*52);
		_weeklyWage = _weeklyGross - _weeklyTax - deduction;
		setYTD(this.getYTD() + _weeklyGross);
		if (_weeklyWage <= 0){ _validEmployee = false;}
		else {_validEmployee = true;}
	}
	public double getWeeklyWage(){return _weeklyWage;}
	public double getWeeklyTax(){return _weeklyTax;}
	public double getWeeklyGross(){return _weeklyGross;}
	public String getWorkType(){ return _workType;}
	public boolean getValidity(){return _validEmployee;}
	public int getTID()		{return super.getTID();}
	public String getName()	{return super.getName();}
	public double getRate()	{return super.getRate();}
	public double getYTD()		{return super.getYTD();}
	public double getHours()	{return super.getHours();}
	public double getDeduct()	{return super.getDeduct();}
	private double getGrossForWeek(double hours, double rate){
		double gross = 0;
		for (int i = 0; i < _hourRange.length; i++){
			if (hours > 0){
				if (hours < _hourRange[i] || _hourRange[i] == -1){
					BigDecimal temp = new BigDecimal((hours * rate * _payMulti[i])).setScale(2, RoundingMode.HALF_UP);
					gross += (temp.doubleValue());
					hours = 0;
				} else {
					BigDecimal temp = new BigDecimal((_hourRange[i] * rate * _payMulti[i])).setScale(2, RoundingMode.HALF_UP);
					gross += (temp.doubleValue());
					hours -= _hourRange[i];
				}
			}
		}
		return gross;
	}
}
