package payroll;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class DocProcessing {
	private Double moneySplit(String a) {
		String[] x = a.split("\\$");
		if (x.length > 1) {
			return Double.parseDouble(x[1]);
		} else
			return Double.parseDouble(x[0]);
	}
	public ArrayList<EmployeeRecord> processDoc(String input) {
		File records = new File(input);
		ArrayList<EmployeeRecord> employees = new ArrayList<>();
		try {
			Scanner scanner = new Scanner(records);
			while (scanner.hasNextLine()) {
				String tempStorage = scanner.nextLine();
				String[] splitInput = tempStorage.split("\t|#");
				if (splitInput[0].equals("")) {
					if (splitInput[0].equals("") && !scanner.hasNextLine() || !scanner.hasNextLine()) {
						scanner.close();
						break;
					}
					continue;
				} else {
					EmployeeRecord temp = new EmployeeRecord();
					int setTaxId =((Integer.parseInt(splitInput[0])));
					String setName =((splitInput[1]));
					String setEmployment = ((splitInput[2]));
					Double setRate = ((moneySplit(splitInput[3])));
					Double setYTD =((moneySplit(splitInput[4])));
					String setStart = ((splitInput[5]));
					String setEnd = ((splitInput[6]));
					Double setHours = ((Double.parseDouble(splitInput[7])));
					Double setDeductions = ((moneySplit(splitInput[8])));
					temp.setRecords(setTaxId, setName, setEmployment, setRate, setYTD, setStart, setEnd, setHours, setDeductions);
					employees.add(temp);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println(records.getAbsolutePath());
			System.err.println("The specified file could not be found");
		}
		return employees;
	}
}