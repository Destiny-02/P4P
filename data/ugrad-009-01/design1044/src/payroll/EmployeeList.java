package payroll;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
public class EmployeeList {
	private ArrayList<Employee> _employeeList;
	public void getEmployees(String filePath)
			throws FileNotFoundException, PayrollDateFormatException, EmployeeDataException {
		_employeeList = new ArrayList<Employee>();
		FileReader fileReader = new FileReader(filePath);
		String[] employeeData = null;
		while (fileReader.hasNextLine()) {
			employeeData = fileReader.getEmployeeLine();
			if (employeeData != null) {
				_employeeList.add(createEmployee(employeeData, true, fileReader.getCurrentLineNumber(), filePath));
			}
		}
		for (Employee e1 : _employeeList) {
			for (Employee e2 : _employeeList) {
				if ((e1 != e2) && (e1.getTID() == e2.getTID())) {
					throw new EmployeeDataException(e1.getEmployeeData(), e2.getEmployeeData(), e1.getLineNumber(),
							e2.getLineNumber(), filePath, "Non unique TID (" + Integer.toString(e2.getTID()) + ")");
				}
			}
		}
	}
	private void sortEmployeeList(Comparator<Employee> comparitor) {
		Collections.sort(_employeeList, comparitor);
	}
	private Employee createEmployee(String[] employeeData, boolean hourly, int line, String filePath)
			throws PayrollDateFormatException, EmployeeDataException {
		if (employeeData[2].equalsIgnoreCase("Hourly")) {
			return new HourlyEmployee(employeeData, line, filePath);
		} else {
			return new SalariedEmployee(employeeData, line, filePath);
		}
	}
	public void process(String process) {
		if (process.equals(Processes.Payslips.toString())) {
			payslips();
		} else if (process.equals(Processes.Employees.toString())) {
			employees();
		} else if (process.equals(Processes.Burden.toString())) {
			burden();
		} else if (process.equals(Processes.PAYE.toString())) {
			paye();
		}
	}
	private void employees() {
		sortEmployeeList(PayrollComparitor.ALPHABETICAL_SORT);
		System.out.print(new PayrollDate().currentDate());
		for (Employee employee : _employeeList) {
			employee.printEmployeeListLine();
		}
	}
	private void payslips() {
		sortEmployeeList(PayrollComparitor.TID_SORT);
		System.out.print(new PayrollDate().currentDate());
		for (Employee employee : _employeeList) {
			employee.printPayslipLine();
		}
	}
	private void burden() {
		System.out.println(new PayrollDate().currentDate());
		System.out.printf("%s to %s\n", _employeeList.get(0).period()[0], _employeeList.get(0).period()[1]);
		double burden = 0;
		for (Employee employee : _employeeList) {
			burden += employee.getGross();
		}
		System.out.printf("Total Burden: $%.2f", burden);
	}
	private void paye() {
		System.out.println(new PayrollDate().currentDate());
		System.out.printf("%s to %s\n", _employeeList.get(0).period()[0], _employeeList.get(0).period()[1]);
		double PAYE = 0;
		for (Employee employee : _employeeList) {
			PAYE += employee.getPAYE();
		}
		System.out.printf("Total PAYE: $%.2f", PAYE);
	}
}
