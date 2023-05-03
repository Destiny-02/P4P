package payroll;
import java.util.LinkedList;
public class Burden {
	public float total_gross(LinkedList<String> list){
		float sum = 0;
		for (int i =0; i < list.size();i++){
			Employee emp = new Employee(list,i);
			float pre_sum = emp.get_gross();
			sum = sum + pre_sum;
		}
		return sum;
	}
	public void printBurden(LinkedList<String> list){
		Employee emp = new Employee(list,0);
		System.out.println(emp.get_period());
		System.out.printf("Total Burden: $%.2f\n",total_gross(list));
	}
}
