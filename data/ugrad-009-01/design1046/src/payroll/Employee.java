
package payroll;
public class Employee{
	private String taxID;
	private String name;
	private String employment;
	private String rate;
	private String YTD;
	private String start;
	private String end;
	private String workHours;
	private String deduction;
	public Employee(String taxID, String name, String employment, String rate, String YTD, String start,
			String end, String workHours, String deduction) {
		this.taxID = taxID;
		this.name = name;
		this.employment = employment;
		this.rate = rate;
		this.YTD = YTD;
		this.start = start;
		this.end = end;
		this.workHours = workHours;
		this.deduction = deduction;
	}
	public String getTaxID() {
		return taxID;
	}
	public String getName() {
		return name;
	}
	public String getEmployment() {
		return employment;
	}
	public String getRate() {
		return rate;
	}
	public String getYTD() {
		return YTD;
	}
	public String getStart() {
		return start;
	}
	public String getEnd() {
		return end;
	}
	public String getWorkHours() {
		return workHours;
	}
	public String getDeduction() {
		return deduction;
	}
}
