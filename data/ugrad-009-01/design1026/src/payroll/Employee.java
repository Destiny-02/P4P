package payroll;
import java.text.DecimalFormat;
abstract class Employee extends FormatProcessing{
	private String TID;
	private String Name;
	private String Type;
	private String Rate;
	private String YTD;
	private String Start;
	private String End;
	private String Hours;
	private String Deduction;
	public Employee(String TID, String Name, String Type, String Rate, String YTD,String Start, String End, String Hours,String Deduction){
		this.TID = TID;
		this.Name = Name;
		this.Type = Type;
		this.Rate = Rate;
		this.YTD = YTD;
		this.Start = Start;
		this.End = End;
		this.Hours = Hours;
		this.Deduction = Deduction;
	}
	public String convertName() {
		String lastName = Name.substring(0, Name.indexOf(","));
		String firstName = Name.substring(Name.indexOf(" ") + 1);
		String cName =  firstName + " " + lastName + ",";
		return cName;
	}
	public String getName() {
		return this.Name;
	}
	public String getTID() {
		return this.TID;
	}
	public String getType() {
		return this.Type;
	}
	public String getRate() {
		return this.Rate;
	}
	public String getYTD() {
		return this.YTD;
	}
	public String getStart() {
		return this.Start;
	}
	public String getEnd() {
		return this.End;
	}
	public String getHours() {
		return this.Hours;
	}
	public String getDeduction() {
		return this.Deduction;
	}
	public String processDeduction() throws customExceptions {
		if (getDeduction().contains("-$")|getDeduction().contains("$-")) {
			throw new customExceptions("Please enter a positive value for Deduction");
		}
		String sDeduction = decimalFormat(getDeduction());
		return sDeduction;
	}
	public String processGross() {
		return "";
	}
	public String processPAYE() {
		return "";
	}
	public String processTimePeriod() {
		String Time = Start + " to " + End;
		return Time;
	}
	public String processYTD() {
		return "";
	}
	protected final String doubleToMoney(Double input) {
		DecimalFormat df = new DecimalFormat("0.00");
		String sans = df.format(input);
		String ans = "$" + sans;
		return ans;
	}
	protected final double moneyToDouble(String input) {
		double ans = Double.parseDouble(input);
		return ans;
	}
	public String processNett() throws customExceptions {
		return "";
	}
}
