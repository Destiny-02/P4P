package payroll;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
public class Payroll {
	public static void main(String[] args) throws FileNotFoundException, PayrollException {
		File employeeData = new File(args[0]);
		String processingType = args[1];
		DataReader dr = new DataReader();
		EmployeeList employeeList = new EmployeeList(new ArrayList<Employable>());
		dr.generateEmployeesFromFile(employeeData, employeeList);
		Processor processor = new Processor();
		switch(processingType){
		case "Payslips":
			System.out.println(processor.process(employeeList, FormatType.PAYSLIPS));
			break;
		case "Employees":
			System.out.println(processor.process(employeeList, FormatType.EMPLOYEES));
			break;
		case "Burden":
			System.out.println(processor.process(employeeList, FormatType.BURDEN));
			break;
		case "PAYE":
			System.out.println(processor.process(employeeList, FormatType.PAYE));
			break;
		default:
			throw new PayrollException("Processing argument not recognized.");
		}
	}
}
