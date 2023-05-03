package payroll;
import java.util.Comparator;
public class Employee implements Comparable<Employee>{
	private int _TID;
	private String _familyName;
	private String _givenName;
	private String _employement;
	private float _rate;
	private float _YTD;
	private String _start;
	private String _end;
	private float _hours;
	private float _deductions;
	private float _gross;
	private float _PAYE;
	private float _nett;
	public Employee(int TID, String[] fullname, String employement, float rate, float YTD, String start, String end, float hours, float deductions) {
		_TID = TID;
		_familyName = fullname[0];
		_givenName = fullname[1];
		_employement = employement;
		_rate = rate;
		_YTD = YTD;
		_start = start;
		_end = end;
		_hours = hours;
		_deductions = deductions;
	}
	public Employee() {
	}
	public int compareTo(Employee other) {
		return ((Integer)_TID).compareTo(other._TID);
	}
	public class NameComparator implements Comparator<Employee> {
		public int compare(Employee e1, Employee e2) {
			if (e1._familyName.compareTo(e2._familyName) != 0) {
				return e1._familyName.compareTo(e2._familyName);
			} else {
				if (e1._givenName.compareTo(e2._givenName) == 0) {
					Integer TID1 = e1._TID;
					Integer TID2 = e2._TID;
					return TID1.compareTo(TID2);
				} else {
					return e1._givenName.compareTo(e2._givenName);
				}
			}
		}
	}
	public void setGross(float gross) {
		_gross = gross;
	}
	public void setNett(float nett) {
		_nett = nett;
	}
	public void setPAYE(float PAYE) {
		_PAYE = PAYE;
	}
	public void setYTD(float YTD) {
		_YTD = YTD;
	}
	public String[] getPayslipInfo(Employee e) {
		String[] info = {Integer.toString(e._TID), e._givenName, e._familyName,e._start,e._end,Float.toString(e._gross), Float.toString(e._PAYE),
							Float.toString(e._deductions),Float.toString(e._nett), Float.toString(e._YTD)};
		return info;
	}
	public String[] getEmployeesData(Employee e) {
		String[] info = {e._familyName, e._givenName, Integer.toString(e._TID), e._employement, Float.toString(e._rate), Float.toString(e._YTD)};
		return info;
	}
	public String[] getPayPeriod(Employee e) {
		String[] payPeriod = {e._start,e._end};
		return payPeriod;
	}
	public String[] getDataToCalc() {
		String[] data = {_employement, Float.toString(_rate), Float.toString(_hours), Float.toString(_gross),Float.toString(_nett),
							Float.toString(_deductions), Float.toString(_PAYE), Float.toString(_YTD)};
		return data;
	}
}
