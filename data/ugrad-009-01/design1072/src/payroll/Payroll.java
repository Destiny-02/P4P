package payroll;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Payroll {
	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<Record> Database = new ArrayList<Record>();
		Payroll proll = new Payroll();
		Database = proll.inputFiles(args);
		switch(args[1]) {
		case "Payslips": {
			Payslips pay = new Payslips();
			pay.process(Database);
			break;
		}
		case "Employees": {
			Employees emp = new Employees();
			emp.process(Database);
			break;
		}
		case "Burden": {
			Burden bur = new Burden();
			bur.process(Database);
			break;
		}
		case "PAYE": {
			PAYE paye = new PAYE();
			paye.process(Database);
			break;
		}
		default: {
			throw new IllegalArgumentException("Invalid process: " + args[1]);
		}
		}
	}
	private ArrayList<Record> inputFiles(String[] args) throws
			FileNotFoundException, IllegalArgumentException {
		File inputFile = new File(args[0]);
		Scanner input = new Scanner(inputFile);
		String nextLine = new String();
		ArrayList<Record> Database = new ArrayList<Record>();
		while (input.hasNextLine()) {
			nextLine = input.nextLine();
			String[] line = nextLine.split("\t");
			Record rec = new Record();
			if (!nextLine.equals("") && nextLine.charAt(0)!='#') {
				rec.storage(line, Database);
			}
		}
		input.close();
		return Database;
	}
}
