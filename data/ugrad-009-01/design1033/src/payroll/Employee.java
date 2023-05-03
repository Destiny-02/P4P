package payroll;
import java.util.Comparator;
public class Employee implements Comparable<Employee> {
	private int _TID;
	private String _lastName;
	private String _firstName;
	private String _incomeType;
	private float _rate;
	private float _YTD;
	private String _start;
	private String _end;
	private float _totalDays;
	private float _hoursWorked;
	private float _deductions;
	private float _PAYE;
	private float _yearlyEarnings;
	public Employee(String TID, String name, String incomeType, String rate, String YTD, String start, String end, float hoursWorked, String deductions){
		_lastName = name.split(",\\s+")[0];
		_firstName = name.split(",\\s+")[1];
		_TID = Integer.parseInt(TID);
		_incomeType = incomeType;
		_rate = (_incomeType.equals("Salaried")?((Float.valueOf((rate.substring(1)))/364)):Float.valueOf((rate.substring(1))));
		_YTD = Float.valueOf((YTD.substring(1)));
		_start = start;
		_end = end;
		_totalDays = totalDays();
		_hoursWorked = hoursWorked;
		_deductions = Float.valueOf((deductions.substring(1)));
		_yearlyEarnings = yearlyEarnings();
		_PAYE = calculatePAYE();
	}
	public int compareTo(Employee B){
		if (_lastName.compareTo(B._lastName)>0){
			return 1;
		} else if (_lastName.compareTo(B._lastName)<0){
			return -1;
		} else {
			return 0;
		}
	}
	private float calculatePAYE(){
		float paye = 0;
		float taxableEarnings = _yearlyEarnings;
		if (taxableEarnings>70000){
			paye+=(taxableEarnings - 70000)*0.33;
			taxableEarnings = 70000;
		}
		if (taxableEarnings>48000){
			paye += (taxableEarnings-48000)*0.3;
			taxableEarnings = 48000;
		}
		if (taxableEarnings>14000){
			paye+= (taxableEarnings-14000)*0.175;
			taxableEarnings = 14000;
		}
		paye+=taxableEarnings *0.105;
		return paye;
	}
	private float yearlyEarnings(){
		if (_incomeType.equals("Salaried")){
			return _rate*364;
		} else if (_hoursWorked <= 40){
			return _rate*_hoursWorked*52;
		} else if (_hoursWorked <=43){
			return (float)(((_hoursWorked - 40)*_rate*1.5 + 40*_rate)*52);
		} else {
			return (float)(((_hoursWorked - 43)*_rate*2 + 40*_rate + 3*_rate*1.5)*52);
		}
	}
	private float totalDays(){
		String[] storeStartDates = _start.split("-");
		String[] storeEndDates = _end.split("-");
		float yearsDays = 364*Float.valueOf(storeEndDates[0]) - 364*Float.valueOf(storeStartDates[0]);
		float monthsDays = 364/12*Float.valueOf(storeEndDates[1]) - 364/12*Float.valueOf(storeStartDates[1]);
		float days = Float.valueOf(storeEndDates[2]) - Float.valueOf(storeStartDates[2]);
		return yearsDays+monthsDays+days+1;
	}
	public static class Comparators{
		public static class EmployeeComparatorByLast implements Comparator<Employee>{
			public int compare(Employee A, Employee B){
				if (A._lastName.compareTo(B._lastName)>0){
					return 1;
				} else if (A._lastName.compareTo(B._lastName)<0){
					return -1;
				} else {
					return 0;
				}
			}
		}
		public static class EmployeeComparatorByFirst implements Comparator<Employee>{
			public int compare(Employee A, Employee B){
				if (A._firstName.compareTo(B._firstName)>0){
					return 1;
				} else if (A._firstName.compareTo(B._firstName)<0){
					return -1;
				} else {
					return 0;
				}
			}
		}
		public static class EmployeeComparatorByTID implements Comparator<Employee>{
			public int compare(Employee A, Employee B){
				if (A._TID > B._TID){
					return 1;
				} else if (A._TID < B._TID){
					return -1;
				} else {
					return 0;
				}
			}
		}
	}
	public static class DisplayDetails{
		public String displayPeriod(Employee A){
			return A._start+" to "+A._end;
		}
		public void displayEmployeeProcess(Employee A){
			if (A._incomeType.equals("Salaried")){
				System.out.println(A._lastName+", "+A._firstName+" ("+A._TID+") "+A._incomeType+", $"+String.format("%.2f",A._rate*364)+" YTD:$"+String.format("%.2f", (A._rate*7 + A._YTD)));
			} else {
				System.out.println(A._lastName+", "+A._firstName+" ("+A._TID+") "+A._incomeType+", $"+String.format("%.2f", A._rate)+" YTD:$"+String.format("%.2f", (A.yearlyEarnings()/364*A._totalDays + A._YTD)));
			}
		}
		public void displayPayslipsProcess(Employee A){
			System.out.println(A._TID+". "+A._firstName+" "+A._lastName+", "+"Period: "+displayPeriod(A)+". Gross: $"+String.format("%.2f",A._yearlyEarnings/364*A._totalDays)+", PAYE: $"+String.format("%.2f",(A._PAYE/52))+", Deductions: $"+String.format("%.2f",A._deductions)+" Nett: $"+String.format("%.2f",(A._yearlyEarnings/364*A._totalDays - A._PAYE/52 - A._deductions))+" YTD: $"+String.format("%.2f",(A._YTD+A._yearlyEarnings/364*A._totalDays)));
		}
		public float displayBurdenProcess(Employee A){
			return (A._yearlyEarnings/364*A._totalDays);
		}
		public float displayPAYEProcess(Employee A){
			return(A._PAYE/52);
		}
	}
}
