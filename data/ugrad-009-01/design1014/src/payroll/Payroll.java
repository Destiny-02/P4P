package payroll;
public class Payroll {
	public static void main(String[] args) {
		UserInput input = new UserInput();
		if (args.length != 2) {
			System.out.println("Arguments did not amount to 2, please try again!!");
		} else if(input.fileRead(args[0])) {
			input.processing(args[1]);
		}
	}
}

