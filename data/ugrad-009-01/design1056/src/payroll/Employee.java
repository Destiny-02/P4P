package payroll;
import java.math.BigDecimal;
import java.util.LinkedList;
public class Employee {
	private int _id;
	private String _name;
	private String _period;
	private float _gross;
	private boolean _isSalaried;
	private String _salaried;
	private float _rate;
	private float _hours;
	private float _deduction;
	private float _PAYE;
	private float _Nett;
	private float _YTD;
	private String _nameEmp;
	public Employee(LinkedList<String> linkedList, int index){
		lineSplit(linkedList,index);
	}
	public void lineSplit(LinkedList<String> list1,int index){
		String str = list1.get(index);
		String[] str_list = str.split("\t");
		_id = (Integer.parseInt(str_list[0]));
		_name = (str_list[1].split(",")[1]+" "+str_list[1].split(",")[0]);
		_period = (str_list[5] + " " + "to" + " " + str_list[6]);
		_isSalaried = (((str_list[2].equals("Salaried"))));
		_salaried = (str_list[2]);
		if (str_list[3].charAt(0)=='$'){
			_rate = (Float.parseFloat(str_list[3].substring(1)));
		}else{
			_rate = 0;
		}
		_hours = (Float.parseFloat(str_list[7]));
		if (str_list[3].charAt(0)=='$'){
			String splitDollar = str_list[3].substring(1);
			if (is_isSalaried()){
				_gross = (round(Float.parseFloat(splitDollar)/52,2));
			} else{
				_gross = (round(getHourlyGross(),2));
			}
		}else {
			System.out.println("Error in Rate Input: '$' sign missing");
			_gross =(0);
		}
		float pay_annual = get_gross()*52;
		float paye;
		if (pay_annual <= 14000){
			paye = (pay_annual*(float)0.105);
			_PAYE = (paye/52);
		} else if(pay_annual <= 48000){
			paye = 14000*(float)0.105 + (pay_annual - 14000)*(float)0.175;
			_PAYE = (paye/52);
		} else if(pay_annual <= 70000){
			paye = 14000*(float)0.105 + 34000*(float)0.175 + (pay_annual - 48000)*(float)0.30 ;
			_PAYE = (paye/52);
		} else {
			paye = 14000*(float)0.105 + 34000*(float)0.175 + 22000*(float)0.30 + (pay_annual - 70000)*(float)0.33 ;
			_PAYE = (paye/52);
		}
		if (str_list[8].charAt(0)=='$'){
			String splitDollar = str_list[8].substring(1);
			_deduction = (Float.parseFloat(splitDollar));
		}
		else {
			System.out.println("Error in Deduction amount: '$' sign missing");
		}
		_Nett = (get_gross() - get_PAYE() - get_deduction());
		String splitDollar = str_list[4].substring(1);
		float ytd = Float.parseFloat(splitDollar);
		_YTD = (ytd + get_gross());
		_nameEmp = (str_list[1]);
	}
	public float round(float d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}
	public float getHourlyGross(){
		float hours = get_hours();
		float pay;
		if ((hours <= 40) && hours >= 0){
			pay = hours*get_rate();
		}
		else if (hours < 43){
			pay = (hours - 40)*get_rate()*(float)1.5 + 40*get_rate();
		}
		else if (hours > 43){
			pay = 40*get_rate() + 3*get_rate()*(float)1.5 + (hours-43)*get_rate()*2;
		}
		else{
			pay = 0;
			System.out.println("Invalid number of hours entered.");
		}
		return pay;
	}
	public int get_id(){
		return _id;
	}
	public String get_name() {
		return _name;
	}
	public String get_period() {
		return _period;
	}
	public float get_gross() {
		return _gross;
	}
	public boolean is_isSalaried() {
		return _isSalaried;
	}
	public String get_salaried() {
		return _salaried;
	}
	public float get_rate() {
		return _rate;
	}
	public float get_hours() {
		return _hours;
	}
	public float get_deduction() {
		return _deduction;
	}
	public float get_PAYE() {
		return _PAYE;
	}
	public float get_Nett() {
		return _Nett;
	}
	public float get_YTD() {
		return _YTD;
	}
	public String get_nameEmp() {
		return _nameEmp;
	}
}
