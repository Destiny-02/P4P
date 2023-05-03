package payroll;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class ProcessInput {
	private final EmployeeRecords _employeeRecords;
	private final String _fileName;
	private final String _whichProcess;
	private boolean _anyErrors;
	private ErrorLog _errorLog;
	ProcessInput(String fileName, String whichProcess) {
		_employeeRecords = new EmployeeRecords();
		_fileName = fileName;
		_whichProcess = whichProcess;
		_errorLog = new ErrorLog();
		_anyErrors = false;
		processData();
	}
	private void processData(){
		BufferedReader br = null;
		String currentLine = null;
		try {
			br = new BufferedReader(new FileReader(_fileName));
			while ((currentLine = br.readLine()) != null) {
				if (!currentLine.isEmpty()){
					if (!(String.valueOf(currentLine.charAt(0)).equals("#"))){
						try {
							EmployeeData tempEmploy = new EmployeeData(currentLine);
							_employeeRecords.addToRecord(tempEmploy);
						} catch (NumberFormatException e){
							_anyErrors = true;
							_errorLog.errorForFormat(currentLine);
						} catch (RuntimeException e){
							_anyErrors = true;
							_errorLog.errorForRuntime(currentLine);
						} catch (ErrorMessageTID e) {
							_anyErrors = true;
							_errorLog.errorForTID(currentLine);
						}
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Unable to find the text file.\nPlease check that you have provided the correct directory.");
			System.exit(1);
		}
	}
	public Form createCurrentForm(){
		Form form = null;
		if (_whichProcess.toLowerCase().equals("payslips")) {
			form = new Payslips(_employeeRecords);
		} else if (_whichProcess.toLowerCase().equals("employees")) {
			form = new Employees(_employeeRecords);
		} else if (_whichProcess.toLowerCase().equals("burden")) {
			form = new Burden(_employeeRecords);
		} else if (_whichProcess.toLowerCase().equals("paye")) {
			form = new Paye(_employeeRecords);
		} else {
			System.out.println("You have not provided a valid process.\nPlease try again with one of the four process (payslips, employees, burden or paye).");
			System.exit(1);
		}
		return form;
	}
	public EmployeeRecords getEmployeeRecords(){
		return _employeeRecords;
	}
	public boolean anyErrors(){
		return _anyErrors;
	}
	public ErrorLog getErrorLog(){
		return _errorLog;
	}
}

