package payroll;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
public class EmployeeList {
	private static final DecimalFormat df= new DecimalFormat("######0.00");
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private LinkedList<Employee> _List;
	private double _TotalPAYE;
	private double _TotalBurden;
	public EmployeeList() {
		_List = new LinkedList<Employee>();
		_TotalPAYE = 0;
		_TotalBurden = 0;
	}
	public void addLast(Employee a) {
		_List.addLast(a);
		_TotalPAYE += a.getPAYE();
		_TotalBurden += a.getGross();
	}
	public void printTotalPAYE() {
		printDate();
		_List.getFirst().printPeriod();
		System.out.println("\n"+"Total PAYE: $" + df.format(_TotalPAYE));
	}
	public void printTotalBurden() {
		printDate();
		_List.getFirst().printPeriod();
		System.out.println("\n"+"Total Burden: $" + df.format(_TotalBurden));
	}
	public void printEmployees() {
		printDate();
		Collections.sort(_List, new EmployeeNameComparator());
		for (Employee a : _List) {
			a.printEmployee();
		}
	}
	public void printPayslips() {
		printDate();
		Collections.sort(_List, new EmployeeTIDComparator());
		for (Employee a : _List) {
			a.printPayslips1();
			a.printPeriod();
			a.printPayslips2();
		}
	}
	private static void printDate(){
		Date dateDate=new Date();
		System.out.println(formatter.format(dateDate));
	}
	private static class EmployeeNameComparator implements Comparator<Employee> {
		public int compare(Employee o1, Employee o2) {
			return o1.Namecompareto(o2);
		}
	}
	private static class EmployeeTIDComparator implements Comparator<Employee> {
		public int compare(Employee o1, Employee o2) {
			return o1.TIDcompareto(o2);
		}
	}
}
