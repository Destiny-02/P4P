package payroll;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Date;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class Payroll {
	public static void main(String[] args) throws IOException{
		ArrayList<Integer> tIDtemp=new ArrayList<Integer>();
		ArrayList<String> familyNameTemp=new ArrayList<String>() ;
		ArrayList<String> givenNameTemp=new ArrayList<String>() ;
		ArrayList<String> employmentTemp=new ArrayList<String>() ;
		ArrayList<Double> rateTemp= new ArrayList<Double>();
		ArrayList<Double> yTDTemp=new ArrayList<Double>();
		ArrayList<String> startDateTemp=new ArrayList<String> ();
		ArrayList<String> endDateTemp=new ArrayList<String>() ;
		ArrayList<Double> hoursTemp= new ArrayList<Double>();
		ArrayList<Double> deductionsTemp=new ArrayList<Double>();
		String line = new String();
		@SuppressWarnings("resource")
		BufferedReader input = new BufferedReader(new FileReader(args[0]));
		line = input.readLine();
		while(!Character.isDigit(line.charAt(0))){
			line = input.readLine();
			if(Character.isDigit(line.charAt(0))){
				break;
			}
		}
		while ((line!=null)){
			if ((line.isEmpty() || line.trim().equals("") || line.trim().equals("\n"))){
				line = input.readLine();
			}
			if (!Character.isDigit(line.charAt(0))){
				new ProcessingPayroll(tIDtemp,familyNameTemp,givenNameTemp,employmentTemp,rateTemp,yTDTemp,startDateTemp,endDateTemp,hoursTemp,deductionsTemp);
				break;
			}
			String[] splitline = line.split("[\t,]");
			int tIDConvert = Integer.parseInt(splitline[0]);
			tIDtemp.add(tIDConvert);
			familyNameTemp.add(splitline[1]);
			givenNameTemp.add(splitline[2]);
			employmentTemp.add(splitline[3]);
			String rateSplit = splitline[4].replaceAll("\\$", "");
			double rateConvert = Double.parseDouble(rateSplit);
			rateTemp.add(rateConvert);
			String yTDSplit = splitline[5].replaceAll("\\$", "");
			double yTDConvert = Double.parseDouble(yTDSplit);
			yTDTemp.add(yTDConvert);
			startDateTemp.add(splitline[6]);
			endDateTemp.add(splitline[7]);
			double hoursConvert = Double.parseDouble(splitline[8]);
			hoursTemp.add(hoursConvert);
			String deductionsSplit = splitline[9].replaceAll("\\$", "");
			double deductionsConvert = Double.parseDouble(deductionsSplit);
			deductionsTemp.add(deductionsConvert);
			if (rateConvert<0){
				System.out.print("Error, negative $ rate is not valid for:");
				System.out.print(tIDConvert);
				System.out.print(".");
				System.out.print(splitline[1]);
				System.out.print(splitline[2]);
			}
			if (yTDConvert<0){
				System.out.println("Error, negative $ Year to date is not valid for:");
				System.out.print(tIDConvert);
				System.out.print(".");
				System.out.print(splitline[1]);
				System.out.print(splitline[2]);
			}
			if (hoursConvert<0){
				System.out.println("Error, negative hours is not valid for:");
				System.out.print(tIDConvert);
				System.out.print(".");
				System.out.print(splitline[1]);
				System.out.print(splitline[2]);
			}
			if (hoursConvert==0){
				System.out.println("Warning, 0 hours is recoginsed for:");
				System.out.print(tIDConvert);
				System.out.print(".");
				System.out.print(splitline[1]);
				System.out.print(splitline[2]);
			}
			if (deductionsConvert<0){
				System.out.println("Error, negative $ deductions is not valid for:");
				System.out.print(tIDConvert);
				System.out.print(".");
				System.out.print(splitline[1]);
				System.out.print(splitline[2]);
			}
			line = input.readLine();
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		String processingArgument =args[1];
		if (processingArgument.equals("Payslips")){
			PayslipsProcessing payslipsObj= new PayslipsProcessing(tIDtemp,familyNameTemp,givenNameTemp,employmentTemp,rateTemp,yTDTemp,startDateTemp,endDateTemp,hoursTemp,deductionsTemp);
			payslipsObj.PayslipsProcessingMethod();
		}
		if (processingArgument.equals("Burden")){
			BurdenProcessing burdenObj= new BurdenProcessing(tIDtemp,familyNameTemp,givenNameTemp,employmentTemp,rateTemp,yTDTemp,startDateTemp,endDateTemp,hoursTemp,deductionsTemp);
			burdenObj.burdenProcessingMethodTotal();
		}
		if (processingArgument.equals("PAYE")){
			PAYEProcessing payeObj= new PAYEProcessing(tIDtemp,familyNameTemp,givenNameTemp,employmentTemp,rateTemp,yTDTemp,startDateTemp,endDateTemp,hoursTemp,deductionsTemp);
			payeObj.PAYEProcessingTotal();
		}
		if (processingArgument.equals("Employees")){
			EmployeesProcessing employeesObj= new EmployeesProcessing(tIDtemp,familyNameTemp,givenNameTemp,employmentTemp,rateTemp,yTDTemp,startDateTemp,endDateTemp,hoursTemp,deductionsTemp);
			employeesObj.employeeProcessingMethod();
		}
	}
}

