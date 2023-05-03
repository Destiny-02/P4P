package payroll;
public class HourlyWage extends Employee {
		public HourlyWage(String familyName, String givenName, String startDate, String endDate, int taxID, double deduction, double rate, double hours, double YTD){
			this.familyName = familyName;
			this.givenName = givenName;
			this.startDate = startDate;
			this.endDate = endDate;
			this.taxID = taxID;
			this.deduction = deduction;
			this.rate = rate;
			this.hours = hours;
			computeGross();
			computePAYE();
			computeNett();
			this.YTD = YTD + gross;
		}
		private void computeGross() {
			if (hours <= 40) {
				this.gross = rate*hours;
			} else if (hours <= 43) {
				this.gross = (rate*40) + ((hours-40)*1.5*rate);
			} else {
				this.gross = (rate*40) + ((hours-43)*2*rate) + (3*1.5*rate);
			}
		}
		public double getGross() {
			return this.gross;
		}
		public void computePAYE() {
			if (gross*52 <= 14000) {
				this.PAYE = (rate*.105)/52;
			} else if (gross*52 <= 48000) {
				this.PAYE = ((14000*.105) + ((gross*52-14000)*.175))/52;
			} else if (gross*52 <= 70000) {
				this.PAYE = ((gross*52-48000)*.30 + (34000*.175) + (14000*.105))/52;
			} else {
				this.PAYE = ((gross*52-70000)*.33 + (22000*.30) + (34000*.175) + (14000*.105))/52;
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
