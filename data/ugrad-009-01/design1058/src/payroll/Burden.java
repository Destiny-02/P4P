package payroll;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
public class Burden implements ProcessType{
	Vector<Employee> _list;
	float _totalGross;
	public Burden(Vector<Employee> list) {
		_list = list;
		Calculations c = new Calculations();
		_totalGross = c.calcTotalGross(list);
	}
	public void print() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String [] payPeriod = _list.get(0).getPayPeriod(_list.get(0));
		System.out.println(df.format(date));
		System.out.println(payPeriod[0]+" to "+payPeriod[1]);
		System.out.printf("Total Burden: $%.2f\n",_totalGross);
	}
}
