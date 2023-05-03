package payroll;
public class SalariedEmployee extends Employee {
	private double _salary;
	private double _pay;
	public SalariedEmployee(int tid, String name, String employment, String rate, String ytd, String startDate,
			String endDate, double hours, String deduction) {
		super(tid, name, employment, rate, ytd, startDate, endDate, hours, deduction);
		_salary = Double.parseDouble(rate);
	}
	public String computeGross() {
		_pay =  _salary/52;
		 String formattedPay = formatDouble(_pay);
		 return formattedPay;
	}
}

