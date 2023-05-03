package payroll;
public class salariedEmployee extends Employee{
	double _ttax;
	public salariedEmployee(String TID, String Name, String Type, String Rate,
			String YTD, String Start, String End, String Hours, String Deduction) {
		super(TID, Name, Type, Rate, YTD, Start, End, Hours, Deduction);
	}
	@Override
	public String processGross() {
		double dmoney = moneyToDouble(getRate().substring(1));
		double drate = dmoney / 52;
		String ans = doubleToMoney(drate);
		return ans;
	}
	@Override
	public String processPAYE() {
		String sgross = getRate().substring(1);
		double igross = Double.parseDouble(sgross);
		if (igross <= 14000) {
			_ttax = igross * 0.105;
		} else if (igross > 14000 && igross <= 48000) {
			_ttax = 14000 * 0.105 + (igross-14000) * 0.175;
		} else if (igross > 48000 && igross <= 70000){
			_ttax = 14000 * 0.105 + 34000 * 0.175 + (igross-48000) * 0.3;
		} else {
			_ttax = 14000 * 0.105 + 34000 * 0.175 + 22000 * 0.3 + (igross- 70000) * 0.33;
		}
		double tax = _ttax/52;
		String stax = doubleToMoney(tax);
		return stax;
	}
	public String processNett() throws customExceptions {
		double gross = moneyToDouble(processGross().substring(1));
		double PAYE = moneyToDouble(processPAYE().substring(1));
		double Extra = moneyToDouble(getDeduction().substring(1));
		double ans = gross-PAYE-Extra;
		if (ans < 0){
			throw new customExceptions("Deduction is greater than income after tax. Please consider revising deduction amount.");
		}
		String Nett = doubleToMoney(ans);
		return Nett;
	}
	@Override
	public String processYTD() {
		double cYTD = moneyToDouble(getYTD().substring(1));
		double gross = moneyToDouble(processGross().substring(1));
		double fYTD = cYTD + gross;
		String ans = doubleToMoney(fYTD);
		return ans;
	}
	@Override
	public String toString(){
		try {
			return getTID()+". "+convertName()+ " Period: " + getStart() + " to " + getEnd() + ". " +"Gross: " +processGross()+ ", "+"PAYE: "+processPAYE()+ ", "+"Deductions: "+processDeduction()+" Nett: "+processNett()+" YTD: "+processYTD();
		} catch (customExceptions e) {
			e.printStackTrace();
		}
		return "";
	}
}
