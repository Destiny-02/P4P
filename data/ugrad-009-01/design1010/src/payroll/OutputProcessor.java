package payroll;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
public class OutputProcessor {
	private ArrayList<Employee> _employeeList;
	private boolean _duplicateTID = false;
	private Employee _employee;
	public OutputProcessor(ArrayList<Employee> employeeList) {
		_employeeList = employeeList;
	}
	public void printDate() {
		String date = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
		System.out.println(date);
	}
	public void sortByTID() throws PayrollException {
		Collections.sort(_employeeList, new Comparator<Employee>() {
			@Override
			public int compare(Employee emp1, Employee emp2) {
				Integer taxID1 = emp1.getTaxID();
				Integer taxID2 = emp2.getTaxID();
				if (taxID1.compareTo(taxID2) == 0) {
					_duplicateTID = true;
				}
				return taxID1.compareTo(taxID2);
			}
		});
		if (_duplicateTID) {
			throw new PayrollException("Duplicate TIDs");
		}
	}
	public void processPayslips() throws PayrollException {
		for (int a = 0; a < _employeeList.size(); a++) {
			_employee = _employeeList.get(a);
			System.out.println(_employee.buildPayslip());
		}
	}
	public void processEmployees() {
		Collections.sort(_employeeList, new Comparator<Employee>() {
			@Override
			public int compare(Employee emp1, Employee emp2) {
				String lastName1 = emp1.getLastName();
				String lastName2 = emp2.getLastName();
				int comp = lastName1.compareTo(lastName2);
				if (comp != 0) {
					return comp;
				} else {
					String firstName1 = emp1.getFirstName();
					String firstName2 = emp2.getFirstName();
					int comp2 = firstName1.compareTo(firstName2);
					if (comp2 != 0) {
						return comp2;
					} else {
						Integer taxID1 = emp1.getTaxID();
						Integer taxID2 = emp2.getTaxID();
						return taxID1.compareTo(taxID2);
					}
				}
			}
		});
		for (int b = 0; b < _employeeList.size(); b++) {
			_employee = _employeeList.get(b);
			System.out.println(_employee.buildEmployeeSummary());
		}
	}
	public void processBurden() {
		float totalGross = 0.0f;
		for (int c = 0; c < _employeeList.size(); c++) {
			_employee = _employeeList.get(c);
			totalGross += _employee.calculatePay();
		}
		System.out.println(_employeeList.get(0).startDateToEndDate());
		System.out.format("Total Burden: $%.2f", totalGross);
		System.out.println();
	}
	public void processPAYE() {
		float totalPAYE = 0.0f;
		for (int d = 0; d < _employeeList.size(); d++) {
			_employee = _employeeList.get(d);
			totalPAYE += _employee.calculateTax();
		}
		System.out.println(_employeeList.get(0).startDateToEndDate());
		System.out.format("Total PAYE: $%.2f", totalPAYE);
		System.out.println();
	}
}
