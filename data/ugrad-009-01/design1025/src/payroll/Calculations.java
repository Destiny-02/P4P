package payroll;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
public class Calculations {
	private Employees _employee;
	public Calculations() {}
	public Calculations(Employees employee) {
		_employee = employee;
	}
	public static void printDate() {
		SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String date = date1.format(cal.getTime());
		System.out.println(date);
	}
	public static ArrayList<?> sortList(ArrayList<Employees> employees, boolean TID) {
		if (TID) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (Employees employee: employees) {
				list.add(employee.getTID());
			}
			Collections.sort(list);
			return list;
		} else {
			ArrayList<String> list = new ArrayList<String>();
			for (Employees employee: employees) {
				list.add(employee.getName());
			}
			Collections.sort(list);
			return list;
		}
	}
	public float getGross() {
		String employment = _employee.getEmployment();
		float hours = _employee.getHours();
		float rate = _employee.getRate();
		if (employment.equals("Salaried")) {
			return (rate/52);
		}
		else {
			if (hours <= 40) {
				return (hours * rate);
			}
			else if (hours <= 43) {
				return (float) (40*rate + (hours - 40)*1.5*rate);
			}
			else {
				return (float) (40*rate + 4.5*rate + (hours - 43)*2*rate);
			}
		}
	}
	public float getPAYE() {
		Calculations person = new Calculations(_employee);
		float annualGross = person.getGross() * 52;
		float totalPAYE = 0;
		while (annualGross > 0) {
			if (annualGross <= 14000) {
				totalPAYE += annualGross * 0.105;
				annualGross = 0;
			}
			else if (annualGross <= 48000) {
				totalPAYE += (annualGross - 14000) * 0.175;
				annualGross = 14000;
			}
			else if (annualGross <= 70000) {
				totalPAYE += (annualGross - 48000) * .3;
				annualGross = 48000;
			}
			else {
				totalPAYE += (annualGross - 70000) * 0.33;
				annualGross = 70000;
			}
		}
		return totalPAYE/52;
	}
	public static void printWeek(ArrayList<Employees> employees) {
		employees.get(0).printStart();
		System.out.print(" to ");
		employees.get(0).printEnd();
		System.out.print("\n");
	}
}