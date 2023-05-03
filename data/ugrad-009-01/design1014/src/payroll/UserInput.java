package payroll;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class UserInput {
	private PeopleList peopleList = new PeopleList();
	public boolean fileRead(String fileName) {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				peopleList.store(currentLine);
			}
			return true;
		} catch (IOException e) {
			System.out.println("File name " + fileName + " couldn't be opened");
			return false;
		}
	}
	public void processing(String processName) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		if (processName.equals("Payslips")) {
			System.out.println(dateFormat.format(currentDate));
			peopleList.payslips();
		} else if (processName.equals("Employees")) {
			System.out.println(dateFormat.format(currentDate));
			peopleList.employees();
		} else if (processName.equals("Burden")) {
			System.out.println(dateFormat.format(currentDate));
			peopleList.burden();
		} else if (processName.equals("PAYE")) {
			System.out.println(dateFormat.format(currentDate));
			peopleList.paye();
		} else {
			System.out.println("Couldn't find " + processName + " processing type!");
			System.out.println("Make sure it is either Payslips, Employees, Burden or PAYE (all case sensitive)");
			return;
		}
		peopleList.printErrors();
	}
}
