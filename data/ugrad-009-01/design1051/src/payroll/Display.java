package payroll;
import java.text.SimpleDateFormat;
import java.util.Date;
interface Display {
	public void display(EmployeeList list);
	public static void printCurrentDate() {
		Date currentDate = new Date();
		SimpleDateFormat dateStructure = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(dateStructure.format(currentDate));
	}
}
