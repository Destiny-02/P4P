package payroll;
import java.util.ArrayList;
public class PAYE extends Calculation {
	private final double FIRST_INCOME_CATEGORY_TAX_RATE = 0.105;
	private final double SECOND_INCOME_CATEGORY_TAX_RATE = 0.175;
	private final double THIRD_INCOME_CATEGORY_TAX_RATE = 0.30;
	private final double LAST_INCOME_CATEGORY_TAX_RATE = 0.33;
	public PAYE(ArrayList<Employees> empList) {
		super(empList);
	}
	public void calculate() {
		for (int i = 0; i < _empList.size(); i++) {
			String str = _empList.get(i).getEmployment();
			double gross = _empList.get(i).getGross();
			double annualGross = 0;
			if ((str.toLowerCase()).equals("hourly")) {
				annualGross = gross * WEEKS_IN_YEAR;
			} else if ((str.toLowerCase()).equals("salaried")) {
				annualGross = _empList.get(i).getRate();
			} else {
				System.out.println("Invalid Employment Type");
			}
			if (annualGross <= 14000) {
				result = (Math.round(100.0 * annualGross * FIRST_INCOME_CATEGORY_TAX_RATE/WEEKS_IN_YEAR))/100.0;
				_empList.get(i).setPaye(result);
			} else if ((annualGross > 14000) && (annualGross <= 48000)) {
				double grossRemaining = annualGross - 14000;
				double first14000 = 0;
				double remaining = 0;
				first14000 = 14000 * FIRST_INCOME_CATEGORY_TAX_RATE;
				remaining = grossRemaining * SECOND_INCOME_CATEGORY_TAX_RATE;
				result = (Math.round(100.0 * (first14000 + remaining)/WEEKS_IN_YEAR))/100.0;
				_empList.get(i).setPaye(result);
			} else if ((annualGross > 48000) && (annualGross <= 70000)) {
				double grossRemaining = annualGross - 48000;
				double first14000 = 0;
				double next34000After14000 = 0;
				double remaining = 0;
				first14000 =14000 * FIRST_INCOME_CATEGORY_TAX_RATE;
				next34000After14000 =34000 * SECOND_INCOME_CATEGORY_TAX_RATE;
				remaining = grossRemaining * THIRD_INCOME_CATEGORY_TAX_RATE;
				result = (Math.round(100.0 * (first14000 + next34000After14000 + remaining)/WEEKS_IN_YEAR))/100.0;
				_empList.get(i).setPaye(result);
			} else {
				double grossRemaining = annualGross - 70000;
				double first14000 = 0;
				double next34000After14000 = 0;
				double next22000After48000 = 0;
				double remaining = 0;
				first14000 = 14000 * FIRST_INCOME_CATEGORY_TAX_RATE;
				next34000After14000 = 34000 * SECOND_INCOME_CATEGORY_TAX_RATE;
				next22000After48000 = 22000 * THIRD_INCOME_CATEGORY_TAX_RATE;
				remaining = grossRemaining * LAST_INCOME_CATEGORY_TAX_RATE;
				result = (Math.round(100.0 * (first14000 + next34000After14000 + next22000After48000 + remaining)/WEEKS_IN_YEAR))/100.0;
				_empList.get(i).setPaye(result);
			}
		}
	}
}
