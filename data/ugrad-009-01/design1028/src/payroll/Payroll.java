package payroll;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class Payroll {
	public static void main(String[] args) throws MyExceptions {
		Payroll e = new Payroll();
		ArrayList<Employee> list = e.storeData(args[0]);
		Processing p = new Processing();
		p.startProcessing(list, args[1]);
	}
	private ArrayList<Employee> storeData(String args) throws MyExceptions {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		try{
			BufferedReader input = new BufferedReader(new FileReader(args));
			String line;
			while((line = input.readLine())!=null){
				String[] check = line.split("");
				if((check[0].equals("#"))||(check[0].equals(""))){
				}else{
					String[] values = line.split("\\t");
					int tax = Integer.parseInt(values[0]);
					int taxId = tax;
					String[] name = values[1].split(" ");
					String name1 = name[0];
					String name2 = name[1];
					String employment = values[2];
					if(!(Character.isUpperCase(name1.charAt(0)))||!(Character.isUpperCase(name2.charAt(0)))||!(Character.isUpperCase(employment.charAt(0)))){
						input.close();
						throw new MyExceptions("Invalid data format!!");
					}
					String value = values[3].replaceAll("\\$","");
					double rate = Double.parseDouble(value);
					value = values[4].replaceAll("\\$", "");
					double ytd = Double.parseDouble(value);
					String start = values[5];
					String end = values[6];
					double hours = Double.parseDouble(values[7]);
					value = values[8].replaceAll("\\$", "");
					double deduction = Double.parseDouble(value);
					employees.add(new Employee(taxId, name1,name2, employment, rate,ytd,start,end,hours,deduction));
				}
			}
			input.close();
		}catch(IOException e){
			e.printStackTrace();
		}catch(MyExceptions e){
			e.printStackTrace();
		}
		return employees;
	}
}
