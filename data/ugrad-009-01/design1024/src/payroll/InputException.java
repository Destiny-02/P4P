
package payroll;
public class InputException extends Exception {
    private String _msg;
    public InputException() {
    }
    public InputException(String msg) {
        super(msg);
        _msg = msg;
    }
    public void printException(){
        System.out.println(_msg);
    }
}
