package payroll;
public class EmployeeData {
	private int _TID;
	private String _name;
	private String _employment;
	private double _rate;
	private double _YTD;
	private String _start;
	private String _end;
	private double _hours;
	private double _deduction;
	private double _gross;
	private double _paye;
	private double _Nett;
	public EmployeeData(String[] a) {
		_TID = Integer.parseInt(a[0]);
		_name = a[1];
		_employment = a[2];
		_rate = Double.parseDouble(a[3]);
		_YTD = Double.parseDouble(a[4]);
		_start = a[5];
		_end = a[6];
		_hours = Double.parseDouble(a[7]);
		_deduction = Double.parseDouble(a[8]);
		_gross = 0;
		_paye = 0;
		_Nett = 0;
	}
	public Integer getTID(){
		return _TID;
	}
	public String getname(){
		return _name;
	}
	public String getemployment(){
		return _employment;
	}
	public double getrate(){
		return _rate;
	}
	public double getYTD(){
		return _YTD;
	}
	public String getstart(){
		return _start;
	}
	public String getend(){
		return _end;
	}
	public double gethours(){
		return _hours;
	}
	public double getdeduction(){
		return _deduction;
	}
	public double getpaye(){
		return _paye;
	}
	public double getgross(){
		return _gross;
	}
	public double getNett(){
		return _Nett;
	}
	public void setpaye(double paye){
		this._paye = paye;
	}
	public void setgross(double gross){
		this._gross = gross;
	}
	public void setYTD(double YTD){
		this._YTD = YTD;
	}
	public void setNett(double Nett){
		this._Nett = Nett;
	}
}
