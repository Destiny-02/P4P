package payroll;
import java.nio.file.NoSuchFileException;
import java.time.format.DateTimeParseException;
public class Payroll {
	private AllEmployees _employeesList;
	private FileReader _loadFile;
	private PayrollProcess _doProcess;
	private enum ProcessType{
		PAYSLIPS(new Payslips()),
		EMPLOYEES(new Employees()),
		PAYE(new PAYE()),
		BURDEN(new Burden());
		private PayrollProcess _someProcess;
		ProcessType(PayrollProcess type){
			_someProcess = type;
		}
		public static PayrollProcess getProcess(String type){
			switch(type.toLowerCase()){
			case "payslips":
				return PAYSLIPS._someProcess;
			case "employees":
				return EMPLOYEES._someProcess;
			case "paye":
				return PAYE._someProcess;
			case "burden":
				return BURDEN._someProcess;
			default:
				throw new RuntimeException("Invalid process: "+type);
			}
		}
	}
	public Payroll(String[] args){
		_employeesList = new AllEmployees();
		if(args.length==0){throw new RuntimeException("No arguments! Please supply arguments for processing.");}
		if(args.length==1){throw new RuntimeException("Either path to file or processing type was not specified (missing argument!)");}
		this.getProcess(args[1]);
		try{
			putFileInReader(args[0]);
		}catch(NoSuchFileException err){
			throw new RuntimeException("File not found at location "+err.getLocalizedMessage());
		}
	    String input = null;
	    while ((input = RecordsFile().getNextLine()) != null) {
	    	try{
	        if(!input.startsWith("#")&&!input.isEmpty()){
	        	EmployeeList().storeRecordFromString(input);
	        }
	        }catch(NumberFormatException nEx){
	        	System.err.println("Error in records file on line "+RecordsFile().getLineNumber()+". "+nEx.getLocalizedMessage());
	        }catch(DateTimeParseException dEx){
	        	System.err.println("Error in records file on line "+RecordsFile().getLineNumber()+". "+dEx.getLocalizedMessage());
	        }catch(ParseException epe){
	        	System.err.println("Error in records file on line "+RecordsFile().getLineNumber()+". "+epe.getLocalizedMessage());
	        }
	    }
	}
	private FileReader RecordsFile(){
		return _loadFile;
	}
	public void putFileInReader(String pathToFile) throws NoSuchFileException{
		_loadFile = new FileReader(pathToFile);
	}
	private AllEmployees EmployeeList(){
		return _employeesList;
	}
	public void getProcess(String type){
		_doProcess = ProcessType.getProcess(type);
	}
	public void doProcess(){
		_doProcess.process(_employeesList);
		this.toOutput(_doProcess.output());
	}
	public void toOutput(String output){
		System.out.print(output);
	}
	public static void main(String[] args){
		Payroll currentPayroll = new Payroll(args);
		currentPayroll.doProcess();
	}
}