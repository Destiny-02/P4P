
package payroll;
import payrollExceptions.DataNumberMismatchException;
import payrollExceptions.InvalidDataException;
public class FileReader {
	private FileIO fileIOObj;
	private int taxID;
	private Name name;
	private EmploymentType type;
	private double rate;
	private double ytd;
	private Date dates;
	private double hours;
	private double deduction;
	public FileReader (FileIO fileIOObj) {
		this.fileIOObj = fileIOObj;
	}
	public AbstractEmployee getAEmployee() {
		assert fileIOObj.getStatus() : "File not yet open.";
		String readString = fileIOObj.readLineFromFile();
		if(readString == null) {
			return null;
		}
		parseArgumentsInLine(readString.split("\t"));
		if(this.type == EmploymentType.HOURLY) {
			return new HourlyEmployee(this.taxID, this.name, this.rate, this.ytd,
					this.dates, this.hours, this.deduction);
		}
		else {
			return new SalariedEmployee(this.taxID, this.name, this.rate, this.ytd,
					this.dates, this.hours, this.deduction);
		}
	}
	private void parseArgumentsInLine(String[] splitString) {
		if(splitString.length != 9) {
			throw new DataNumberMismatchException();
		}
		int i = 0;
		for(InputType inType : InputType.values()) {
			try {
				parser(inType , splitString[i++]);
			} catch (Exception ex) {
				System.err.println(ex);
				System.exit(1);
			}
		}
	}
	private void parser(InputType inType, String toParse)
			throws NumberFormatException, InvalidDataException {
		switch(inType) {
		case TID:
			this.taxID = Integer.parseUnsignedInt(toParse);
			break;
		case NAME:
			this.name = new Name(toParse.split(","));
			break;
		case EMPLOYMENT:
			if(toParse.matches("Salaried")) {
				this.type = EmploymentType.SALARIED;
			}
			else if(toParse.matches("Hourly")) {
				this.type = EmploymentType.HOURLY;
			}
			else {
				throw new InvalidDataException(toParse
						+ " does not match any employment type.");
			}
			break;
		case RATE:
			this.rate = this.parseMoney(toParse);
			break;
		case YTD:
			this.ytd = this.parseMoney(toParse);
			break;
		case START:
			this.dates = new Date(parseDate(toParse));
			break;
		case END:
			this.dates.setEndDate(parseDate(toParse));
			break;
		case HOURS:
			this.hours = Double.parseDouble(toParse);
			break;
		case DEDUCTION:
			this.deduction = this.parseMoney(toParse);
			break;
		default:
			assert false : toParse;
		}
	}
	private double parseMoney(String toParse) throws NumberFormatException, InvalidDataException {
		if(toParse.matches("\\$[0-9]+\\.[0-9]+")) {
			double doubleTemp = Double.parseDouble(toParse.substring(1));
			if(doubleTemp < 0.0) {
				throw new InvalidDataException(doubleTemp + " is not a valid value.");
			}
			return doubleTemp;
		}
		else if(toParse.matches("\\$[0-9]+")) {
			return Integer.parseUnsignedInt(toParse.substring(1));
		}
		throw new InvalidDataException("Invalid money value: " + toParse);
	}
	private int[] parseDate(String toParse) throws NumberFormatException, InvalidDataException {
		String[] items;
		items = toParse.split("[-]");
		if(items.length != 3) {
			throw new InvalidDataException("Invalid date value: " + toParse);
		}
		int[] temp = new int[3];
		for(int index = 0; index < items.length; index++) {
			temp[index] = Integer.parseUnsignedInt(items[index]);
		}
		return temp;
	}
}
