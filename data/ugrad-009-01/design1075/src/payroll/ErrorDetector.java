package payroll;
import java.util.List;
import java.util.ArrayList;
public class ErrorDetector {
	private EmployeeList _list;
	public ErrorDetector (EmployeeList list) {
		_list = list;
	}
	public void Detect () throws EmployeeException {
		final List<String> usedTIDs = new ArrayList<String>();
		_list.sortEmployees("TID");
		for (Employee employee: _list) {
			DetectWrongDate(employee.givePayPeriod());
			DetectDuplicateTID(usedTIDs, employee);
		}
	}
	private void DetectWrongDate(String payPeriod) throws EmployeeException {
		payPeriod = payPeriod.replaceAll("[^\\d]", " ");
		String[] dateInfo = payPeriod.split("\\s+");
		int startYear = Integer.parseInt(dateInfo[0]);
		int startMonth = Integer.parseInt(dateInfo[1]);
		int startDay = Integer.parseInt(dateInfo[2]);
		int endYear = Integer.parseInt(dateInfo[3]);
		int endMonth = Integer.parseInt(dateInfo[4]);
		int endDay = Integer.parseInt(dateInfo[5]);
		if (startYear > endYear) {
			throw new EmployeeException("End date before start date");
		}
		else if (startYear == endYear) {
			if (startMonth > endMonth) {
				throw new EmployeeException("End date before start date");
			}
			else if (startMonth == endMonth) {
				if (startDay > endDay) {
					throw new EmployeeException("End date before start date");
				}
			}
		}
	}
	private void DetectDuplicateTID (List<String> usedTIDs, Employee employee) throws EmployeeException{
		final String TID = employee.giveTID();
		if (usedTIDs.contains(TID)) {
			throw new EmployeeException("Duplicate TID's");
		}
		else {
		usedTIDs.add(TID);
		}
	}
}
