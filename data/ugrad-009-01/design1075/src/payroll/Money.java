package payroll;
import java.text.NumberFormat;
public class Money {
	private double _moneyValue;
	public Money(double moneyValue) {
		_moneyValue = moneyValue;
	}
	public String moneyFormat2dp () {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return nf.format(_moneyValue).replaceAll(",", "");
	}
}
