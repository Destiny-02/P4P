package payroll;
public final class Calculations {
	private double _gross;
	private double _paye;
	public Calculations(EmployeeData data){
		double Rate = data.getrate();
		double YTD = data.getYTD();
		double Hours = data.gethours();
		double Deduction = data.getdeduction();
		double gross;
		double Nett;
		double paye;
		if(data.getemployment().equals("Salaried")){
			gross = Math.round((Rate / 52) * 100.0) / 100.0;
			YTD = YTD + gross;
		} else {
			if(Hours > 43){
				Hours = 40 + 3 * 1.5 + (Hours - 43) * 2;
			} else if (Hours > 40) {
				Hours = 40 + (Hours - 40) * 1.5;
			}
			gross = Rate * Hours;
			YTD = YTD + gross;
			Rate = gross * 52;
		}
		if(Rate > 70000) {
			paye = (Rate - 70000) * 0.33 + 22000 * 0.30 + 34000 * 0.175 + 14000 * 0.105;
		} else if(Rate > 48000){
			paye = (Rate - 48000) * 0.30 + 34000 * 0.175 + 14000 * 0.105;
		} else if(Rate > 14000){
			paye = (Rate - 14000) * 0.175 + 14000 * 0.105;
		} else {
			paye = Rate * 0.105;
		}
		paye = Math.round((paye / 52) * 100.0) / 100.0;
		Nett = gross - paye - Deduction;
		data.setpaye(paye);
		data.setYTD(YTD);
		data.setgross(gross);
		data.setNett(Nett);
		_gross = gross;
		_paye = paye;
	}
	public double getgross() {
		return _gross;
	}
	public double getpaye() {
		return _paye;
	}
}
