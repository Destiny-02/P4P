package payroll;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Payroll {
	public static void main(String[] args) throws NumberFormatException, IOException{
		FileInputStream fstream = new FileInputStream(args[0]);
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String line;
		if(args[1].equals("PAYE")){
			ArrayList<String> dates = new ArrayList<String>();
			double totaltax = 0;
			double t = 0;
			while ((line = br.readLine()) != null)   {
				if(line.isEmpty()){
					continue;
				}
				if (Character.isDigit(line.charAt(0))){
					Payments tax = new Payments(line);
					t = tax.PAYEtax();
					totaltax = totaltax + t;
					String[] parts = line.split("\\t");
					dates.add(parts[5]);
					dates.add(parts[6]);
				}
			}
			Display d = new Display(totaltax,dates);
			d.displayPAYE();
		}else if(args[1].equals("Employees")){
			ArrayList<String> names = new ArrayList<String>();
			while ((line = br.readLine()) != null)   {
				if(line.isEmpty()){
					continue;
				}
				if (Character.isDigit(line.charAt(0))){
					Employees emp = new Employees(line);
					names.add(emp.employeeLine(line));
				}
			}
			Collections.sort(names);
			Display n = new Display(names);
			n.displayEmployees(names);
		}else if(args[1].equals("Payslips")){
			ArrayList<String> org = new ArrayList<String>();
			ArrayList<Integer> taxID = new ArrayList<Integer>();
			ArrayList<String> names = new ArrayList<String>();
			while ((line = br.readLine()) != null)   {
				if(line.isEmpty()){
					continue;
				}
				if (Character.isDigit(line.charAt(0))){
					Payslip p = new Payslip(line);
					names.add(p.payslipLine(line));
					Employees taxid = new Employees(line);
					taxID.add(taxid.gettaxID());
				}
			}
			Collections.sort(taxID);
			Payslip o = new Payslip();
			org = o.organisedlist(taxID,names);
			Display n = new Display(org);
			n.displayPayslip(org);
		}else if(args[1].equals("Burden")){
			double weekpay = 0;
			double totalgross = 0;
			List<String> dates = new ArrayList<String>();
			while ((line = br.readLine()) != null)   {
				if(line.isEmpty()){
					continue;
				}
				if (Character.isDigit(line.charAt(0))){
					String[] parts = line.split("\\t");
					parts[3] = parts[3].replace("$", "");
					Income pay = new Income(line);
					weekpay = pay.GrossPay(Double.parseDouble(parts[3]),Double.parseDouble(parts[7]));
					totalgross = totalgross + weekpay;
					dates.add(parts[5]);
					dates.add(parts[6]);
				}
			}
			Display d = new Display(totalgross,dates);
			d.displayBurden();
		}else{
			System.out.println("!!Error!! None of the options you type out were valid.");
		}
	}
}

