package payroll;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;
public class Employees implements ProcessType {
	Vector<Employee> _list;
	public Employees(Vector<Employee> list) {
		_list = list;
		Calculations c = new Calculations();
		for (int i = 0; i < _list.size(); i++) {
			_list.get(i).setGross(c.calcGross(_list.get(i)));
			_list.get(i).setYTD(c.calcNewYTD(_list.get(i)));
		}
	}
	public void print() {
		Collections.sort(_list, new Employee(). new NameComparator());
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(df.format(date));
		for (int i = 0; i < _list.size(); i++) {
			String[] info = _list.get(i).getEmployeesData(_list.get(i));
			System.out.print(info[0]+", "+info[1]+" ");
			System.out.print("("+info[2]+") ");
			System.out.print(info[3]+", ");
			System.out.printf("$%.2f ",Float.valueOf(info[4]));
			System.out.printf("YTD:$%.2f",Float.valueOf(info[5]));
			System.out.println();
		}
	}
}
