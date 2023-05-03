package payroll;
import java.util.Collections;
import java.util.Comparator;
public class outputFormat{
	public void printOutput(database db, int type){
		if (type == 1){
			for (int i=0; i<db.employeeList.size(); i++){
				String[] name = db.employeeList.get(i).name.split(", ");
				db.employeeList.get(i).name = (name[1] + " " + name[0]);
				System.out.printf("%s. %s, Period: %s to %s. Gross: $%.2f, PAYE: $%s, Deductions: $%.2f Nett: $%.2f YTD: $%.2f\n", db.employeeList.get(i).tid, db.employeeList.get(i).name, db.employeeList.get(i).start, db.employeeList.get(i).end, db.employeeList.get(i).gross, db.employeeList.get(i).paye, db.employeeList.get(i).deductions, (db.employeeList.get(i).gross-db.employeeList.get(i).deductions-Double.parseDouble(db.employeeList.get(i).paye)), db.employeeList.get(i).ytd + db.employeeList.get(i).gross);
			}
		}
		else if (type == 2){
			for (int i=0; i<db.employeeList.size(); i++){
				if (db.employeeList.get(i).salary)
					System.out.printf("%s (%s) Salaried, $%.2f YTD:$%.2f\n", db.employeeList.get(i).name, db.employeeList.get(i).tid, db.employeeList.get(i).rate, db.employeeList.get(i).ytd + db.employeeList.get(i).gross);
				else{
					System.out.printf("%s (%s) Hourly, $%.2f YTD:$%.2f\n", db.employeeList.get(i).name, db.employeeList.get(i).tid, db.employeeList.get(i).rate, db.employeeList.get(i).ytd + db.employeeList.get(i).gross);
				}
			}
		}
		else if (type == 3){
			Double burden = (double) 0;
			for (int i=0; i<db.employeeList.size(); i++){
				burden = burden + db.employeeList.get(i).gross;
			}
			System.out.printf("%s to %s\nTotal Burden: $%.2f\n", db.employeeList.get(0).start, db.employeeList.get(0).end, burden);
		}
		else if (type == 4){
			Double totalPaye = (double) 0;
			for (int i=0; i<db.employeeList.size(); i++){
				totalPaye = totalPaye + Double.parseDouble(db.employeeList.get(i).paye);
			}
			System.out.printf("%s to %s\nTotal PAYE: $%.2f\n", db.employeeList.get(0).start, db.employeeList.get(0).end, totalPaye);
		}
}
	public void alphaSort(database db){
		Collections.sort(db.employeeList, new Comparator<employee>() {
			public int compare(employee e1, employee e2) {
				return e1.name.compareTo(e2.name);
			}
		});
	}
	public void numSort(database db){
		Collections.sort(db.employeeList, new Comparator<employee>() {
			public int compare(employee e1, employee e2) {
				return (e1.tid-e2.tid);
			}
		});
	}
}