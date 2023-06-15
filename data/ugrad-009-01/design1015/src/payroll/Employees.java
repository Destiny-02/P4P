package payroll;
public final class Employees extends Operations {
	private String _name;
	private int _taxID;
	private String _wageType;
	private float _rate;
	private float _YTD;
	public Employees(String name, int taxID, String wageType, float rate, float YTD){
			_name = name;
			_taxID = taxID;
			_wageType = wageType;
			_rate = rate;
			_YTD = YTD;
	}
	public void printOutput(){
		System.out.println(_name + " (" + _taxID + ") " + _wageType + ", $" + roundToTwoDec(_rate) + " YTD:$" + roundToTwoDec(_YTD));
	}
}
