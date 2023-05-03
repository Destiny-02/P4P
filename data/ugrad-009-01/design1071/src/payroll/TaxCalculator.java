package payroll;
public class TaxCalculator {
	public enum TaxBracket {
		HIGH 		(0.33, 70000),
		MEDIUMHIGH	(0.30, 48000),
		MEDIUMLOW	(0.175, 14000),
		LOW			(0.105, 0);
		private final double _rate;
		private final double _range;
		private TaxBracket(double rate, double range){
			_rate = rate;
			_range = range;
		}
		public double rate(){
			return _rate;
		}
		public double range(){
			return _range;
		}
	}
	public double computeYearTax(double gross){
		double paye;
		if (gross > TaxBracket.HIGH.range()){
			paye = (gross - TaxBracket.HIGH.range()) * TaxBracket.HIGH.rate() +
					(TaxBracket.HIGH.range() - TaxBracket.MEDIUMHIGH.range())* TaxBracket.MEDIUMHIGH.rate() +
					(TaxBracket.MEDIUMHIGH.range() - TaxBracket.MEDIUMLOW.range()) * TaxBracket.MEDIUMLOW.rate() +
					(TaxBracket.MEDIUMLOW.range() - TaxBracket.LOW.range()) * TaxBracket.LOW.rate();
		} else if (gross > TaxBracket.MEDIUMHIGH.range()){
			paye = (gross - TaxBracket.MEDIUMHIGH.range())* TaxBracket.MEDIUMHIGH.rate() +
					(TaxBracket.MEDIUMHIGH.range() - TaxBracket.MEDIUMLOW.range()) * TaxBracket.MEDIUMLOW.rate() +
					(TaxBracket.MEDIUMLOW.range() - TaxBracket.LOW.range()) * TaxBracket.LOW.rate();
		} else if (gross > TaxBracket.MEDIUMLOW.range()){
			paye = (gross - TaxBracket.MEDIUMLOW.range())* TaxBracket.MEDIUMLOW.rate() +
					(TaxBracket.MEDIUMLOW.range() - TaxBracket.LOW.range()) * TaxBracket.LOW.rate();
		} else {
			paye = (gross - TaxBracket.LOW.range()) * TaxBracket.LOW.rate();
		}
		return paye;
	}
	public double computeWeekTax(double gross){
		return computeYearTax(gross)/52;
	}
}
