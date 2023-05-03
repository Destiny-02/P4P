package payroll;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
public class Payroll {
	public static void main(String[] args) throws IOException{
		List<String> inputList = new ArrayList<String>();
		String inputString = null;
		Scanner input = new Scanner(new File(args[0]));
		while(input.hasNextLine()) {
			String line = input.nextLine();
			if (line.length() != 0) {
				if (line.charAt(0) != '#') {
					inputList.add(line);
				}
			}
		}
		input.close();
		DatePrinter datePrinter = new DatePrinter();
		inputString = args[1];
		if (inputString.equals("Payslips") ||
				inputString.equals("PAYE")) {
			List<Payslip> payslipList = new ArrayList<Payslip>();
			Payslip anySlip = null;
			for (String employeeInput : inputList) {
				anySlip = new Payslip(employeeInput);
				payslipList.add(anySlip);
			}
			if (inputString.equals("PAYE")) {
				Paye paye = new Paye(payslipList);
				paye.printPaye();
			} else {
				Collections.sort(payslipList);
				for (Payslip employee : payslipList) {
					employee.printPayslip();
				}
			}
		} else if (inputString.equals("Employees") ||
				inputString.equals("Burden")) {
			List<Employees> employeeList = new ArrayList<Employees>();
			Employees anyEmployee = null;
			for (String employeeInput : inputList) {
				anyEmployee = new Employees(employeeInput);
				employeeList.add(anyEmployee);
			}
			if (inputString.equals("Burden")) {
				Burden burden = new Burden(employeeList);
				burden.printBurden();
			} else {
				Collections.sort(employeeList);
				for (Employees employee : employeeList) {
					employee.printEmployees();
				}
			}
			return;
		}
	}
}
