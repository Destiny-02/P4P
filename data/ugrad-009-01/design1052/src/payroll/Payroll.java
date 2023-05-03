package payroll;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Payroll {
	private final String _file;
	private final EmployeeList _employeesList;
	private final Processing _outputProcessor;
	public static void main(String[] args) {
		if (args.length != 2){
			throw new RuntimeException("Invalid amount of arguments");
		}
		Payroll payroll = new Payroll(args[0], args[1]);
		payroll.printToConsole();
	}
	private Payroll(String file, String processMode){
		_file = file;
		_outputProcessor = new ProcessingFactory().getProcess(processMode);
		_employeesList = this.getEmployeesFromFile();
	}
	private EmployeeList getEmployeesFromFile(){
		EmployeeList employees = new EmployeeList();
		EmployeeFactory employeeFactory = new EmployeeFactory();
		try {
			FileReader file = new FileReader(_file);
			BufferedReader bufferedReader = new BufferedReader(file);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (!line.startsWith("#") & !line.isEmpty()){
					Employee employee = employeeFactory.getEmployeeFromString(line);
					employees.add(employee);
				}
			}
			bufferedReader.close();
			file.close();
		} catch (IOException e){
			System.out.println("File does not exist");
			System.exit(0);
		}
		return employees;
	}
	public void printToConsole(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		_outputProcessor.printToConsole(_employeesList);
	}
}
