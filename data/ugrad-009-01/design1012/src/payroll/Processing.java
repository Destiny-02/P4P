package payroll;
import java.util.ArrayList;
public class Processing {
	static void employeesProcessing(ArrayList<Employee> employeeList){
		Date datePrinter = new Date();
		datePrinter.printDate();
		Rounder twoDecimalPlacesRounder = new Rounder();
		for(int i = 0; i < employeeList.size();i++){
			Employee employee = employeeList.get(i);
			EmployeeType employeeType = employee.getEmployeeType();
			String[] employeeName = employee.getGivenNames(false);
			String taxID = employee.getTaxID();
			double rate = employee.getRate();
			Earnings YTD_Updater = new Earnings();
			try{
				employee.updateYTD(employee.getYTD() + YTD_Updater.Gross(employeeList.get(i), false));
			} catch(EarningsErrorException EEExc){
			}
			double YTD = employee.getYTD();
			YTD = twoDecimalPlacesRounder.roundToTwoDecimalPlaces(YTD);
			System.out.print("" + employeeName[0] + ", " + employeeName[1]);
			System.out.print(" (" + taxID + ")");
			if(employeeType == EmployeeType.HOURLY){
				System.out.print(" Hourly, ");
			}
			if(employeeType == EmployeeType.SALARY){
				System.out.print(" Salaried, ");
			}
			System.out.print(String.format( "$%.2f", rate ));
			System.out.print(" YTD:$");
			System.out.print(String.format( "%.2f", YTD ) + "\n");
		}
	}
	static void payslipsProcessing(ArrayList<Employee> employeeList){
		Tax tax = new Tax();
		Earnings earnings = new Earnings();
		Date datePrinter = new Date();
		datePrinter.printDate();
		for(int i = 0 ; i < employeeList.size(); i++){
			Employee employee = employeeList.get(i);
			try{
				double gross = earnings.Gross(employee, false);
				double paye = tax.PAYE(employee, tax.calculateTaxBracket(employee));
				double nett = earnings.Nett(employee);
				double deductions = employee.getDeductions();
				double YTD = employee.getYTD() + gross;
				String taxID = employee.getTaxID();
				String[] givenNames = employee.getGivenNames(true);
				System.out.print(taxID + ". ");
				System.out.print("" + givenNames[0] + " " + givenNames[1] + ", Period: ");
				datePrinter.startAndEndDate(employee);
				System.out.print(". Gross: $" + String.format("%.2f", gross) + ", PAYE: $" + String.format("%.2f", paye) + ", Deductions: $" + String.format("%.2f", deductions));
				System.out.print(" Nett: $" + String.format("%.2f", nett) + " YTD: $" + String.format("%.2f", YTD));
				System.out.println("");
			} catch(EarningsErrorException EEExc){
			}
		}
	}
	static void burdenProcessing(ArrayList<Employee> employeeList){
		Date datePrinter = new Date();
		datePrinter.printDate();
		double totalBurden = 0;
		try{
			if(employeeList.size() > 0){
				datePrinter.startAndEndDate(employeeList.get(0));
			}
			Earnings earningCalculator = new Earnings();
			Rounder twoDecimalPlacesRounder = new Rounder();
			for(int i = 0; i < employeeList.size(); i++){
				totalBurden = totalBurden + earningCalculator.Gross(employeeList.get(i), false);
				totalBurden = twoDecimalPlacesRounder.roundToTwoDecimalPlaces(totalBurden);
			}
		} catch(EarningsErrorException EEExc){
		}
		System.out.println("\nTotal Burden: $" + String.format("%.2f", totalBurden));
	}
	static void PAYEProcessing(ArrayList<Employee> employeeList){
		Date datePrinter = new Date();
		datePrinter.printDate();
		if(employeeList.size() > 0){
			datePrinter.startAndEndDate(employeeList.get(0));
		}
		Tax payeCalculator = new Tax();
		double PAYE = 0;
		for(int i = 0; i < employeeList.size(); i++){
			double taxBracket = payeCalculator.calculateTaxBracket(employeeList.get(i));
			PAYE = PAYE + payeCalculator.PAYE(employeeList.get(i), taxBracket);
		}
		System.out.println("\nTotal PAYE: $" + String.format("%.2f", PAYE));
	}
}
