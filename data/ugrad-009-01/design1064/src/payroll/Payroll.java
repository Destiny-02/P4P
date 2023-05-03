package payroll;
import java.util.ArrayList;
import java.util.Date;
public class Payroll {
	private void printPaySlips(ArrayList<EmployeeRecord> records){
		Sorting sort = new Sorting();
		sort.sort(records, "sortbyTID");
		Date date = new Date();
		System.out.printf("%tF\n",date);
		for(int i = 0; i < records.size(); i++){
			records.get(i).printPaySlip();
		}
	}
	private void printEmployee(ArrayList<EmployeeRecord> records){
		Sorting sort = new Sorting();
		sort.sort(records,"sortbyName");
		Date date = new Date();
		System.out.printf("%tF\n",date);
		for(int i = 0; i < records.size(); i++){
			records.get(i).printEmployeeDetails();
		}
	}
	public static void main(String[] args) {
		DocProcessing processor = new DocProcessing();
		Process process = null;
		Payroll printInfo = new Payroll();
		String input = args[0];
		ArrayList<EmployeeRecord> records = processor.processDoc(input);
			if (args[1].equals("PAYE")) {
				process = new Tax();
				process.process(records);
			} else if (args[1].equals("Burden")) {
				process = new Payment();
				process.process(records);
			} else if (args[1].equals("Employees")) {
				printInfo.printEmployee(records);
			} else if (args[1].equals("Payslips")) {
				printInfo.printPaySlips(records);
			}
	}
}

