package payroll;
import java.util.ArrayList;
public class ErrorCheck {
	private ArrayList<EmployeeData> ErrorChecklist = new ArrayList<EmployeeData>();
	public ErrorCheck(ArrayList<EmployeeData> list){
		ErrorChecklist = list;
	}
	public void CheckDuplication() throws PayrollExceptions{
		for(int i = 0; i < ErrorChecklist.size(); i++){
			for(int j = i + 1; j < ErrorChecklist.size(); j++){
				int DupTIDCheck = ErrorChecklist.get(j).getTID();
				if(ErrorChecklist.get(i).getTID() == DupTIDCheck){
					throw new PayrollExceptions("DuplicatedTaxIDFound");
				}
			}
		}
	}
	public void CheckDates() throws PayrollExceptions{
		for(EmployeeData data : ErrorChecklist) {
			String[] startDate = data.getstart().split("\\-");
			String[] endDate = data.getend().split("\\-");
			if(Integer.parseInt(startDate[0]) > Integer.parseInt(endDate[0])){
				throw new PayrollExceptions("TheEndDateIsBeforeTheStartDate");
			} else if(Integer.parseInt(startDate[1]) > Integer.parseInt(endDate[1])){
				throw new PayrollExceptions("TheEndDateIsBeforeTheStartDate");
			} else if(Integer.parseInt(startDate[2]) > Integer.parseInt(endDate[2])) {
				throw new PayrollExceptions("TheEndDateIsBeforeTheStartDate");
			}
		}
	}
	public void NegativeInput() throws PayrollExceptions{
		for(EmployeeData data : ErrorChecklist){
			if(data.getrate() < 0 | data.getYTD() < 0){
				throw new PayrollExceptions("NegativeValueFoundInTheInput");
			} else if(data.gethours() < 0 | data.getdeduction() < 0){
				throw new PayrollExceptions("NegativeValueFoundInTheInput");
			}
		}
	}
	public void NegativeEarn() throws PayrollExceptions{
		for(EmployeeData data : ErrorChecklist){
			if(data.getNett() < 0){
				throw new PayrollExceptions("TheDeductionsIsMoreThanTheEarn");
			}
		}
	}
}
