package payroll;
import java.util.List;
import java.util.Vector;
public abstract class CompanyDetails {
	private List<String>  _tid= new Vector<String>();
	private List<String>  _name= new Vector<String>();
	private List<String>  _employment= new Vector<String>();
	private List<String>  _rate= new Vector<String>();
	private List<String>  _ytd= new Vector<String>();
	private List<String>  _start= new Vector<String>();
	private List<String>  _end= new Vector<String>();
	private List<String>  _hours= new Vector<String>();
	private List<String>  _deduction= new Vector<String>();
	private List<List<String>> _allInfoList= new Vector<List<String>>();
	public void fillListOfList(){
		_allInfoList.add(_tid);
		_allInfoList.add(_name);
		_allInfoList.add(_employment);
		_allInfoList.add(_rate);
		_allInfoList.add(_ytd);
		_allInfoList.add( _start);
		_allInfoList.add( _end);
		_allInfoList.add(_hours);
		_allInfoList.add(_deduction);
	}
	public void separateInfoLists(List<String> information){
		for (int i = 0; i <information.size(); i ++){
			if (i % 9 == 0){
				_tid.add(information.get(i));
			}else if (i % 9 == 1){
				_name.add(information.get(i));
			}else if (i % 9 == 2){
				_employment.add(information.get(i));
			}else if (i % 9 == 3){
				_rate.add(information.get(i));
			}else if (i % 9 == 4){
				_ytd.add(information.get(i));
			}else if (i % 9 == 5){
				_start.add(information.get(i));
			}else if (i % 9 == 6){
				_end.add(information.get(i));
			}else if (i % 9 == 7){
				_hours.add(information.get(i));
			}else if (i % 9 == 8){
				_deduction.add(information.get(i));
			}
		}
	}
	public void sortEmployeesInfo(){
		String temp;
		for (int i = 0; i < _name.size() - 1; i++){
			for (int j = 0; j < _name.size() -1; j ++){
				if ((_name.get(j+1).compareTo(_name.get(j)) < 0)){
					for (int k = 0; k < _allInfoList.size(); k ++){
						temp = _allInfoList.get(k).get(j);
						_allInfoList.get(k).set(j, _allInfoList.get(k).get(j+1));
						_allInfoList.get(k).set(j+1, temp);
					}
				}
			}
		}
	}
	public void sortPayslipsInfo() throws NumberFormatException{
		String temp;
		for (int i = 0; i < _tid.size() - 1; i++){
			for (int j = 0; j < _tid.size() -1; j ++){
				if (Integer.parseInt(_tid.get(j + 1)) < Integer.parseInt(_tid.get(j))){
					for (int k = 0; k < _allInfoList.size(); k ++){
						temp = _allInfoList.get(k).get(j);
						_allInfoList.get(k).set(j,_allInfoList.get(k).get(j+1));
						_allInfoList.get(k).set(j+1, temp);
					}
				} else if (Integer.parseInt(_tid.get(j + 1)) == Integer.parseInt(_tid.get(j))){
					System.out.println("ERROR! Invalid TID entries! All TID entires must be unique!");
				}
			}
		}
	}
	public double calculateTotalPaye(){
		double totalPaye =0;
		List<String> payeInformation = new Vector<String>();
		for (int i = 0; i < _name.size(); i ++){
			payeInformation.clear();
			for (int j = 0; j < _allInfoList.size(); j++){
				payeInformation.add(_allInfoList.get(j).get(i));
			}
			EmployeeDetails payeRecord = new EmployeeDetails(payeInformation);
			totalPaye = Double.parseDouble(payeRecord.calculatePaye().substring(1)) + totalPaye;
		}
		return totalPaye;
	}
	public double calculateBurden(){
		double burden =0;
		List<String >burdenInformation = new Vector<String>();
		for (int i = 0; i < _name.size(); i ++){
			burdenInformation.clear();
			for (int j = 0; j < _allInfoList.size(); j++){
				burdenInformation.add(_allInfoList.get(j).get(i));
			}
			EmployeeDetails burdenRecord = new EmployeeDetails(burdenInformation);
			burden = Double.parseDouble(burdenRecord.calculateGross().substring(1)) + burden;
		}
		return burden;
	}
	public void printEmployeesResult(){
		List<String >employeesInformation = new Vector<String>();
		for (int i = 0; i < _name.size(); i ++){
			employeesInformation.clear();
			for (int j = 0; j < _allInfoList.size(); j++){
				employeesInformation.add(_allInfoList.get(j).get(i));
			}
			EmployeeDetails employeesRecord = new EmployeeDetails(employeesInformation);
			employeesRecord.printEmployeesRecords();
		}
	}
	public void printPayslipsResult(){
		List<String >payslipsInformation = new Vector<String>();
		for (int i = 0; i < _tid.size(); i ++){
			payslipsInformation.clear();
			for (int j = 0; j < _allInfoList.size(); j++){
				payslipsInformation.add(_allInfoList.get(j).get(i));
			}
			EmployeeDetails payslipsRecord = new EmployeeDetails(payslipsInformation);
			payslipsRecord.printPayslipsRecords();
		}
	}
	public void printPeriod(){
		System.out.println(_start.get(0)+" to "+_end.get(0));
	}
}
