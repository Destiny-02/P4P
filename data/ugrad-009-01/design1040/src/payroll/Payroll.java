package payroll;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
public class Payroll {
    private static Roster _myRoster;
    public static void main(String[] args) throws IOException {
        LinkedList<String> inputList = new LinkedList<>();
        String content;
        try {
            FileReader fs = new FileReader(args[0]);
            BufferedReader theReader = new BufferedReader(fs);
            while ((content = theReader.readLine()) != null) {
                if(content.indexOf('#') < 0 && content.length() > 1) {
                    inputList.add(content);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("IO Error Occurred");
        }
        _myRoster = new Roster();
        for(String swag : inputList) {
            String[] allInfo = swag.split("\t");
            _myRoster.addEmployee(allInfo);
        }
        String inputProcess = args[1];
        Process theProcess = applyProcess(inputProcess);
        if (theProcess.engage()) {
        } else {
            throw new PayrollException("Invalid Process");
        }
    }
    public static Roster getRoster() {
        return _myRoster;
    }
    public static Process applyProcess(String process) {
        if (process.equals("Payslips")) {
            return new PayslipProcess();
        } else if (process.equals("Employees")) {
            return new EmployeeProcess();
        } else if (process.equals("Burden")) {
            return new BurdenProcess();
        } else if (process.equals("PAYE")) {
            return new PAYEProcess();
        } else {
            return new InvalidProcess();
        }
    }
}