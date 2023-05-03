package payroll;
public class SalariedWage extends Employee {
	public SalariedWage(String familyName, String givenName, String startDate, String endDate, int taxID, double deduction, double rate, double hours, double YTD){
		this.familyName = familyName;
		this.givenName = givenName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.taxID = taxID;
		this.gross = rate/52.00;
		this.YTD = YTD + gross;
		this.deduction = deduction;
		this.rate = rate;
		this.hours = hours;
		computePAYE();
		computeNett();
	}
	public double getGross() {
		return this.gross;
	}
	public void computePAYE() {
		if (rate <= 14000) {
			this.PAYE = (rate*.105)/52;
		} else if (rate <= 48000) {
			this.PAYE = ((14000*.105) + ((rate-14000)*.175))/52;
		} else if (rate <= 70000) {
			this.PAYE = ((rate-48000)*.30 + (34000*.175) + (14000*.105))/52;
		} else {
			this.PAYE = ((rate-70000)*.33 + (22000*.30) + (34000*.175) + (14000*.105))/52;
		}
	}
	public double getPAYE() {
		return this.PAYE;
	}
	public void computeNett() {
		this.Nett = gross - PAYE - deduction;
	}
	public double getNett() {
		return this.Nett;
	}
	public double getYTD() {
		return this.YTD;
	}
}
