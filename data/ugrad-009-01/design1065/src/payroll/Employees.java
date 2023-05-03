package payroll;
import java.text.DecimalFormat;
public class Employees {
	private String _rate;
	private String _name;
	private String _employment;
	private String _taxID;
	public Employees(String line){
		String[] parts = line.split("\\t");
		_taxID = parts[0];
		_name = parts[1];
		_employment = parts[2];
		_rate = parts[3];
	}
	public int gettaxID(){
		return Integer.parseInt(_taxID);
	}
	public String employeeLine(String line){
		Income i = new Income(line);
		DecimalFormat formatter = new DecimalFormat("$0.00");
		_rate = _rate.replace("$", "");
		String rate = (formatter.format((Double.parseDouble(_rate))));
		String newy = i.YTD(line);
		DecimalFormat formatter1 = new DecimalFormat("$0.00");
		newy = (formatter1.format((Double.parseDouble(newy))));
		return(_name +" (" + _taxID + ") " + _employment + ", " + rate + " YTD:" + newy);
	}
}
