package payroll;
import java.util.ArrayList;
public class ProcessingPayroll {
	protected  ArrayList<Integer> _tID = new ArrayList<Integer>();
	protected ArrayList<String> _familyName = new ArrayList<String>();
	protected  ArrayList<String> _givenName = new ArrayList<String>();
	protected ArrayList<String> _employment = new ArrayList<String>();
	protected ArrayList<Double> _rate = new ArrayList<Double>();
	protected  ArrayList<Double> _yTD = new ArrayList<Double>();
	protected ArrayList<String> _startDate = new ArrayList<String>();
	protected  ArrayList<String> _endDate = new ArrayList<String>();
	protected ArrayList<Double> _hours = new ArrayList<Double>();
	protected ArrayList<Double> _deductions= new ArrayList<Double>();
	public ProcessingPayroll(ArrayList<Integer> tID , ArrayList<String> familyName,ArrayList<String> givenName,ArrayList<String> employment,ArrayList<Double> rate,ArrayList<Double> yTD,ArrayList<String> startDate,ArrayList<String> endDate,ArrayList<Double> hours,  ArrayList<Double> deductions){
		_tID=tID;
		_familyName= familyName;
		_givenName= givenName;
		_employment=employment;
		_rate=rate;
		_yTD=yTD;
		_startDate=startDate;
		_endDate=endDate;
		_hours=hours;
		_deductions=deductions;
	}
	ArrayList<Integer> gettID(){
		return _tID;
	}
	ArrayList<String> getfamilyName(){
		return _familyName;
	}
	ArrayList<String> getgivenName(){
		return _givenName;
	}
	ArrayList<String> getemployment(){
		return _employment;
	}
	ArrayList<Double> getrate(){
		return _rate;
	}
	ArrayList<Double> getyTD(){
		return _yTD;
	}
	ArrayList<String> getstartDate(){
		return _startDate;
	}
	ArrayList<String> getendDate(){
		return _endDate;
	}
	ArrayList<Double> gethours(){
		return _hours;
	}
	ArrayList<Double> getdeductions(){
		return _deductions;
	}
}
