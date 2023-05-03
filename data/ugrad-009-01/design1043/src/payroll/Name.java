package payroll;
public class Name {
	public String swap(String _name){
		String[] names = _name.split(", ");
		return (names[1] + " " + names[0]+",");
	}
}
