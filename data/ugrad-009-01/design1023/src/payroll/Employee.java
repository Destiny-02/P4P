package payroll;
public class Employee {
	private int tid;
	private String name;
	private String payType;
	private double rate;
	private double ytd;
	private String start;
	private String end;
	private double hours;
	private double deductions;
	private double paye;
	private double grossIncome;
	private double netIncome;
	private double endYtd;
	public Employee(String tid, String name, String payType, String rate, String ytd, String start, String end, String hours, String deductions){
		this.tid = Integer.parseInt(tid);
		this.name = name;
		this.payType = payType;
		this.rate = Double.parseDouble(rate.replace("$", ""));
		this.ytd = Double.parseDouble(ytd.replace("$", ""));
		this.start = start;
		this.end = end;
		this.hours = Double.parseDouble(hours);
		this.deductions = Double.parseDouble(deductions.replace("$", ""));
	}
	public int getTid(){
		return tid;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getPayType(){
		return payType;
	}
	public double getRate(){
		return rate;
	}
	public double getYtd(){
		return ytd;
	}
	public String getStart(){
		return start;
	}
	public String getEnd(){
		return end;
	}
	public double getHours(){
		return hours;
	}
	public double getDeductions(){
		return deductions;
	}
	public double getPaye() {
		return paye;
	}
	public void setPaye(double paye) {
		this.paye = paye;
	}
	public double getGrossIncome() {
		return grossIncome;
	}
	public void setGrossIncome(double grossIncome) {
		this.grossIncome = grossIncome;
	}
	public double getNetIncome() {
		return netIncome;
	}
	public void setNetIncome(double netIncome) {
		this.netIncome = netIncome;
	}
	public double getEndYtd() {
		return endYtd;
	}
	public void setEndYtd(double endYtd) {
		this.endYtd = endYtd;
	}
}
