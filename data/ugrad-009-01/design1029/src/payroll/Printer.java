package payroll;
import java.text.DecimalFormat;
public class Printer {
	private double _value;
	private String _reportType;
	public Printer(){
		_value = 0.0;
		_reportType = null;
	}
	public Printer(double value, String reportType){
		_value = value;
		_reportType = reportType;
	}
	public void printSimpleReport(){
		System.out.print("Total " + _reportType + ": $" );
		DecimalFormat df = new DecimalFormat("####0.00");
		System.out.println(df.format(_value));
	}
}