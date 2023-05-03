package payroll;
import java.text.DecimalFormat;
import java.util.ArrayList;
public class Processor {
	public void calculate(ArrayList<Employee> empList, String process) {
		if (process.equals("Payslips")) {
			payslipProcess(empList);
		}
		else if (process.equals("Employees")) {
			employeesProcess(empList);
		}
		else if (process.equals("Burden")) {
			burdenProcess(empList);
		}
		else if (process.equals("PAYE")) {
			payeProcess(empList);
		}
		else {
			System.out.println("Not a valid process");
		}
	}
	private void payeProcess(ArrayList<Employee> empList) {
		Calculation calculate = new Calculation();
		Display disp = new Display();
		double totalPAYE = 0.0;
		for (Employee employee : empList) {
			double gross = calculate.checkEmployment(employee, calculate);
			double PAYE = calculate.CalcTax(employee, gross);
			totalPAYE = totalPAYE + PAYE;
		}
		DecimalFormat dec = new DecimalFormat("#.00");
		String roundedPAYE = dec.format(totalPAYE);
		disp.PAYE(empList.get(0), roundedPAYE);
	}
	private void burdenProcess(ArrayList<Employee> empList) {
		Calculation calculate = new Calculation();
		Display disp = new Display();
		double totalGross = 0.0;
		for (Employee employee : empList) {
			double gross = calculate.checkEmployment(employee, calculate);
			totalGross = totalGross + gross;
		}
		DecimalFormat dec = new DecimalFormat("#.00");
		String roundedGross = dec.format(totalGross);
		disp.Burden(empList.get(0), roundedGross);
	}
	private void employeesProcess(ArrayList<Employee> empList) {
		Calculation calculate = new Calculation();
		Sorting sort = new Sorting();
		Display disp = new Display();
		sort.compareName(empList, 1);
		for (Employee employee : empList) {
			double gross = calculate.checkEmployment(employee, calculate);
			double YTD = calculate.CalcYTD(employee, gross);
			DecimalFormat dec = new DecimalFormat("#.00");
			String roundedYTD = dec.format(YTD);
			disp.Employees(employee, roundedYTD);
		}
	}
	private void payslipProcess(ArrayList<Employee> empList) {
		Calculation calculate = new Calculation();
		Sorting sort = new Sorting();
		Display disp = new Display();
		sort.compareTaxID(empList, 0);
		for (Employee employee : empList) {
			double gross = calculate.checkEmployment(employee, calculate);
			double PAYE = calculate.CalcTax(employee, gross);
			double Nett = calculate.CalcNett(employee, gross, PAYE);
			double YTD = calculate.CalcYTD(employee, gross);
			DecimalFormat dec = new DecimalFormat("#.00");
			String roundedGross = dec.format(gross);
			String roundedPAYE = dec.format(PAYE);
			String roundedNett = dec.format(Nett);
			String roundedYTD = dec.format(YTD);
			disp.payslips(employee, roundedGross, roundedPAYE, roundedNett, roundedYTD);
		}
	}
}
