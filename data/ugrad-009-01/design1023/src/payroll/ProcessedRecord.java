package payroll;
import java.util.ArrayList;
public class ProcessedRecord{
	private ArrayList<Employee> record = new ArrayList<Employee>();
	public ProcessedRecord(EmployeeRecord record){
		for(int i = 0; i < record.size(); i++){
			Employee employee = record.get(i);
			Calculator calc = new Calculator(employee);
			employee.setGrossIncome(calc.getGrossIncome());
			employee.setPaye(calc.getPaye());
			employee.setNetIncome(calc.getNetIncome());
			employee.setEndYtd(calc.getYtd());
			this.record.add(employee);
		}
	}
	public Employee get(int index){
		return record.get(index);
	}
	public int size(){
		return record.size();
	}
	public ArrayList<Employee> getRecord(){
		return record;
	}
}
