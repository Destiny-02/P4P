package payroll;
import java.util.List;
public class EmployeeDetails {
    private String _tid;
    private String _name;
    private String _employment;
    private String _rate;
    private String _ytd;
    private String _start;
    private String _end;
    private String _hours;
    private String _deduction;
    private String _gross;
    private String _paye;
    private String _nett;
    public EmployeeDetails(List<String> information){
    	_tid = information.get(0);
    	_name = information.get(1);
    	_employment = information.get(2);
    	_rate = information.get(3);
    	_ytd = information.get(4);
    	_start = information.get(5);
    	_end = information.get(6);
    	_hours = information.get(7);
    	_deduction = information.get(8);
    }
    public String calculateGross ()  throws NumberFormatException{
    	double rate = Double.parseDouble(removeSign(_rate));
    	double hours = Double.parseDouble(_hours);
    	double gross = 0;
    	if (_employment.equals("Salaried")){
    		gross = round(rate / 52);
    	} else if (_employment.equals("Hourly")){
    		if ((0 <= hours)&&(hours <= 40)){
    			gross = round(hours * rate);
    		}else if ((40 < hours)&&(hours <= 43)){
    			gross = round(40 * rate) + round((hours-40) * rate * 1.5);
    		}else if ((hours > 43)){
    			gross = round(40 * rate) + round(3 * rate * 1.5)+ round((hours-43) * rate * 2);
    		}
    	}
    	_gross = String.format("%.2f", gross);
    	_gross = ("$"+_gross);
    	return (_gross);
    }
    public String calculatePaye() throws NumberFormatException{
    	double gross = Double.parseDouble(removeSign(calculateGross()));
    	double annualGross = (gross * 52);
    	double paye = 0;
    	if (annualGross > 70000){
    		paye = round(14000 * 0.105) + round (34000 * 0.175) + round (22000 * 0.3)+ round ((annualGross - 70000)* 0.33);
    	} else if ((annualGross > 48000)&&(annualGross <= 70000)){
    		paye = round(14000 * 0.105) + round (34000 * 0.175) + round ((annualGross - 48000) * 0.3);
    	} else if ((annualGross > 14000) && (annualGross <= 48000)){
    		paye = round(14000 * 0.105) + round ((annualGross - 14000) * 0.175);
    	} else if ((annualGross >= 0)&&(annualGross <= 14000)){
    		paye = round(annualGross * 0.105);
    	}
    	paye = round(paye / 52);
    	_paye = String.format("%.2f", paye);
    	_paye= ("$"+_paye);
    	return _paye;
    }
    private String calculateNett()  throws NumberFormatException{
    	double gross = Double.parseDouble(removeSign(calculateGross()));
    	double paye = Double.parseDouble(removeSign(calculatePaye()));
    	double deduction = Double.parseDouble(removeSign(_deduction));
    	double nett = gross - paye - deduction;
    	_nett = String.format("%.2f", nett);
    	_nett = ("$"+_nett);
    	return _nett;
    }
    private String calculateYtd()  throws NumberFormatException{
    	double gross = Double.parseDouble(removeSign(calculateGross()));
    	double ytd = Double.parseDouble(removeSign(_ytd));
    	double newYtd = ytd + gross;
    	_ytd= String.format("%.2f", newYtd);
    	_ytd = ("$"+_ytd);
    	return _ytd;
    }
    public void printPayslipsRecords(){
		String[] name = _name.split(",");
		System.out.print(_tid+". ");
		System.out.print(name[1].substring(1)+" "+name[0]);
		System.out.print(", Period: "+_start +" to "+_end);
		System.out.print(". Gross: "+calculateGross());
		System.out.print(", PAYE: "+calculatePaye());
		System.out.print(", Deductions: "+ toStandardFormat(_deduction));
		System.out.print(" Nett: "+calculateNett());
		System.out.print(" YTD: "+calculateYtd());
		System.out.println();
    }
    public void printEmployeesRecords(){
		System.out.print(_name);
		System.out.print(" ("+_tid +") ");
		System.out.print(_employment +", ");
		System.out.print(toStandardFormat(_rate) + " ");
		System.out.print("YTD:"+calculateYtd());
		System.out.println();
    }
    private String toStandardFormat(String employeeDetail)  throws NumberFormatException{
    	double detail = Double.parseDouble(removeSign(employeeDetail));
    	employeeDetail = String.format("%.2f", detail);
    	employeeDetail = ("$"+ employeeDetail);
    	return employeeDetail;
    }
    private double round(double numToRound){
    	double roundedNum;
    	roundedNum = (Math.round(numToRound * 100))/ 100.00;
		return roundedNum;
    }
    private String removeSign(String string){
    	if (!(string.charAt(0) == '$' )){
    		System.out.println(" ERROR! All money amounts need to begin with '$'! The following calculations may be wrong because a money amount not starting with '$' is detected!");
    	}
    	return string.substring(1);
    }
}
