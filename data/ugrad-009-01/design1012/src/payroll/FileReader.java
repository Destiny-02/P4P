package payroll;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
public class FileReader {
	EmployeeList reader(String args) throws FileNotFoundException{
		EmployeeList employeeList = new EmployeeList();
		HashSet<String> tidChecker = new HashSet<String>();
		try{
			File file = new File(args);
			if(file.exists()){
				Scanner scanner = new Scanner(file);
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					if(line.equals("#---------------- FILE END --------------------------")){
						break;
					}
					if(!(line.equals("#---------------- FILE START --------------------------") || line.equals(""))){
						String splitLine[] = line.split("\t");
						if(!(splitLine[0].equals("#TID"))){
							Employee employee = new Employee();
							try{
								employee.fieldInitialiser(splitLine, employeeList, employee, tidChecker);
							} catch(DuplicateEmployeeException duplicateExc) {
								System.out.println("**An employee with a duplicate tax ID number to a previously entered employee is present in the input file.**");
								System.out.println("**This \"duplicate\" employee has been ignored and not been entered into the system**");
							} catch(InputFormatException IPFEx){
								System.out.println("**The employee with the invalid name and/or date has not been entered into the system**");
							}
						}
					}
				}
				scanner.close();
			}
			else{
				System.out.println("File \"" + args + "\" not found. Please try again!");
				System.exit(0);
			}
		}
		catch(NullPointerException NPEExc){
			System.out.println("Invalid file \"" + args + "\". Please try again!");
		}
		return employeeList;
	}
}
