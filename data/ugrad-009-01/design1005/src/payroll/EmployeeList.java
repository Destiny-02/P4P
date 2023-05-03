package payroll;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ArrayList;
class EmployeeList {
    void sortById(LinkedList<Person> Employees) {
        Collections.sort(Employees, new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return (p1.getTaxId() - p2.getTaxId());
            }
        });
    }
    void sortByName(LinkedList<Person> Employees) {
        Collections.sort(Employees, new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                if (p1.getName()[0].compareTo(p2.getName()[0])==0) {
                    return (p1.getName()[1].compareTo(p2.getName()[1]));
                }
                return (p1.getName()[0].compareTo(p2.getName()[0]));
            }
        });
    }
    ArrayList<Integer> checkTaxIds(ArrayList<Integer> taxIdList) {
        ArrayList<Integer> doubleUps = new ArrayList<>();
        for (int i=0; i<taxIdList.size()-1; i++) {
            for (int j=i+1; j<taxIdList.size(); j++) {
                if (taxIdList.get(i).equals(taxIdList.get(j))) {
                    if (!doubleUps.contains(taxIdList.get(i))) {
                        doubleUps.add(taxIdList.get(i));
                    }
                }
            }
        }
        doubleUps.sort(Comparator.naturalOrder());
        return doubleUps;
    }
}