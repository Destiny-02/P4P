package payroll;
public class Employee{
	private String _givenName;
	private String _familyName;
	private int _TID;
	private String _payType;
	private double _rate;
	private double _YTD;
	private String _startDate;
	private String _endDate;
	private double _hours;
	private double _deductions;
	private double _gross;
	private double _PAYE;
	private double _nett;
	private Store _store;
	public Employee(String[] employeeInfo, Store store) {
		_store = store;
		_TID = Integer.parseInt(employeeInfo[0]);
		String[] names = splitNames(employeeInfo[1]);
		_givenName = names[1];
		_familyName = names[0];
		_payType = employeeInfo[2];
		_rate = checkDoubleForNeg(Double.parseDouble(removeMoneySign(employeeInfo[3])));
		_startDate = employeeInfo[5];
		_endDate = employeeInfo[6];
		_hours = Double.parseDouble(employeeInfo[7]);
		_deductions = Double.parseDouble(removeMoneySign(employeeInfo[8]));
		_gross = calculateGross();
		_PAYE = calculatePAYE();
		_nett = calculateNett();
		_YTD = checkDoubleForNeg(Double.parseDouble(removeMoneySign(employeeInfo[4]))) + _gross;
		checkForNegNum();
	}
	public int getTID() {
		return _TID;
	}
	public String getGivenName() {
		return _givenName;
	}
	public String getFamilyName() {
		return _familyName;
	}
	public double getGross() {
		return _gross;
	}
	public double getPAYE() {
		return _PAYE;
	}
	public void displayPayslip() {
		System.out.print(_TID + ". ");
		System.out.print(_givenName + " ");
		System.out.print(_familyName + ", ");
		System.out.print("Period: " + _startDate + " to " + _endDate + ". ");
		System.out.print("Gross: $" + String.format("%1$.2f", _gross) + ", ");
		System.out.print("PAYE: $" + String.format("%1$.2f", _PAYE) + ", ");
		System.out.print("Deductions: $" + String.format("%1$.2f", _deductions) + " ");
		System.out.print("Nett: $" + String.format("%1$.2f", _nett) + " ");
		System.out.print("YTD: $" + String.format("%1$.2f", _YTD) + "\n");
	}
	public void displayEmployee() {
		System.out.print(_familyName + ", " + _givenName);
		System.out.print(" (" + _TID + ") ");
		System.out.print(_payType + ", $" + String.format("%1$.2f", _rate));
		System.out.print(" YTD:$" + String.format("%1$.2f", _YTD) + "\n");
	}
	private double calculateGross() {
		double normalHours = _store.getNormalHours();
		double timeAndAHalf = _store.getTimeAndAHalf();
		double gross;
		double tempHours = 0;
		if (isSalaried(_payType)) {
			gross = _rate / 52;
		} else {
			if (_hours > normalHours && _hours <= 43) {
				tempHours += normalHours;
				tempHours += (_hours - normalHours) * 1.5;
				tempHours = Math.round(tempHours * 4.0) / 4.0;
			} else if (_hours > timeAndAHalf) {
				tempHours += (timeAndAHalf - normalHours) * 1.5;
				tempHours += (_hours - timeAndAHalf) * 2.0;
				tempHours = Math.round(tempHours * 4.0) / 4.0;
			} else {
				tempHours = _hours;
			}
			gross = _rate * tempHours;
		}
		return Math.round(gross * 100.0) / 100.0;
	}
	private double calculatePAYE() {
		double[] taxBrackets = _store.getTaxBrackets();
		double[] taxRate = _store.getTaxRate();
		double PAYE = 0;
		double tempGross;
		if (isSalaried(_payType)) {
			tempGross = _rate;
		} else {
			tempGross = _gross * 52;
		}
		for (int i = 0; i < taxBrackets.length; i++) {
			if (tempGross <= taxBrackets[i]) {
				PAYE += tempGross * taxRate[i];
				PAYE = Math.round(PAYE * 100.0) / 100.0;
				break;
			} else {
				PAYE += taxBrackets[i] * taxRate[i];
				PAYE = Math.round(PAYE * 100.0) / 100.0;
				tempGross -= taxBrackets[i];
			}
			if (i == taxBrackets.length - 1) {
				PAYE += tempGross * taxRate[i + 1];
				PAYE = Math.round(PAYE * 100.0) / 100.0;
			}
		}
		return Math.round(PAYE / 52.0 * 100.0) / 100.0;
	}
	private double calculateNett() {
		double nett;
		nett = _gross - _PAYE - _deductions;
		if (nett < 0) {
			throw new PayrollException(_givenName + " " + _familyName + " has a negative nett of " + nett + ".");
		}
		return nett;
	}
	private String[] splitNames(String names) {
		String[] splitNames = names.split(", ");
		if (splitNames.length != 2) {
			throw new PayrollException("Wrong name format: " + names);
		}
		return splitNames;
	}
	private String removeMoneySign(String money) {
		String returnMoney = "";
		if (money.charAt(0) == '$') {
			returnMoney = money.substring(1);
		} else {
			throw new PayrollException("Tried to remove '$' from something that doesn't have '$' at the start. Possibly wrong formatting: " + money);
		}
		return returnMoney;
	}
	private boolean isSalaried(String payType) {
		boolean isSalaried;
		if (payType.toLowerCase().equals("salaried")) {
			isSalaried = true;
		} else {
			isSalaried = false;
		}
		if (!isSalaried && !payType.toLowerCase().equals("hourly")) {
			throw new PayrollException("Employee Pay Type not recognized (either Salaried or Hourly): " + _payType);
		}
		return isSalaried;
	}
	private void checkForNegNum() {
		if (_rate < 0 || _YTD < 0 || _hours < 0 || _deductions < 0 || _gross < 0 || _PAYE < 0 || _nett < 0 || _TID < 0) {
			throw new PayrollException("One of " + _givenName + " " + _familyName + "'s fields is negative.");
		}
	}
	private double checkDoubleForNeg(double number) {
		if (number < 0) {
			throw new PayrollException("This number shouldn't be negative: " + number + ", for this employee " + _givenName + " " + _familyName);
		}
		return number;
	}
}
