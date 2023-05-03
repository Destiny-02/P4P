package payroll;
public class hourlyEmployee extends Employee{
	double _ttax;
	double _tIncome;
	public hourlyEmployee(String TID, String Name, String Type, String Rate,
			String YTD, String Start, String End, String Hours, String Deduction) {
		super(TID, Name, Type, Rate, YTD, Start, End, Hours, Deduction);
	}
	@Override
	public String processGross() {
		double dmoney = moneyToDouble(getRate().substring(1));
		double sHours = moneyToDouble(getHours());
		if (sHours <= 40) {
			_tIncome = sHours * dmoney;
		} else if (sHours > 40 && sHours <= 43) {
			_tIncome = 40 * dmoney + (sHours-40) * dmoney * 1.5;
		} else {
			_tIncome = 40 * dmoney + 3 * dmoney * 1.5 + (sHours-43) * dmoney * 2;
		}
		String ans = doubleToMoney(_tIncome);
		return ans;
	}
	@Override
	public String processPAYE() {
		double dgross = moneyToDouble(processGross().substring(1));
		double tgross = dgross * 52;
		if (tgross <= 14000) {
			_ttax = tgross * 0.105;
		} else if (tgross > 14000 && tgross <= 48000) {
			_ttax = 14000 * 0.105 + (tgross-14000) * 0.175;
		} else if (tgross > 48000 && tgross <= 70000){
			_ttax = 14000 * 0.105 + 34000 * 0.175 + (tgross-48000) * 0.3;
		} else {
			_ttax = 14000 * 0.105 + 34000 * 0.175 + 22000 * 0.3 + (tgross- 70000) * 0.33;
		}
		double tax = _ttax/52;
		String stax = doubleToMoney(tax);
		return stax;
	}
	@Override
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
			return getTID()+". "+convertName()+ " " + "Period: " + getStart() + " to " + getEnd() + ". " +"Gross: " +processGross()+", "+"PAYE: "+processPAYE()+", "+"Deductions: "+processDeduction()+" Nett: "+processNett()+" YTD: "+processYTD();
		} catch (customExceptions e) {
			e.printStackTrace();
		}
		return "";
	}
}
