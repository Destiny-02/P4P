package payroll;
import java.text.DecimalFormat;
public class calculations {
	private void calcOvertime(database db, int i){
		if(db.employeeList.get(i).hours > 40 && db.employeeList.get(i).hours <= 43){
			db.employeeList.get(i).hours = 40 + (db.employeeList.get(i).hours-40)*1.5;
		}
		else if (db.employeeList.get(i).hours > 43){
			db.employeeList.get(i).hours = 44.5 + (db.employeeList.get(i).hours-43)*2;
		}
	}
	public void calcGross(database db){
		for (int i=0; i<db.employeeList.size(); i++){
			if (db.employeeList.get(i).salary){
				db.employeeList.get(i).gross = (float) (db.employeeList.get(i).rate/52);
			}
			else{
				calcOvertime(db, i);
				db.employeeList.get(i).gross = (float) (db.employeeList.get(i).rate*db.employeeList.get(i).hours);
			}
		}
	}
	public void calcPaye(database db){
		for (int i=0; i<db.employeeList.size(); i++){
			DecimalFormat df = new DecimalFormat("#.00");
			if (db.employeeList.get(i).salary){
				if (db.employeeList.get(i).rate <= 14000){
					db.employeeList.get(i).paye =  df.format((0.105*(db.employeeList.get(i).gross)));
				}
				else if(db.employeeList.get(i).rate > 14000 && db.employeeList.get(i).rate <= 48000){
					db.employeeList.get(i).paye = df.format((0.175*((db.employeeList.get(i).rate)-14000)/52 + 0.105*14000/52));
				}
				else if(db.employeeList.get(i).rate > 48000 && db.employeeList.get(i).rate <= 70000){
					db.employeeList.get(i).paye = df.format((0.3*((db.employeeList.get(i).rate)-48000)/52 +  0.105*14000/52 + 0.175*34000/52));
				}
				else {
					db.employeeList.get(i).paye = df.format((0.33*((db.employeeList.get(i).rate)-70000)/52 + 0.105*14000/52 + 0.175*34000/52 + 0.30*22000/52));
				}
			}
			else{
				if (db.employeeList.get(i).gross*52 <= 14000){
					db.employeeList.get(i).paye = df.format(0.105*(db.employeeList.get(i).gross*52)/52);
				}
				else if(db.employeeList.get(i).gross*52 > 14000 && db.employeeList.get(i).gross*52 <= 48000){
					db.employeeList.get(i).paye = df.format(0.175*((db.employeeList.get(i).gross*52)-14000)/52 + 0.105*14000/52);
				}
				else if(db.employeeList.get(i).gross*52 > 48000 && db.employeeList.get(i).gross*52 <= 70000){
					db.employeeList.get(i).paye = df.format(0.3*((db.employeeList.get(i).gross*52)-48000)/52 +  0.105*14000/52 + 0.175*34000/52);
				}
				else {
					db.employeeList.get(i).paye = df.format(0.33*((db.employeeList.get(i).gross*52)-70000)/52 + 0.105*14000/52 + 0.175*34000/52 + 0.30*22000/52);
				}
			}
		}
	}
}