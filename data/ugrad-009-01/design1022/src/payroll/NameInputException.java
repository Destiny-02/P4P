package payroll;
public class NameInputException extends Exception {
    public NameInputException(String message) {
        super(message);
    }
    public void reportError(){
        System.out.println("**Error** Name format is not valid");
    }
}
