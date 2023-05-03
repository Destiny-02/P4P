package payroll;
import java.util.LinkedList;
import java.util.Scanner;
public class EmployeeList {
	private LinkedList<Employee> _employeeList;
	public EmployeeList() {
		_employeeList = new LinkedList<Employee>();
	}
	public void storeData(Scanner file, String process) {
		while (file.hasNextLine()) {
			String line = file.nextLine();
			if (!(line.startsWith("#"))) {
				Employee person = new Employee(line);
				addToList(person, process);
			}
		}
	}
	public int giveValueSize() {
		int size = _employeeList.size();
		return size;
	}
	public String[] returnRequiredStrings(String process) {
		String[] allStrings = new String[_employeeList.size()];
		for (int i = 0; i < _employeeList.size(); i++) {
			Employee E = _employeeList.get(i);
			String employeeString = giveCorrectString(E, process);
			allStrings[i] = employeeString;
		}
		return allStrings;
	}
	public double returnRequiredTotal(String process) {
		double total = 0;
		for (int i = 0; i < _employeeList.size(); i++) {
			Employee E = _employeeList.get(i);
			total += getValue(E, process);
		}
		return total;
	}
	public String returnPeriod() {
		Employee person = _employeeList.getFirst();
		String period = person.giveCopyOfPeriod();
		return period;
	}
	private void addToList(Employee E, String processType) {
		int position = 0;
		boolean finished = false;
		if (_employeeList.size() == 0) {
			_employeeList.addFirst(E);
		} else {
			while (position < _employeeList.size() && !finished) {
				Employee person = _employeeList.get(position);
				if (!(E.compare(person, processType))) {
					_employeeList.add(position, E);
					finished = true;
				}
				position++;
			}
			if (!finished) {
				_employeeList.addLast(E);
			}
		}
	}
	private String giveCorrectString(Employee E,String process) {
		if (process.equals("Payslips")) {
			return E.constructPayslip();
		} else {
			return E.constructEmployeeDescription();
		}
	}
	private double getValue(Employee E, String process) {
		if (process.equals("Burden")) {
			double gross = Double.parseDouble(E.returnGrossAsString());
			return gross;
		} else {
			double PAYE = Double.parseDouble(E.returnPAYEAsString());
			return PAYE;
		}
	}
}
