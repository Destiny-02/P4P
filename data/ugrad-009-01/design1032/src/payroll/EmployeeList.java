package payroll;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
public class EmployeeList {
	private List<Employee> _employeeArray;
	public List<Employee> getEmployeeArray() {
		return _employeeArray;
	}
	public void makeEmployeeList(String filename) {
		_employeeArray = new ArrayList<Employee>();
		File file = new File(filename);
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if(!line.startsWith("#") && !line.equals("")) {
				String[] employeeInfo = line.split(("[\t,$]+"));
				Employee employee = new Employee(employeeInfo);
				_employeeArray.add(employee);
				}
			}
			sc.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void sortListByID() {
		Collections.sort(_employeeArray, new IDComparator());
	}
	public void sortListByName() {
		Collections.sort(_employeeArray, new NameComparator());
	}
	public void outputProcess(String processType) {
		double totalBurden = 0;
		double totalPAYE = 0;
		Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		System.out.println(ft.format(dNow));
		for (int listIndex = 0; listIndex < _employeeArray.size(); listIndex++) {
			if (processType.equals("Payslips")) {
				Payslip payslip = new Payslip(_employeeArray.get(listIndex));
				payslip.printPayslip();
			}
			else if (processType.equals("Employees")) {
				System.out.println(_employeeArray.get(listIndex).generateReport(processType, 0.0));
			}
			totalBurden += _employeeArray.get(listIndex).getGrossCopy();
			totalPAYE += _employeeArray.get(listIndex).getPAYECopy();
		}
		if (processType.equals("Burden")) {
			System.out.println(_employeeArray.get(0).generateReport(processType, totalBurden));
		}
		else if (processType.equals("PAYE")) {
			System.out.println(_employeeArray.get(0).generateReport(processType, totalPAYE));
		}
	}
}