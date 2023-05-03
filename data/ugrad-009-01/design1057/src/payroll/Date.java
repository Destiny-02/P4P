
package payroll;
public class Date {
	private int[] start;
	private int[] end;
	public Date(int startYear, int startMonth, int startDay,
				int endYear, int endMonth, int endDay) {
		this.start = new int[]{startYear, startMonth, startDay};
		this.end = new int[]{endYear, endMonth, endDay};
	}
	public Date(int[] start, int[] end) {
		this.start = start;
		this.end = end;
	}
	public Date(int[] start) {
		this.start = start;
	}
	public void setEndDate(int[] end) {
		this.end = end;
	}
	public String getStartDateFormat() {
		return(String.format("%4d-%02d-%02d", start[0], start[1], start[2]));
	}
	public String getEndDateFormat() {
		return(String.format("%4d-%02d-%02d", end[0], end[1], end[2]));
	}
}
