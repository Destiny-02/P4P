package payroll;
import java.util.Collections;
import java.util.LinkedList;
public class EmployeeProcess {
	public LinkedList<String> sortEmployeeProcess(LinkedList<String> list){
		LinkedList<String> list2 = new LinkedList<>();
		for (int i = 0; i < list.size();i++){
			Employee emp = new Employee(list,i);
			String result = (emp.get_nameEmp() + " ("+emp.get_id() +") "+ emp.get_salaried()+","
			+ " $" + String.format("%.2f",emp.get_rate()) + " YTD:$"+String.format("%.2f",emp.get_YTD()));
			list2.add(result);
		}
		Collections.sort(list2);
		return list2;
	}
	public void printEmployeeProcess(LinkedList<String> list){
		LinkedList<String> sList = sortEmployeeProcess(list);
		for (int i = 0; i < sList.size();i++){
			System.out.println(sList.get(i));
		}
	}
}
