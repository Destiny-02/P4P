package payroll;
public class Earnings {
	double Gross(Employee employee, Boolean year) throws EarningsErrorException{
		double rate = employee.getRate();
		double hoursWorked = employee.getHoursWorked();
		EmployeeType employeeType = employee.getEmployeeType();
		Rounder twoDecimalPlacesRounder = new Rounder();
		if(employeeType == EmployeeType.HOURLY){
			if(hoursWorked <= 40){
			rate =  rate * hoursWorked * 52;
			} else if(hoursWorked <= 43){
				double gross = rate * 40 + rate * 1.5 * (hoursWorked - 40);
				rate =  gross * 52;
			} else {
				double gross = rate * 40 + rate * 4.5 + rate * 2 * (hoursWorked - 43);
				rate =  gross * 52;
			}
		}
		if(rate < 0){
			throw new EarningsErrorException(employee.getTaxID());
		}
		if(employeeType == EmployeeType.HOURLY && year){
			rate = twoDecimalPlacesRounder.roundToTwoDecimalPlaces(rate);
			return rate;
		} else if (employeeType == EmployeeType.HOURLY && !year){
			rate = rate/52;
			rate = twoDecimalPlacesRounder.roundToTwoDecimalPlaces(rate);
			return rate;
		}
		if(rate < 0){
			throw new EarningsErrorException(employee.getTaxID());
		}
		rate = twoDecimalPlacesRounder.roundToTwoDecimalPlaces(rate);
		if(year){
		return rate;
		} else{
			rate = rate/52;
			rate = twoDecimalPlacesRounder.roundToTwoDecimalPlaces(rate);
			return rate;
		}
	}
	double Nett(Employee employee) throws EarningsErrorException{
		Earnings grossCalculator = new Earnings();
		Tax payeCalculator = new Tax();
		double gross = grossCalculator.Gross(employee, false);
		double paye = payeCalculator.PAYE(employee, payeCalculator.calculateTaxBracket(employee));
		double deductions = employee.getDeductions();
		double nett = gross - deductions - paye;
		if(nett < 0){
			throw new EarningsErrorException(employee.getTaxID());
		}
		return nett;
	}
}
