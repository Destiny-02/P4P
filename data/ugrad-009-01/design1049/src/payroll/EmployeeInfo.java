package payroll;
public class EmployeeInfo {
	private int _tid;
	private String _name;
	private String _employeeType;
	private double _rate;
	private double _ytd;
	private String _startDate;
	private String _endDate;
	private double _hours;
	private double _deductions;
	private double _gross;
	private double _tax;
	private double _nett;
	private double _newYtd;
	public EmployeeInfo (String line){
		String[] splitLine = line.split("\t");
		MoneyProcess calcMoney = new MoneyProcess();
		double gross, tax, nett;
		_tid = Integer.parseInt(splitLine[0]);
		_name = splitLine[1];
		_employeeType = splitLine[2];
		_rate = Double.parseDouble(splitLine[3].substring(1));
		_ytd = Double.parseDouble(splitLine[4].substring(1));
		_startDate = splitLine[5];
		_endDate = splitLine[6];
		_hours = Double.parseDouble(splitLine[7]);
		_deductions = Double.parseDouble(splitLine[8].substring(1));
		if (splitLine[2].equals("Salaried")) {
			gross = calcMoney.calcGrossSalaried(Double.parseDouble(splitLine[3].substring(1)));
			_gross = gross;
			tax = calcMoney.payPeriodTax(Double.parseDouble(splitLine[3].substring(1)));
			_tax = tax;
		} else {
			gross = calcMoney.calcGrossHourly((Double.parseDouble(splitLine[3].substring(1))), (Double.parseDouble(splitLine[7])));
			_gross = gross;
			double annualPay = calcMoney.hourlyAnnual(gross);
			tax = calcMoney.payPeriodTax(annualPay);
			_tax = tax;
		}
		nett = gross - (tax + (Double.parseDouble(splitLine[8].substring(1))));
		_nett = nett;
		_newYtd = gross + (Double.parseDouble(splitLine[4].substring(1)));
	}
public EmployeeInfo (){
		_tid = 0;
		_name = null;
		_employeeType = null;
		_rate = 0;
		_ytd = 0;
		_startDate = null;
		_endDate = null;
		_hours = 0;
		_deductions = 0;
		_gross = 0;
		_tax = 0;
		_nett = 0;
		_newYtd = 0;
	}
	public int getTID() {
		return _tid;
	}
	public String getName() {
		return _name;
	}
	public String getEmployeeType() {
		return _employeeType;
	}
	public double getRate() {
		return _rate;
	}
	public double getYTD() {
		return _ytd;
	}
	public String getStartDate() {
		return _startDate;
	}
	public String getEndDate() {
		return _endDate;
	}
	public double getHours() {
		return _hours;
	}
	public double getDeductions() {
		return _deductions;
	}
	public double getGross() {
		return _gross;
	}
	public double getTax() {
		return _tax;
	}
	public double getNett() {
		return _nett;
	}
	public double getNewYTD() {
		return _newYtd;
	}
}
