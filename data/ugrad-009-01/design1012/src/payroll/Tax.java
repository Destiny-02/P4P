package payroll;
public class Tax {
	double calculateTaxBracket(Employee employee){
		double yearsEarnings;
		double rate = employee.getRate();
		double hoursWorked = employee.getHoursWorked();
		EmployeeType employeeType = employee.getEmployeeType();
		if(employeeType == EmployeeType.SALARY){
			yearsEarnings = rate;
		} else {
			yearsEarnings = rate * hoursWorked * 52;
		}
		if(yearsEarnings <= 14000){
			return 0.105;
		}
		if(yearsEarnings <= 48000){
			return 0.175;
		}
		if(yearsEarnings <= 70000){
			return 0.30;
		}
		return 0.33;
	}
	double PAYE(Employee employee, double taxBracket){
		Earnings gross = new Earnings();
		double employeePAYE = 0;
		try{
			double grossPay = gross.Gross(employee, true);
			if(taxBracket == 0.105){
				employeePAYE = grossPay * taxBracket;
			}
			if(taxBracket == 0.175){
				employeePAYE = 1470 + (grossPay - 14000) * taxBracket;
			}
			if(taxBracket == 0.30){
				employeePAYE = 7420 + (grossPay - 48000) * taxBracket;
			}
			if(taxBracket == 0.33){
				employeePAYE = 14020 + (grossPay - 70000) * taxBracket;
			}
			employeePAYE = employeePAYE/52;
			Rounder twoDecimalPlacesRounder = new Rounder();
			employeePAYE = twoDecimalPlacesRounder.roundToTwoDecimalPlaces(employeePAYE);
		} catch(EarningsErrorException EEExc){
		}
		return employeePAYE;
	}
}
