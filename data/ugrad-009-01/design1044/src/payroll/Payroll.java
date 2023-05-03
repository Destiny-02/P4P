package payroll;
import java.io.FileNotFoundException;
public class Payroll {
	private static EmployeeList _EmployeeList;
	public static void main(String args[]) {
		if (args.length != 2) {
			System.out.printf("ERROR: Invalid input arguements:");
			for (String s : args) {
				System.out.print(" " + s);
			}
			System.out.println("\nERROR: 2 Arguements required (file path, processing type)");
			return;
		} else if (!Processes.contains(args[1])) {
			System.out.println("ERROR: Invalid processing type");
			return;
		}
		else {
			_EmployeeList = new EmployeeList();
			try {
				_EmployeeList.getEmployees(args[0]);
			}
			catch (FileNotFoundException e) {
				System.out.println("ERROR: Please check file path, details:");
				System.out.println(e.getMessage());
				return;
			} catch (PayrollDateFormatException e) {
				System.out.println("ERROR: Please check date format, details:");
				System.out.println(e.getMessage());
				return;
			} catch (EmployeeDataException e) {
				System.out.println("ERROR: Please check employee data, details:");
				System.out.println(e.getMessage());
				return;
			} catch (Exception e) {
				System.out.println("ERROR: details:");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		_EmployeeList.process(args[1]);
		System.out.println("");
	}
}
