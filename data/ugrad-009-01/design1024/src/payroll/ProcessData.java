
package payroll;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
public class ProcessData {
    private String _allText;
    private int _size = 0;
    private EmployeeList _empList;
    public ProcessData(String input) {
        BufferedReader br = null;
        try {
            String CurrentLine;
            _empList = new EmployeeList();
            int lineCount = 0;
            br = new BufferedReader(new FileReader(input));
            String employeeText = "";
            Set<String> TIDset = new HashSet<String>();
            while ((CurrentLine = br.readLine()) != null) {
                if (!CurrentLine.isEmpty()) {
                    try {
                        if (!(CurrentLine.charAt(0) == ('#'))) {
                            employeeText = CurrentLine;
                            lineCount += 1;
                            String[] empElements = employeeText.split("\t");
                            if (empElements[3].charAt(0)!='$' || empElements[4].charAt(0)!='$' ||
                                    empElements[8].charAt(0)!='$'){
                                throw new InputException("Invalid Employee: Please check Line "
                                        + lineCount + " for missing relevant $ sign.");
                            }
                            if (empElements.length != 9) {
                                throw new InputException("Invalid Employee: Please check "
                                        + "Line " + lineCount + " and ensure all information"
                                        + " is set in the appropriate fashion, separated"
                                        + " by a tab space.");
                            }
                            if (!TIDset.contains(empElements[0])){
                                TIDset.add(empElements[0]);
                            } else {
                                throw new InputException("Invalid Employee: Please check "
                                        + "Line " + lineCount + " for duplicate TID.");
                            }
                            Employee currentEmp = new Employee(empElements);
                            _empList.add(currentEmp);
                        }
                    } catch (InputException e){
                        e.printException();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void processOutput(Process inputProcess) {
        inputProcess.processOutput(_empList);
    }
}
