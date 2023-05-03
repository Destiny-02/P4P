package payroll;
import java.util.Comparator;
public enum PayrollComparitor implements Comparator<Employee> {
	TID_SORT {
		public int compare(Employee e1, Employee e2) {
			return e1.getTID() - e2.getTID();
		}
	},
	ALPHABETICAL_SORT {
		public int compare(Employee e1, Employee e2) {
			if (e1.getFamilyName().compareToIgnoreCase(e2.getFamilyName()) != 0) {
				return e1.getFamilyName().compareToIgnoreCase(e2.getFamilyName());
			} else if (e1.getFamilyName().compareToIgnoreCase(e2.getFamilyName()) != 0) {
				return e1.getFamilyName().compareToIgnoreCase(e2.getFamilyName());
			} else {
				return e1.getTID() - e2.getTID();
			}
		}
	};
	public static Comparator<Employee> getComparator(final PayrollComparitor option) {
		return new Comparator<Employee>() {
			public int compare(Employee e1, Employee e2) {
				return e1.getTID() - e2.getTID();
			}
		};
	}
}
