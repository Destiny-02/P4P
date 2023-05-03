package payroll;
import java.util.Collections;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class EmployeeList {
	private ArrayList<Employee> _employeeList;
	public EmployeeList() {
		_employeeList = new ArrayList<Employee>();
	}
	public void createEmployeeList(String args) {
		try {
			Scanner employeeInfo = new Scanner(new FileReader(args));
			int taxID;
			double rate, ytd, hours, deductions, gross, incomeTax, nett;
			while (employeeInfo.hasNextLine()) {
				String line = employeeInfo.nextLine();
				if(!(line.startsWith("#")||(line.isEmpty()))) {
					String[] split = line.split(("\t|, "));
					taxID = Integer.parseInt(split[0]);
					split[4] = split[4].replace("$","");
					rate = Double.parseDouble(split[4]);
					split[5] = split[5].replace("$","");
					ytd = Double.parseDouble(split[5]);
					hours = Double.parseDouble(split[8]);
					split[9] = split[9].replace("$","");
					deductions = Double.parseDouble(split[9]);
					if (split[3].equals("Salaried")) {
						SalaryCalculations salary = new SalaryCalculations();
						gross = salary.grossAmount(hours, rate);
						incomeTax = salary.incomeTax(gross, rate);
						nett = salary.nettDeductions(gross, deductions, incomeTax);
						ytd = salary.ytdCalculation(gross, ytd);
					} else {
						HourlyCalculations hourly = new HourlyCalculations();
						gross = hourly.grossAmount(hours, rate);
						incomeTax = hourly.incomeTax(gross, rate);
						nett = hourly.nettDeductions(gross, deductions, incomeTax);
						ytd = hourly.ytdCalculation(gross, ytd);
					}
					Employee employee = new Employee(nett, incomeTax, gross, taxID, split[2], split[1], split[3], rate, ytd, split[6], split[7], hours, deductions);
					_employeeList.add(employee);
				}
			}
			employeeInfo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void calculatePayslipsProcessing (){
		currentDate();
		taxIDSort();
		for (int i =0; i < _employeeList.size(); i++) {
			Employee employ = _employeeList.get(i);
			employ.payslipsProcessing();
		}
	}
	public void calculateEmployeeProcessing(){
		currentDate();
		nameSort();
		for (int i= 0; i < _employeeList.size(); i++) {
			Employee employ = _employeeList.get(i);
			employ.employeesProcessing();
		}
	}
	public void calculateBurdenProcessing() {
		currentDate();
		double totalBurden = 0;
		Employee employee = _employeeList.get(0);
		employee.printDate();
		for (int i= 0; i < _employeeList.size(); i++) {
			Employee employ = _employeeList.get(i);
			totalBurden = employ.burdenProcessing(totalBurden);
		}
		System.out.println("Total Burden: $" + totalBurden);
	}
	public void calculatePAYEProcessing() {
		currentDate();
		double totalPAYE = 0;
		Employee employee = _employeeList.get(0);
		employee.printDate();
		for (int i= 0; i < _employeeList.size(); i++) {
			Employee employ = _employeeList.get(i);
			totalPAYE = employ.pAYEProcessing(totalPAYE);
		}
		System.out.println("Total PAYE: $" + totalPAYE);
	}
	public void currentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		System.out.println(dateFormat.format(currentDate));
	}
	public void nameSort() {
		Collections.sort(_employeeList, new Employee.EmployeeSortName());
	}
	public void taxIDSort() {
		Collections.sort(_employeeList, new Employee.EmployeeSorttaxID());
	}
}
