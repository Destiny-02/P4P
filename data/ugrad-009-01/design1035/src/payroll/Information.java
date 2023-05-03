package payroll;
import java.util.Scanner;
import java.util.TreeMap;
import java.io.File;
import java.io.FileNotFoundException;
public class Information {
	private final TreeMap<Integer, Employee> employeeInfo = new TreeMap<Integer, Employee>();
	public void Insert(String filename ) throws FileNotFoundException  {
		File file = new File(filename);
		Scanner scanFile = new Scanner(file);
		while(scanFile.hasNextLine()) {
			String currentLine = scanFile.nextLine();
			if (!(currentLine.startsWith("#") && !(currentLine.startsWith(" ") && !(currentLine.equals(""))))) {
				String[] thisLine = currentLine.split("\t");
				employeeInfo.put(Integer.parseInt(thisLine[0]), new Employee(Integer.parseInt(thisLine[0]), thisLine[1], thisLine[2], Double.parseDouble(thisLine[3].replaceAll("[^\\d.]", "")), Double.parseDouble(thisLine[4].replaceAll("[^\\d.]", "")), thisLine[5], thisLine[6], Double.parseDouble(thisLine[7]), Double.parseDouble(thisLine[8].replaceAll("[^\\d.]", ""))));
				}
			}
		}
	public TreeMap<Integer, Employee> getEmployeeInfo() {
		return employeeInfo;
	}
}
