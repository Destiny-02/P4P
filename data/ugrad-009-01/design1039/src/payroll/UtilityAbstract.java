package payroll;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
public abstract class UtilityAbstract {
	public double round(double value) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	public String toString2DP(double input){
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(input);
	}
}