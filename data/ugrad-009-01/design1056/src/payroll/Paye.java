package payroll;
import java.util.LinkedList;
public class Paye{
	public float total_Paye(LinkedList<String> list){
		float sum = 0;
		for (int i =0; i < list.size();i++){
			Employee emp = new Employee(list,i);
			float pre_paye = emp.get_PAYE();
			sum = sum + pre_paye;
		}
		return sum;
	}
	public void printPaye(LinkedList<String> list){
		Employee emp = new Employee(list,0);
		System.out.println(emp.get_period());
		System.out.printf("Total PAYE: $%.2f\n",total_Paye(list));
	}
}

