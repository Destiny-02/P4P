package payroll;
import java.util.Collections;
public class CheckRecord {
	public void dataErrorCheck(EmployeeRecord employeeRecord) {
		int tid = 0;
		Collections.sort(employeeRecord.getEmployeeList());
		for (Employee e : employeeRecord.getEmployeeList()) {
			if (tid == e.getTid()) {
				throw new RuntimeException("The TID in the data are not unique");
			}
			if (!e.vaildEmpolyeeFinancialInfo()) {
				throw new RuntimeException(
						"The empolyees financial data must be non-negative");
			}
		}
	}
}
