import java.util.LinkedList;


public class OutputQueueHandler {
	public LinkedList<OutputWrapper> outputQueue; 
	
	public OutputQueueHandler(){
		outputQueue = new LinkedList<OutputWrapper>();
	}
	
	public synchronized void addResultToQueue(OutputWrapper outputObject){
		outputQueue.add(outputObject);
	}
	
	public void findResultInQueue(int Id){
		boolean found=false;
		for (OutputWrapper x : outputQueue)
		{
			if (x.requestId==Id){
				found=true;
				for (Integer y : x.sortedList)
					System.out.print(y + "\t");
				System.out.println("");
			}
		}
		if (!found)
			System.out.println("Request Id Not Found!");
	}

}
