package payroll;
import java.util.ArrayList;
public class Sorting{
	public void compareTaxID(ArrayList<Employee> empList, int index) {
		int taxID1;
		int taxID2;
		Employee temp;
		for (int i = 0; i < empList.size(); i++) {
			for (int j = 1; j < empList.size(); j++) {
				taxID2 = Integer.parseInt(empList.get(j).getTaxID());
				taxID1 = Integer.parseInt(empList.get(j - 1).getTaxID());
				if (taxID1 > taxID2) {
					temp = empList.get(j - 1);
					empList.set(j - 1, empList.get(j));
					empList.set(j, temp);
				}
			}
		}
	}
	public void compareName(ArrayList<Employee> empList, int index) {
		Employee temp;
		String name1, name2;
		for (int i = 0; i < empList.size(); i++) {
			for(int j = 1; j < empList.size(); j++) {
				name1 = empList.get(j - 1).getName();
				name2 = empList.get(j).getName();
				if (name1.compareTo(name2) > 0) {
					temp = empList.get(j - 1);
					empList.set(j - 1, empList.get(j));
					empList.set(j, temp);
				}
			}
		}
	}
}