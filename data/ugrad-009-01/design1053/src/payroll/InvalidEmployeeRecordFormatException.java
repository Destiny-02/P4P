package payroll;
public class InvalidEmployeeRecordFormatException extends Exception {
  private static final long serialVersionUID = 1L;
  public InvalidEmployeeRecordFormatException(){
    super("Malformed employee record format");
  }
  public InvalidEmployeeRecordFormatException(String msg){
    super(msg);
  }
  public InvalidEmployeeRecordFormatException(int index, String msg){
    super("Invalid record on line: " + index + ". " + msg);
  }
}
