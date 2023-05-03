package payroll;
import java.util.ArrayList;
import java.text.DecimalFormat;
public class Payslips extends Processing {
	private int _taxid;
	private String _firstname, _lastname, _employment, _startperiod, _endperiod;
	private double _rate, _yeartodate, _hours, _deduction, _gross, _paye, _nett, _currentYTD;
	private Employees temp = new Employees(_taxid, _lastname, _firstname, _employment, _rate, _yeartodate, _startperiod, _endperiod,
			_hours, _deduction, _gross, _paye, _nett, _currentYTD);
	public Payslips(ArrayList<Employees> empList) {
		super(empList);
	}
	public void sort() {
		for (int i = 0; i <_empList.size(); i++) {
			for (int j = 0; j < _empList.size()-1; j++) {
				if (_empList.get(j).getTaxid() >= _empList.get(j+1).getTaxid()) {
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
			System.out.println(_empList.get(i).getTaxid() + ". " + _empList.get(i).getFirstname() + " " + _empList.get(i).getLastname() +
			", Period: " + _empList.get(i).getStart() + " to " + _empList.get(i).getEnd() + ". Gross: $" + df.format(_empList.get(i).getGross()) +
			", PAYE: $" + df.format(_empList.get(i).getPaye()) + ", Deductions: $" + df.format(_empList.get(i).getDeduction()) + " Nett: $" +
			df.format(_empList.get(i).getNett()) + " YTD: $" + df.format(_empList.get(i).getCurrentYTD()));
		}
	}
}
