package payroll;
public class ProcessingFactory {
	public enum ProcessMode {
		payslips("Payslips"), employees("Employees"), burden("Burden"), paye("PAYE");
		private final String process;
		private ProcessMode(final String process){
			this.process = process;
		}
		public String toString(){
			return process;
		}
		public static ProcessMode getProcessMode(String name){
			for( ProcessMode mode: values()){
				if(mode.toString().equals(name)){
					return mode;
				}
			}
			throw new RuntimeException(name + " is not a valid process mode");
		}
	}
	public Processing getProcess(String processMode){
		switch(ProcessMode.getProcessMode(processMode)){
			case payslips:{
				return new PayslipsProcessing();
			}
			case employees:{
				return new EmployeesProcessing();
			}
			case burden:{
				return new BurdenProcessing();
			}
			case paye:{
				return new PayeProcessing();
			}
			default: {
				throw new RuntimeException(processMode + " is not a valid process mode");
			}
		}
	}
}
