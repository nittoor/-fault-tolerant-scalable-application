import java.io.File;

final class Constants {
	public static final String Sort   = "sort";
	public static final String Result = "results";
	public static final String Quit   = "quit";
	
	public static final String SpaceCharacter  = " ";
	public static final String RequestService  = "RequestService";
	public static final String ProvideService  = "ProvideService";
	
	public static final String WorkSpacePath   = System.getProperty("user.home") + File.separator + "FaultTolerantWorkSpace";
	public static final String HomeDirectory   = System.getProperty("user.home") + File.separator ;
	
	public static final String workFile   = "FAULT_TOLERANT_LAST_STATE.txt";
	public static final String queueFile  = "FAULT_TOLERANT_QUEQE_STATE.txt";
	public static final String completedQueueFile  = "FAULT_TOLERANT_COMPLETED_QUEQE_STATE.txt";
	public static final String processCountFile    = "FAULT_TOLERANT_PROCESS_COUNT.txt";
	
	public static final String MonitorPersonalFile = "FAULT_TOLERANT_MONITORS_FILE";
	//public static final String inputQueueFile  = "FAULT_TOLERANT_INPUT_QUEQE_STATE.txt";
	//public static final String outputQueueFile  = "FAULT_TOLERANT_OUTPUT_QUEQE_STATE.txt";
	public static final int readAheadLimit= 1000;
	
	public static final int maxProcesses = 5;
	
	public static boolean checkCustomFolder(){
		File customDir = new File(Constants.WorkSpacePath);
		if (customDir.exists()) {
		    //System.out.println(customDir + " already exists");
		    return true;
		} else if (customDir.mkdirs()) {
		    //System.out.println(customDir + " was created");
		    return true;
		} else {
		    //System.out.println(customDir + " was not created");
		    return false;
		}
	}
}
