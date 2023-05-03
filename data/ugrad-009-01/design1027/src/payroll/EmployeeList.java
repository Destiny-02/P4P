package payroll;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
public class EmployeeList {
	private HashMap<Integer, Employee> myHash;
	private List<Integer> taxIDList;
	public EmployeeList () {
		myHash = new HashMap<Integer, Employee>();
		taxIDList = new ArrayList<Integer>();
	}
	public void addToList(String employeeData) {
		Employee currentEmployee = new Employee(employeeData);
		myHash.put(currentEmployee.getTaxID(), currentEmployee);
		taxIDList.add(currentEmployee.getTaxID());
	}
	public void calculateEmployees(String processType) {
		if (processType.equals("Burden")) {
			processBurden();
		} else if (processType.equals("PAYE")) {
			processPAYE();
		} else if (processType.equals("Employees")) {
			processEmployees();
		} else if (processType.equals("Payslips")) {
			processPayslips();
		}
	}
	private void processBurden() {
		double burdenSum = 0;
		Employee tempEmployee = myHash.get(taxIDList.get(0));
		for (Employee currentEmployee : myHash.values()) {
			Employee.Calculations calc = currentEmployee.new Calculations();
			burdenSum += calc.calculateWeeklyGross();
		}
		OutputBurden output = new OutputBurden(burdenSum, tempEmployee);
	}
	private void processPAYE() {
		double PAYESum = 0;
		Employee tempEmployee = myHash.get(taxIDList.get(0));
		for (Employee currentEmployee : myHash.values()) {
			Employee.Calculations calc = currentEmployee.new Calculations();
			PAYESum += calc.calculateWeeklyPAYE();
		}
		OutputPAYE output = new OutputPAYE(PAYESum, tempEmployee);
	}
	private void processPayslips() {
		Employee currentEmployee = myHash.get(taxIDList.get(0));;
		Collections.sort(taxIDList);
		for (int currentID : taxIDList) {
			currentEmployee = myHash.get(currentID);
			Employee.Calculations calc = currentEmployee.new Calculations();
			double[] payslipDetails = calc.calculatePayslip();
			OutputPayslip output = new OutputPayslip(currentEmployee, payslipDetails);
		}
	}
	private void processEmployees() {
		HashMap<String, Employee> tempHash = new HashMap<String, Employee>();
		for (Employee currentEmployee : myHash.values()) {
			String newKey = currentEmployee.getName() + currentEmployee.getTaxID();
			tempHash.put(newKey, currentEmployee);
		}
		List<String> taxNameList = new ArrayList<String>(tempHash.keySet());
		Collections.sort(taxNameList);
		for (String currentKey : taxNameList) {
			Employee currentEmployee = tempHash.get(currentKey);
			Employee.Calculations calc = currentEmployee.new Calculations();
			double employeeDetails = calc.calculateEmployee();
			OutputEmployee output = new OutputEmployee(currentEmployee, employeeDetails);
		}
	}
}