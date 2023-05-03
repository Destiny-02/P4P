package payroll;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Payroll {
    public static void main(String[] args) throws MyExceptions,FileNotFoundException {
        EmployeeList personlist = new EmployeeList();
        File inputfile = new File(args[0]);
        Scanner scan = new Scanner(inputfile);
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (!(line.equals("")) && (!(line.charAt(0) == ('#')))) {
                String[] info = line.split("\t");
                if ((info.length != 9) || (info[3].charAt(0)!='$')||(info[4].charAt(0)!='$')||(info[8].charAt(0)!='$')){
                    throw new MyExceptions("Invalid Employee Record Format");
                }
                Employee person = new Employee(info);
                personlist.add(person);
            }
        }
        Command process;
        switch(args[1]){
            case "Payslips" :
                process = new ProcessPayslips();
                break;
            case "Employees":
                process = new ProcessEmployees();
                break;
            case "Burden":
                process = new ProccessBurden();
                break;
            case "PAYE":
                process = new ProccessPAYE();
                break;
            default: throw new MyExceptions("Invalid Proccess");
        }
        process.engage(personlist);
    }
}
