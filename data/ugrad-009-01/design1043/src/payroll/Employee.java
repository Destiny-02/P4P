package payroll;
import java.util.Comparator;
public class Employee {
	private int _TID;
	private String _name;
	private String _earnType;
	private double _rate;
	private double _YTD;
	private String _startDate;
	private String _endDate;
	private double _hours;
	private double _deduction;
	public Employee(int TID, String name, String earnType,double rate, double YTD, String startDate, String endDate, double hours, double deduction){
		_TID = TID;
		_name = name;
		_earnType = earnType;
		_rate = rate;
		_YTD =YTD;
		_startDate = startDate;
		_endDate = endDate;
		_hours = hours;
		_deduction = deduction;
	}
	public boolean earnType(){
		if(_earnType.equals("Salaried")){
			return true;
		}
		else{
			return false;
		}
	}
	public void printEarnings(){
		System.out.printf("$%.2f", _rate);
	}
	public void printTid(){
		System.out.print(get_TID());
	}
	public void printName(boolean swap){
		Name n = new Name();
		String name = _name;
			if(swap){
				name = n.swap(get_name());
			}
		System.out.print(""+name);
	}
	public void printEarnType(){
		System.out.print("" + _earnType);
	}
	public void printDeduction(){
		System.out.printf("$%.2f ",_deduction);
	}
	public void printDate(){
		System.out.print(get_startDate() + " to " + get_endDate());
	}
	public void printNett(Employee employee){
		PAYE paye = new PAYE();
		double nett = paye.grossSubtractPaye(employee);
		nett -= _deduction;
		if(nett< 0){
			nett =0;
		}
		System.out.printf("$%.2f ", nett);
	}
	public void tidIdentical(Employee e1, Employee e2) throws InputError{
		if(e1._TID == e2._TID){
			throw new InputError("TID Number (" + e1._TID + ") is not unique");
		}
	}
	public void nonsense(Employee employee) throws InputError{
		if(employee._TID < 0 ||employee._deduction <0 ||employee._rate<0 ||employee._hours <0 || employee._YTD < 0){
			throw new InputError("Input contains nonsense negative values");
		}
	}
	public static final Comparator<Employee> NAME_ORDER = new Comparator<Employee>() {
		public int compare(Employee e1, Employee e2) {
		return e1._name.compareTo(e2._name);
		}
	};
	public static final Comparator<Employee> TID_ORDER = new Comparator<Employee>() {
		public int compare(Employee e1, Employee e2) {
		return e1._TID - (e2._TID);
		}
	};
	private int get_TID() {
		return _TID;
	}
	private String get_name() {
		return _name;
	}
	public double get_rate() {
		return _rate;
	}
	public double get_YTD() {
		return _YTD;
	}
	private String get_startDate(){
		return _startDate;
	}
	private String get_endDate(){
		return _endDate;
	}
	public double get_hours() {
		return _hours;
	}
}
