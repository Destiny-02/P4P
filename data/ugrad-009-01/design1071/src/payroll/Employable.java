package payroll;
public interface Employable {
	public static final int WEEKS_IN_YEAR = 52;
	public static final double TIME_AND_HALF = 1.5;
	public static final double DOUBLE_TIME = 2.0;
	public static final int WORK_WEEK = 40;
	public static final int OVER_TIME = 43;
	public double getWeekly();
	public double getYearSalary();
	public double getPaye();
	public double getNett();
	public double getNewYTD();
	public String generateEntry(FormatType ft);
	public int getID();
	public String getLastName();
	public String getStartDate();
	public String getEndDate();
}
