package payroll;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
public class Payroll{
	public static void main(String[] args)
			throws MissingEmployeeInformationException, NumberFormatException{
		try {
			BufferedReader in = new BufferedReader(new FileReader(args[0]));
			Scanner scanner = new Scanner(in);
			EmployeesDatabase database = new EmployeesDatabase();
			int lineNum = 1;
			String[] line = {""};
			boolean invalidFileContent = true;
			do{
				String str = scanner.nextLine();
				if (str.startsWith("#")){
					lineNum++;
					continue;
				}
				if (str.matches("^\\s*$")){
					lineNum++;
					continue;
				}
				LineChecker checker = new LineChecker(str, lineNum);
				if (checker.isInvalidLine()){
					lineNum++;
					continue;
				}
				invalidFileContent = false;
				if (str.matches("^\\s+.+$")){
					line = str.split("^\\s+");
					line = line[1].split("\t+");
				}else{
					line = str.split("\t+");
				}
				Employee e = new Employee(line[0], line[1], line[2], line[3],
						line[4], line[5], line[6], line[7], line[8]);
				database.putData(e);
				lineNum++;
			}while (scanner.hasNextLine());
			scanner.close();
			if (invalidFileContent){
				throw new MissingEmployeeInformationException("All attempts of loading information from the given input file "
						+ "are failed. " + "(All lines' loadings failed.)\n"
						+ "Please ensure all input texts/Strings are given in appropriate format.\n"
						+ "(e.g. no whitespace(s) within any numeric value, such as Rate;\n"
						+ "no empty field(s) of information -- should be exactly 9 fields in total for each employee)");
			}
			@SuppressWarnings("unused")
			Processor processor = new Processor(database, args[1]);
		} catch (FileNotFoundException e1) {
			System.out.println("\n"
					+ "Sorry the indicated file can not be found in the specified directory");
			e1.printStackTrace();
			return;
		} catch (NumberFormatException e2) {
			System.out.println("\n"
					+ "Program terminates due to failing to convert input numeric value into sensible int/float type: \n"
					+ "numeric values must not contain any non-numeric character(s) except '$', '.'(not for TID).");
			e2.printStackTrace();
			return;
		}
	}
}
