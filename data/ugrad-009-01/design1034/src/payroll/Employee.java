package payroll;
import java.text.DecimalFormat;
public abstract class Employee implements Comparable<Employee>{
	private int tid;
	private String name;
	private String employment;
	private String rate;
	private String ytd;
	private String startDate;
	private String endDate;
	private String deduction;
	public Employee(int tid, String name, String employment, String rate, String ytd,
			String startDate, String endDate, double hours, String deduction) {
		this.tid = tid;
		this.name = name;
		this.employment = employment;
		this.rate = rate;
		this.ytd = ytd;
		this.startDate = startDate;
		this.endDate = endDate;
		this.deduction = deduction;
	}
	public int getTID() {
		return tid;
	}
	public String getEmployment() {
		return employment;
	}
	public String getRate() {
		double doubleRate = Double.parseDouble(rate);
		rate = formatDouble(doubleRate);
		return rate;
	}
	public String getFirstName() {
		String[] splitNames = name.split(",");
		return splitNames[1];
	}
	public String getLastName() {
		String[] splitNames = name.split(",");
		return splitNames[0];
	}
	public String getStartDate() {
		return startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public String getDeduction() {
		double doubleDeduction = Double.parseDouble(deduction);
		deduction = formatDouble(doubleDeduction);
		return deduction;
	}
	public int compareTo(Employee e) {
		return tid - e.tid;
	}
	public String computePaye() {
		double _deduction = Double.parseDouble(deduction);
		double deductedPay = Double.parseDouble(this.computeGross()) - Double.parseDouble(this.computeTax()) - _deduction;
		String roundedDeductions = formatDouble(deductedPay);
		return roundedDeductions;
	}
	public String computeYTD() {
		double addedYtd = Double.parseDouble(ytd) + Double.parseDouble(this.computeGross());
		ytd = formatDouble(addedYtd);
		return ytd;
	}
	public String computeTax() {
		String gross = computeGross();
		double grossDouble = Double.parseDouble(gross);
		grossDouble = grossDouble * 52;
		double taxation;
		if (grossDouble<=14000 && grossDouble>0) {
			taxation = grossDouble*0.105/52;
		} else if (grossDouble>14000 && grossDouble <= 48000){
			taxation = (14000*0.105 + (grossDouble-14000)*0.175)/52;
		} else if (grossDouble>48000 && grossDouble <= 70000) {
			taxation = (14000*0.105 + 34000*0.175 + (grossDouble - 48000)*0.3)/52;
		} else if (grossDouble>70000) {
			taxation = (14000*0.105 + 34000*0.175 + 22000*0.3 + (grossDouble-70000)*0.33)/52;
		} else {
			throw new ArithmeticException();
		}
		String roundedTax = formatDouble(taxation);
		return roundedTax;
	}
	public String formatDouble(double unformattedDouble) {
		DecimalFormat df = new DecimalFormat("#.00");
		String formattedDouble = df.format(unformattedDouble);
		return formattedDouble;
	}
	public abstract String computeGross();
}

