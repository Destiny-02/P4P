package payroll;
public class EmployeeList {
	private Employee _head;
	public EmployeeList() {
		_head = null;
	}
	public Employee GetFirst() {
		return _head;
	}
	public void add(String[] employeeInfo) {
		Employee current = _head;
		Employee previous = _head;
		Employee newEmployee = new Employee(employeeInfo);
		String currentLastName, currentFirstName, newLastName = "", newFirstName, previousLastName = "";
		if (_head == null) {
			_head = newEmployee;
			return;
		}
		while (current != null) {
			currentLastName = current.GetLastName();
			currentFirstName = current.GetFirstName();
			newLastName = newEmployee.GetLastName();
			newFirstName = newEmployee.GetFirstName();
			previousLastName = previous.GetLastName();
			if ((currentLastName).compareTo(newLastName) == 0) {
				if (currentFirstName.compareTo(newFirstName) >= 0) {
					previous.SetNext(newEmployee);
					newEmployee.SetNext(current);
					return;
				}
			} else if (currentLastName.compareTo(newLastName) > 0) {
				if (current == _head) {
					newEmployee.SetNext(current);
					_head = newEmployee;
					return;
				}
				previous.SetNext(newEmployee);
				newEmployee.SetNext(current);
				return;
			}
			previous = current;
			current = current.GetNext();
		}
		if ((current == null) && (previousLastName.compareTo(newLastName) < 0)) {
			previous.SetNext(newEmployee);
		}
	}
	public Employee FindNextSmallest(int previousTaxID) {
		int smallestID = 2147483647;
		Employee nextSmallest = null;
		Employee current = _head;
		int currentID = current.GetTaxID();
		while (current != null) {
			currentID = current.GetTaxID();
			if ((currentID > previousTaxID) && (currentID < smallestID)) {
				nextSmallest = current;
				smallestID = currentID;
			}
			current = current.GetNext();
		}
		return nextSmallest;
	}
}
