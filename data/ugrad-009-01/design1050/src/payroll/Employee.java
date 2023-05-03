package payroll;
import java.lang.Integer;
import java.lang.Float;
import java.math.BigDecimal;
public class Employee {
	private int _TID;
	private String _givenName;
	private String _familyName;
	private boolean _salaried;
	private double _rate;
	private double _YTD;
	private double _hoursWorked;
	private double _deductions;
	private String _startDate;
	private String _endDate;
	private double _gross;
	private double _yearlyGross;
	private double _PAYE;
	private double _nett;
	public Employee(String information, int index){
		information = information.replace("$", "");
		String location = null;
		String[] thisInformation = (information.split("\\t+|,\\s+"));
		try{
			location = "TID";
			_TID = Integer.parseInt(thisInformation[0]);
			location = "family Name";
			_familyName = thisInformation[1];
			location = "given Name";
			_givenName = thisInformation[2];
			location = "salary Declaration";
			_salaried = (thisInformation[3].equals("Salaried") ? true : false);
			location = "rate";
			_rate = Float.parseFloat(thisInformation[4]);
			location = "Year to Date";
			_YTD = Float.parseFloat(thisInformation[5]);
			location = "start Date";
			_startDate = thisInformation[6];
			location = "End Date";
			_endDate = thisInformation[7];
			location = "Hours worked";
			_hoursWorked = Float.parseFloat(thisInformation[8]);
			location = "deductions";
			_deductions = Float.parseFloat(thisInformation[9]);
			if(_rate < 0 | _YTD < 0| _hoursWorked < 0 | _deductions < 0){
				System.out.println("Warning: Employee declaration for " + _givenName + " " + _familyName + " [" + _TID + "] contains negative numbers");
			}
		} catch (IndexOutOfBoundsException ioobex){
			System.out.println("Error: line of information #" + index + "\n\""
					+ information + "\" Not formatted correctly"
					+ "Error with " + location
					+ "\n Please input in format:\n"
					+ "TID	Name(familyName, givenName)	Employment	Rate	YTD	Start	End	Hours	Deduction\n");
		} catch (NumberFormatException nfx){
			System.out.println("Error: Please input numbers using digits for line #"
					+ index + ", in the format: \n"
					+ "TID	Name(familyName, givenName)	Employment	Rate	YTD	Start	End	Hours	Deduction\n"
					+ "Error with " + location);
		}
	}
		public void calculateGross(){
			double moneyEarnt;
			double rate = _rate;
			double hoursWorked = _hoursWorked;
			if(_salaried){
				moneyEarnt = (rate / 52);
			} else {
				if (hoursWorked <= 40){
				moneyEarnt = (hoursWorked * rate);
				} else {
					moneyEarnt = 40 * rate;
					hoursWorked -= 40;
					if (hoursWorked < 3){
						moneyEarnt += hoursWorked * rate * 1.5;
					} else {
						moneyEarnt += rate * 4.5;
						hoursWorked -= 3;
						moneyEarnt += (hoursWorked * rate * 2);
					}
				}
			}
			_gross = roundTo2DP(moneyEarnt);
		}
		public void calculateYearlyGross(){
			if (_salaried){
				_yearlyGross = _rate;
			} else {
				_yearlyGross = (_gross * 52);
			}
		}
		public void calculatePAYE(){
			double PAYE = 0;
			double taxRate = 0.105;
			double yearlyGross = _yearlyGross;
			if (yearlyGross < 14000.00){
			} else {
				yearlyGross -= 14000.00;
				PAYE += 1470;
				if (yearlyGross < 34000.00){
					taxRate = 0.175;
				} else {
					yearlyGross -= 34000.00;
					PAYE += 5950;
					if (yearlyGross < 22000.00){
						taxRate = 0.3;
					} else {
						yearlyGross -= 22000.00;
						PAYE += 6600;
						taxRate = 0.33;
					}
				}
			}
			_PAYE = roundTo2DP((PAYE + (yearlyGross * taxRate))/52);
		}
		public void calculateNett(){
			double nett = _gross - (_PAYE + _deductions);
			if(nett < 0){
				System.out.println("Warning: Employee "
						+ _givenName + " " + _familyName
						+ " did not earn enough to cover their deductions this week");
			}
			_nett = nett;
		}
		public void calculateYTD(){
			_YTD += _gross;
		}
		public String justWantDate(){
			return _startDate + " to " + _endDate;
		}
		private double roundTo2DP(double PAYE){
			BigDecimal result = new BigDecimal(String.valueOf(PAYE)).setScale(2, BigDecimal.ROUND_HALF_UP);
			return result.doubleValue();
		}
		public int getTID(){
			int TID = _TID;
			return TID;
		}
		public String getGivenName(){
			String givenName = _givenName;
			return givenName;
		}
		public String getFamilyName(){
			String familyName = _familyName;
			return familyName;
		}
		public String getGross(){
			return String.valueOf(_gross);
		}
		public String getPAYE(){
			return String.valueOf(_PAYE);
		}
		public String forEmployeesCommand(){
			String salary = new String();
			if (_salaried){
				salary = "Salaried,";
			} else {
				salary = "Hourly,";
			}
			return String.format(_familyName + ", " + _givenName
					+ " (" + _TID + ") " + salary + " $%.2f YTD:$%.2f",
					_rate, _YTD);
		}
		public String forPayslipsCommand(){
			return String.format(_TID + ". " + _givenName + " " + _familyName
					+ ", Period: " + _startDate + " to " + _endDate
					+ ". Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f Nett: $%.2f YTD: $%.2f",
					_gross, _PAYE, _deductions, _nett, _YTD);
		}
}