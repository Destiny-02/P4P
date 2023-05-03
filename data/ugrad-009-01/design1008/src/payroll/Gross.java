package payroll;
import java.util.ArrayList;
public class Gross extends Calculation {
	public Gross (ArrayList<Employees> empList) {
		super(empList);
	}
	public void calculate() {
		for (int i = 0; i < _empList.size(); i++) {
			String str = _empList.get(i).getEmployment();
			if ((str.toLowerCase()).equals("hourly")) {
				double hours = _empList.get(i).getHours();
				double rate = _empList.get(i).getRate();
				if (hours <= 40) {
					result = (Math.round(100.0 *rate * hours))/100.0;
					_empList.get(i).setGross(result);
				} else if ((hours > 40) && (hours <= 43)) {
					double hoursRemaining = hours - 40;
					double first40Hrs = 0;
					double remaining = 0;
					first40Hrs = (Math.round(100.0 * rate * 40))/100.0;
					remaining = (Math.round(100.0 * rate * 1.5 * hoursRemaining))/100.0;
					result = first40Hrs + remaining;
					_empList.get(i).setGross(result);
				} else {
					double hoursRemaining = hours - 43;
					double first40Hrs = 0;
					double threeHrsAfter40Hrs = 0;
					double remaining = 0;
					first40Hrs = (Math.round(100.0 * rate * 40))/100.0;
					threeHrsAfter40Hrs = (Math.round(100.0 * rate * 1.5 * 3))/100.0;
					remaining = (Math.round(100.0 * rate * 2 * hoursRemaining))/100.0;
					result = first40Hrs + threeHrsAfter40Hrs + remaining;
					_empList.get(i).setGross(result);
				}
			} else if ((str.toLowerCase()).equals("salaried")) {
				double rate = _empList.get(i).getRate();
				result = (Math.round(100.0*rate / WEEKS_IN_YEAR))/100.0;
				_empList.get(i).setGross(result);
			} else {
				System.out.println("Invalid employment type");
			}
		}
	}
}

