package payroll;
public class EmployeeData {
	private final Integer _tid;
	private final String _firstName;
	private final String _lastName;
	private final String _employType;
	private final Double _rate;
	private final Double _ytd;
	private final String _startDate;
	private final String _endDate;
	private final Double _hours;
	private final Double _deduction;
	private Double _grossAmount;
	private Double _taxAmount;
	EmployeeData(String infoLine){
		String[] infoSplit = infoLine.split("\t");
		if (infoSplit.length != 9){
			throw new RuntimeException();
		}
		String[] nameSplit = infoSplit[1].split(",");
		if (nameSplit.length != 2){
			throw new NumberFormatException();
		}
		_tid = Integer.parseInt(infoSplit[0]);
		_firstName = nameSplit[1].trim();
		_lastName = nameSplit[0].trim();
		_employType = infoSplit[2];
		_rate = Double.parseDouble(infoSplit[3].replaceAll("[$]",""));
		_ytd = Double.parseDouble(infoSplit[4].replaceAll("[$]",""));
		_startDate = infoSplit[5];
		_endDate = infoSplit[6];
		_hours = Double.parseDouble(infoSplit[7]);
		_deduction = Double.parseDouble(infoSplit[8].replaceAll("[$]",""));
		calculateGross();
		calculateTax();
		checkForErrors();
	}
	private void calculateGross(){
		Double grossAmount = 0.0;
		if ((_employType.toLowerCase()).equals("salaried")) {
			grossAmount = (_rate/52.0);
		} else if ((_employType.toLowerCase()).equals("hourly")){
			if (_hours <= 40) {
				grossAmount = (_rate*_hours);
			} else if (_hours <= 43){
				grossAmount = (_rate*40.0) + (_rate*1.5*(_hours-40.0));
			} else {
				grossAmount = (_rate*40.0) + (_rate*1.5*3.0) + (_rate*2.0*(_hours-43.0));
			}
		}
		_grossAmount =  (double)Math.round(grossAmount*100.0)/100.0;
	}
	private void calculateTax(){
		Double annualTax;
		Double annualGross = _grossAmount*52.0;
		if (annualGross <= 14000){
			annualTax = 0.105*annualGross;
		} else if (annualGross <= 48000) {
			annualTax = 0.105*14000 + 0.175*(annualGross-14000);
		} else if (annualGross <= 70000) {
			annualTax = 0.105*14000 + 0.175*34000 + 0.3*(annualGross-48000);
		} else {
			annualTax = 0.105*14000 + 0.175*34000 + 0.3*22000 + 0.33*(annualGross-70000);
		}
		_taxAmount = (double)Math.round(annualTax/52.0*100.0)/100.0;
	}
	private void checkForErrors(){
		if (!(_employType.toLowerCase().equals("salaried")) && (!(_employType.toLowerCase().equals("hourly")))){
			throw new NumberFormatException();
		}
		if (!_startDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
			throw new NumberFormatException();
		}
		if ((_rate < 0) || (_hours < 0) || (_tid < 0) || (_grossAmount < _deduction)){
			throw new NumberFormatException();
		}
		String[] startDateSplit = _startDate.split("-");
		String[] endDateSplit = _endDate.split("-");
		if (Integer.parseInt(endDateSplit[0]) < Integer.parseInt(startDateSplit[0])) {
			throw new NumberFormatException();
		} else if (Integer.parseInt(endDateSplit[1]) < Integer.parseInt(startDateSplit[1])) {
			throw new NumberFormatException();
		} else if (Integer.parseInt(endDateSplit[2]) < Integer.parseInt(startDateSplit[2])) {
			throw new NumberFormatException();
		}
	}
	public String payslipFormat(){
		String stringOutput;
		stringOutput = String.format("%d. %s %s, Period: %s to %s. Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f Nett: $%.2f YTD: $%.2f",
		_tid, _firstName, _lastName, _startDate, _endDate, _grossAmount, _taxAmount, _deduction, (_grossAmount-_taxAmount - _deduction), (_ytd + _grossAmount));
		return stringOutput;
	}
	public String employeesFormat(){
		String stringOutput;
		stringOutput = String.format("%s, %s (%d) %s, $%.2f YTD:$%.2f", _lastName, _firstName, _tid, _employType, _rate, _ytd + _grossAmount);
		return stringOutput;
	}
	public String orderFormat(){
		String stringOutput = _lastName + _firstName + _tid;
		return stringOutput;
	}
	public String dateFormat(){
		String stringOutput = _startDate + " to " + _endDate;
	    return stringOutput;
	}
	public Integer getTID() {
		return _tid;
	}
	public Double getTaxAmount() {
		return _taxAmount;
	}
	public Double getGrossAmount() {
		return _grossAmount;
	}
}

