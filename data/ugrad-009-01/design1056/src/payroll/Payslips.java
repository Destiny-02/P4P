package payroll;
import java.util.Collections;
import java.util.LinkedList;
public class Payslips {
	public int[] getIdList(LinkedList<String> list){
		int[] arrayID = new int[list.size()];
		for (int i = 0; i < list.size();i++){
			Employee emp = new Employee(list,i);
			arrayID[i] = emp.get_id();
		}
		return arrayID;
	}
	public LinkedList<String> sortList_id(LinkedList<String> list){
		int[] arr = getIdList(list);
		for (int i = 0; i < arr.length; i++) {
	        for (int j = i + 1; j < arr.length; j++) {
	            int tmp = 0;
	            if (arr[i] > arr[j]) {
	                tmp = arr[i];
	                arr[i] = arr[j];
	                Collections.swap(list, i, j);
	                arr[j] = tmp;
	            }
	        }
	    }
		for (int i = 1; i < arr.length;i++){
			if (arr[i] == arr[i-1]){
				System.out.printf("WARNING: Same ID detected (%d) for more than one Employee.\n",arr[i]);
			}
		}
		return list;
	}
	public void printPayslips(LinkedList<String> linkedList){
		sortList_id(linkedList);
		for (int i = 0;i < linkedList.size();i++){
			Employee emp = new Employee(linkedList,i);
			System.out.printf("%d.%s, Period: %s. Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f Nett: $%.2f YTD: $%.2f\n"
					,emp.get_id(),emp.get_name(),emp.get_period(),emp.get_gross(),emp.get_PAYE(),
					emp.get_deduction(),emp.get_Nett(),emp.get_YTD());
			if (emp.get_Nett()<0){
				System.out.printf("WARNING: The Nett amount for %s is negative\n",emp.get_name());
			}
		}
	}
}
