package payroll;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;
public class EmployeeList {
	private List<Employee> employees = new Vector<Employee>();
	private Employee.DisplayDetails display = new Employee.DisplayDetails();
	public void generateEmployeeList(BufferedReader fis) throws IOException{
		String str=fis.readLine();
		while ((str=fis.readLine()) != null){
			if (str.length()>0 && (str.charAt(0) != '#')){
				try {
					String[] splitData = str.split("\\t");
					ValidInput testingInput = new ValidInput(splitData[0], splitData[2], splitData[3], splitData[4], splitData[5], splitData[6], Float.valueOf(splitData[7]), splitData[8]);
					if (testingInput.checkValidity()){
						Employee employee = new Employee(splitData[0], splitData[1], splitData[2], splitData[3], splitData[4], splitData[5], splitData[6], Float.valueOf(splitData[7]), splitData[8]);
						employees.add(employee);
					} else {
						throw new EmployeeException("Invalid Input: Format could be missing $, or values are either too small or too big");
					}
				} catch (NumberFormatException e){
					throw new EmployeeException("Format of file is wrong!");
				}
			}
		}
	}
	public void sortByLast(){
		Employee.Comparators.EmployeeComparatorByLast lastComparator = new Employee.Comparators.EmployeeComparatorByLast();
		Collections.sort(employees, lastComparator);
	}
	public void sortByFirst(){
		Employee.Comparators.EmployeeComparatorByFirst firstComparator = new Employee.Comparators.EmployeeComparatorByFirst();
		Collections.sort(employees, firstComparator);
	}
	public void sortByTID(){
		Employee.Comparators.EmployeeComparatorByTID TID = new Employee.Comparators.EmployeeComparatorByTID();
		Collections.sort(employees, TID);
	}
	public void displayDate(){
		Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		System.out.println(ft.format(dNow));
	}
	public static class Processes{
		public static class EmployeesProcesses implements Command{
			public void process(EmployeeList list){
				list.sortByLast();
				for (Employee element: list.employees) {
					list.display.displayEmployeeProcess(element);
				}
			}
		}
		public static class PayslipsProcess implements Command{
			public void process(EmployeeList list){
				list.sortByTID();
				for (Employee element: list.employees) {
					list.display.displayPayslipsProcess(element);
				}
			}
		}
		public static class BurdenProcess implements Command{
			public void process(EmployeeList list){
				float totalBurden = 0;
				System.out.println(list.display.displayPeriod(list.employees.get(0)));
				for (Employee element: list.employees){
					totalBurden += list.display.displayBurdenProcess(element);
				}
				System.out.println("Total Burden: $"+String.format("%.2f",totalBurden));
			}
		}
		public static class PAYEProcess implements Command{
			public void process(EmployeeList list){
				float totalPaye = 0;
				System.out.println(list.display.displayPeriod(list.employees.get(0)));
				for (Employee element: list.employees){
					totalPaye+=list.display.displayPAYEProcess(element);
				}
				System.out.println("Total PAYE: $"+String.format("%.2f",totalPaye));
			}
		}
	}
}

