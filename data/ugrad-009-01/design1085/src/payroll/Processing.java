package payroll;
import java.text.DecimalFormat;
public class Processing implements Comparable<Processing>{
	private String _date;
	private String _TID;
	private String[] _Name;
	private String _Period;
	private String _Gross;
	private String _PAYE;
	private String _Deductions;
	private String _Nett;
	private String _YTD;
	private String _Employment;
	private String _Rate;
	public Processing(Employee a){
		DecimalFormat format = new DecimalFormat("##.00");
		_TID=a.getTID();
		_Employment=a.getEmployment();
		_Rate=format.format(Double.parseDouble(a.getRate()));
		double Gross=a.calcGross();
		_Gross=format.format(Gross);
		Gross=Double.parseDouble(_Gross);
		_Name=a.getName();
		_Period=a.getPeriod();
		double PAYE;
		double AnnualIncome;
		double rate=Double.parseDouble(a.getRate());
		if(a.isSalaried()){
			AnnualIncome=rate;
		}else{
			AnnualIncome=Gross*52;
		}
		if(AnnualIncome<=14000){
			PAYE=(AnnualIncome*0.105)/52;
		}else if(AnnualIncome>14000&AnnualIncome<=48000){
			PAYE=(14000*0.105+(AnnualIncome-14000)*0.175)/52;
		}else if(AnnualIncome>48000 &AnnualIncome<70000){
			PAYE=(14000*0.105+34000*0.175+(AnnualIncome-48000)*0.3)/52;
		}else{
			PAYE=(14000*0.105+34000*0.175+22000*0.3+(AnnualIncome-70000)*0.33)/52;
		}
		_PAYE=format.format(PAYE);
		_Deductions=format.format(Double.parseDouble(a.getDeduction()));
		_Nett=format.format(Gross-PAYE-Double.parseDouble(_Deductions));
		double YTD;
		YTD=Double.parseDouble(a.getYTD())+Gross;
		_YTD=format.format(YTD);
	}
	public void Payslipsprocessing(){
		String output=_TID+"."+_Name[1]+" "+_Name[0]+", "+"Period: "+_Period+". Gross: $"+_Gross+", PAYE: $"+_PAYE
				+", Deductions: $"+_Deductions+" Nett: $"+_Nett+" YTD: $"+_YTD;
		System.out.println(output);
	}
	public void Employeesprocess(){
		String output= _Name[0]+","+_Name[1]+" ("+_TID+") "+_Employment+", $"+_Rate+" YTD:$"+_YTD;
		System.out.println(output);
	}
	public double getGross(){
		return Double.parseDouble(_Gross);
	}
	public double getPAYE(){
		return Double.parseDouble(_PAYE);
	}
	@Override
	public int compareTo(Processing o) {
		int comparedTID =Integer.parseInt(o._TID);
		if(Integer.parseInt(this._TID)>comparedTID){
			return 1;
		}else if(Integer.parseInt(this._TID) == comparedTID){
			return 0;
		}else{
			return -1;
		}
	}
}
