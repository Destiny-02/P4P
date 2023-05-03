package payroll;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
public class Processing{
	public Processing(){
	}
	public void startProcessing(ArrayList<Employee> list, String args) throws MyExceptions{
		if(args.equals("Payslips")){
			payslip(list);
		}else if (args.equals("Employees")){
			employees(list);
		}else if (args.equals("Burden")){
			burden(list);
		}else if (args.equals("PAYE")){
			paye(list);
		}
	}
	private void payslip(ArrayList<Employee> list) throws MyExceptions{
		for(int i = 0; i<list.size();i++){
			double gross = getGrossAndYTD(i,list);
			double paye = payeCalculation(gross);
			list.get(i).setPaye(paye);
			double nett = gross - paye - list.get(i).getDeduction();
			if(nett<0){
				throw new MyExceptions("The amount to be paid is negative!!(ERROR)");
			}
			list.get(i).setNett(nett);
		}
		payslipSort(list);
		System.out.printf("%s%tY-%<tm-%<td\n","", new Date());
		for(Employee i:list){
			System.out.println(String.format("%d. %s %s Period: %s to %s. Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f Nett: $%.2f YTD: $%.2f",i.getTaxId(),i.getName2(),i.getName1(),i.getStart(),i.getEnd(),i.getGross(),i.getPaye(),i.getDeduction(),i.getNett(),i.getYTD()));
		}
	}
	private void employees(ArrayList<Employee> list){
		for(int i = 0; i<list.size();i++){
			getGrossAndYTD(i,list);
		}
		employeesSort(list);
		System.out.printf("%s%tY-%<tm-%<td\n","", new Date());
		for(Employee i:list){
			System.out.println(String.format("%s %s (%d) %s, $%.2f YTD:$%.2f",i.getName1(),i.getName2(),i.getTaxId(),i.getEmployment(),i.getRate(),i.getYTD()));
		}
	}
	private void burden(ArrayList<Employee> list){
		double totalGross= 0;
		for(int i = 0; i<list.size();i++){
			totalGross+=grossCalculation(i,list);
		}
		System.out.printf("%s%tY-%<tm-%<td\n","", new Date());
		System.out.printf("%s to %s\nTotal Burden: $%.2f\n", list.get(0).getStart(),list.get(0).getEnd(),totalGross);
	}
	private void paye(ArrayList<Employee> list){
		double paye=0;
		for(int i = 0; i<list.size();i++){
			double gross = getGrossAndYTD(i,list);
			paye += payeCalculation(gross);
		}
		System.out.printf("%s%tY-%<tm-%<td\n","", new Date());
		System.out.printf("%s to %s\nTotal PAYE: $%.2f\n", list.get(0).getStart(),list.get(0).getEnd(),paye);
	}
	private double getGrossAndYTD(int i, ArrayList<Employee> list){
		double gross = grossCalculation(i, list);
		list.get(i).setGross(gross);
		double oldValue = list.get(i).getYTD();
		list.get(i).setYTD(oldValue+gross);
		return gross;
	}
	private double grossCalculation(int i, ArrayList<Employee> list){
		double gross;
		String employment = list.get(i).getEmployment().toLowerCase();
		double rate = list.get(i).getRate();
		double hours = list.get(i).getHours();
		if (employment.equals("salaried")){
			gross = rate/52;
			return gross;
		}else if (employment.equals("hourly")){
			double extraHours = hours - 40;
			if((extraHours < 3)&&(extraHours>0)){
				gross = (rate*40)+(rate*1.5*extraHours);
			}else if(extraHours>=3){
				gross = (rate*40)+(rate*2*extraHours);
			}else{
				gross = hours*rate;
			}
		return gross;
		}else{
			throw new MyExceptions("Invlide input data!!(Should be Salaried or Hourly");
		}
	}
	private double payeCalculation(double gross){
		double paye,gross1,gross2;
		double annualGross = gross * 52;
		if(annualGross <= 14000){
			paye = annualGross * 0.105/52;
		}else if ((annualGross<=48000)&&(annualGross>14000)){
			gross1 = annualGross-14000;
			paye = (gross1 * 0.175 + 14000*0.105)/52;
		}else if ((annualGross<=70000)&&(annualGross>48000)){
			gross1 = annualGross-48000;
			gross2 = 48000-14000;
			paye = (gross1 * 0.3 + gross2*0.175 + 14000*0.105)/52;
		}else{
			gross1 = annualGross-70000;
			paye = (gross1 * 0.33+22000*0.3+34000*0.175+14000*0.105)/52;
		}
		return paye;
	}
	private void payslipSort(ArrayList<Employee> list){
		Collections.sort(list, new Comparator<Employee>(){
			public int compare(Employee e1,Employee e2){
				int id1 = e1.getTaxId();
				int id2 = e2.getTaxId();
				int value1 = id1-id2;
				if(value1 == 0){
					String name1a = e1.getName2();
					String name2a = e2.getName2();
					int value2 = name1a.compareTo(name2a);
					if(value2 == 0){
						String name1b = e1.getName1();
						String name2b = e2.getName1();
						return name1b.compareTo(name2b);
					}else{
						return value2;
					}
				}
				return value1;
			}
		});
	}
	private void employeesSort(ArrayList<Employee> list){
		Collections.sort(list, new Comparator<Employee>(){
			public int compare(Employee e1,Employee e2){
				String name1a = e1.getName1();
				String name2a = e2.getName1();
				int value1 = name1a.compareTo(name2a);
				if(value1 == 0){
					String name1b = e1.getName2();
					String name2b = e2.getName2();
					int value2 = name1b.compareTo(name2b);
					if(value2 == 0){
						int id1 = e1.getTaxId();
						int id2 = e2.getTaxId();
						return id1-id2;
					}else{
						return value2;
					}
				}
				return value1;
			}
		});
	}
}
