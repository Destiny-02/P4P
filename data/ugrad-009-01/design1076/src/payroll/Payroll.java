package payroll;
public class Payroll {
	public static void main(String[] args){
		try{
			if (args.length != 2 ){
				String msg1="\nYou have to enter exactly 2 arguments, you have entered: " +args.length + " arguments. \n";
				String msg2="Hint: The first argument has to be the path of the file. The second one has to be the type of processing.";
				throw new PayrollUserException(msg1 + msg2);
			}
			EmployeeList employeeList = new EmployeeList();
			employeeList.initialise();
			new InputProcessor().addEmployeeToListFromFile(args[0], employeeList);
			PaymentList paymentList = new PaymentList();
			paymentList.initialise();
			Employee employee;
			for (int i=0; i<employeeList.getLength();i++){
				employee=employeeList.getEmployee(i);
				Payment payment = new Payment(employee);
				paymentList.add(payment);
			}
			Processing p = new ProcessingFactory().getProcessingObject(args[1]);
			p.output(paymentList);
		}
		catch (PayrollUserException e){
			e.printStackTrace();
		}
	}
}
