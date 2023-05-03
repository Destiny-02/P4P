package payroll;
public abstract class Employee {
	private int _TID;
	private String _name;
	private String _employmentType;
	private double _rate;
	private double _YTD;
	private String _start;
	private String _end;
	private double _hours;
	private double _deduction;
	public Employee (String[] processedInput){
		_TID = Integer.parseInt(processedInput[0]);
		_name = processedInput [1];
		_employmentType = processedInput[2];
		_rate = Double.parseDouble(processedInput[3]);
		_YTD = Double.parseDouble(processedInput[4]);
		_start= processedInput [5];
		_end = processedInput [6];
		_hours = Double.parseDouble(processedInput[7]);
		_deduction = Double.parseDouble(processedInput[8]);
	}
	public int getTID(){
		return _TID;
	}
	public String getName(){
		return _name;
	}
	public String getEmploymentType(){
		return _employmentType;
	}
	public double getRate(){
		return _rate;
	}
	public double getYTD(){
		return _YTD;
	}
	public String getStart(){
		return _start;
	}
	public String getEnd(){
		return _end;
	}
	public double getHours(){
		return _hours;
	}
	public double getDeduction(){
		return _deduction;
	}
	abstract public double calcGross();
	abstract public double calcYearGross();
	public double calcPAYE(double yearlyGross){
		double remaining = yearlyGross;
		double PAYE  = 0;
		while (true){
			if (yearlyGross<=14000){
				PAYE = round(yearlyGross*0.105);
				break;
			}
			else {
				PAYE = round(14000*0.105);
				remaining -= 14000;
			}
			if (yearlyGross<=48000 && yearlyGross>14000){
				PAYE += round(remaining*0.175);
				break;
			}
			else {
				PAYE += round((48000-14000)*0.175);
				remaining -= (48000-14000);
			}
			if (yearlyGross<=70000 && yearlyGross>48000){
				PAYE += round(remaining*0.30);
				break;
			}
			else {
				PAYE +=  round((70000-48000)*0.30);
				remaining -= (70000-48000);
			}
			if (yearlyGross > 70000){
				PAYE += round(remaining * 0.33);
				break;
			}
		}
		return round(PAYE/52);
	}
	protected double round(double value){
		return Math.round(value*100.0)/100.0;
	}
	public String getFullName(){
		String s= getName();
		String[] array = s.split(",");
		return array[1].trim() + " " + array[0];
	}
	public String getPeriod(){
		String start = getStart();
		String end = getEnd();
		return (start + " to " + end);
	}
}
