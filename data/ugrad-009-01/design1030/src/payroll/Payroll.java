package payroll;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class Payroll {
	public final static DateFormat _dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	String _fileName;
	String _processType;
	Company _Company = new Company();
	public static void main(String[] args) {
		Payroll Payer = new Payroll();
		Payer._fileName = args[0];
		Payer._processType = args[1];
		Payer.process();
	}
	public void process() {
		readFile();
		Calendar cal = Calendar.getInstance();
		System.out.println(_dateFormat.format(cal.getTime()));
		if (_processType.equals("Payslips")) {
			try {
				_Company.writePayslips();
			} catch (Exception e) {
				System.out.println("Error Printing Payslips");
				e.printStackTrace();
			}
		} else if(_processType.equals("Employees")){
			_Company.writeEmployees();
		} else if(_processType.equals("Burden")) {
			_Company.writeBurden();
		} else if(_processType.equals("PAYE")) {
			_Company.writePAYE();
		} else {
			System.out.println("There is an error with the processing specified in the programs arguments.");
		}
	}
	public void readFile() {
		int linenum = 1;
		Integer taxID;
		String firstName;
		String lastName;
		BigDecimal rate;
		BigDecimal ytd;
		Date start;
		Date end;
		String payType;
		BigDecimal hours;
		BigDecimal deductions;
		try(BufferedReader reader = new BufferedReader(new FileReader(_fileName))) {
			String line = reader.readLine();
			while (line != null) {
				if(!line.startsWith("#")){
					String[] employeeFields = line.split("\\s+");
					taxID = (Integer.parseInt(employeeFields[0]));
					firstName = employeeFields[2];
					lastName = employeeFields[1].replace(",", "");
					payType = employeeFields[3];
					if (!employeeFields[5].startsWith("$")) {
						throw new Exception("No $ amount found for YTD");
					}
					if (!employeeFields[9].startsWith("$")) {
						throw new Exception("No $ amount found for deductions");
					}
					if (!employeeFields[4].startsWith("$")) {
						throw new Exception("No $ amount found for rate");
					}
					try{
						start = _dateFormat.parse(employeeFields[6]);
					} catch(ParseException parseExcept) {
						throw new Exception("Start Date incorrect format. Format: yyyy-MM-dd");
					}
					try{
						end = _dateFormat.parse(employeeFields[7]);
					} catch(ParseException parseExcept) {
						throw new Exception("End Date incorrect format. Format: yyyy-MM-dd");
					}
					long diffInWeeks = ((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24 ) + 1) / 7;
					if(diffInWeeks != 1) {
						throw new Exception("Pay Period not 7 days");
					}
					try{
						hours = new BigDecimal((employeeFields[8]));
					} catch(NumberFormatException numExcept){
						throw new Exception("Hours not a valid number");
					}
					try {
						ytd = new BigDecimal((employeeFields[5].replace("$", ""))).setScale(2, RoundingMode.HALF_UP);
					} catch (NumberFormatException numexcept) {
						throw new Exception("Year to Date is not a valid number");
					}
					try{
						deductions = new BigDecimal((employeeFields[9].replace("$", ""))).setScale(2, RoundingMode.HALF_UP);
					} catch(NumberFormatException numExcept){
						throw new Exception("Hours not a valid number");
					}
					try {
						rate = new BigDecimal((employeeFields[4].replace("$", ""))).setScale(2, RoundingMode.HALF_UP);
					} catch (NumberFormatException numexcept){
						throw new Exception("Rate not a valid number");
					}
					Employee CurrentEmployee = _Company.getEmployee(taxID);
					if(CurrentEmployee == null){
						CurrentEmployee = new Employee(taxID, lastName, firstName, payType, rate, ytd, start, end, hours, deductions);
					} else {
						throw new Exception("Duplicate Employee in file");
					}
					_Company.addEmployee(CurrentEmployee);
				}
				linenum ++;
				line = reader.readLine();
			}
		} catch(IOException exceptIO) {
			exceptIO.printStackTrace();
		} catch(Exception exceptNum) {
			System.out.println("Errors in file on line " + linenum + " - check formatting");
			System.out.println("Error - " + exceptNum.getMessage());
			exceptNum.printStackTrace();
		}
	}
}
