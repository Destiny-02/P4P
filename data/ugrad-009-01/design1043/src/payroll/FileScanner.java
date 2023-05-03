package payroll;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class FileScanner {
	public ArrayList<Employee> fileReader(String input){
		ArrayList<Employee> _employeeList = new ArrayList<Employee>();
		Scanner s = null;
		try {
			s = new Scanner(new FileInputStream(new File(input)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(s.hasNextLine()){
			String employeeData = s.nextLine();
			String[] tempEmployeeData = employeeData.split("\t");
			if(tempEmployeeData[0].startsWith("#") || employeeData.isEmpty()){
				if(!s.hasNextLine()){
					break;
				}
			}
			else{
				ExceptionCheck e = new ExceptionCheck();
				e.checkException(tempEmployeeData);
				Employee tempEmployee = new Employee(Integer.parseInt(tempEmployeeData[0]),tempEmployeeData[1], tempEmployeeData[2],Double.parseDouble(rid$(tempEmployeeData[3])),Double.parseDouble(rid$(tempEmployeeData[4])),(tempEmployeeData[5]),(tempEmployeeData[6]),Double.parseDouble(tempEmployeeData[7]),Double.parseDouble(rid$(tempEmployeeData[8])));
				e.checkNonsenseValues(tempEmployee);
				_employeeList.add(tempEmployee);
			}
		}
		for(int i=0; i < _employeeList.size()-1;i++){
			Employee emp = (_employeeList.get(i));
			try {
				emp.tidIdentical(_employeeList.get(i),_employeeList.get(i+1));
			} catch (InputError e) {
				{System.out.println(e);}
			}
		}
		s.close();
		return _employeeList;
	}
	private String rid$(String s){
		s = s.replace("$", "");
		return s;
	}
}

