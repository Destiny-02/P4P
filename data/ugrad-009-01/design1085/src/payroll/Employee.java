package payroll;
public class Employee {
	private String _TID;
	private String _Name;
	private String _Employment;
	private String _Rate;
	private String _YTD;
	private String _Start;
	private String _End;
	private String _Hours;
	private String _Deduction;
	public Employee(String[] array){
		_TID=array[0];
		_Name=array[1];
		_Employment=array[2];
		_Rate=array[3];
		_YTD=array[4];
		_Start=array[5];
		_End=array[6];
		_Hours=array[7];
		_Deduction=array[8];
	}
	public boolean isSalaried(){
		if(_Employment.equals("Salaried")){
			return true;
		}else{
			return false;
		}
	}
	public double calcGross(){
		String temp=_Rate;
		temp=temp.substring(1,temp.length());
		double rate = Double.parseDouble(temp);
		double Gross;
		double hours = Double.parseDouble(_Hours);
		if(isSalaried()){
			Gross=rate/52;
		} else{
			if(hours<=40){
				Gross=rate*hours;
			}else if(hours<43 &hours>40){
				Gross=rate*(40+(hours-40)*1.5);
			} else{
				Gross=rate*(40+3*1.5+(hours-43)*2);
			}
		}
		return Gross;
	}
	public String[] getName(){
		String[] temp=_Name.split(",");
		return temp;
	}
	public String getPeriod(){
		String Period=_Start+" to "+_End;
		return Period;
	}
	public String getRate(){
		String rate=_Rate;
		rate=rate.substring(1,rate.length());
		return rate;
	}
	public String getDeduction(){
		String deduction=_Deduction;
		deduction=deduction.substring(1,deduction.length());
		return deduction;
	}
	public String getYTD(){
		String YTD=_YTD;
		YTD=YTD.substring(1,YTD.length());
		return YTD;
	}
	public String getTID(){
		return _TID;
	}
	public String getEmployment(){
		return _Employment;
	}
}
