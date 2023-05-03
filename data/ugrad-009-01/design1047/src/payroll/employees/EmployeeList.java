
package payroll.employees;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import payroll.PayrollException;
@SuppressWarnings("serial")
public class EmployeeList extends ArrayList<Employee>{
	public final static DecimalFormat df = new DecimalFormat("#.00");
	private String _start;
	private String _end;
	public EmployeeList(int capacity, Scanner scan) throws PayrollException {
		ensureCapacity(capacity);
		do {
			String line = scan.nextLine();
			Scanner scannedLine;
			if (!line.isEmpty() && line.charAt(0) != '#') {
				scannedLine = new Scanner(line);
				scannedLine.useDelimiter("\t\\W|\t|, ");
				String[] arr = new String[10];
				for (int i = 0; i < 10; i++) {
					if (scannedLine.hasNext()) {
						arr[i] = scannedLine.next();
					} else {
						scannedLine.close();
						throw new PayrollException("\n\tThere was missing data in the following" +
						" line of input: \n\t" + line + "\n\tIn this line there was/were only "
						+ i + " piece(s) of data instead of the required 10");
					}
				}
				if (_start == null && _end == null) {
					_start = arr[6];
					_end = arr[7];
				} else {
					if (!_start.equals(arr[6]) || !_end.equals(arr[7])) {
						scannedLine.close();
						throw new PayrollException("Pay periods are inconsistant between employees");
					}
				}
				add(new Employee(arr));
				scannedLine.close();
			}
		} while (scan.hasNextLine());
		scan.close();
	}
	public String payPeriod() {
		return _start + " to " + _end;
	}
	public ArrayList<String> payslips() throws PayrollException {
		Collections.sort(this, new tidComparator());;
		ArrayList<String> payslip = new ArrayList<String>(this.size());
		String period = "Period: " + _start + " to " + _end + ". ";
		for (Employee employee : this) {
			payslip.add(employee.payslip(period));
		}
		return payslip;
	}
	public ArrayList<String> employees() throws PayrollException {
		Collections.sort(this);
		ArrayList<String> employeeStatement = new ArrayList<String>(this.size());
		for (Employee employee : this) {
			employeeStatement.add(employee.employeeStatement());
		}
		return employeeStatement;
	}
	public ArrayList<String> burden() throws PayrollException {
		float burden = 0;
		for (Employee employee : this) {
			burden += employee.burden();
		}
		ArrayList<String> burdenStatement = new ArrayList<String>(2);
		burdenStatement.add(payPeriod());
		burdenStatement.add("Total Burden: $" + df.format(burden));
		return burdenStatement;
	}
	public ArrayList<String> paye() throws PayrollException {
		float paye = 0;
		for (Employee employee : this) {
			paye += employee.paye();
		}
		ArrayList<String> payeStatement = new ArrayList<String>();
		payeStatement.add(payPeriod());
		payeStatement.add("Total PAYE: $" + df.format(paye));
		return payeStatement;
	}
}
class tidComparator implements Comparator<Employee>{
	public int compare(Employee e1, Employee e2) {
		if (e1.getTID() < e2.getTID()) {
			return -1;
		} else if (e1.getTID() > e2.getTID()) {
			return 1;
		} else {
			return 0;
		}
	}
}