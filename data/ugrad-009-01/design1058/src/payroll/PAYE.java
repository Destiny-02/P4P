package payroll;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
public class PAYE implements ProcessType{
	Vector<Employee> _list;
	float _totalPaye;
	public PAYE(Vector<Employee> list) {
		Calculations c = new Calculations();
		_list = list;
		_totalPaye = c.calcTotalPaye(_list);
	}
	public void print() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String[] payPeriod = _list.get(0).getPayPeriod(_list.get(0));
		System.out.println(df.format(date));
		System.out.println(payPeriod[0]+" to "+payPeriod[1]);
		System.out.printf("Total PAYE: $%.2f\n",_totalPaye);
	}
}
