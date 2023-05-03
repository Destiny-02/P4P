package payroll;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
public class Payroll {
	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream input =new FileInputStream(args[0]);
		Scanner scanner=new Scanner(input);
		EmployeeList List=new EmployeeList();
		while (scanner.hasNextLine()){
			String line=scanner.nextLine();
			if(line.isEmpty()||line.contains("#")){}
			else{
			List.addLast(Reader(line));
			}
			}
		scanner.close();
		if(args[1].equals("Payslips")){List.printPayslips();}
		else if(args[1].equals("Employees")){List.printEmployees();}
		else if(args[1].equals("Burden")){List.printTotalBurden();}
		else if(args[1].equals("PAYE")){List.printTotalPAYE();}
		else{System.out.println("Wrong Input");}
	}
	private static Employee Reader(String a){
		String[] person=a.split(", |\t");
		int TID =  Integer.parseInt(person[0]);
		String FamilyName=person[1];
		String GivenName = person[2];
		Employment state=new Employment(person[3],Double.parseDouble(person[4].substring(1,person[4].length())));
		double YTD=Double.parseDouble(person[5].substring(1,person[5].length()));
		String start=person[6];
		String end=person[7];
		double Hours=Double.parseDouble(person[8]);
		double Deduction=Double.parseDouble(person[9].substring(1,person[9].length()));
		return new Employee(TID,FamilyName,GivenName,state,YTD,Hours,Deduction,start,end);
	}
}
