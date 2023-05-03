package payroll;
import java.text.DecimalFormat;
public class Income {
	private String _employment;
	private double _rate;
	private double _hours;
	public Income(String line){
		String[] parts = line.split("\\t");
		_employment = parts[2];
		parts[3] = parts[3].replace("$", "");
		_rate = Double.parseDouble(parts[3]);
		_hours=Double.parseDouble(parts[7]);
	}
	public Income(String employment,double rate,double hours){
		_employment = employment;
		_rate = rate;
		_hours = hours;
	}
	public double GrossPay(double rate,double hours){
		if(_employment.equals("Salaried")){
			double week = _rate/52;
			DecimalFormat df = new DecimalFormat("0.00");
			week = Double.valueOf(df.format(week));
			return week;
		}else{
			double week;
			if(_hours <=40.00 ){
				week = _hours * _rate;
			}else if(_hours<=43){
				week = 40*_rate + (_hours-40)*_rate*1.5;
			}else{
				week =	_rate*40 + 3*_rate*1.5 + ((_hours-43)*_rate*2);
			}
			DecimalFormat df = new DecimalFormat("0.00");
			week = Double.valueOf(df.format(week));
			return week;
		}
	}
	public String YTD(String line){
		String[] _strLine = line.split("\\t");
		_strLine[3] = _strLine[3].replace("$", "");
		_strLine[4] = _strLine[4].replace("$", "");
		double beforeYTD = Double.parseDouble(_strLine[4]);
		double newYTD;
		if(_strLine[2].equals("Salaried")){
			Income i = new Income(line);
			double weekgross =	i.GrossPay(_rate,_hours);
			newYTD = beforeYTD + weekgross;
		}else{
			Income i = new Income(line);
			double weekgross =	i.GrossPay(_rate,_hours);
			newYTD = beforeYTD + weekgross;
		}
		String newY = Double.toString(newYTD);
		return newY;
	}
}
