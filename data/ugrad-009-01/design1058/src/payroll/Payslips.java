package payroll;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;
public class Payslips implements ProcessType {
	Vector<Employee> _list;
	public Payslips(Vector<Employee> list) {
		_list = list;
		Calculations c = new Calculations();
		for (int i = 0; i < _list.size(); i++) {
			_list.get(i).setGross(c.calcGross(_list.get(i)));
			_list.get(i).setPAYE(c.calcPAYE(_list.get(i)));
			try {
				_list.get(i).setNett(c.calcNett(_list.get(i)));
			} catch (InvalidDataException e) {
				e.printStackTrace();
			}
			_list.get(i).setYTD(c.calcNewYTD(_list.get(i)));
		}
	}
	public void print() {
		Collections.sort(_list);
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(df.format(date));
		for (int i = 0; i < _list.size(); i++) {
			String[] info = _list.get(i).getPayslipInfo(_list.get(i));
			System.out.print(info[0]+". ");
			System.out.print(info[1]+" ");
			System.out.print(info[2]+", ");
			System.out.print("Period: "+info[3]+" to "+info[4]+". ");
			System.out.printf("Gross: $%.2f, ",Float.valueOf(info[5]));
			System.out.print("PAYE: $"+info[6]+", ");
			System.out.printf("Deductions: $%.2f ",Float.valueOf(info[7]));
			System.out.print("Nett: $"+info[8]+" ");
			System.out.print("YTD: $"+info[9]);
			System.out.println();
		}
	}
}
