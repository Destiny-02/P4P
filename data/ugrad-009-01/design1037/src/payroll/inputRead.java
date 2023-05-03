package payroll;
import java.util.Scanner;
public class inputRead {
	Scanner input;
	private boolean hasNext(){
		return (input.hasNextLine());
	}
	private void enterEmployee(String line, database db){
		String[] parts = line.split("\t");
		employee temp = new employee();
		temp.tid = Integer.parseInt(parts[0]);
		temp.name = parts[1];
		temp.salary = (parts[2].equals("Salaried"));
		temp.rate = Double.parseDouble(parts[3].substring(1,parts[3].length()));
		temp.ytd = Double.parseDouble(parts[4].substring(1,parts[4].length()));
		temp.start = parts[5];
		temp.end = parts[6];
		temp.hours = Double.parseDouble(parts[7]);
		temp.deductions = Double.parseDouble(parts[8].substring(1,parts[8].length()));
		db.employeeList.add(temp);
	}
	public void getEmployees(database db){
		while (hasNext()){
			String line = input.nextLine();
			if (line.equals(null)){
				break;
			}
			if (!(line.isEmpty())){
				if (!(line.substring(0,1).equals("#")) && !(line.substring(0,1).equals(""))){
					enterEmployee(line, db);
				}
			}
		}
	}
}