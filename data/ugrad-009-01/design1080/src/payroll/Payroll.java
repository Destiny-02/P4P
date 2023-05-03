package payroll;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
public class Payroll {
	private ArrayList<Employee> _employees = new ArrayList<Employee>();
	private Operation _operation;
	public Payroll(String[] args) {
		getInput(args[0]);
		for (Operation op : Operation.values()) {
			if (op.toString().equals(args[1])) {
				_operation = op;
			}
		}
		if (!_employees.isEmpty()) {
			switch(_operation) {
				case Payslips:
					Collections.sort(_employees, new TaxIDComparator());
					PayslipProcessing p = new PayslipProcessing(_employees);
					break;
				case Employees:
					Collections.sort(_employees, new FamilyNameComparator());
					EmployeeProcessing e = new EmployeeProcessing(_employees);
					break;
				case Burden:
					BurdenProcessing b = new BurdenProcessing(_employees);
					break;
				case PAYE:
					PayeProcessing pa = new PayeProcessing(_employees);
					break;
			}
		}
	}
	private void getInput(String fileName) {
		File file = new File(fileName);
		try {
			Scanner s = new Scanner(file);
			while (s.hasNext()) {
				String line = s.nextLine();
				if (!line.equals("")) {
					if ((line.charAt(0) != ('#')) && (line.charAt(0) != (' '))) {
						String[] arr = line.split("\t");
						Employee employee = arr[2].equals("Salaried") ? new SalariedEmployee(line) : new HourlyEmployee(line);
						_employees.add(employee);
					}
				}
			}
			s.close();
			checkListIds();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File not found");
		}
		catch (PayrollException e) {
			e.printStackTrace();
			System.out.println("Payroll Exception");
		}
	}
	private void checkListIds() throws PayrollException {
		int counter;
		for (Employee employee : _employees) {
			int id = employee.getTid();
			counter = 0;
			for (Employee employeeInner : _employees) {
				counter += (employeeInner.getTid() == id) ? 1 : 0;
			}
			if (counter > 1) {
				throw new PayrollException("Tax ID occurs multiple times.");
			}
		}
	}
	public static void main(String[] args) {
		Payroll m = new Payroll(args);
	}
}
