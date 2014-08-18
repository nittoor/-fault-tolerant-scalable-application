import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Scanner;


public class RequestThread implements Runnable {
	
	private InputFileQueueHandler queueHandle;
	private OutputFileQueueHandler outputQueueHandle;
	private static int RequestCounter;
	private ProcessEnvelope processEnvelope;
	private volatile boolean shouldQuit;
	public Thread threadId;
	public void shutdown(){this.shouldQuit=true;this.threadId.interrupt();}
	private Scanner scanInput;
	
	
	public RequestThread(InputFileQueueHandler inQueue,OutputFileQueueHandler outQueue,boolean isFirstProcess)
	{
		RequestCounter=0;
		queueHandle=inQueue;
		outputQueueHandle=outQueue;
		processEnvelope = new ProcessEnvelope();
		
		//intputQueueFile = new FileForQueue(Constants.WorkSpacePath+ File.separator + Constants.inputQueueFile);
		shouldQuit=false;
		if (isFirstProcess)
			loadPreviousState();
		
		scanInput = new Scanner(System.in);
		
		threadId = new Thread(this, "RequestThread");
		threadId.start();
		
	}
	
	private void loadPreviousState(){
		
		String inputFileName = Constants.WorkSpacePath + File.separator + Constants.workFile ;
		//System.out.println("Input File is : " + inputFileName);
		File customPath = new File(inputFileName);
		 
		if (customPath.exists()) {
			try {
				BufferedReader inputFile = new BufferedReader(new FileReader(inputFileName));
				String   line = "";
				String[] lineArray;
				while ((line = inputFile.readLine()) != null) {
			    	lineArray = line.split(" ");
			    	BaseProcessWrapper newBaseProcessWrapper = getBaseProcessWrapperFromLine(lineArray);
					System.out.println(RequestCounter);
					queueHandle.addToQueue(newBaseProcessWrapper);
					//intputQueueFile.addRequestId(RequestCounter);
			    }
				inputFile.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			//System.out.println( Constants.workFile + " file not found");
		}
		
		    
	}
	
	private BaseProcessWrapper getBaseProcessWrapperFromLine(String[] inputLineArray)
	{
		RequestCounter=Integer.parseInt(inputLineArray[0]);
		inputLineArray[0] = Constants.Sort;
		String inputScanLine = new String();
		inputScanLine = Constants.Sort;
		for (int i=1; i< inputLineArray.length;i++){
			inputScanLine += Constants.SpaceCharacter;
			inputScanLine += inputLineArray[i];
		}
    	ScanLineStore scanLineStore = new ScanLineStore(inputScanLine);
    	BaseProcess  processReference = processEnvelope.getProcess(scanLineStore);
    	return new BaseProcessWrapper(processReference,RequestCounter);
	}
	
	private boolean hasNextLine() {
	    try {
			while (System.in.available() == 0) {
			    // [variant 1
			    try {
					Thread.sleep(10);
			    } catch (InterruptedException e) {
			        System.out.println("Thread is interrupted.. breaking from loop");
			        return false;
			    }// ]

			    // [variant 2 - without sleep you get a busy wait which may load your cpu
			    //if (this.isInterrupted()) {
			    //    System.out.println("Thread is interrupted.. breaking from loop");
			    //    return false;
			    //}// ]
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return scanInput.hasNextLine();
	}
	
	@Override
	public void run(){
		// TODO Auto-generated method stub
		
		String inputScanLine= new String();
		BaseProcess  processReference ;
		
		do {
			if (hasNextLine())
			{
				inputScanLine = scanInput.nextLine();
				ScanLineStore scanLineStore = new ScanLineStore(inputScanLine);
				processReference = processEnvelope.getProcess(scanLineStore);
				if (processReference!=null)
				{
					switch (scanLineStore.getInputType()){
					case Constants.Sort:
						BaseProcessWrapper tempBaseProcessWrapper= new BaseProcessWrapper(processReference,++RequestCounter);
						System.out.println(RequestCounter);
						queueHandle.addToQueue(tempBaseProcessWrapper);
						//intputQueueFile.addRequestId(RequestCounter);
						break;
					case Constants.Result:
						processReference.runProcess();
						
						if  (outputQueueHandle.findResultInQueue(Integer.parseInt(scanLineStore.getInputScanWords()[1])))
							System.out.println("");
						else if (queueHandle.checkInWholeQueue(Integer.parseInt(scanLineStore.getInputScanWords()[1])))
						{
							System.out.println("Processing");
						}
						else
							System.out.println("Unknwn Request Id");
						break;
					case Constants.Quit:
						processReference.runProcess();
						shouldQuit= true;
						return;
					}
				}
			}
			
		}while(!shouldQuit);
	}

}
