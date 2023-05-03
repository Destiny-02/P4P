package payroll;
import java.util.Comparator;
public abstract class Employee {
	private final int _TID;
	private final String _name;
	private String _startDate;
	private String _endDate;
	protected Employee(int TID,String name,String start,String end){
		_name = name;
		_TID = TID;
		_startDate = start;
		_endDate = end;
	}
	protected String getName() {
		return _name;
	}
	protected int getTID() {
		return _TID;
	}
	public void printPayPeriod(){
		System.out.println(_startDate+" to "+_endDate);
	}
	protected String[] payslipProcess(){
		String[] name = _name.split(",");
		String[] a = new String[]{Integer.toString(_TID),name[1].trim(),name[0].trim(),_startDate,_endDate};
		return a;
	}
	protected abstract void printEmployee();
	protected abstract void printPayslip();
	protected static class NameCompare implements Comparator<Employee>{
		public int compare(Employee one, Employee two){
			int a = one._name.compareTo(two._name);
			if(a==0){
				if(one._TID<two._TID){
					return -1;
				}else{
					return 1;
				}
			}else{
				return one._name.compareTo(two._name);
			}
		}
	}
	protected static class TIDCompare implements Comparator<Employee>{
		public int compare(Employee one, Employee two) {
			if(one._TID==two._TID){
				return 0;
			}else if(one._TID<two._TID){
				return -1;
			}else{
				return 1;
			}
		}
	}
}
