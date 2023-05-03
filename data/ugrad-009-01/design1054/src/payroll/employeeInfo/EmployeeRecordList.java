package payroll.employeeInfo;
import java.util.ArrayList;
public class EmployeeRecordList {
	ArrayList<EmployeeRecord> _records;
	Date _periodStart;
	Date _periodEnd;
	public EmployeeRecordList() {
		_records = new ArrayList<EmployeeRecord>();
	}
	public boolean AddEmployeeRecord(EmployeeRecord recordToAdd) {
		for (EmployeeRecord record : _records) {
			if (EmployeeRecord.SORT_TAXID.compare(record, recordToAdd) == 0) {
				return true;
			}
		}
		_records.add(recordToAdd);
		if (_periodStart == null || _periodEnd == null) {
			_periodStart = recordToAdd.GetPeriodStartAndEnd()[0];
			_periodEnd = recordToAdd.GetPeriodStartAndEnd()[1];
		}
		return false;
	}
	public String PayslipProcessing() {
		_records.sort(EmployeeRecord.SORT_TAXID);
		String returnString = "";
		for (EmployeeRecord record: _records) {
			returnString += record.PayslipFormat();
		}
		return returnString;
	}
	public String EmployeeProcessing() {
		_records.sort(EmployeeRecord.SORT_NAME);
		String returnString = "";
		for (EmployeeRecord record : _records) {
			returnString += record.EmployeeFormat();
		}
		return returnString;
	}
	public String BurdenProcessing() {
		float runningGross = 0;
		String returnString = "";
		for (EmployeeRecord record: _records) {
			runningGross += record.GetGrossIncomeOverPeriod();
		}
		returnString += String.format("%s to %s\nTotal Burden: $%.2f\n",
				_periodStart.toString(),
				_periodEnd.toString(),
				runningGross);
		return returnString;
	}
	public String PAYEProcessing() {
		float runningPAYE = 0;
		String returnString = "";
		for (EmployeeRecord record: _records) {
			runningPAYE += record.GetAnnualPAYETax() / 52.0f;
		}
		returnString += String.format("%s to %s\nTotal PAYE: $%.2f\n",
				_periodStart,
				_periodEnd,
				runningPAYE);
		return returnString;
	}
}
