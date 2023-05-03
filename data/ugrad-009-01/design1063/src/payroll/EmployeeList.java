package payroll;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
public class EmployeeList {
	private ArrayList<Employee>_theEmployeeList = new ArrayList<Employee>();
	public EmployeeList(String args0) throws IOException{
		InputStreamReader cin = null;
		try{
		@SuppressWarnings("resource")
		BufferedReader in = new BufferedReader(new FileReader(args0));
		String line = in.readLine();
		line = in.readLine();
		while ((line = in.readLine()) != null) {
			if(line.compareTo("") == 0){
				continue;
			}
			else if(line.charAt(0) == '#'){
				return;
			}
			else{
			String[] employeeData = line.split("\t|, ");
			Employee em1 = new Employee(employeeData);
			_theEmployeeList.add(em1);
			}
		}
		in.close();
		}finally{
			if (cin != null){
				cin.close();
			}
		}
	}
	public void printCurrentDate(){
		Date date = new Date();
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(formatDate.format(date));
	}
	public void Payslips(){
		printCurrentDate();
		Collections.sort(_theEmployeeList, new Employee.TAXIDComparator());
		for(int i = 0; i < _theEmployeeList.size(); i++){
			_theEmployeeList.get(i).printPaySlips();
		}
	}
	public void Employees(){
		printCurrentDate();
		Collections.sort(_theEmployeeList, new Employee.NameComparator());
		for(int j = 0; j < _theEmployeeList.size(); j++){
			_theEmployeeList.get(j).printEmployees();
		}
	}
	public void Burden(){
		double _burden = 0.00;
		for(int k = 0; k < _theEmployeeList.size(); k++){
			_burden = _theEmployeeList.get(k).round(_burden + _theEmployeeList.get(k).getGross());
		}
		printCurrentDate();
		_theEmployeeList.get(0).printStartToEndDate();
		System.out.println("Total Burden: $" +_burden);
	}
	public void PAYE(){
		double _PAYE1 = 0.00;
		for (int l = 0; l < _theEmployeeList.size(); l++){
			_PAYE1 = _theEmployeeList.get(l).round(_PAYE1 + _theEmployeeList.get(l).getPAYE());
			}
		printCurrentDate();
		_theEmployeeList.get(0).printStartToEndDate();
		System.out.println("Total PAYE: $" +_PAYE1);
		}
}
		
