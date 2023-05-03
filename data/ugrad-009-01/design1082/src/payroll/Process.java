package payroll;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Process {
	private ArrayList<EmployeeData> processlist = new ArrayList<EmployeeData>();
	private double _Burden;
	private double _NetPaye;
	private String _startDate;
	private String _endDate;
	public Process(ArrayList<EmployeeData> list) throws PayrollExceptions {
		for(EmployeeData data : list){
			Calculations result = new Calculations(data);
			_Burden += result.getgross();
			_NetPaye += result.getpaye();
		}
		_startDate = list.get(0).getstart();
		_endDate = list.get(0).getend();
		processlist = list;
		ErrorCheck errorChecklist = new ErrorCheck(list);
		errorChecklist.CheckDuplication();
		errorChecklist.CheckDates();
		errorChecklist.NegativeInput();
		errorChecklist.NegativeEarn();
	}
	public void date(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
	}
	public void Payslips(){
		List<Integer> TIDs = new ArrayList<Integer>();
		for(EmployeeData data : processlist){
			TIDs.add(data.getTID());
		}
		Collections.sort(TIDs);
		for(int i = 0; i < TIDs.size(); i++){
			for(EmployeeData data : processlist){
				int TID = data.getTID();
				if(TIDs.get(i) == TID){
					String[] name = data.getname().replace(" " , "").split("\\,");
					System.out.print(data.getTID() + ". " + name[1] + " " + name[0] + ",");
					System.out.print(" " + "Period: " + _startDate + " " + "to" + " " + _endDate + ". ");
					System.out.print("Gross: " + "$" + String.format("%.2f", data.getgross()) + ", " + "PAYE: " + "$");
					System.out.print(String.format("%.2f",data.getpaye()) + ", " + "Deductions: " + "$");
					System.out.print(String.format("%.2f", data.getdeduction()) + " Nett: $" + String.format("%.2f", data.getNett()));
					System.out.print(" YTD: $" + String.format("%.2f", data.getYTD()));
					System.out.println();
				}
			}
		}
	}
	public void Employees(){
		List<String> FamilyNames = new ArrayList<String>();
		for(EmployeeData data : processlist){
			String[] name = data.getname().replace(" " , "").split("\\,");
			FamilyNames.add(name[0]);
		}
		Collections.sort(FamilyNames);
		for(int i = 0; i < FamilyNames.size(); i++){
			for(EmployeeData data : processlist){
				String[] name = data.getname().replace(" " , "").split("\\,");
				if(name[0].equals(FamilyNames.get(i))){
					System.out.print(data.getname() + " (" + data.getTID() + ") " + data.getemployment() + ", ");
					System.out.print("$" + String.format("%.2f", data.getrate()) + " YTD:$");
					System.out.print(String.format("%.2f", data.getYTD()) + "\n");
				}
			}
		}
	}
	public void Burden(){
		System.out.println(_startDate + " " + "to" + " " + _endDate);
		System.out.println("Total Burden:" + " " + "$" + String.format("%.2f", _Burden));
	}
	public void PAYE(){
		System.out.println(_startDate + " " + "to" + " " + _endDate);
		System.out.println("Total PAYE:" + " " + "$" + String.format("%.2f", _NetPaye));
	}
}
