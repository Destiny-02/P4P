package payroll;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
public class Payroll {
    public static void main(String[] args) {
        LinkedList<Person> Employees = new LinkedList<>();
        String line;
        int lineCount=0;
        ArrayList<Integer> taxIdList = new ArrayList<>();
        boolean errors = false, noPeople = true;
        InvalidInputException error = new InvalidInputException();
        try {
            FileInputStream fs = new FileInputStream(args[0]);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fs));
            do {
                line = reader.readLine();
                lineCount++;
                if ((line.startsWith("#"))||(line.equals(""))) {
                    continue;
                }
                String[] payArray = line.split("\t");
                if (payArray.length != 9) {
                    error.printErrorMessage(lineCount,"Incorrect number of employee details found, check tab spacings are properly implemented");
                    continue;
                }
                try {
                    taxIdList.add(Integer.parseInt(payArray[0]));
                    Person p = new Person(payArray, lineCount);
                    Employees.add(p);
                    noPeople = false;
                } catch (InvalidInputException iie) {
                    errors = true;
                }
            } while (reader.ready());
            reader.close();
        } catch (IOException ioe) {
            System.out.println("Error Reading File");
            System.exit(0);
        }
        EmployeeList list = new EmployeeList();
        ArrayList<Integer> doubleUps = list.checkTaxIds(taxIdList);
        if (doubleUps.size()!=0) {
            System.out.print("Error: Same Tax ID used more than once (Tax ID: ");
            for (int i : doubleUps) {
                if (i == doubleUps.get(doubleUps.size() - 1)) {
                    System.out.print(i);
                } else
                    System.out.print(i + ", ");
            }
            System.out.print(")\n");
            errors = true;
        }
        if (noPeople) {
            System.out.println("Error: No employees found in input file");
            System.exit(0);
        }
        if (errors) {
            System.out.println("System aborted due to errors in input file");
            System.exit(0);
        }
        switch (args[1]) {
            case "Payslips":
                Payslips p = new Payslips();
                p.printInfo(Employees);
                break;
            case "Employees":
                Employees e = new Employees();
                e.printInfo(Employees);
                break;
            case "Burden":
                Burden b = new Burden(Employees);
                b.printInfo(Employees);
                break;
            case "PAYE":
                PAYE t = new PAYE(Employees);
                t.printInfo(Employees);
                break;
            default:
                System.out.println("Error - check processing type");
        }
    }
}