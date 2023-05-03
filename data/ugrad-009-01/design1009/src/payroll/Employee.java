package payroll;
import java.text.DecimalFormat;
public class Employee {
	private static final DecimalFormat df= new DecimalFormat("######0.00");
	private int _TID;
	private String _FamilyName;
	private String _GivenName;
	private Employment _Employment;
	private double _YTD;
	private double _Hours;
	private double _Deduction;
	private String _Period;
	private double _Gross;
	private double _Tax;
	private double _Nett;
	public Employee(int TID,String FamilyName,String GivenName,Employment Employment,double YTD,double Hours,double Deduction,String start,String end){
		_TID=TID;
		_FamilyName=FamilyName;
		_GivenName=GivenName;
		_Employment=Employment;
		_Hours=Hours;
		_Deduction=Deduction;
		_Period=start+" to "+end;
		_Gross=getDouble(setGross());
		_Tax=getDouble(setTax());
		_Nett=getDouble(setNett());
		_YTD=YTD+_Gross;
	}
	private double setGross(){
		if(_Employment.getEmploymentstate()){
			return _Employment.getRate()/52;
		}
		else{
			if(_Hours<=40){
				return _Employment.getRate()*_Hours;
			}
			else if(40<_Hours&&_Hours<=43){
				return _Employment.getRate()*40+_Employment.getRate()*(_Hours-40)*1.5;
			}
			else{
				return _Employment.getRate()*40+_Employment.getRate()*3*1.5+_Employment.getRate()*(_Hours-43)*2;
			}
		}
	}
	private double setTax(){
		if(_Gross<=(14000/52)){
			return _Gross*0.105;
		}
		else if(_Gross<=(48000/52)){
			return 28.27+(_Gross-269.23)*0.175;
		}
		else if(_Gross<=(70000/52)){
			return 142.69+(_Gross-923.08)*0.3;
		}
		else{
			return 269.61+(_Gross-1346.15)*0.33;
		}
	}
	private double setNett(){
		return _Gross-_Tax-_Deduction;
	}
	private static double getDouble(double a){
		return (double)(Math.round(a*100)/100.0);
	}
	public double getGross(){
		return this._Gross;
	}
	public double getPAYE(){
		return this._Tax;
	}
	public int Namecompareto(Employee o1){
		String name1=this._FamilyName+", "+this._Employment;
		String name2=o1._FamilyName+", "+o1._Employment;
		return name1.compareTo(name2);
	}
	public int TIDcompareto(Employee o1){
		if ( this._TID < o1._TID) {
			return -1;
			} else if ( this._TID > o1._TID) {
			return 1;
			} else {
			return 0;
			}
	}
    public void printEmployee(){
		System.out.println(this._FamilyName+", "+this._GivenName+" ("+this._TID+") "+this._Employment.getEmploymentstateString()+", $"+df.format(this._Employment.getRate())+" YTD:$"+df.format(this._YTD));
	}
    public void printPayslips1(){
		System.out.print(this._TID+". "+this._GivenName+" "+this._FamilyName+", Period: ");
	}
    public void printPeriod(){
    	System.out.print(this._Period);
    }
    public void printPayslips2(){
    	System.out.println(". Gross: $"+df.format(this._Gross)+", PAYE: $"+df.format(this._Tax)+", Deductions: $"+df.format(this._Deduction)+" Nett: $"+df.format(this._Nett)+" YTD: $"+df.format(this._YTD));
    }
}
