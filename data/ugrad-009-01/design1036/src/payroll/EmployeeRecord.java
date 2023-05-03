package payroll;
import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
public class EmployeeRecord {
	private ArrayList<Employee> employeeList;
	public void readInRecord() {
		InputStreamReader stdin = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(stdin);
		employeeList = new ArrayList<Employee>();
		String line = "";
		try {
			while ((line = in.readLine()) != null) {
				if ((line.indexOf("#") == -1) && !(line.isEmpty())) {
					line = line.replaceAll(", ", "\t");
					line = line.replaceAll("[^a-zA-Z\t0-9-. ]", "");
					Employee employee = new Employee();
					employee.storeInformation(line);
					employeeList.add(employee);
				}
			}
		} catch (IOException exception) {
		}
	}
	public ArrayList<Employee> getEmployeeList() {
		return this.employeeList;
	}
	public void printEmployees() {
		Collections.sort(employeeList, new EmployeeNameComparator());
		if (this.employeeList != null) {
			for (Employee e : this.employeeList) {
				e.displayEmployeeInfo();
			}
		}
	}
	public void printPayslips() {
		Collections.sort(employeeList);
		if (this.employeeList != null) {
			for (Employee e : this.employeeList) {
				e.displayPayslip();
			}
		}
	}
	public void printBurden() {
		int burden = 0;
		if (this.employeeList != null) {
			for (Employee e : this.employeeList) {
				burden += e.getGrossIncome();
			}
			Finances f = new Finances();
			employeeList.get(0).getPayPeriod();
			System.out.println("Total Burden: " + f.currencyFormat(burden));
		}
	}
	public void printPaye() {
		int paye = 0;
		if (this.employeeList != null) {
			for (Employee e : this.employeeList) {
				paye += e.getPaye();
			}
			Finances f = new Finances();
			employeeList.get(0).getPayPeriod();
			System.out.println("Total PAYE: " + f.currencyFormat(paye));
		}
	}
}
