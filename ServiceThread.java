import java.io.File;


public class ServiceThread implements Runnable {
	private InputFileQueueHandler queueHandle;
	private OutputFileQueueHandler outputQueueHandle;
	public Thread threadId;
	private volatile boolean shouldQuit;
	//private FileForQueue outputQueueFile;
	public void shutdown(){this.shouldQuit=true;this.threadId.interrupt();}
	
	public ServiceThread(InputFileQueueHandler inQueue,OutputFileQueueHandler outQueue)
	{
		queueHandle=inQueue;
		outputQueueHandle=outQueue;
		shouldQuit=false;
		//outputQueueFile = new FileForQueue(Constants.WorkSpacePath+ File.separator + Constants.outputQueueFile);
		threadId= new Thread(this, "ServiceThread");
		threadId.start();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		BaseProcessWrapper baseProcessInQueue;
		while(!shouldQuit)
		{
			try{
				if (!queueHandle.isEmpty())
				{
					baseProcessInQueue=queueHandle.getHeadOfQueue();
					if (baseProcessInQueue!=null)
					{
						//System.out.println("Handling object: "+ baseProcessInQueue.requestId);
						if (baseProcessInQueue.baseProcessRef.runProcess())
						{
							OutputWrapper tempOutput = new OutputWrapper(baseProcessInQueue.requestId,((SortProcess)baseProcessInQueue.baseProcessRef).integerArray);
							outputQueueHandle.addResultToQueue(tempOutput);
							baseProcessInQueue=null;
					//		outputQueueFile.addOutputRequest(tempOutput);
						//	queueHandle.deleteFromQueue();
						}
					}
				}
			}catch(NullPointerException x){
				//System.out.println("NullPointerException at service thread run()");
			}
		}
			//System.out.println("Exiting Run ServiceThread Thread!");

	}

}
