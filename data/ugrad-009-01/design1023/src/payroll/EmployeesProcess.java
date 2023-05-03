package payroll;
import java.util.ArrayList;
import java.util.Collections;
public class EmployeesProcess extends Display implements Process{
	private ProcessedRecord records = null;
	private ArrayList<Employee> sortedRecord = new ArrayList<Employee>();
	private ArrayList<String> sortedNames = new ArrayList<String>();
	private String date = getDate();
	public void execute(ProcessedRecord records) {
		this.records = records;
		sort();
	}
	public void display() {
		System.out.println(date);
		for(Employee employee: sortedRecord){
			System.out.println(employee.getName()+" ("+employee.getTid()+") "+employee.getPayType()
				+", $"+getDecimalFormat(employee.getRate())+" YTD:$"+getDecimalFormat(employee.getEndYtd()));
		}
	}
	private void sort(){
		for(int i=0; i<records.size(); i++){
			String name = records.get(i).getName();
			sortedNames.add(name);
		}
		Collections.sort(sortedNames);
		for(int i=0; i<sortedNames.size();i++){
			for(int j=0; j<records.size(); j++){
				Employee employee = records.get(j);
				if(employee.getName().equals(sortedNames.get(i))){
					sortedRecord.add(employee);
				}
			}
		}
	}
}
