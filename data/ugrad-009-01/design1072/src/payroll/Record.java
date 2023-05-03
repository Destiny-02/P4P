package payroll;
import java.util.ArrayList;
import java.util.Comparator;
public class Record  {
	private String _FirstName;
	private String _LastName;
	private String _Employment;
	private double _Rate;
	private double _YTD;
	private String _Start;
	private String _End;
	private double _Hours;
	private double _Deduction;
	private int _FID;
	public void storage(String[] input, ArrayList<Record> Database) {
		Record addedFile = new Record();
		addedFile = addData(addedFile, input);
		Database.add(addedFile);
	}
	public static Record addData(Record addedFile, String[] input) {
		if (input.length<9) {
			throw new IllegalArgumentException("Invalid input line: " + input +
					" not in format: TID Name Employment Rate YTD Start End Hours Deduction");
		}
		addedFile.setFID(Integer.parseInt(input[0]));
		String[] Names = input[1].split(", ");
		addedFile.setFirstName(Names[1]);
		addedFile.setLastName(Names[0]);
		if (!input[2].equals("Salaried") && !input[2].equals("Hourly")) {
			throw new IllegalArgumentException("Invalid employment input: " + input[2]);
		}
		addedFile.setEmployment(input[2]);
		if (input[3].charAt(0) != '$') {
			throw new IllegalArgumentException("Invalid rate input: " + input[3] +
					" not in format: $123.45");
		}
		addedFile.setRate(Double.parseDouble(input[3].substring(1)));
		if (input[3].charAt(0) != '$') {
			throw new IllegalArgumentException("Invalid YTD input: " + input[4]
					                                + " not in format: $123.45");
		}
		addedFile.setYTD(Double.parseDouble(input[4].substring(1)));
		addedFile.setStart(input[5]);
		addedFile.setEnd(input[6]);
		if ((Double.parseDouble(input[7])*4)%2 != 0) {
		}
		addedFile.setHours(Double.parseDouble(input[7]));
		if (input[8].charAt(0) != '$') {
			throw new IllegalArgumentException("Invalid deduction input: "
					                + input[8] + " not in format: $123.45");
		}
		addedFile.setDeduction(Double.parseDouble(input[8].substring(1)));
		return addedFile;
	}
	public class OrderByFID implements Comparator<Record> {
		public int compare(Record f1, Record f2) {
			if(f1._FID < f2._FID) {
				return -1;
			}
			else if(f1._FID > f2._FID) {
				return 1;
			}
			else {
				throw new IllegalArgumentException(
						"Two equiavalent FIDs: " + f1._FID + " from employee files: "
								+ f1._LastName + " & " + f2._LastName);
			}
		}
	}
	public class OrderByLastName implements Comparator<Record> {
		public int compare(Record f1, Record f2) {
			if (f1._LastName.compareTo(f2._LastName) > 0){
				return 1;
			}
			else if (f1._LastName.compareTo(f2._LastName) < 0) {
				return -1;
			}
			else {
				return f1._FirstName.compareTo(f2._FirstName) ;
			}
		}
	}
	private void setFID(int fID) {
		_FID = fID;
	}
	private void setFirstName(String firstName) {
		_FirstName = firstName;
	}
	private void setLastName(String lastName) {
		_LastName = lastName;
	}
	private void setEmployment(String employment) {
		_Employment = employment;
	}
	private void setRate(double rate) {
		_Rate = rate;
	}
	private void setYTD(double yTD) {
		_YTD = yTD;
	}
	private void setStart(String start) {
		_Start = start;
	}
	private void setEnd(String end) {
		_End = end;
	}
	private void setHours(double hours) {
		_Hours = hours;
	}
	private void setDeduction(double deduction) {
		_Deduction = deduction;
	}
	public int getFID() {
		return _FID;
	}
	public String getFirstName() {
		return _FirstName;
	}
	public String getLastName() {
		return _LastName;
	}
	public String getEmployment() {
		return _Employment;
	}
	public double getRate() {
		return _Rate;
	}
	public double getYTD() {
		return _YTD;
	}
	public String getStart() {
		return _Start;
	}
	public String getEnd() {
		return _End;
	}
	public double getHours() {
		return _Hours;
	}
	public double getDeduction() {
		return _Deduction;
	}
}
