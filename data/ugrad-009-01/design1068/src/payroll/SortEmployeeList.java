package payroll;
import java.util.Comparator;
public class SortEmployeeList{
	private IndividualEmployee[] _employeesList;
	public SortEmployeeList(IndividualEmployee[] employeesList){
		_employeesList = employeesList;
	}
	public void sortEmployeeListTID(){
		int smallestTIDIndex;
		IndividualEmployee temp;
		for(int i = 0; i < _employeesList.length-1; i++){
			smallestTIDIndex = i;
			for(int j = i+1; j < _employeesList.length; j++){
				if(_employeesList[smallestTIDIndex].getTid() > _employeesList[j].getTid()){
					smallestTIDIndex = j;
				}
			}
			if(i!=smallestTIDIndex){
				temp = _employeesList[smallestTIDIndex];
				_employeesList[smallestTIDIndex] = _employeesList[i];
				_employeesList[i] = temp;
			}
		}
	}
	public void sortEmployeeListByName(){
		int alphabetIndex;
		IndividualEmployee temp;
		for(int i = 0; i < _employeesList.length-1; i++){
			alphabetIndex = i;
			for(int j = i+1; j < _employeesList.length; j++){
				int state = compareNames(_employeesList[alphabetIndex],_employeesList[j]);
				if(state > 0){
					alphabetIndex = j;
				}
			}
			if(alphabetIndex!=i){
				temp = _employeesList[alphabetIndex];
				_employeesList[alphabetIndex] = _employeesList[i];
				_employeesList[i] = temp;
			}
		}
	}
	public int compareNames(IndividualEmployee o1, IndividualEmployee o2){
		String name1 = o1.getLastName();
        String name2 = o2.getLastName();
        int result = name1.compareTo(name2);
        if(result==0){
        	name1 = o1.getFirstName();
        	name2 = o2.getFirstName();
            result = name1.compareTo(name2);
            if(result == 0){
            	int num1 = o1.getTid();
            	int num2 = o2.getTid();
            	if(num1<num2){
            		return 0;
            	} else {
            		return 1;
            	}
            }
            return result;
        }
        return result;
	}
}
