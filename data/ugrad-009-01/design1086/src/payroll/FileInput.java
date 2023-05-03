package payroll;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
public class FileInput {
	public EmployeeList processFile (String fileName, EmployeeList myList) throws EmploymentException{
		try {
			String line;
			FileReader fileReader = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fileReader);
			HashSet<Integer> encounteredTaxIDs= new HashSet<Integer>();
			while ((line = br.readLine()) != null){
				try {
					if (!(line.isEmpty()) && (line.charAt(0) != '#')){
						line = line.replace("$", "");
						String[] information = 	line.split("\t");
						Employee aEmployee = null;
						if (information[2].toLowerCase().equals("salaried")){
							aEmployee = new SalariedEmployee(Integer.parseInt(information[0]), information[1], information[2],
									Double.parseDouble(information[3]), Double.parseDouble(information[4]), information[5], information[6],
									Double.parseDouble(information[7]), Double.parseDouble(information[8]));
						} else if (information[2].toLowerCase().equals("hourly")){
							aEmployee = new HourlyEmployee (Integer.parseInt(information[0]), information[1], information[2],
									Double.parseDouble(information[3]), Double.parseDouble(information[4]), information[5], information[6],
									Double.parseDouble(information[7]), Double.parseDouble(information[8]));
						} else {
							throw new EmploymentException(information);
						}
						if (encounteredTaxIDs.add(Integer.parseInt(information[0])) == true){
							myList.insertInformation(aEmployee);
						} else{
							ErrorReport.printDuplicateTaxIDError(Integer.parseInt(information[0]), information[1]);
						}
					}
				} catch (EmploymentException e){
					e.printEmploymentError();
				} catch (NumberFormatException n){
					n.getMessage();
				} catch (RuntimeException r){
				}
			}
			br.close();
		} catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "', please check your filename");
		} catch(IOException  ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
		return myList;
	}
}
