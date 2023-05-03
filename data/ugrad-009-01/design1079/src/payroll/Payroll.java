package payroll;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;
public class Payroll {
	public static void main(String[] args)  {
		try{
			String inputFileName = args[0];
			String processing = args[1];
			ArrayList<Employee> employees = readEmployees(inputFileName);
			if(processing.equalsIgnoreCase("Payslips")){
				printPaySlips(employees);
			}else if(processing.equalsIgnoreCase("Employees")){
				processEmployee(employees);
			}else if(processing.equalsIgnoreCase("Burden")){
				processBurden(employees);
			}else if(processing.equalsIgnoreCase("PAYE")){
				processPaye(employees);
			}else{
				System.out.println("Invalid command.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private static ArrayList<Employee> readEmployees(String inputFile) throws FileNotFoundException{
		ArrayList<Employee> employees = new ArrayList<>();
		Scanner scanner = new Scanner(new File(inputFile));
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			if(line.trim().equals("") || line.startsWith("#"))
				continue;
			else{
				try{
					String[] details = line.split("\t");
					int tid = Integer.parseInt(details[0]);
					String name = details[1];
					String type = details[2];
					double rate = removeDollar(details[3]);
					double ytd = removeDollar(details[4]);
					String start = details[5];
					String end = details[6];
					double hours = Double.parseDouble(details[7]);
					double deductions = removeDollar(details[8]);
					if(type.equalsIgnoreCase("hourly"))
						employees.add(new HourlyEmployee(tid, name, rate, ytd, start, end, hours, deductions));
					else if(type.equalsIgnoreCase("salaried"))
						employees.add(new SalariedEmployee(tid, name, rate, ytd, start, end, hours, deductions));
				}catch(Exception e){
				}
			}
		}
		scanner.close();
		return employees;
	}
	private static void printPaySlips(ArrayList<Employee> employees){
		printTodayDate();
		Collections.sort(employees);
		for (Employee employee : employees) {
			employee.printPayslip();
		}
	}
	private static void processEmployee(ArrayList<Employee> employees){
		printTodayDate();
		for (Employee employee : employees) {
			employee.processEmployee();
		}
	}
	private static double removeDollar(String amount) throws Exception{
		if(amount.startsWith("$"))
			return Double.parseDouble(amount.substring(1));
		else
			throw new Exception("Invalid dollar amount");
	}
	private static void processBurden(ArrayList<Employee> employees){
		printTodayDate();
		if(employees.size() > 0){
			String start = Utility.dateToString(employees.get(0).getStart());
			String end  = Utility.dateToString(employees.get(0).getEnd());
			System.out.printf("%s to %s\n",start,end);
			double burden = 0;
			for (Employee employee : employees) {
				burden += employee.getGrossPay();
			}
			System.out.printf("Total Burden: $%.2f\n", burden);
		}else
			System.out.println("No employees in list.");
	}
	private static void processPaye(ArrayList<Employee> employees){
		printTodayDate();
		if(employees.size() > 0){
			String start = Utility.dateToString(employees.get(0).getStart());
			String end  = Utility.dateToString(employees.get(0).getEnd());
			System.out.printf("%s to %s\n",start,end);
			double paye = 0;
			for (Employee employee : employees) {
				paye += employee.getPayE();
			}
			System.out.printf("Total PAYE: $%.2f\n", paye);
		}else
			System.out.println("No employees in list.");
	}
	private static void printTodayDate(){
		System.out.println(Utility.dateToString(new Date()));
	}
}
