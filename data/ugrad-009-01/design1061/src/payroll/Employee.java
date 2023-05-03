package payroll;
import java.math.BigDecimal;
import java.math.RoundingMode;
public abstract class Employee {
	private final static int[] _taxRange = new int[]{14000, 34000, 22000, -1};
	private final static double[] _taxRate = new double[]{0.105, 0.175, 0.3, 0.33};
	private int _tid;			private String _name;
	private double _rate;		private double _hours;
	private double _deduction;	private double _ytd;
	public Employee
	(int tid, String name, double rate, double ytd, double hours, double deduction){
		_tid = tid;		_name = name;		_rate = rate;
		_ytd = ytd;		_hours = hours;		_deduction = deduction;
	}
	int getTID()		{return _tid;}
	String getName()	{return _name;}
	double getRate()	{return _rate;}
	double getYTD()		{return _ytd;}
	double getHours()	{return _hours;}
	double getDeduct()	{return _deduction;}
	void setYTD(double value) {_ytd = value;}
	double getWeeklyTax(double amount){
		double annualTax = 0;
		for (int i = 0; i < _taxRate.length; i++){
			if (amount > 0){
				if (amount < _taxRange[i] || _taxRange[i] == -1){
					BigDecimal temp = new BigDecimal((amount * _taxRate[i])).setScale(2, RoundingMode.HALF_UP);
					annualTax += (temp.doubleValue());
					amount = 0;
				} else {
					BigDecimal temp = new BigDecimal((_taxRange[i] * _taxRate[i])).setScale(2, RoundingMode.HALF_UP);
					annualTax += (temp.doubleValue());
					amount -= _taxRange[i];
				}
			}
		}
		BigDecimal temp = new BigDecimal(annualTax/52).setScale(2, RoundingMode.HALF_UP);
		return  temp.doubleValue();
	}
}
