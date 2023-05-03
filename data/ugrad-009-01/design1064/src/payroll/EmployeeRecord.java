package payroll;
import java.text.DecimalFormat;
public class EmployeeRecord {
	private Integer taxId;
	private String name;
	private String employment;
	private Double rate;
	private Double ytd;
	private String start;
	private String end;
	private Double hours;
	private Double deductions;
	public Integer getTaxId() {
		return taxId;
	}
	public String getName(){
		return name;
	}
	public String getEmployment() {
		return employment;
	}
	public Double getRate() {
		return rate;
	}
	public String getStart() {
		return start;
	}
	public String getEnd() {
		return end;
	}
	public Double getHours() {
		return hours;
	}
	public void nameOrder(){
		String[] order = name.split(",");
		name = (order[1]+ " " + order[0]);
	}
	public void setRecords(Integer setTaxID, String setName, String setEmployment,
			Double setRate, Double setYTD, String setStart, String setEnd, Double setHours, Double setDeductions){
		taxId = setTaxID;
		name = setName;
		employment = setEmployment;
		rate = setRate;
		ytd = setYTD;
		start = setStart;
		end = setEnd;
		hours = setHours;
		deductions = setDeductions;
	}
	public void printPaySlip(){
		Tax tax = new Tax();
		Payment pay = new Payment();
		DecimalFormat dformat = new DecimalFormat("#.00");
			double GrossPay = pay.calculateGross(hours, employment, rate);
			double PAYE = tax.taxCalc(hours, employment, rate);
			double Net = GrossPay - PAYE - deductions;
			double newYtd = GrossPay + ytd;
			nameOrder();
			System.out.println(taxId + "." + name + ", Period: " + start + " to "
					+ end + ". Gross: $" + dformat.format(GrossPay) + ", PAYE: $" + dformat.format(PAYE) + ", Deductions: $"
					+ dformat.format(deductions) + " Nett: $" + dformat.format(Net) + " YTD: $" + dformat.format(newYtd));
	}
	public void printEmployeeDetails(){
			Payment grosspay = new Payment();
			DecimalFormat dformat = new DecimalFormat("#.00");
			double newytd = grosspay.calculateGross(hours, employment,
						rate) + ytd;
				System.out.println(name + " (" + taxId + ") "
						+ employment + ", $" + dformat.format(rate) + " YTD:$" + dformat.format(newytd));
	}
	}

