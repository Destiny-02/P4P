package payroll;
import java.util.ArrayList;
public class Employees {
	private final int _taxID;
	private final String _name;
	private final String _employment;
	private final float _rate;
	private final float _ytd;
	private final String _start;
	private final String _end;
	private final float _hours;
	private final float _deductions;
	public int getTID() {
		return _taxID;
	}
	public String getName() {
		return _name;
	}
	public static int find(Object input, ArrayList<Employees> employees, boolean TID) {
		if (TID) {
			for (int i = 0; i < employees.size(); i++) {
				if (employees.get(i).getTID() == (int)input) {
					return i;
				}
			}
			return -1;
		} else {
			for (int i = 0; i < employees.size(); i++) {
				if (employees.get(i).getName().equals(input)) {
					return i;
				}
			}
			return -1;
		}
	}
	public String getEmployment() {
		return _employment;
	}
	public float getRate() {
		return _rate;
	}
	public float getYTD() {
		return _ytd;
	}
	public void printStart() {
		System.out.print(_start);
	}
	public void printEnd() {
		System.out.print(_end);
	}
	public float getHours() {
		return _hours;
	}
	public float getDeductions() {
		return _deductions;
	}
	public Employees(String line) {
		String[] details = line.split("\t");
		_taxID = Integer.valueOf(details[0]);
		_name = details[1];
		_employment = details[2];
		_rate = Float.valueOf(details[3].substring(1));
		_ytd = Float.valueOf(details[4].substring(1));
		_start = details[5];
		_end = details[6];
		_hours = Float.valueOf(details[7]);
		_deductions = Float.valueOf(details[8].substring(1));
	}
}
