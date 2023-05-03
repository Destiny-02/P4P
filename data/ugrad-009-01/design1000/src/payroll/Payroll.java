package payroll;
public class Payroll {
	public static void main(String[] args) {
		FileInput newFile = new FileInput(args[0], args[1]);
		newFile.readFile();
	}
}