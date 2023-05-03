package payroll;
import java.util.ArrayList;
public class BurdenProc {
	public void burden(ArrayList<Employee> employeeList) {
		Today date = new Today();
		date.printTodayDate();
		System.out.println("");
		employeeList.get(0).printDate();
		System.out.println("");
		double burden =0;
		for(int i = 0; i < employeeList.size(); i++){
			Employee _employee = employeeList.get(i);
			Gross g = new Gross();
			burden += g.calcGross(_employee);
	}
		System.out.printf("Total Burden: $%.2f", burden);
}
}
