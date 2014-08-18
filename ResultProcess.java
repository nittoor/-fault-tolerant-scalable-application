import java.io.File;


public class ResultProcess implements BaseProcess {

	private String[] inputScanWords;
	private int requestId;
	//FileForQueue inputQueueFile;
	//FileForQueue outputQueueFile;
	
	public ResultProcess(String[] inputWords){
		inputScanWords=inputWords;
		//inputQueueFile  = new FileForQueue(Constants.WorkSpacePath+ File.separator + Constants.inputQueueFile);
		//outputQueueFile = new FileForQueue(Constants.WorkSpacePath+ File.separator + Constants.outputQueueFile);
	}
	
	@Override
	public boolean runProcess() {
		// TODO Auto-generated method stub
		//System.out.println("Result Process");
		requestId = Integer.parseInt(inputScanWords[1]);
		System.out.println("Checking for reuqest Id " + requestId );
		String[] outputArray = null; 
		
		/*
		if (outputQueueFile.findRequestId(requestId,outputArray))
		{
			for (String x : outputArray)
				System.out.println(x + " ");
		}
		else if (inputQueueFile.findRequestId(requestId,outputArray)){
			System.out.println("Processing...");
		}
		else
			System.out.println("Could Not Find the request Id");
			*/
		return true;
	}
	
	public String describe(){
		String newString = new String();
		newString += requestId;
		return newString;
	}

}
