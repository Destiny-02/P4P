package payroll;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
public class Records {
	public void getEmployeeRecords(ArrayList<Employee> empList, String file) {
		try {
			File scan = new File(file);
			Scanner input = new Scanner(scan);
			while (input.hasNextLine()) {
				String aRecord = input.nextLine();
				if (!aRecord.equals("")) {
					if (aRecord.charAt(0) != '#') {
						String[] values = aRecord.split("\t");
						RecordValues(empList, values);
					}
				}
			}
			input.close();
		}
		catch(Exception e){
			System.out.println("Error in file");
		}
	}
		public void RecordValues(ArrayList<Employee> arrList, String[] line) {
			Employee values = new Employee(line[0], line[1], line[2], line[3].substring(1),
					line[4].substring(1),line[5], line[6] ,line[7], line[8].substring(1));
			arrList.add(values);
		}
}