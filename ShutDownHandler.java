import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

class ShutDownHandler extends Thread {
		private InputFileQueueHandler inputQueue;
		private OutputFileQueueHandler outputQueue;
		private Thread mainThread;
		private RequestThread requestThread;
		private ServiceThread serviceThread;
		private ProcessCount processCountHandle;
		public ShutDownHandler(InputFileQueueHandler inQueue,OutputFileQueueHandler outQueue ,
				RequestThread reqThread,ServiceThread  serThread, Thread main, ProcessCount prcCnt) {
			inputQueue    = inQueue;
			outputQueue   = outQueue;
			requestThread = reqThread;
			serviceThread = serThread;
			mainThread    = main;
			processCountHandle=  prcCnt;
		}
 
		@Override
		public void run() {
			System.out.println("Clean up before quitting...");
			requestThread.shutdown();
			serviceThread.shutdown();
			//mainThread.interrupt();
			
			
			while (requestThread.threadId.isAlive() || serviceThread.threadId.isAlive() || mainThread.isAlive()) {
				System.out.println("Threads still alive. Waiting..." + requestThread.threadId.isAlive()+" " + serviceThread.threadId.isAlive() +" "+ mainThread.isAlive());
				//requestThread.shutdown();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if (Constants.checkCustomFolder()){
				
				File file = new File(Constants.WorkSpacePath +File.separator +Constants.MonitorPersonalFile);
				
				if (processCountHandle.decHead()==0 || (processCountHandle.getHead()==1 && file.exists()))
				{
					String outputFileName = Constants.WorkSpacePath + File.separator + Constants.workFile ;
					try {
						PrintWriter  outputFile = new PrintWriter(new FileWriter(outputFileName));
						
						BaseProcessWrapper tempBaseProcessWrapper;
						ArrayList<Integer> tempArrayList;
						try{
							while (!inputQueue.isEmpty()){
								tempBaseProcessWrapper = inputQueue.getHeadOfQueue();
								tempArrayList = ((SortProcess)tempBaseProcessWrapper.baseProcessRef).integerArray;
								
								outputFile.print(tempBaseProcessWrapper.requestId);
								for (Integer x : tempArrayList){
									outputFile.print(" "+x);
								//	System.out.print(" "+x);
								}
							//System.out.println("");
								outputFile.println();
								inputQueue.deleteFromQueue();
							}
						}catch(java.lang.NullPointerException x){
							//System.out.println("NullPointerException caught at ShutDownHalder run()");
						}
						System.out.println("processCountHandle.deleteFile()" + processCountHandle.deleteFile());
						System.out.println("inputQueue.deleteFile()" + inputQueue.delete());
						outputFile.close();
						System.out.println("outputQueue.delete()" + outputQueue.delete());
						if (file.exists())
							System.out.println("file.delete()" + file.delete());
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
					
			}
			//System.out.println("OK cleaned up. Quitting...");
		}
	}