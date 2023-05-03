package payroll;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
public class Payroll {
	public static void main(String[] args) throws EmployeeException {
		Hashtable<Integer, Employee> employeeList = new Hashtable<Integer,Employee>();
		List<String> input = new ArrayList<String>();
		final String PAYSLIPS = "Payslips";
		final String EMPLOYEES = "Employees";
		final String BURDEN = "Burden";
		final String PAYE = "PAYE";
		try {
			String line;
			String fileName = args[0];
			InputStream inputStream = new FileInputStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			while((line = br.readLine()) != null){
				if((!(line.equals(""))) && (!(line.substring(0, 1)).equals("#"))){
					input.add(line);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < input.size(); i++){
			Employee employee = new Employee(input.get(i));
			Integer TID = employee.GetTID();
			employeeList.put(TID, employee);
		}
		String process = args[1];
		if(process.equals(PAYSLIPS)){
			Payslips payslips = new Payslips();
			payslips.process(employeeList);
		}
		else if(process.equals(EMPLOYEES)){
			Employees employees = new Employees();
			employees.process(employeeList);
		}
		else if(process.equals(BURDEN)){
			Burden burden = new Burden();
			burden.process(employeeList);
		}
		else if(process.equals(PAYE)){
			PAYE paye = new PAYE();
			paye.process(employeeList);
		}
		else{
			System.out.println("INVALID PROCESS");
		}
	}
}
