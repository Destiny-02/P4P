package payroll;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.LinkedList;
public class EmployeeList{
	private LinkedList<Employee> _empLinkedList = new LinkedList<Employee>();
	public EmployeeList(String inputFile, String processing){
		File fileObj = new File(inputFile);
		readEmployees(fileObj.file);
	}
	public void readEmployees(FileReader file){
		Scanner input = new Scanner(file);
		String str = null;
		while(input.hasNext()){
			str = input.nextLine();
			str = str.trim();
			if(!str.equals("")){
				if(!str.substring(0,1).equals("#")){
					_empLinkedList.add(new Employee(str));
				}
			}
		}
		input.close();
		try {
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(_empLinkedList.size()==0){
			System.err.println("No Employees Found");
			throw new IllegalArgumentException();
		}
	}
	public LinkedList<Employee> getList(){
		return _empLinkedList;
	}
	public void swap(int i1, int i2){
		Employee e1 = _empLinkedList.get(i1);
		Employee e2 = _empLinkedList.get(i2);
		_empLinkedList.remove(i1);
		_empLinkedList.add(i1,e2);
		_empLinkedList.remove(i2);
		_empLinkedList.add(i2,e1);
	}
	public void sort(String process){
		for(int i=0;i<_empLinkedList.size()-1;i++){
			for(int j=0;j<_empLinkedList.size()-1-i;j++){
				if(_empLinkedList.get(j).compare(_empLinkedList.get(j+1), process)>0){
					swap(j,j+1);
				}
			}
		}
	}
	public double total(String process){
		double total = 0;
		if(process.equals("Burden")){
			for(int i=0;i<_empLinkedList.size(); i++){
				total += _empLinkedList.get(i).getGross();
			}
		} else if(process.equals("PAYE")){
			for(int i=0;i<_empLinkedList.size(); i++){
				total += _empLinkedList.get(i).getPAYE();
			}
		}
		return total;
	}
}
