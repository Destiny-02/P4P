package payroll;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
public class Employee {
	private static final int WEEKS_PER_YEAR = 52;
	private static final float WEEKLY_TIME = (float)40;
	private static final float TIME_AND_A_HALF = (float)1.5;
	private static final float DOUBLE_TIME = (float)2;
	private static final float TAX_BRACKET_1 = (float)0.105;
	private static final float TAX_BRACKET_2 = (float)0.175;
	private static final float TAX_BRACKET_3 = (float)0.3;
	private static final float TAX_BRACKET_4 = (float)0.33;
	private int _taxID;
	private String _name;
	private String _wageType;
	private float _rate;
	private float _YTD;
	private String _startDate;
	private String _endDate;
	private float _hours;
	private float _deductions;
	private float _grossWeekEarnings;
	private float _PAYE;
	private float _NETT;
	private String _givenNames;
	private String _familyName;
	private static float totalBurden;
	private static float totalPAYE;
	public Employee(int taxID, String name,
			String wageType, float rate, float YTD,
			String startDate, String endDate,
			float hours, float deductions )
			throws Error{
		_taxID = taxID;
		_name = name;
		_wageType = wageType;
		_rate = rate;
		_YTD = YTD;
		_startDate = startDate;
		_endDate = endDate;
		_hours = hours;
		_deductions = deductions;
		String[] namesArray = _name.replaceAll("\\s","").split(",");
		_givenNames = namesArray[1];
		_familyName = namesArray[0];
		 switch (wageType) {
         case "Salaried":
        	 _grossWeekEarnings = rate/WEEKS_PER_YEAR;
        	 calculatePAYE(rate);
             break;
         case "Hourly":
        	 if(hours<=40){
        		 _grossWeekEarnings = WEEKLY_TIME*rate;
        	 } else if (hours<=43){
        		 _grossWeekEarnings = WEEKLY_TIME*rate + TIME_AND_A_HALF*(hours-40)*rate;
        	 } else {
        		 _grossWeekEarnings = WEEKLY_TIME*rate + TIME_AND_A_HALF*3*rate + DOUBLE_TIME*(hours-43)*rate;
        	 }
        	 calculatePAYE(_grossWeekEarnings*WEEKS_PER_YEAR);
        	 break;
         default:
        	throw new Error("The employee " + this._givenNames + " " + this._familyName + " has the (wrong) salary type \"" + this._wageType + "\"");
		 }
		 totalBurden += this._grossWeekEarnings;
		 totalPAYE += this._PAYE;
	_NETT = _grossWeekEarnings - _PAYE - deductions;
	_YTD = YTD + _grossWeekEarnings;
	this.checkDateAndTime();
	this.checkCurrencyValues();
}
	private void calculatePAYE(float annualEarnings){
		float annualPAYE;
		if(annualEarnings<=14000){
			annualPAYE = TAX_BRACKET_1*_rate;
		} else if (annualEarnings<=48000){
			annualPAYE = TAX_BRACKET_1*14000 + TAX_BRACKET_2*(annualEarnings-14000);
		} else if (annualEarnings<=70000){
			annualPAYE = TAX_BRACKET_1*14000 + TAX_BRACKET_2*34000 + TAX_BRACKET_3*(annualEarnings-48000);
		} else {
			annualPAYE = TAX_BRACKET_1*14000 + TAX_BRACKET_2*34000 + TAX_BRACKET_3*22000 + TAX_BRACKET_4*(annualEarnings-70000);
		}
		_PAYE = annualPAYE/WEEKS_PER_YEAR;
	}
	public Payslips createPayslip(){
		return new Payslips(this._taxID,
				this._givenNames, this._familyName,
				this._startDate, this._endDate,
				this._PAYE, this._deductions, this._grossWeekEarnings,
				this._YTD, this._NETT);
	}
	public Employees createEmployees(){
		return new Employees(this._name,
				this._taxID, this._wageType,
				this._rate, this._YTD);
	}
	public Burden createBurden(){
		return new Burden(this._startDate,
				this._endDate,totalBurden);
	}
	public PAYE createPAYE(){
		return new PAYE(this._startDate,
				this._endDate,totalPAYE);
	}
	private static final Comparator<Employee> TAXID_SORT = new Comparator<Employee>(){
		public int compare(Employee employee1, Employee employee2) {
			return Integer.compare(employee1._taxID,employee2._taxID);
		}
	};
	private static final Comparator<Employee> NAME_SORT = new Comparator<Employee>(){
		public int compare(Employee employee1, Employee employee2) {
			int compareFamilyName = employee1._familyName.compareTo(employee2._familyName);
			if (compareFamilyName!=0){
				return compareFamilyName;
			} else {
				int compareGivenName = employee1._givenNames.compareTo(employee2._givenNames);
				return (compareGivenName != 0 ? compareGivenName : Integer.compare(employee1._taxID,employee2._taxID));
			}
		}
	};
	public static List<Employee> sortList(List<Employee> list, String operation){
		switch(operation){
		case "Payslips": Collections.sort(list,TAXID_SORT);
			break;
		case "Employees": Collections.sort(list,NAME_SORT);
			break;
		}
		return list;
	}
public static void checkUniqueTaxIDs(List<Employee> list) throws Error{
	Collections.sort(list,TAXID_SORT);
	for (int i=0; i<list.size()-1; i++){
		if (list.get(i)._taxID == list.get(i+1)._taxID){
			throw new Error("The employees \"" + list.get(i)._name + "\" and  \"" + list.get(i+1)._name +
					"\" have the same tax ID (" + list.get(i)._taxID + ")");
		}
	}
}
private void checkDateAndTime() throws Error{
	if (this._startDate.compareTo(this._endDate)>=0){
		throw new Error("The employee " + this._givenNames + " " + this._familyName +
				" (" + this._taxID + ") has a start date (" + this._startDate + ") after the end date (" + this._endDate + ")");
	}
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	dateFormat.setLenient(false);
	try {
		  Date startDateCheck = dateFormat.parse(this._startDate);
		  Date endDateCheck = dateFormat.parse(this._endDate);
	} catch (ParseException e) {
		throw new Error("The employee " + this._givenNames + " " + this._familyName + " (" + this._taxID + ") has a start date (" + this._startDate + ") and an end date (" + this._endDate + ") "
				+ "when at least one date has the incorrect format.");
	}
	if (this._hours%(float)0.25 != 0){
		throw new Error("The employee " + this._givenNames + " " + this._familyName + " (" + this._taxID + ") has an invalid (unrounded) number of hours worked (" + this._hours + ")");
	}
	if (this._hours < 0){
		throw new Error("The employee " + this._givenNames + " " + this._familyName + " (" + this._taxID + ") has an invalid (negative"
				+ ") number of hours worked (" + this._hours + ")");
	}
}
private void checkCurrencyValues() throws Error{
	if (this._NETT < 0){
		throw new Error("The employee " + this._givenNames + " " + this._familyName + " (" + this._taxID + ") has an invalid nett of $" + this._NETT);
	}
	if (this._wageType.compareTo("Hourly")==0 && (this._rate < 15.25)){
		throw new Error("The employee " + this._givenNames + " " + this._familyName + " (" + this._taxID + ") has an hourly rate of $" +
				this._rate + " which is lower than the minimum hourly wage of $15.25 (min. wage as of April 1st 2016)");
	}
}
}