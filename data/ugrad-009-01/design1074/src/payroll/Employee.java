package payroll;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
public class Employee implements Comparable<Employee>{
	private String _TID;
	private int _TIDint;
	private String _firstname;
	private String _lastname;
	private String _employment;
	private String _rate;
	private float _rateFlo;
	private String _YTD;
	private float _YTDFlo;
	private String _start;
	private String _end;
	private String _hours;
	private float _hoursFlo;
	private String _deduction;
	private float _deductionFlo;
	private float _gross;
	private float _paye;
	public Employee(String TID, String fullname, String employment, String rate,
			String YTD, String start, String end, String hours, String deduction){
		this._TID = TID;
		this._TIDint = Integer.parseInt(_TID);
		String[] parseName = fullname.split("\\s+");
		this._firstname = parseName[1];
		this._lastname = parseName[0];
		this._employment = employment;
		this._rate = rate;
		this._rateFlo = toFloat(_rate);
		this._YTD = YTD;
		this._YTDFlo = toFloat(_YTD);
		this._start = start;
		this._end = end;
		this._hours = hours;
		this._hoursFlo = toFloat(_hours);
		this._deduction = deduction;
		this._deductionFlo = toFloat(_deduction);
		this._gross = gross();
		this._paye = paye();
	}
	public Employee(){
		this._TID = "0";
		this._TIDint = Integer.parseInt(_TID);
		this._firstname = "Administrator";
		this._lastname = "Administrator";
		this._employment = "Administrator";
		this._rate = "0";
		this._rateFlo = toFloat(_rate);
		this._YTD = "0";
		this._YTDFlo = toFloat(_YTD);
		this._start = "0";
		this._end = "0";
		this._hours = "0";
		this._hoursFlo = toFloat(_hours);
		this._deduction = "0";
		this._deductionFlo = toFloat(_deduction);
		this._gross = gross();
		this._paye = paye();
	}
	private float toFloat(String str){
		float flo = 0.00000000f;
		if (str.indexOf("$")==-1){
			flo=Float.parseFloat(str);
		}else{
			flo=Float.parseFloat(str.substring(str.indexOf("$")+1));
		}
		return flo;
	}
	private float round(float f){
		BigDecimal b = new BigDecimal(f);
		float flo = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		return flo;
	}
	private float gross(){
		float f;
		if (_employment.equalsIgnoreCase("Salaried")){
			f = _rateFlo/52;
			return round(f);
		}else{
			if (_hoursFlo<=40){
				f = _rateFlo*_hoursFlo;
				return round(f);
			}else if (_hoursFlo<=43){
				f = _rateFlo*(_hoursFlo-40)*1.5f;
				f += _rateFlo*40;
				return round(f);
			}else{
				f = _rateFlo*(_hoursFlo-43)*2;
				f += _rateFlo*3*1.5f;
				f += _rateFlo*40;
				return round(f);
			}
		}
	}
	private float calcTax(float yr){
		float f;
		if (yr <= 14000){
			f = yr * 10.5f / 100 / 52;
			return round(f);
		}else if (yr <= 48000){
			f = (yr - 14000) * 17.5f / 100;
			f += 14000 * 10.5f / 100;
			f = f/52;
			return round(f);
		}else if (yr <= 70000){
			f = (yr - 48000) * 30 / 100;
			f += (48000 - 14000) * 17.5f / 100;
			f += 14000 * 10.5f / 100;
			f = f/52;
			return round(f);
		}else{
			f = (yr - 70000) * 33 / 100;
			f += (70000 - 48000) * 30 / 100;
			f += (48000 - 14000) * 17.5f / 100;
			f += 14000 * 10.5f / 100;
			f = f/52;
			return round(f);
		}
	}
	private float paye(){
		float yr;
		if (_employment.equalsIgnoreCase("Salaried")){
			yr = _rateFlo;
			return calcTax(yr);
		}else{
			yr = gross() * 52;
			return calcTax(yr);
		}
	}
	public void showDate(){
		Date date=new Date();
		SimpleDateFormat form=new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(form.format(date));
	}
	@Override
	public int compareTo(Employee o) {
		int TID = _TIDint;
		if (TID < o._TIDint){
			return -1;
		}else if (TID > o._TIDint){
			return 1;
		}else{
			return 0;
		}
	}
	public class EmployeeComparator implements Comparator<Employee> {
		@Override
		public int compare(Employee o1, Employee o2) {
			String surname1 = o1._lastname;
			String surname2 = o2._lastname;
			String givenname1 = o1._firstname;
			String givenname2 = o2._firstname;
			if (surname1.equals(surname2)){
				if ((givenname1.compareTo(givenname2) < 0)){
					return -1;
				}else if (givenname1.compareTo(givenname2) > 0){
					return 1;
				}else{
					return 0;
				}
			}else{
				if ((surname1.compareTo(surname2) < 0)){
					return -1;
				}else if (surname1.compareTo(surname2) > 0){
					return 1;
				}else{
					return 0;
				}
			}
		}
	}
	public class Payslips implements Processing {
		private EmployeesDatabase _database;
		public Payslips(EmployeesDatabase database){
			this._database = database;
			displayOutput();
		}
		public void displayOutput(){
			showDate();
			_database.TIDsort();
			LinkedList<Employee> eL = _database.getStaffRoll();
			for (Iterator<Employee> iter = eL.iterator(); iter.hasNext();) {
				Employee o = iter.next();
				System.out.printf((o._TID + ". " + o._firstname +
						" " + o._lastname + " Period: " +
						o._start + " to " + o._end + ". Gross: $"
						+ "%.2f" + ", PAYE: $" + "%.2f" + ", Deductions: $"
						+ "%.2f" + " Nett: $" + "%.2f" + " YTD: $" + "%.2f\n"),
						o._gross, o._paye, o._deductionFlo,
						(o._gross -o._paye - o._deductionFlo),
						(o._gross + o._YTDFlo));
			}
		}
	}
	public class Employees implements Processing {
		private EmployeesDatabase _database;
		public Employees(EmployeesDatabase database){
			this._database = database;
			displayOutput();
		}
		public void displayOutput() {
			showDate();
			_database.nameSort();
			LinkedList<Employee> eL = _database.getStaffRoll();
			for (Iterator<Employee> iter = eL.iterator(); iter.hasNext();) {
				Employee o = iter.next();
				System.out.printf((o._lastname +
						" " + o._firstname + " (%d) " +
						o._employment + ", $%.2f YTD:$%.2f\n"),
						o._TIDint, o._rateFlo,
						(o._gross + o._YTDFlo));
			}
		}
	}
	public class Burden implements Processing {
		private EmployeesDatabase _database;
		public Burden(EmployeesDatabase database){
			this._database = database;
			displayOutput();
		}
		public void displayOutput() {
			showDate();
			LinkedList<Employee> eL = _database.getStaffRoll();
			float totalBurden = 0.00000f;
			for (Iterator<Employee> iter = eL.iterator(); iter.hasNext();) {
				Employee o = iter.next();
				totalBurden += o._gross;
			}
			System.out.printf((eL.get(new Random().nextInt(eL.size()))._start + " to "
					+ eL.get(new Random().nextInt(eL.size()))._end + "\nTotal Burden: $%.2f\n"), totalBurden);
		}
	}
	public class PAYE implements Processing {
		private EmployeesDatabase _database;
		public PAYE(EmployeesDatabase database){
			this._database = database;
			displayOutput();
		}
		public void displayOutput() {
			showDate();
			LinkedList<Employee> eL = _database.getStaffRoll();
			float totalPaye = 0.00000f;
			for (Iterator<Employee> iter = eL.iterator(); iter.hasNext();) {
				Employee o = iter.next();
				totalPaye += o._paye;
			}
			System.out.printf((eL.get(new Random().nextInt(eL.size()))._start + " to "
					+ eL.get(new Random().nextInt(eL.size()))._end + "\nTotal PAYE: $%.2f\n"), totalPaye);
		}
	}
}
