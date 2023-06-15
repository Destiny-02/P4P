package payroll;
import java.io.FileNotFoundException;
import java.io.IOException;
import payroll.exceptions.PayrollException;
import payroll.file.PayrollFile;
import payroll.output.processing.BurdenProcessor;
import payroll.output.processing.EmployeeProcessor;
import payroll.output.processing.PAYEProcessor;
import payroll.output.processing.PayslipProcessor;
import payroll.output.processing.Processor;
public class Payroll {
	public static void main(String[] args) throws IOException, PayrollException {
		PayrollFile file = null;
		try{
		file = new PayrollFile(args[0]);
		} catch(FileNotFoundException e){
			System.err.println("Error: files: " + args[0] + " could not be found. Please try again.");
			return;
		}
		file.readAll();
		file.close();
		Processor proc;
		switch(args[1]){
			case "Payslips"  : proc = new PayslipProcessor();
							   break;
			case "Employees" : proc = new EmployeeProcessor();
							   break;
			case "Burden"	 : proc = new BurdenProcessor();
							   break;
			case "PAYE"		 : proc = new PAYEProcessor();
							   break;
			default     	 : proc = null;
		}
		file.updatePayFields();
		file.passData(proc);
		proc.process();
	}
}