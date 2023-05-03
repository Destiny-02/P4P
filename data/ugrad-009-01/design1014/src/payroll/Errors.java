package payroll;
import java.util.TreeMap;
import java.util.HashSet;
public class Errors {
	private int _currentPersonNumber = 0;
	private HashSet<Integer> tidSet = new HashSet<Integer>();
	private TreeMap<Integer,String> errorMap = new TreeMap<Integer,String>();
	public boolean checkTID(int tid) {
		if(tidSet.add(tid) == false) {
			tidError();
			return false;
		} else
			return true;
		}
	public boolean checkEmployment(String employment) {
		if (employment.equalsIgnoreCase("Salaried") || employment.equalsIgnoreCase("Hourly")) {
			return true;
		} else {
			employmentError();
			return false;
		}
	}
	public boolean checkNegative(double rate, double ytd, double hours, double deduction, double nett) {
		_currentPersonNumber++;
		if ((rate >= 0) && (ytd >= 0) && (hours >= 0) && (deduction >= 0) && (nett >= 0)) {
			return true;
		} else {
			negativeError();
			return false;
		}
	}
	public void numberFormatError(String input) {
		if (!input.isEmpty() && !input.contains("#")) {
			_currentPersonNumber++;
			numberError();
		}
	}
	public void enoughInformationError(String input) {
		if (!input.isEmpty() && !input.contains("#")) {
			_currentPersonNumber++;
			informationError();
		}
	}
	private void negativeError() {
		String statement = "Person " + _currentPersonNumber + " was ommited due to a Negative Value";
		errorMap.put(_currentPersonNumber, statement);
	}
	private void informationError() {
		String statement = "Person " + _currentPersonNumber + " was ommited due to Insufficeint Information";
		errorMap.put(_currentPersonNumber, statement);
	}
	private void tidError() {
		String statement = "Person " + _currentPersonNumber + " was ommited due to a Duplicate TID";
		errorMap.put(_currentPersonNumber, statement);
	}
	private void employmentError() {
		String statement = "Person " + _currentPersonNumber + " was ommited due to the Employment not being Salaried or Hourly";
		errorMap.put(_currentPersonNumber, statement);
	}
	private void numberError() {
		String statement = "Person " + _currentPersonNumber + " was ommited due to Information containing a word where a number is supposed to be";
		errorMap.put(_currentPersonNumber, statement);
	}
	public void printErrors() {
		if (!errorMap.isEmpty()){
			System.out.println("\n#---------------Error Report---------------#");
			for (int key: errorMap.keySet()) {
				String statement = errorMap.get(key);
				System.out.println("# " + statement);
			}
		}
	}
}
