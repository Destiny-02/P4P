package payroll;
public class Employee {
	private int _taxID;
	private String _name;
	private boolean _isSalary;
	private double _rate;
	private double _totalEarnings;
	private String _startDate;
	private String _endDate;
	private float _hours;
	private double _deductions;
	Employee (String userData) {
		String[] userDetails = userData.split("\t");
		_taxID = Integer.parseInt(userDetails[0]);
		_name = userDetails[1];
		_isSalary = checkEmployment(userDetails[2]);
		_rate = removeDollar(userDetails[3]);
		_totalEarnings = removeDollar(userDetails[4]);
		_startDate = userDetails[5];
		_endDate = userDetails[6];
		_hours = Float.parseFloat(userDetails[7]);
		_deductions = removeDollar(userDetails[8]);
	}
	public class Calculations {
		private double _yearlyGross;
		private double _paye;
		Calculations() {
			_yearlyGross = calculateYearlyGross();
			_paye = calculateWeeklyPAYE();
		}
		private double calculateYearlyGross () {
			if (_isSalary) {
				return _rate;
			} else {
				double weeklyRate = calculateWeeklyWageGross();
				return weeklyRate * 52;
			}
		}
		private double calculateWeeklyWageGross () {
			double currentSum = 0;
			float overTimeHours;
			float hoursWorked = _hours;
			while (hoursWorked != 0) {
				if (hoursWorked > 43) {
					overTimeHours = hoursWorked - 43;
					currentSum += overTimeHours * _rate * 2;
					hoursWorked -= overTimeHours;
				} else if (hoursWorked > 40) {
					overTimeHours = hoursWorked - 40;
					currentSum += overTimeHours * _rate * 1.5;
					hoursWorked -= overTimeHours;
				} else {
					currentSum += hoursWorked * _rate;
					hoursWorked = 0;
				}
			}
			return currentSum;
		}
		public double calculateWeeklyPAYE() {
			double requiredTax = 0;
			double remainingGross;
			double yearlyGross = _yearlyGross;
			while (yearlyGross != 0) {
				if (yearlyGross > 70000) {
					remainingGross = yearlyGross - 70000;
					requiredTax += remainingGross * 0.33;
					yearlyGross -= remainingGross;
				} else if (yearlyGross > 48000) {
					remainingGross = yearlyGross - 48000;
					requiredTax += remainingGross * 0.3;
					yearlyGross -= remainingGross;
				} else if (yearlyGross > 14000) {
					remainingGross = yearlyGross - 14000;
					requiredTax += remainingGross * 0.175;
					yearlyGross -= remainingGross;
				} else {
					requiredTax += yearlyGross * 0.105;
					yearlyGross = 0;
				}
			}
			return requiredTax / 52;
		}
		public double calculateWeeklyGross() {
			return _yearlyGross / 52;
		}
		private double calculateNett () {
			double tempNett = (_yearlyGross / 52) - _paye - _deductions;
			return Math.round(tempNett * 100.0) / 100.0;
		}
		private double calculateYTD () {
			double tempYTD = _totalEarnings + (_yearlyGross / 52);
			return Math.round(tempYTD * 100.0) / 100.0;
		}
		public double[] calculatePayslip() {
			double[] calculatedValues = new double[5];
			calculatedValues[0] = calculateWeeklyGross();
			calculatedValues[1] = calculateWeeklyPAYE();
			calculatedValues[2] = _deductions;
			calculatedValues[3] = calculateNett();
			calculatedValues[4] = calculateYTD();
			return calculatedValues;
		}
		public double calculateEmployee() {
			return calculateYTD();
		}
	}
	private boolean checkEmployment (String employment) {
		if (employment.equals("Salaried")) {
			return true;
		} else {
			return false;
		}
	}
	private double removeDollar (String moneyInput) {
		String dollarRemoved = moneyInput.replace("$", "");
		return Double.parseDouble(dollarRemoved);
	}
	public int getTaxID() {
		return _taxID;
	}
	public String getName() {
		return _name;
	}
	public boolean onSalary() {
		return _isSalary;
	}
	public double getRate() {
		return _rate;
	}
	public double getEarningsToDate() {
		return _totalEarnings;
	}
	public String getStartDate() {
		return _startDate;
	}
	public String getEndDate() {
		return _endDate;
	}
	public float getHours() {
		return _hours;
	}
	public double getDeductions() {
		return _deductions;
	}
}