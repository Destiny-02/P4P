package payroll;
import java.text.DecimalFormat;
public abstract class PayrollProcess{
	private String _output;
	public static final double REGULAR = 1;
	public static final double OVERTIME3 = 1.5;
	public static final double OVERTIMEGT3 = 2;
	public static final DecimalFormat CURRENCYFORMAT = new DecimalFormat("#0.00");
	public String output() {
		Date today = new Date();
		if(_output!=null){
			return today.toString()+"\n"+_output;
		}else{
			return today.toString()+"\n";
		}
	}
	protected void setOutput(String op){
		_output = op;
	}
	abstract void process(AllEmployees all);
	protected String format(double currency){
		return CURRENCYFORMAT.format(currency);
	}
	public double calculateOvertime(double hours){
		double pay = 0;
		if((hours -= 40) < 0){
			pay = (40+hours)*REGULAR;
			return pay;
		}else if((hours -= 3) < 0){
			pay+=40*REGULAR+(3+hours)*OVERTIME3;
			return pay;
		}else{
			pay+=40*REGULAR+3*OVERTIME3+pay*OVERTIMEGT3;
			return pay;
		}
	}
}