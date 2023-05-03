package payroll;
import java.util.ArrayList;
import java.util.Collections;
public class Employees extends Print {
	double _YTD = 0;
	public void process(ArrayList<Record> Database) {
		Record rec = new Record();
		Record.OrderByLastName orderLast = rec.new OrderByLastName();
		Collections.sort(Database, orderLast);
		printDate();
		for (int i = 0; i<Database.size();i++) {
			System.out.print(Database.get(i).getLastName() + ", ");
			System.out.print(Database.get(i).getFirstName());
			System.out.print(" (" + Database.get(i).getFID() + ") ");
			System.out.print(Database.get(i).getEmployment() + ", ");
			printMoney(Database.get(i).getRate());
			System.out.print(" YTD:");
			_YTD = calcYTD(Database.get(i));
			printlnMoney(_YTD);
		}
	}
	public double calcYTD(Record person) {
		Burden bur = new Burden();
		if (person.getEmployment().equals("Salaried")) {
			return person.getYTD() + bur.calcWeeklyGross(person.getRate());
		}
		else {
			return person.getYTD() +
					bur.calcWeeklyGross(person.getHours(),person.getRate());
		}
	}
}

