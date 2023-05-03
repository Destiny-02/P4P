package payroll;
import java.util.ArrayList;
public class Payroll {
	public static void main(String[] args) {
		ArrayList<Employee> arrList = new ArrayList<Employee>();
		Records data = new Records();
		data.getEmployeeRecords(arrList, args[0]);
		Processor processor = new Processor();
		processor.calculate(arrList, args[1]);
	}
}