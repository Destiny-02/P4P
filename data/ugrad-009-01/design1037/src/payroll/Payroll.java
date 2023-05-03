package payroll;
import java.util.Date;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class Payroll {
	public static void main(String[] args) throws FileNotFoundException {
		database db = new database();
		db.reader.input = new Scanner(new FileInputStream(new File(args[0])));
		db.reader.getEmployees(db);
		if(args[1].equals("Payslips")){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			System.out.println(dateFormat.format(date));
			db.outF.numSort(db);
			db.calc.calcGross(db);
			db.calc.calcPaye(db);
			db.outF.printOutput(db, 1);
		}
		else if(args[1].equals("Employees")){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			System.out.println(dateFormat.format(date));
			db.outF.alphaSort(db);
			db.calc.calcGross(db);
			db.outF.printOutput(db, 2);
		}
		else if(args[1].equals("Burden")){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			System.out.println(dateFormat.format(date));
			db.calc.calcGross(db);
			db.outF.printOutput(db, 3);
		}
		else if(args[1].equals("PAYE")){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			System.out.println(dateFormat.format(date));
			db.calc.calcGross(db);
			db.calc.calcPaye(db);
			db.outF.printOutput(db, 4);
		}
		else{
			System.out.println("You entered a wrong process type");
		}
	}
}
