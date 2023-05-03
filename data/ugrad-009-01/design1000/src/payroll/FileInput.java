package payroll;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
public class FileInput {
	private String output;
	private BufferedReader processing;
	private DecimalFormat DPs = new DecimalFormat("#0.00");
	private ArrayList<Employee> list = new ArrayList<Employee>();
	public FileInput(String filename, String output) {
		this.output = output;
		try {
			processing = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
	}
	public void readFile() {
		String[] splitString = {""};
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		String line;
		try {
			System.out.println(timeStamp);
			while((line = processing.readLine()) != null) {
				if (line.length() > 0 && !line.startsWith("#")) {
				splitString = line.split("\t|, ");
				int taxID = Integer.parseInt(splitString[0]);
				double deduction = Double.parseDouble(splitString[9].substring(1));
				double rate = Double.parseDouble(splitString[4].substring(1));
				double hours = Double.parseDouble(splitString[8]);
				double YTD = Double.parseDouble(splitString[5].substring(1));
				if (splitString[3].equals("Salaried")) {
					SalariedWage person = new SalariedWage(splitString[1], splitString[2], splitString[6], splitString[7], taxID, deduction, rate, hours, YTD);
					list.add(person);
				} else {
					HourlyWage person = new HourlyWage(splitString[1], splitString[2], splitString[6], splitString[7], taxID, deduction, rate, hours, YTD);
					list.add(person);
				}
			}
		}
				if (output.equals("Payslips")) {
					taxID();
					for (Employee person : list) {
						output = person.getTaxID() + ". " + person.getGivenName() + " " + person.getFamilyName() + ", " + "Period: " + person.getStartDate() + " to " + person.getEndDate() + "." + " Gross: $" + DPs.format(person.getGross()) + ", PAYE: $" + DPs.format(person.getPAYE()) + ", Deductions: $" + DPs.format(person.getDeduction()) + " Nett: $" + DPs.format(person.getNett()) + " YTD: $" + DPs.format(person.getYTD());
						System.out.println(output);
					}
				} else if (output.equals("Employees")) {
					lastNameCompare();
					for (Employee person : list) {
						output = person.getFamilyName() + ", " + person.getGivenName() + " (" + person.getTaxID() + ") " + (person instanceof SalariedWage ? "Salaried" : "Hourly")  + ", $" + DPs.format(person.getRate()) + " YTD:$" + DPs.format(person.getYTD());
						System.out.println(output);
					}
				} else if (output.equals("Burden")) {
					double burden = 0;
					for (Employee person : list) {
						burden += person.getGross();
						output = person.getStartDate() + " to " + person.getEndDate() + "\n" + "Total Burden: $" + DPs.format(burden);
					}
					System.out.println(output);
				} else if (output.equals("PAYE")) {
					double totalPAYE = 0;
					for (Employee person : list) {
						totalPAYE += person.getPAYE();
						output = person.getStartDate() + " to " + person.getEndDate() + "\n" + "Total PAYE: $" + DPs.format(totalPAYE);
					}
					System.out.println(output);
				} else {
					output = "method could not be found";
					System.out.println(output);
				}
		} catch (NumberFormatException e) {
			System.out.println("Number format exception");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("I/O Exceptio");
			e.printStackTrace();
		}
			}
	public void firstNameCompare() {
		Collections.sort(list, new Employee.firstNameCompare());
	}
	public void lastNameCompare() {
		Collections.sort(list, new Employee.lastNameCompare());
	}
	public void taxID() {
		Collections.sort(list, new Employee.taxIDCompare() );
	}
}
