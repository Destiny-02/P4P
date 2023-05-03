package payroll;
import java.text.DecimalFormat;
public class Processing {
	EmployeeList _eList;
	private String _process;
	private String _fileName;
	public Processing(String fileName, String prcss){
		_process = prcss;
		_fileName = fileName;
	}
	public void createList(){
		_eList = new EmployeeList(_fileName,_process);
	}
	public void processList(){
		if(_process.equals("Payslips")||_process.equals("Employees")){
			_eList.sort(_process);
			for(int i=0; i<_eList.getList().size();i++){
				_eList.getList().get(i).printDetails(_process);
			}
		} else if(_process.equals("Burden")||_process.equals("PAYE")){
			DecimalFormat df = new DecimalFormat("#.00");
			double total = _eList.total(_process);
			System.out.println(_eList.getList().get(0)._start+" to "+_eList.getList().get(0)._end);
			System.out.println("Total "+_process+": $"+df.format(total));
		} else {
			throw new IllegalArgumentException("Process name is invalid!\n"
					+ "Should either be \"Payslip\", \"Burden\", \"Employees\", or \"PAYE\" [case sensitive].");
		}
	}
}
