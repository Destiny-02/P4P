package payroll;
import java.util.ArrayList;
import java.util.Collections;
public class Payroll {
	private ArrayList<Employment> employeeData = new ArrayList<Employment>();
	public static void main(String[] args){
		String fileName = args[0];
		Payroll p = new Payroll();
		ReadFile file = new ReadFile(fileName);
		file.sortFile(p.employeeData);
		int numOfEmploy = p.employeeData.size();
		if(args[1].equals("Payslips")){
			p.sortByTid();
			for(int k = 0; k < numOfEmploy; k++){
				Employment employee = p.employeeData.get(k);
				if (k == 0){
					employee.displayDate();
				}
				employee.displayPaySlip();
			}
		} else if(args[1].equals("Employees")){
			p.sortByName();
			for(int k = 0; k < numOfEmploy; k++){
				Employment employee = p.employeeData.get(k);
				if (k == 0){
					employee.displayDate();
				}
				employee.displayEmployeeSlip();
			}
		} else if(args[1].equals("Burden")){
			double totalBurden = 0;
			for(int k = 0; k < numOfEmploy; k++){
				Employment employee = p.employeeData.get(k);
				totalBurden = employee.calculateBurdenTotal(totalBurden);
				if(k == (numOfEmploy - 1)){
					employee.displayBurdenSlip(totalBurden);
				}
			}
		} else {
			double totalPaye= 0;
			for(int k = 0; k < numOfEmploy; k++){
				Employment employee = p.employeeData.get(k);
				totalPaye = employee.calculatePayeTotal(totalPaye);
				if(k == (numOfEmploy - 1)){
					employee.displayPayeSlip(totalPaye);
				}
			}
		}
	}
	public void sortByTid(){
		Collections.sort(employeeData, new Employment.EmploymentComparatorTid());
	}
	public void sortByName(){
		Collections.sort(employeeData, new Employment.EmploymentComparatorName());
	}
}