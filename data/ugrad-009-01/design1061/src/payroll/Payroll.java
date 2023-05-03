package payroll;
import java.io.FileReader;
import java.io.BufferedReader;
public class Payroll {
	private final static String _pathNameError = "==== ERROR: Valid file not found ====";
	private final static String _processNameError = "==== ERROR: There was no process with that name ====";
	private final static String _argsInputError = "=== ERROR: invalid number of arguments given to program ===";
	private final static String _emptyFileError = "=== ERROR: The given file was empty ===";
	public static final int _nameAsKey = 0;
	public static final int _tidAsKey = 1;
	public static void main(String[] args){
		if (args.length == 2){
			String filePath = args[0];
			String process = args[1];
			if ( checkFileExist(filePath) ){
				ProcessFile fileReader = new ProcessFile(filePath);
				String[] infoList = fileReader.getFileLines();
				int key;
				if (process.equals("Payslips")){key = _tidAsKey;}
				else {key = _nameAsKey;}
				EmployeeList employeeList = new EmployeeList(infoList, key);
				if (process.equals("Payslips")){
					employeeList.outputPayslips();
				} else if(process.equals("Employees")){
					employeeList.outputEmployees();
				} else if(process.equals("Burden")){
					employeeList.outputBurden();
				} else if (process.equals("PAYE")){
					employeeList.outputPAYE();
				} else {
					System.out.println(_processNameError);
				}
			}
		} else {
			System.out.println(_argsInputError);
		}
	}
	private static boolean checkFileExist(String filePath){
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			if (br.readLine() == null){
				System.out.println(_emptyFileError);
				br.close();
				return false;
			}
			br.close();
		} catch (Exception e){
			System.out.println(_pathNameError);
			return false;
		}
	return true;
	}
}
