package payroll;
import java.util.ArrayList;
public class PAYEProc {
	public void payeProcess(ArrayList<Employee> employeeList){
	Today date = new Today();
	date.printTodayDate();
	System.out.println("");
	employeeList.get(0).printDate();
	System.out.println("");
	double paye = 0;
	for(int i = 0; i < employeeList.size(); i++){
		Employee _employee = employeeList.get(i);
		PAYE p = new PAYE();
		paye += p.calcPaye(_employee);
	}
	System.out.printf("Total PAYE: $%.2f",paye);
	}
}
