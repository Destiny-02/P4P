package payroll;
public class Name {
	private String firstName;
	private String lastName;
	public Name(String firstName, String lastName) {
		this.firstName = firstName.trim();
		this.lastName = lastName.trim();
	}
	public Name(String[] names) {
		this.lastName = names[0].trim();
		this.firstName = names[1].trim();
	}
	public String getFirstName() {
		return this.firstName;
	}
	public String getLastName() {
		return this.lastName;
	}
}
