package payroll;
public interface Process {
	public void execute(ProcessedRecord record);
	public void display();
}
