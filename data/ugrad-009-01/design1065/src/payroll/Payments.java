package payroll;
import java.text.DecimalFormat;
public class Payments {
	private String _employment;
	private double _rate;
	private double _hours;
	public	Payments(String line){
		String[] parts = line.split("\\t");
		_employment = parts[2];
		parts[3] = parts[3].replace("$", "");
		_rate = Double.parseDouble(parts[3]);
		_hours=Double.parseDouble(parts[7]);
	}
	public double PAYEtax(){
		if(_employment.equals("Salaried")){
			double annualTax;
			double weektax;
			if(_rate<=14000){
				annualTax = 14000*0.105;
			}else if (_rate<=48000){
				annualTax = (14000*0.105) + ((_rate-14000)*0.175);
			}else if (_rate<=70000){
				annualTax = ((14000*0.105) + (34000*0.175) + (_rate-48000)*0.30);
			}else {
				annualTax = ((14000*0.105) + (34000*0.175) + (22000*0.30) + (_rate-70000)*0.33);
			}
			weektax = annualTax/52;
			DecimalFormat df = new DecimalFormat("#.##");
			weektax = Double.valueOf(df.format(weektax));
			return weektax;
		}else{
			Income i = new Income(_employment,_rate,_hours);
			double weekpay =i.GrossPay(_rate,_hours);
			double annualpay = weekpay*52;
			double annualTax;
			double weektax;
			if(annualpay<=14000){
				annualTax = 14000*0.105;
			}else if (annualpay<=48000){
				annualTax = (14000*0.105) + ((annualpay-14000)*0.175);
			}else if (annualpay<=70000){
				annualTax = ((14000*0.105) + (34000*0.175) + (annualpay-48000)*0.30);
			}else {
				annualTax = ((14000*0.105) + (34000*0.175) + (22000*0.30) + (annualpay-70000)*0.33);
			}
			weektax = annualTax/52;
			DecimalFormat df = new DecimalFormat("#.##");
			weektax = Double.valueOf(df.format(weektax));
			return weektax;
		}
	}
}
