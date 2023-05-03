package payroll;
import java.util.Comparator;
public abstract class Employee {
	protected String familyName;
	protected String givenName;
	protected String startDate;
	protected String endDate;
	protected int taxID;
	protected double gross;
	protected double PAYE;
	protected double deduction;
	protected double Nett;
	protected double YTD;
	protected double rate;
	protected double hours;
	String getFamilyName() {
		return familyName;
	}
	void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	String getGivenName() {
		return givenName;
	}
	void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	String getStartDate() {
		return startDate;
	}
	void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	String getEndDate() {
		return endDate;
	}
	void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	int getTaxID() {
		return taxID;
	}
	void setTaxID(int taxID) {
		this.taxID = taxID;
	}
	double getDeduction() {
		return this.deduction;
	}
	void setDeduction(double deduction) {
		this.deduction = deduction;
	}
	double getRate() {
		return this.rate;
	}
	void setRate(double rate) {
		this.rate = rate;
	}
	double getHours() {
		return this.hours;
	}
	abstract double getYTD();
	abstract double getNett();
	abstract double getPAYE();
	abstract double getGross();
	public static class firstNameCompare implements Comparator<Employee> {
		public int compare(Employee person1,Employee person2) {
			return person1.givenName.compareTo(person2.givenName);
		}
	}
	public static class lastNameCompare implements Comparator<Employee> {
		public int compare(Employee person1,Employee person2) {
			return person1.familyName.compareTo(person2.familyName);
		}
	}
	public static class taxIDCompare implements Comparator<Employee> {
		public int compare(Employee person1,Employee person2) {
			if (person1.taxID > person2.taxID) {
				return 1;
			} else if (person1.taxID < person2.taxID) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
