package payroll;
import payroll.commands.Burden;
import payroll.commands.Employees;
import payroll.commands.Paye;
import payroll.commands.Payslips;
import payroll.employees.EmployeeList;
import payroll.input.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
public class Payroll {
	static String _filename = "";
	static String _command = "";
	static EmployeeList _employeeList;
	static FileReader _fileReader;
	static Scanner _keyboard = new Scanner(System.in);
	public static final double CentsInADollar = 100;
	public static void main(String[] args) {
		if (args.length == 2) {
			_filename = args[0];
			_command = args[1];
		}
		_employeeList = new EmployeeList();
		readFile();
		executeCommand();
		_keyboard.close();
	}
	public static String dateToday() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dateobj = new Date();
		return df.format(dateobj);
	}
	public static double roundCent(double money) {
		money *= CentsInADollar;
		money = Math.round(money);
		return money / CentsInADollar;
	}
	static void readFile() {
		_fileReader = new FileReader();
		verifyFilename();
		_fileReader.readInTo(_employeeList);
	}
	static void verifyFilename() {
		while (!_fileReader.doesFileExist(_filename)) {
			System.out.println("Sorry, that file does not exist. Please enter a valid filename:");
			_filename = _keyboard.nextLine();
		}
	}
	@SuppressWarnings("unused")
	static void executeCommand() {
		forever: while (true) {
			switch (_command) {
			case "Payslips":
				Payslips payslips = new Payslips(_employeeList);
				break forever;
			case "Employees":
				Employees employees = new Employees(_employeeList);
				break forever;
			case "Burden":
				Burden burden = new Burden(_employeeList);
				break forever;
			case "PAYE":
				Paye paye = new Paye(_employeeList);
				break forever;
			case "q":
				break forever;
			default:
				System.out.println("Please enter a valid command or just q to quit");
				_command = _keyboard.nextLine();
			}
		}
	}
}
