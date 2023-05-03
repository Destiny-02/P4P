package payroll;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
public class PrintOutput {
	private LinkedList<String> _list1 = new LinkedList<String>();
	public LinkedList<String> getInput(String args[]){
		File file = new File(args[0]);
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if ((line.startsWith("#"))||(line.length()==0)){
					continue;
				}
				else {
					_list1.add(line);
				}
			}
			sc.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return _list1;
	}
	public void printDate(){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(df.format(new Date()));
	}
	public void printOutput(String args[]){
		getInput(args);
		if (args[1].equals("Payslips")){
			Payslips py = new Payslips();
			py.printPayslips(_list1);
		}else if (args[1].equals("Employees")){
			EmployeeProcess process = new EmployeeProcess();
			process.printEmployeeProcess(_list1);
		}else if (args[1].equals("Burden")){
			Burden burden = new Burden();
			burden.printBurden(_list1);
		}else if (args[1].equals("PAYE")){
			Paye PAYE = new Paye();
			PAYE.printPaye(_list1);
		}else{
			System.out.printf("Invalid Input: %s. Please enter valid input\n",args[1]);
		}
	}
}
