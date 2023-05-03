package payroll;
import java.util.ArrayList;
public class PayslipsProcess extends Display implements Process{
	private ArrayList<Employee> records = new ArrayList<Employee>();
	private String date = getDate();
	public void execute(ProcessedRecord records) {
		this.records = records.getRecord();
		sort();
	}
	public void display() {
		System.out.println(date);
		for(Employee employee: records){
			System.out.println(employee.getTid()+". "+employee.getName()
				+", Period: "+employee.getStart()+" to "+employee.getEnd()
				+". Gross: $"+getDecimalFormat(employee.getGrossIncome())+", PAYE: $"
				+getDecimalFormat(employee.getPaye())+", Deductions: $"+getDecimalFormat(employee.getDeductions())
				+" Nett: $"+getDecimalFormat(employee.getNetIncome())+" YTD: $"+getDecimalFormat(employee.getEndYtd()));
		}
	}
	private void sort(){
		int j;
		Employee tempEmployee;
		for (int i = 1; i < records.size(); i++) {
			tempEmployee = records.get(i);
			j = i;
			while (j > 0 && records.get(j - 1).getTid() > tempEmployee.getTid()) {
				records.set(j, records.get(j-1));
				j--;
			}
			records.set(j, tempEmployee);
		}
		for(Employee employee: records){
			String[] names = employee.getName().split(", ");
			employee.setName(names[1]+" "+names[0]);
		}
	}
}
