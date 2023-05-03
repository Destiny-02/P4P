package payroll;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
public class Payroll {
	public static void main(String[] args) throws IOException,FileNotFoundException {
		List<Data> Payslips = new ArrayList<Data>();
		BufferedReader scan = new BufferedReader(new FileReader(args[0]));
		int tid;
		boolean check=false;
		double hours,rate,ytd,deduction;
		String input,first_name,last_name,employment_status,start ="",end="";
		String t1,t2,t3;
		String[] entry,name_entry;
		input=scan.readLine();
		while((input)!=null){
			if(input.equals("")||input.charAt(0)=='#'){
				input=scan.readLine();
				continue;
			}
			if(Character.isDigit(input.charAt(0))){
				check=true;
				entry=input.split("\t");
				if(entry.length<9){
					throw new RuntimeException("Missing Data");
				}
				tid=Integer.parseInt(entry[0]);
				name_entry=entry[1].split(",");
				last_name=name_entry[0];
				first_name=name_entry[1].substring(0, 0) + name_entry[1].substring(1);
				employment_status=entry[2];
				t1=entry[3].substring(0, 0) + entry[3].substring(1);
				rate=Double.parseDouble(t1);
				if(rate<0){throw new RuntimeException("Invalid entry ($"+rate+")");}
				if(!(entry[3].contains("$"))){throw new RuntimeException("Invalid entry ("+entry[3]+")");}
				t2=entry[4].substring(0, 0) + entry[4].substring(1);
				ytd=Double.parseDouble(t2);if(ytd<0){throw new RuntimeException("Invalid entry ($"+ytd+")");}
				if(!(entry[4].contains("$"))){throw new RuntimeException("Invalid entry ("+entry[4]+")");}
				start=entry[5];
				end=entry[6];
				hours=Double.parseDouble(entry[7]);if(hours<0){throw new RuntimeException("Invalid entry ("+hours+")");}
				t3=entry[8].substring(0, 0) + entry[8].substring(1);
				deduction=Double.parseDouble(t3);if(deduction<0){throw new RuntimeException("Invalid entry ($"+deduction+")");}
				if(!(entry[8].contains("$"))){throw new RuntimeException("Invalid entry ("+entry[8]+")");}
				input=scan.readLine();
				Payslips.add(new Data(tid,last_name,first_name,employment_status,rate,ytd,hours,deduction));
			}
		}
		if(!check){
			throw new RuntimeException("Empty File");
		}
		double earnings=0,tax=0,nett=0,burden=0,yTD,sum=0;
		String processing=args[1];
		if(processing.equals("Payslips")){
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			System.out.println(format.format(date));
			Collections.sort(Payslips);
			for(int i=0;i<Payslips.size();i++){
				Payslips nt=new Payslips(Payslips.get(i).TaxID,Payslips.get(i).firstName,Payslips.get(i).lastName,Payslips.get(i).Employment,Payslips.get(i).Rate,Payslips.get(i).YTD,Payslips.get(i).Hours,Payslips.get(i).Deduction);
				PayE pe=new PayE(Payslips.get(i).Employment,Payslips.get(i).Rate,Payslips.get(i).Hours);
				Burden bd=new Burden(Payslips.get(i).Employment,Payslips.get(i).Rate,Payslips.get(i).Hours);
				burden = bd.getGross();
				earnings=pe.calculateIncome();
				if(Payslips.get(i).Deduction>earnings){
					throw new RuntimeException("Deduction ("+Payslips.get(i).Deduction+") is more than earnings("+earnings+")");
				}
				tax=pe.getIncomeTax(earnings);
				Employee ep=new Employee(Payslips.get(i).TaxID,Payslips.get(i).firstName,Payslips.get(i).lastName,Payslips.get(i).Employment,Payslips.get(i).Rate,Payslips.get(i).YTD,Payslips.get(i).Hours);
				yTD=ep.calculate_newYTD(burden);
				nett=nt.giveNett(burden, tax);
				nt.printPayslips(start,end,yTD,burden,nett,tax);
			}
		}
		else if(processing.equals("Burden")){
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			System.out.println(format.format(date));
			for(int i=0;i<Payslips.size();i++){
				Burden bd=new Burden(Payslips.get(i).Employment,Payslips.get(i).Rate,Payslips.get(i).Hours);
				sum+=bd.getGross();
			}
			Burden bd=new Burden(Payslips.get(0).Employment,Payslips.get(0).Rate,Payslips.get(0).Hours);
			bd.printBurden(start,end,sum);
		}
		else if(processing.equals("Employees")){
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			System.out.println(format.format(date));
			for(int i=0;i<Payslips.size();i++){
				Burden bd=new Burden(Payslips.get(i).Employment,Payslips.get(i).Rate,Payslips.get(i).Hours);
				burden=bd.getGross();
				Employee ep=new Employee(Payslips.get(i).TaxID,Payslips.get(i).lastName,Payslips.get(i).firstName,Payslips.get(i).Employment,Payslips.get(i).Rate,Payslips.get(i).YTD,Payslips.get(i).Hours);
				ytd=ep.calculate_newYTD(burden);
				ep.printEmployees(ytd);
			}
		}
		else if(processing.equals("PAYE")){
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			System.out.println(format.format(date));
			for(int i=0;i<Payslips.size();i++){
				PayE pe=new PayE(Payslips.get(i).Employment,Payslips.get(i).Rate,Payslips.get(i).Hours);
				earnings=pe.calculateIncome();
				sum+=pe.getIncomeTax(earnings);
			}
			PayE pe=new PayE(Payslips.get(0).Employment,Payslips.get(0).Rate,Payslips.get(0).Hours);
			pe.printPAYE(start,end,sum);
		}
		else{
			throw new RuntimeException("Invalid Processing("+processing+")");
		}
	}
}
