package payroll;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
public class CreateEmployee {
	String _TID;
	public CreateEmployee() {
	}
	public void addEmployee(List<Employee> employeeList, String line) {
		String[] employeeData = line.split("\\t+");
		_TID = employeeData[0];
		int TID;
		String name;
		String dates[] = new String[2];
		double rate, YTD, hours, deduction;
		try {
			TID = checkTID(employeeList, employeeData[0]);
			name = checkName(employeeData[1]);
			checkEmployment(employeeData[2]);
			rate = checkMoneyValue(employeeData[3]);
			YTD = checkMoneyValue(employeeData[4]);
			dates = checkPeriod(employeeData[5], employeeData[6]);
			hours = checkHours(employeeData[7]);
			deduction = checkMoneyValue(employeeData[8]);
		} catch (InputException e) {
			e.printStackTrace();
			return;
		}
		if (employeeData[2].equals("Salaried")) {
			employeeList.add(new SalariedEmployee(TID, name, rate, YTD, dates, hours, deduction));
		} else if (employeeData[2].equals("Hourly")) {
			employeeList.add(new HourlyEmployee(TID, name, rate, YTD, dates, hours, deduction));
		}
	}
	private int checkTID(List<Employee> employeeList, String stringValue) throws InputException {
		int TIDValue;
		try {
			TIDValue = Integer.parseInt(stringValue);
			if (TIDValue < 1) {
				throw new InputException("TID is negative. See TID " + _TID);
			}
		} catch (NumberFormatException e) {
			throw new InputException("TID is not a valid integer. See TID " + _TID);
		}
		for (Employee currentEmployee : employeeList) {
			if (currentEmployee.getTID() == TIDValue) {
				throw new InputException("TID is a duplicate. See TID " + _TID);
			}
		}
		return TIDValue;
	}
	private String checkName(String name) throws InputException {
		if (name.contains(",")) {
			return name;
		} else {
			throw new InputException("Name is in the wrong format. See TID " + _TID);
		}
	}
	private boolean checkEmployment(String employment) throws InputException {
		if (employment.equals("Salaried") || employment.equals("Hourly")) {
			return true;
		} else {
			throw new InputException("Employment type is not valid. See TID " + _TID);
		}
	}
	private double checkMoneyValue(String moneyValue) throws InputException {
		if (moneyValue.substring(0, 1).equals("$")) {
			moneyValue = moneyValue.substring(1);
			double value = checkDouble(moneyValue);
			return value;
		} else {
			throw new InputException("A value does not begin with $. See TID " + _TID);
		}
	}
	private double checkDouble(String doubleValue) throws InputException {
		double value;
		try {
			value = Double.parseDouble(doubleValue);
		} catch (NumberFormatException e) {
			throw new InputException("A monetary value cannot be parsed. See TID " + _TID);
		}
		if (value >= 0) {
			return value;
		} else {
			throw new InputException("A monetary value is negative. See TID " + _TID);
		}
	}
	private double checkHours(String hoursStr) throws InputException {
		double hours = checkDouble(hoursStr);
		if (hours % 0.25 == 0) {
			return hours;
		} else {
			throw new InputException("The hour value is invalid. See TID " + _TID);
		}
	}
	private String[] checkPeriod(String d1, String d2) throws InputException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		Date dateOne = null;
		Date dateTwo = null;
		try {
			dateOne = format.parse(d1);
			dateTwo = format.parse(d2);
		} catch (ParseException e) {
			throw new InputException("Date values are in the wrong format. See TID " + _TID);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateOne);
		calendar.add(Calendar.DAY_OF_YEAR, 6);
		dateOne = calendar.getTime();
		if (dateOne.equals(dateTwo)) {
			String dates[] = {d1, d2};
			return dates;
		}
		throw new InputException("Incorrect period on the dates. See TID " + _TID);
	}
}
