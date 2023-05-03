package payroll;
import java.util.ArrayList;
public class PAYE extends Print {
	private double _total = 0;
	private double _PAYE = 0;
	private double _yearlyGross = 0;
	public void process(ArrayList<Record> Database) {
		for (int i = 0; i<Database.size();i++) {
			if (Database.get(i).getEmployment().equals("Salaried")) {
				_PAYE = calcWeeklyPAYE(Database.get(i).getRate());
				_total = _total + _PAYE;
			}
			else {
				Burden b = new Burden();
				_yearlyGross = b.calcWeeklyGross(Database.get(i).getHours(),
						Database.get(i).getRate())*52.0;
				_PAYE = calcWeeklyPAYE(_yearlyGross);
				_total = _total + _PAYE;
			}
		}
		printDate();
		printlnPeriod(Database.get(0).getStart(), Database.get(0).getEnd());
		printlnMoney("PAYE", _total);
	}
	public double calcWeeklyPAYE(double yearlyGross) {
		if(yearlyGross<=14000) {
			return (yearlyGross*0.105)/52.0;
		}
		else if (yearlyGross<=48000) {
			return (1470.0 + (yearlyGross-14000)*0.175)/52.0;
		}
		else if (yearlyGross<=70000) {
			return (7420.0 + (yearlyGross-48000)*0.3)/52.0;
		}
		else {
			return (14020.0 + (yearlyGross-70000)*0.33)/52.0;
		}
	}
}

