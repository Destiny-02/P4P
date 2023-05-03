package payroll;
import java.util.Arrays;
public class EmployeeList extends PayrollList<Employee>{
	public Employee getEmployee(int index){
		Employee e = _list.get(index);
		if (e instanceof SalariedEmployee){
			return (SalariedEmployee) e;
		}
		else{
			return (HourlyEmployee) e;
		}
	}
	public void checkDuplicateTID() throws PayrollUserException{
		int[] TIDList=new int[_list.size()];
		int i=0;
		for(Employee e: _list){
			TIDList[i]=e.getTID();
			i++;
		}
		Arrays.sort(TIDList);
		for (int j=0; j<TIDList.length-1;j++){
			if (TIDList[j]==TIDList[j+1]){
				int TID = TIDList[j];
				String msg="Error: Duplicate Tax ID detected. The ID: " + TID + " has been used more than once.";
				throw new PayrollUserException(msg);
			}
		}
	}
}
