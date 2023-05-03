package payroll;
import java.util.ArrayList;
import java.util.Collections;
public class Payslips extends Print {
	private double _gross= 0;
	private double _tax = 0;
	public void process(ArrayList<Record> Database) {
		Record rec = new Record();
		Record.OrderByFID orderFID = rec.new OrderByFID();
		Collections.sort(Database, orderFID);
		printDate();
		for (int i = 0; i<Database.size();i++) {
			System.out.print(Database.get(i).getFID() + ". ");
			System.out.print(Database.get(i).getFirstName());
			System.out.print(" " + Database.get(i).getLastName() + ", ");
			System.out.print("Period: ");
			printPeriod(Database.get(i).getStart(), Database.get(i).getEnd());
			printGross(Database.get(i));
			printPAYE(Database.get(i));
			System.out.print(", Deductions: ");
			printMoney(Database.get(i).getDeduction());
			System.out.print(" Nett: ");
			printMoney(_gross - _tax - Database.get(i).getDeduction());
			System.out.print(" YTD: ");
			Employees emp = new Employees();
			double YTD = emp.calcYTD(Database.get(i));
			printlnMoney(YTD);
		}
	}
	private void printGross(Record rec) {
		Burden bur = new Burden();
		System.out.print(". Gross: ");
		if (rec.getEmployment().equals("Salaried")) {
			_gross = bur.calcWeeklyGross(rec.getRate());
			printMoney(_gross);
		}
		else {
			_gross = bur.calcWeeklyGross(rec.getHours(),rec.getRate());
			printMoney(_gross);
		}
	}
	private void printPAYE(Record rec) {
		PAYE paye = new PAYE();
		System.out.print(", PAYE: ");
		if (rec.getEmployment().equals("Salaried")) {
			_tax = paye.calcWeeklyPAYE(rec.getRate());
			printMoney(_tax);
		}
		else {
			_tax = paye.calcWeeklyPAYE(_gross*52);
			printMoney(_tax);
		}
	}
}
