package payroll;
import java.util.List;
public class Burden {
	private String _startdate;
	private String _enddate;
	private double _burden;
	public Burden(List<Employees> employees) {
		_startdate = employees.get(0).getStartDate();
		_enddate = employees.get(0).getEndDate();
		_burden = 0;
		setBurden(employees);
	}
	private void setBurden(List<Employees> employees) {
		for (Employees employee : employees) {
			_burden += employee.getGross();
		}
	}
	public void printBurden(){
		System.out.format("%s to %s%nTotal Burden: $%.2f%n",
				_startdate, _enddate, _burden);
	}
}
