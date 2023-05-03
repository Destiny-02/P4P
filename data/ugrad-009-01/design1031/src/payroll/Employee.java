package payroll;
import java.lang.String;
import java.text.DecimalFormat;
import java.lang.Error;
public class Employee{
	protected Integer _TaxID;
	protected String[] _name;
	protected String _fullName;
	private String _Employment;
	private double _rate;
	private double _YTD;
	protected String _start;
	protected String _end;
	private double _hours;
	private double _deductions;
	public Employee(String str){
		String[] input = str.trim().split("[\\s+]");
		this._TaxID = Integer.parseInt(input[0]);
		if(input[1].substring(input[1].length()-1).equals(",")){
			this._name = new String[]{input[1],input[2]};
			this._fullName = input[1] + " " + input[2];
		} else {
			throw new IllegalArgumentException("Name in wrong format");
		}
		this._Employment = input[3];
		if(input[4].substring(0,1).equals("$")){
			this._rate = Double.parseDouble(input[4].substring(1));
		} else {
			System.err.println("DollarSign is missing!");
			throw new Error();
		}
		this._YTD = Double.parseDouble(input[5].substring(1));
		this._start = input[6];
		this._end = input[7];
		this._hours = Double.parseDouble(input[8]);
		this._deductions = Double.parseDouble(input[9].substring(1));
	}
	public double getPAYE(){
		double yearlyRate = getGross()*52;
		double incomeTax = 0;
		if(0<=yearlyRate&&yearlyRate<=14000){
			incomeTax = yearlyRate*.105;
		} else if(14000<yearlyRate&&yearlyRate<=48000){
			incomeTax = 14000*.105 + (yearlyRate - 14000)*.175;
		} else if(48000<yearlyRate&&yearlyRate<=70000){
			incomeTax = 14000*.105 + 34000*.175 + (yearlyRate - 48000)*.3;
		} else if(yearlyRate>70000){
			incomeTax = 14000*.105 + 34000*.175 + 22000*.3 + (yearlyRate - 70000)*.33;
		} else {
			System.err.println("Data is in the wrong format");
			throw new IllegalArgumentException();
		}
		return incomeTax/52;
	}
	public double getGross(){
		double gross = 0;
		if(this._Employment.equalsIgnoreCase("hourly")){
			double hours = this._hours;
			if(0<=hours&&hours<=40){
				gross = _rate*hours;
			} else if(40<hours&&hours<=43){
				gross = _rate*(40+(hours-40)*1.5);
			} else if(43<hours){
				gross = _rate*(44.5+(hours-43)*3);
			} else {
				System.err.println("hours an employee has worked for should not be negative");
				throw new IllegalArgumentException();
			}
		} else if(this._Employment.equalsIgnoreCase("salaried")){
			gross = this._rate/52;
		} else {
			System.err.println("An employee should only have either \"Salaried\" or \"Hourly\""
					+ "\n[case insensitive] Employment");
			throw new IllegalArgumentException();
		}
		return gross;
	}
	public double getNett(){
		return getGross() - (getPAYE() + this._deductions);
	}
	public double getYTD(){
		return getGross()+this._YTD;
	}
	public void printDetails(String prcss){
		DecimalFormat df = new DecimalFormat("#.00");
		if(prcss.equals("Payslips")){
			System.out.println(_TaxID+". "+_name[1]+" "+_name[0]+" Period: "+_start+" to "+_end+". Gross: $"+
					df.format(getGross())+", PAYE: $"+df.format(getPAYE())+", Deductions: $"+
					df.format(_deductions)+" Nett: $"+df.format(getNett())+" YTD: $"+df.format(getYTD()));
		} else if(prcss.equals("Employees")){
			System.out.println(_name[0]+" "+_name[1]+" ("+_TaxID+") "+_Employment+", $"+df.format(_rate)+
					" YTD:$"+df.format(getYTD()));
		}
	}
	public int compare(Employee e, String process) {
		if(process.equals("Employees")){
			return _fullName.compareTo(e._fullName);
		} else if(process.equals("Payslips")){
			return _TaxID.compareTo(e._TaxID);
		} else {
			throw new IllegalArgumentException("Process name is invalid!\n"
					+ "Should either be \"Payslip\", \"Burden\", \"Employees\", or \"PAYE\" [case sensitive].");
		}
	}
}
