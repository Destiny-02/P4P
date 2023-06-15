package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
public abstract class AbstractProcessingMethods {
	protected void getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		System.out.printf(dateFormat.format(cal.getTime()) + "\n");
	}
	protected void displayPayPeriod(List<Employee> employeeList) {
		Employee currentEmployee = employeeList.get(0);
		String[] payPeriod = new String[2];
		payPeriod[0] = currentEmployee.getStartDate();
		payPeriod[1] = currentEmployee.getEndDate();
		System.out.printf(payPeriod[0] + " to " + payPeriod[1] + "\n");
	}
	protected double getPAYE(double gross) {
		double taxAmount = 0;
		gross = gross*52;
		if (gross < 14000) {
			taxAmount = gross*0.105;
		} else if (gross > 70000) {
			gross -= 70000;
			taxAmount = 14020;
			taxAmount += gross * 0.33;
		} else if (gross > 48000) {
			gross -= 48000;
			taxAmount = 7420;
			taxAmount += gross * 0.3;
		} else if (gross > 14000) {
			gross -= 14000;
			taxAmount = 1470;
			taxAmount += gross * 0.175;
		}
		return roundNearestCent(taxAmount/52);
	}
	protected double getPAYE(Employee currentEmployee) {
		double gross = currentEmployee.getGross();
		return getPAYE(gross);
	}
	protected double getYTD(Employee currentEmployee) {
		return currentEmployee.getGross() + currentEmployee.getYTD();
	}
	protected double roundNearestCent(double value) {
		value *= 100;
		value = Math.round(value);
		value /= 100;
		return value;
	}
}	
