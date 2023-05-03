package payroll;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.isReadable;
public class Processor {
    private EmployeeRecord _employeeRecord = new EmployeeRecord();
    public Processor(String arg0, String arg1) {
        ScanFileToRecord(Paths.get(arg0));
        ProcessDecider(arg1);
    }
    String GetISO8601FormatDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
    private void ScanFileToRecord(Path filePath) {
        String[] splitLine;
        String line;
        if (exists(filePath) && isReadable(filePath)) {
            try (InputStream in = Files.newInputStream(filePath);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                while (reader.ready()) {
                    line = reader.readLine();
                    if (line.equals("") || line.startsWith("#")) {
                        continue;
                    }
                    splitLine = line.replace(", ", "\t").replace("$", "").split("\t");
                    if (splitLine[3].toLowerCase().equals("hourly")) {
                        _employeeRecord.AddEmployee(new HourlyEmployee(splitLine));
                    } else if (splitLine[3].toLowerCase().equals("salaried")) {
                        _employeeRecord.AddEmployee(new SalariedEmployee(splitLine));
                    } else {
                        throw new PayrollRuntimeException("A line in the text file did not match the expected format.");
                    }
                }
            } catch (IOException msg) {
                throw new PayrollRuntimeException("The file experienced an IO exception.");
            }
        } else if(!exists(filePath)) {
            throw new PayrollRuntimeException("The specified file path does not exist.");
        } else {
            throw new PayrollRuntimeException("The specified file was not readable.");
        }
    }
    private void ProcessDecider(String arg1) {
        System.out.println(GetISO8601FormatDate());
        switch (arg1) {
            case "Payslips":    System.out.println(_employeeRecord.PayslipProcessingString());
                break;
            case "Employees":   System.out.println(_employeeRecord.EmployeeProcessingString());
                break;
            case "Burden":      System.out.println(_employeeRecord.BurdenProcessingString());
                break;
            case "PAYE":        System.out.println(_employeeRecord.PAYEProcessingString());
                break;
            default:            throw new PayrollRuntimeException("Input '" + arg1 + "' did not match any processes.");
        }
    }
}
