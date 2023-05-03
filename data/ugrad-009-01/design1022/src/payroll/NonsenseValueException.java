package payroll;
public class NonsenseValueException extends Exception {
    private String _message;
    NonsenseValueException(String message){
        super(message);
       _message = message;
    }
    public void recordError(){
        System.out.println(_message);
    }
}
