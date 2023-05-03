package payroll;
import java.util.ArrayList;
public class YearToDate extends Calculation {
	public YearToDate(ArrayList<Employees> empList) {
		super(empList);
	}
	public void calculate() {
		for (int i = 0; i < _empList.size(); i++) {
			double gross = _empList.get(i).getGross();
			double ytdBeforePay = _empList.get(i).getYTD();
			result = gross + ytdBeforePay;
			_empList.get(i).setCurrentYTD(result);
		}
	}
}