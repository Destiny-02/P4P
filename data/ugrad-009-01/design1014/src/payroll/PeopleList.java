package payroll;
import java.util.Map;
import java.util.TreeMap;
public class PeopleList {
	private Person _firstPerson;
	private Person _lastPerson;
	private Errors errors = new Errors();
	public void store(String currentLine) {
		try {
			currentLine = currentLine.replaceAll("[$]", "");
			currentLine = currentLine.replaceAll("[,]", "\t");
			String[] informationArray = currentLine.split("\t");
			if (informationArray.length != 10) {
				throw new ArrayIndexOutOfBoundsException();
			}
			int tid = Integer.parseInt(informationArray[0]);
			String lastName = informationArray[1];
			String firstName = informationArray[2].replaceAll(" ", "");
			String employment = informationArray[3];
			double rate = Double.parseDouble(informationArray[4]);
			double ytd = Double.parseDouble(informationArray[5]);
			String startDate = informationArray[6];
			String endDate = informationArray[7];
			double hours = Double.parseDouble(informationArray[8]);
			double deduction = Double.parseDouble(informationArray[9]);
			Person person = new Person(tid, lastName, firstName, employment, rate, ytd, startDate, endDate, hours, deduction);
			if (errors.checkNegative(rate, ytd, hours, deduction, person.getNett())) {
				if (errors.checkEmployment(employment)) {
					if (errors.checkTID(tid)) {
						addPersonToList(person);
					}
				}
			}
		} catch (NumberFormatException e) {
			errors.numberFormatError(currentLine);
		} catch (ArrayIndexOutOfBoundsException e) {
			errors.enoughInformationError(currentLine);
		}
	}
	private void addPersonToList(Person personToBeAdded) {
		if (_lastPerson == null) {
			_firstPerson = personToBeAdded;
			_lastPerson = personToBeAdded;
		} else {
			_lastPerson.setNextPerson(personToBeAdded);
			_lastPerson = personToBeAdded;
		}
	}
	public void payslips() {
		Person currentPerson = _firstPerson;
		Map<Integer, Person> peopleMap = new TreeMap<Integer, Person>();
		boolean stillHaveNextLine = true;
		while (stillHaveNextLine == true) {
			peopleMap.put(currentPerson.getTid(), currentPerson);
			if (currentPerson.getNextPerson() != null) {
				currentPerson = currentPerson.getNextPerson();
			} else {
				stillHaveNextLine = false;
			}
		}
		for (int key: peopleMap.keySet()) {
			Person personFromMap = peopleMap.get(key);
			System.out.printf("%d. %s %s, Period: %s to %s. Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f Nett: $%.2f YTD: $%.2f\n",
					personFromMap.getTid(), personFromMap.getFirstName(), personFromMap.getLastName(), personFromMap.getStart(), personFromMap.getEnd(), personFromMap.getGross(),
					personFromMap.getPaye(), personFromMap.getDeduction(), personFromMap.getNett(), personFromMap.getYtd());
		}
	}
	public void employees() {
		Person currentPerson = _firstPerson;
		Map<String, Person> peopleMap = new TreeMap<String, Person>();
		boolean stillHaveNextLine = true;
		while (stillHaveNextLine == true) {
			peopleMap.put(currentPerson.getLastName() + currentPerson.getFirstName() + currentPerson.getTid(), currentPerson);
			if (currentPerson.getNextPerson() != null) {
				currentPerson = currentPerson.getNextPerson();
			} else {
				stillHaveNextLine = false;
			}
		}
		for (String key: peopleMap.keySet()) {
			Person personFromMap = peopleMap.get(key);
			System.out.printf("%s, %s (%d) %s, $%.2f YTD:$%.2f\n", personFromMap.getLastName(), personFromMap.getFirstName(),
					personFromMap.getTid(), personFromMap.getEmployment(), personFromMap.getRate(), personFromMap.getYtd());
		}
	}
	public void paye() {
		Person currentPerson = _firstPerson;
		boolean stillHaveNextLine = true;
		double totalPaye = 0;
		while (stillHaveNextLine == true) {
			totalPaye = totalPaye + currentPerson.getPaye();
			if (currentPerson.getNextPerson() != null) {
				currentPerson = currentPerson.getNextPerson();
			} else {
				stillHaveNextLine = false;
			}
		}
		System.out.printf("%s to %s\nTotal PAYE: $%.2f\n", currentPerson.getStart(), currentPerson.getEnd(), totalPaye);
	}
	public void burden() {
		Person currentPerson = _firstPerson;
		boolean stillHaveNextLine = true;
		double totalBurden = 0;
		while (stillHaveNextLine == true) {
			totalBurden = totalBurden + currentPerson.getGross();
			if (currentPerson.getNextPerson() != null) {
				currentPerson = currentPerson.getNextPerson();
			} else {
				stillHaveNextLine = false;
			}
		}
		System.out.printf("%s to %s\nTotal Burden: $%.2f\n", currentPerson.getStart(), currentPerson.getEnd(), totalBurden);
	}
	public void printErrors() {
		errors.printErrors();
	}
}

