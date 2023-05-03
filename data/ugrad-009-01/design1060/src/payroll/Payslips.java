package payroll;
import java.util.Arrays;
public class Payslips implements Form{
	private Integer[] _printOrder;
	private EmployeeRecords _employeeRecords;
	Payslips(EmployeeRecords employeeRecords) {
		_employeeRecords = employeeRecords;
		_printOrder = null;
	}
	public void processForm() {
		Object[] objectArray = _employeeRecords.getKeySet().toArray();
		_printOrder = Arrays.copyOf(objectArray, objectArray.length, Integer[].class);
		Arrays.sort(_printOrder);
	}
	Integer[] getPrintOrder() {
		return _printOrder;
	}
}

