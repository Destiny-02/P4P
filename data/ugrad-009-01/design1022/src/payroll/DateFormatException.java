package payroll;
public class DateFormatException extends Exception {
    DateFormatException(String message){
        super(message);
    }
    public void reportError(){
        System.out.println("**Error** Date format is not valid");
    }
}
