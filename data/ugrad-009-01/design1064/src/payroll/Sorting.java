package payroll;
import java.util.ArrayList;
public class Sorting {
	public void sort(ArrayList<EmployeeRecord> sortedRecords, String compareby){
		if (compareby.equals("sortbyTID")){
		for (int i = 0; i < sortedRecords.size(); i++) {
			for (int j = 0; j < sortedRecords.size(); j++) {
				if (comparebyTID(sortedRecords.get(i), sortedRecords.get(j))) {
					EmployeeRecord temp = new EmployeeRecord();
					temp = sortedRecords.get(j);
					sortedRecords.set(j, sortedRecords.get(i));
					sortedRecords.set(i, temp);
				}
			}
		}
	}else if (compareby.equals("sortbyName")) {
		for (int i = 0; i < sortedRecords.size(); i++) {
			for (int j = 0; j < sortedRecords.size(); j++) {
				if (compareByName(sortedRecords.get(i), sortedRecords.get(j))) {
					EmployeeRecord temp = new EmployeeRecord();
					temp = sortedRecords.get(j);
					sortedRecords.set(j, sortedRecords.get(i));
					sortedRecords.set(i, temp);
				}
			}
		}
	}
	}
	private boolean comparebyTID(EmployeeRecord record1, EmployeeRecord record2) {
		if (record1.getTaxId() > record2.getTaxId()) {
			return false;
		} else
			return true;
	}
	private boolean compareByName(EmployeeRecord record1, EmployeeRecord record2) {
		if (record1.getName().compareTo(record2.getName()) < 0) {
			return true;
		} else if (record1.getName().compareTo(record2.getName()) == 0) {
			return (record1.getTaxId() < record2.getTaxId());
		} else
			return false;
	}
}
