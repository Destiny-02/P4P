package payroll;
class Tax {
	public static final double TAX14000 = 0.105;
	public static final double TAX48000 = 0.175;
	public static final double TAX70000 = 0.30;
	public static final double TAXOVER70 = 0.33;
	private final double _income;
	Tax(double income){
		_income = income;
	}
	public double calculateTax(){
		double taxabledollars;
		if(_income>0&&_income<=14000){
			taxabledollars = _income*TAX14000;
		}else if(_income>14000&&_income<=48000){
			taxabledollars = 14000*TAX14000+(_income-14000)*TAX48000;
		}else if(_income>48000&&_income<=70000){
			taxabledollars = 14000*TAX14000+34000*TAX48000+(_income-48000)*TAX70000;
		}else if(_income>70000){
			taxabledollars = 14000*TAX14000+34000*TAX48000+22000*TAX70000+(_income-70000)*TAXOVER70;
		}else{
			taxabledollars = 0;
		}
		return taxabledollars;
	}
}