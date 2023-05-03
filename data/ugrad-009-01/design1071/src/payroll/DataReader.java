package payroll;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
public class DataReader {
	public void generateEmployeesFromFile(File file, EmployeeList list){
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null){
				if (line.isEmpty() || !Character.isDigit(line.charAt(0))) {
					continue;
				}
				String[] splitInfo = line.split("\\t|, ");
				parseEmployee(splitInfo, list);
			}
			br.close();
		} catch (IOException ioex){
			System.out.println("File not found.");
		} catch (PayrollException prex){
			System.out.println(prex.toString());
		}
	}
	public void parseEmployee(String[] rawData, EmployeeList list) throws PayrollException{
		String[] employeeInfo = new String[10];
		int i = 0;
		for (String elem : rawData){
			employeeInfo[i] = elem;
			i++;
		}
		int id = Integer.parseInt(employeeInfo[0]);
		String ln = employeeInfo[1];
		String fn = employeeInfo[2];
		String emp = employeeInfo[3];
		double rate = Double.parseDouble(employeeInfo[4].replaceAll("[^\\d.]+", ""));
		double ytd = Double.parseDouble(employeeInfo[5].replaceAll("[^\\d.]+", ""));
		String sd = employeeInfo[6];
		String ed = employeeInfo[7];
		double hr = Double.parseDouble(employeeInfo[8]);
		double dd = Double.parseDouble(employeeInfo[9].replaceAll("[^\\d.]+", ""));
		switch(emp){
			case "Salaried":
				list.addEmployee(new SalariedEmployee(id, ln, fn, emp, rate, ytd, sd, ed, hr, dd));
				break;
			case "Hourly":
				list.addEmployee(new HourlyEmployee(id, ln, fn, emp, rate, ytd, sd, ed, hr, dd));
				break;
			default:
				throw new PayrollException("Unrecognizable employee type.");
		}
		}
}
