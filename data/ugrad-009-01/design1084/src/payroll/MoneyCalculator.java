package payroll;
public class MoneyCalculator {
	public static double roundNearestCent(double a){
		return Math.round(a * 100.0) / 100.0;
	}
	public static double hourlyRate(double rate,double hr) {
		double gross = 0;
		if(hr>43){
			gross += (hr-43)*2*rate;
			hr -= (hr-43);
		}
		if(hr>40){
			gross += (hr-40)*1.5*rate;
			hr -= (hr-40);
		}
		gross += hr*rate;
		return gross;
	}
	public static double calcTax(double income){
		double totalTax = 0;
		if(income>70000){
			totalTax += (income-70000) * 0.33;
			income -= (income-70000);
		}
		if(income>48000){
			totalTax += (income-48000) * 0.30;
			income -= (income-48000);
		}
		if(income>14000){
			totalTax += (income-14000) * 0.175;
			income -= (income-14000);
		}
		totalTax += income * 0.105;
		return totalTax;
	}
}

