import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class HelloWorld { 
	private static boolean welcomeScreen(){
		System.out.println("Welcome to theFaultTolerantSort system: ");
		System.out.println("");
		System.out.println("Enter Your Choice From: ");
		System.out.println("	sort <list of space separated integers>");
		System.out.println("	results <request id>");
		System.out.println("	quit");
		//scanLineStore.printInputScanWords();
		//scanLineStore.getInputType();
		return true;
	}
	
	
	private  static boolean process(){
		ProcessCount newProcessCountManger  = new ProcessCount (Constants.WorkSpacePath+  File.separator + Constants.processCountFile);
		File file = new File(Constants.WorkSpacePath +File.separator +Constants.MonitorPersonalFile);
		
		if (newProcessCountManger.processNumber < Constants.maxProcesses || 
				(file.exists() && newProcessCountManger.processNumber == Constants.maxProcesses  ))
		{
			boolean firstProcess=(newProcessCountManger.processNumber==1);
			InputFileQueueHandler newInQueue    = new InputFileQueueHandler();
			OutputFileQueueHandler newOutQueue  = new OutputFileQueueHandler(); 
			RequestThread requestThread     = new RequestThread(newInQueue,newOutQueue,firstProcess);
			ServiceThread serviceThread     = new ServiceThread(newInQueue,newOutQueue);
			ShutDownHandler shutDownHandler = new ShutDownHandler(newInQueue,newOutQueue,requestThread,serviceThread,Thread.currentThread(),newProcessCountManger);
			Runtime.getRuntime().addShutdownHook(shutDownHandler);
			try {
				//System.out.println("Waiting!");
				requestThread.threadId.join();
				//System.out.println("Bye RequestThread!");
				serviceThread.shutdown();
				serviceThread.threadId.join();
				//System.out.println("Bye serviceThread!");
				//System.out.println("Finished!");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			}
		}
		else if (newProcessCountManger.processNumber == Constants.maxProcesses)
		{
			
			
			System.out.println("maxProcesses reached.. instances will be managed by current process");
			
			if (!file.exists()){
	        		Constants.checkCustomFolder();
	        		try {
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
			
			while (true)
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				// 	TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (newProcessCountManger.getHead()<Constants.maxProcesses)
				{
					launchProgram();
				}
			}
			//newProcessCountManger.decHead();
		}
		else
		{
			System.out.println("Crossed the process upper limit.. instances being managed by Monitor process");
			newProcessCountManger.decHead();
		}
		return true;
	}
	
	static void launchProgram()
	{
		try{
			String[] launchCommand= {"gnome-terminal","--tab", "-e", "java -cp "+Constants.HomeDirectory+"Desktop/FaultTolerantSystem.jar HelloWorld"};
			Process p = Runtime.getRuntime().exec(launchCommand); 
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	
		/*
		try {
		      runProcess("java -cp /home/cloudera/Desktop/FaultTolerantSystem.jar HelloWorld");
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		    */
	}
	
	private static void printLines(String name, InputStream ins) throws Exception {
	    String line = null;
	    BufferedReader in = new BufferedReader(
	        new InputStreamReader(ins));
	    while ((line = in.readLine()) != null) {
	        System.out.println(name + " " + line);
	    }
	  }

	  private static void runProcess(String command) throws Exception {
	    Process pro = Runtime.getRuntime().exec(command);
	    printLines(command + " stdout:", pro.getInputStream());
	    printLines(command + " stderr:", pro.getErrorStream());
	    pro.waitFor();
	    System.out.println(command + " exitValue() " + pro.exitValue());
	  }
	
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		welcomeScreen();
		process();
	}

}
