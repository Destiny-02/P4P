package payroll;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
public class Payroll{
	public static void printDate(){
		Date todayDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String DatePrint = format.format(todayDate);
		System.out.println(DatePrint);
	}
	public static void main(String[] args) throws IOException {
		FileInputStream in = null;
		String currentline;
		String[] temp;
		LinkedList<Employee> inputFiles=new LinkedList();
		try{
			in = new FileInputStream(args[0]);
			Scanner scanner = new Scanner(in);
			while(scanner.hasNextLine()){
				currentline = scanner.nextLine();
				temp = currentline.split("\t");
				if (temp.length==9){
					if(temp[1].equals("Name")){
						break;
					}
				}
			}
			while(scanner.hasNextLine()){
				currentline = scanner.nextLine();
				temp = currentline.split("\t");
				if(temp.length==9){
					Employee putin=new Employee(temp);
					inputFiles.add(putin);
				}
			}
		}finally{
			if(in!=null){
				in.close();
			}
		}
		Burden burden=new Burden(inputFiles.get(0));
		PAYE PAYE=new PAYE(inputFiles.get(0));
		LinkedList<Processing> informationAfterProcessing= new LinkedList();
		for (int i = 0; i<inputFiles.size();i++){
			Processing employeeInformationAfterProcessing=new Processing(inputFiles.get(i));
			informationAfterProcessing.add(employeeInformationAfterProcessing);
			burden.Add(employeeInformationAfterProcessing.getGross());
			PAYE.Add(employeeInformationAfterProcessing.getPAYE());
		}
		if (args[1].equals("Payslips")){
			Collections.sort(informationAfterProcessing);
			printDate();
			for(int i = 0;i<informationAfterProcessing.size();i++){
				informationAfterProcessing.get(i).Payslipsprocessing();
			}
		}else if(args[1].equals("Employees")){
			printDate();
			for (int i = 0; i<inputFiles.size();i++){
				Processing employeeInformationAfterProcessing=new Processing(inputFiles.get(i));
				informationAfterProcessing.add(employeeInformationAfterProcessing);
				employeeInformationAfterProcessing.Employeesprocess();
			}
		}
		else if(args[1].equals("Burden")){
			printDate();
			burden.Processing();
		}else if(args[1].equals("PAYE")){
			printDate();
			PAYE.Processing();
		}
	}
}

