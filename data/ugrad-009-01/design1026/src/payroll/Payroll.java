package payroll;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
class IDComparator implements Comparator<Employee> {
	@Override
	public int compare(Employee a, Employee b) {
		double daTID = Double.parseDouble(a.getTID());
		double dbTID = Double.parseDouble(b.getTID());
		int ans= daTID < dbTID ? -1 : daTID == dbTID ? 0 : 1;
		if (ans == 0){
			try {
				throw new customExceptions("Please delete any duplicate tax IDs or check input carefully below");
			} catch (customExceptions e) {
				e.printStackTrace();
			}
		}
		return ans;
	}
}
class NameComparator implements Comparator<Employee> {
	@Override
	public int compare(Employee a, Employee b) {
		int ans= a.getName().compareTo(b.getName());
		if (ans == 0){
			try {
				throw new customExceptions("Please delete any duplicate instances of employees below");
			} catch (customExceptions e) {
				e.printStackTrace();
			}
		}
		return ans;
	}
}
public class Payroll extends FormatProcessing{
	public static void main(String[] args) throws IOException, customExceptions{
		BufferedReader s = null;
		List<Employee> records = new ArrayList<Employee>();
		try {
			s = new BufferedReader(new FileReader(args[0]));
			String line = null;
			while ((line = s.readLine())!= null) {
				if (!line.contains("#")& !line.equals("")){
					if(!line.contains("\t")){
						throw new customExceptions("Please separate input entries using the tab key");
					}
					String[] info = line.split("\t");
					if (info.length < 9){
						throw new customExceptions("Missing employee data detected. Please check input file");
					}
					if (!line.contains("$")){
						throw new customExceptions("Please enter character '$' before any monetary value");
					}
					Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(info[5]);
					Date date2= new SimpleDateFormat("yyyy-MM-dd").parse(info[6]);
					if (date1.compareTo(date2)>0){
						throw new customExceptions("Please enter an End date later than the Start date");
					}
					if (line.contains("Hourly")){
						Employee entry =new hourlyEmployee(info[0], info[1], info[2],info[3],info[4],info[5],info[6],info[7],info[8]);
						records.add(entry);
					}
					if (line.contains("Salaried")){
						Employee entry =new salariedEmployee(info[0], info[1], info[2],info[3],info[4],info[5],info[6],info[7],info[8]);
						records.add(entry);
					}
				}
			}
			switch (args[1]) {
			case "Payslips":
				outputdate();
				Collections.sort(records, new IDComparator());
				int i = 0;
				while (i < records.size()) {
					System.out.println(records.get(i));
					i++;
				}
				break;
			case "Employees":
				outputdate();
				Collections.sort(records, new NameComparator());
				int j = 0;
				while (j < records.size()) {
					System.out.println(records.get(j).getName()+" ("+records.get(j).getTID()+") "+records.get(j).getType()+", "+decimalFormat(records.get(j).getRate())+" YTD:"+records.get(j).processYTD());
					j++;
				}
				break;
			case "Burden":
				outputdate();
				int k = 0;
				System.out.println(records.get(k).processTimePeriod());
				double total3 = 0;
				while (k < records.size()) {
					String sans = records.get(k).processGross().substring(1);
					double ians = Double.parseDouble(sans);
					total3 += ians;
					k++;
				}
				System.out.println("Total Burden: $"+ total3);
				break;
			case "PAYE":
				outputdate();
				int l = 0;
				System.out.println(records.get(l).processTimePeriod());
				double total4 = 0;
				while (l < records.size()) {
					String sans = records.get(l).processPAYE().substring(1);
					double ians = Double.parseDouble(sans);
					total4 += ians;
					l++;
				}
				System.out.println("Total PAYE: $"+ total4);
				break;
			default: System.out.println("Invalid processing request. Please try again");
			break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (customExceptions e){
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		finally {
			if (s != null) {
				s.close();
			}
		}
	}
}
