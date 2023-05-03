package payroll;
public class MissingDataException extends Exception {
    MissingDataException(String message){
        super(message);
    }
    public void recordError(){
        System.out.println("**Error** There is date missing from this record");
    }
}
