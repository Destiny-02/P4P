package payroll;
import java.text.ParseException;
import java.util.Date;
public abstract class Employee implements Comparable<Employee>{
	public static final int WEEKS_IN_YEAR = 52, PAY_PERIOD = 7;
	private int tid;
	private String familyName;
	private String firstName;
	private double rate;
	private double ytd;
	private Date start;
	private Date end;
	private double hours;
	private double deduction;
	public Employee(int tid, String fullName, double rate, double ytd, String start, String end,
			double hours, double deduction) throws ParseException {
		this.tid = tid;
		int index = fullName.indexOf(", ");
		this.familyName = fullName.substring(0, index);
		this.firstName = fullName.substring(index + 2);
		this.rate = rate;
		this.ytd = ytd;
		this.start = Utility.stringToDate(start);
		this.end = Utility.stringToDate(end);
		this.hours = hours;
		this.deduction = deduction;
	}
	public abstract double getGrossPay();
	public abstract void  processEmployee();
	public String getName() {
		return firstName + " "+familyName;
	}
	public double getPayE() {
		double annualGross = getGrossPay() * WEEKS_IN_YEAR;
		double annualTax = 0;
		double rem = annualGross;
			double band = Math.min(rem, 14000);
			annualTax = band * 0.105;
			rem -= band;
		if(annualGross >= 14000){
			band = Math.min(rem, 34000);
			annualTax += band * 0.175;
			rem -= band;
		}
		if(annualGross >= 48000){
			band = Math.min(rem, 22000);
			annualTax += band * 0.30;
			rem -= band;
		}
		if(annualGross >= 70000){
			annualTax += rem * 0.33;
		}
		return Utility.round(annualTax/WEEKS_IN_YEAR);
	}
	public double getNetPay(){
		return getGrossPay() - deduction - getPayE();
	}
	public double getFinalYTD(){
		return ytd + getGrossPay();
	}
	public void printPayslip(){
		System.out.printf("%d. %s, Period: %s to %s. Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f Nett: $%.2f YTD: $%.2f\n",
				tid, getName(),Utility.dateToString(start), Utility.dateToString(end),
				getGrossPay(), getPayE(), deduction, getNetPay(), getFinalYTD());
	}
	public int compareTo(Employee o) {
		return this.tid - o.tid;
	}
	public String getFamilyName() {
		return familyName;
	}
	public String getFirstName() {
		return firstName;
	}
	public int getTid() {
		return tid;
	}
	public Date getStart() {
		return start;
	}
	public Date getEnd() {
		return end;
	}
	public double getRate() {
		return rate;
	}
	public double getHours() {
		return hours;
	}
}
