package payroll;
import java.util.ArrayList;
public class Burden extends Print{
	private double _total = 0;
	private double _gross = 0;
	public void process(ArrayList<Record> Database) {
		for (int i = 0; i<Database.size();i++) {
			if (Database.get(i).getEmployment().equals("Salaried")) {
				_gross = calcWeeklyGross(Database.get(i).getRate());
				_total = _total + _gross;
			}
			else {
				_gross = calcWeeklyGross(Database.get(i).getHours(),
						Database.get(i).getRate());
				_total = _total + _gross;
			}
		}
		printDate();
		printlnPeriod(Database.get(0).getStart(), Database.get(0).getEnd());
		printlnMoney("Burden", _total);
	}
	public double calcWeeklyGross(double rate) {
		return rate/52.0;
	}
	public double calcWeeklyGross(double hours, double rate) {
		if (hours<=40) {
			return rate*hours;
		}
		else if (hours<=43) {
			return 40*rate + (hours-40)*rate*1.5;
		}
		else {
			return 40*rate + 3*rate*1.5 + (hours-43)*rate*2;
		}
	}
}
