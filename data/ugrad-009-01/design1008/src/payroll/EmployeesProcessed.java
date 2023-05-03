package payroll;
import java.util.ArrayList;
import java.text.DecimalFormat;
public class EmployeesProcessed extends Processing {
	private int _taxid;
	private String _firstname, _lastname, _employment, _startperiod, _endperiod;
	private double _rate, _yeartodate, _hours, _deduction, _gross, _paye, _nett, _currentYTD;
	private Employees temp = new Employees(_taxid, _lastname, _firstname, _employment, _rate, _yeartodate, _startperiod, _endperiod,
			_hours, _deduction, _gross, _paye, _nett, _currentYTD);
	public EmployeesProcessed(ArrayList<Employees> empList) {
		super(empList);
	}
	public void sort() {
		for (int i = 0; i <_empList.size(); i++) {
			for (int j = 0; j < _empList.size()-1; j++) {
				if (_empList.get(j).getLastname().compareTo(_empList.get(j+1).getLastname())>0) {
					temp = _empList.get(j);
					_empList.set(j, _empList.get(j+1));
					_empList.set(j+1, temp);
				}
			}
		}
	}
	public void printDate() {
		System.out.println(dateFormat.format(date));
	}
	public void print() {
		DecimalFormat df = new DecimalFormat("0.00");
		for (int i = 0; i < _empList.size(); i++) {
			System.out.println(_empList.get(i).getLastname() + ", " + _empList.get(i).getFirstname() + " (" + _empList.get(i).getTaxid() +
			") " + _empList.get(i).getEmployment() + ", $" + df.format(_empList.get(i).getRate()) + " YTD:$" + df.format(_empList.get(i).getCurrentYTD()));
		}
	}
}
