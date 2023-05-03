package payroll;
public class Employee{
	private int _taxID;
	private String _name1,_name2,_employment,_start, _end;
	private double _gross,_ytd,_rate,_hours,_paye,_deduction,_nett;
	public Employee(int taxID,String name1,String name2, String employment, double rate, double ytd,
			String start, String end, double hours, double deduction){
		_taxID = taxID;
		_name1 = name1;
		_name2 = name2;
		_employment = employment;
		_rate = rate;
		_ytd = ytd;
		_start = start;
		_end = end;
		_hours = hours;
		_deduction = deduction;
	}
	public int getTaxId(){
		   return _taxID;
	}
	public String getName1(){
		   return _name1;
	}
	public String getName2(){
		   return _name2;
	}
	public String getEmployment(){
		   return _employment;
	}
	public double getRate(){
		   return _rate;
	}
	public double getHours(){
		   return _hours;
	}
	public double getDeduction(){
		   return _deduction;
	}
	public void setEmployment(String employment){
		_employment = employment;
	}
	public double getYTD(){
		   return _ytd;
	}
	public void setYTD(double ytd){
		_ytd = ytd;
	}
	public double getGross(){
		   return _gross;
	}
	public void setGross(double gross){
		_gross = gross;
	}
	public double getNett(){
		   return _nett;
	}
	public void setNett(double nett){
		_nett = nett;
	}
	public String getStart(){
		   return _start;
	}
	public String getEnd(){
		   return _end;
	}
	public void setPaye(double paye){
		_paye = paye;
	}
	public double getPaye(){
		   return _paye;
	}
}
