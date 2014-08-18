import java.io.File;
import java.util.LinkedList;


public class OutputFileQueueHandler {
	private FileQueue outputQueue; 
	
	public OutputFileQueueHandler(){
		outputQueue = new FileQueue(Constants.WorkSpacePath+  File.separator + Constants.completedQueueFile);
	}
	
	public synchronized void addResultToQueue(OutputWrapper outputObject){
		String ouputString = Integer.toString(outputObject.requestId);
		for (int x : outputObject.sortedList )
			ouputString += " "+x;
		//System.out.println("adding to output queue" + ouputString);
		outputQueue.add(ouputString);
	}
	
	public boolean findResultInQueue(int Id){
		boolean found=false;
		String result = outputQueue.checkEntry(Id); 
		if (result == null )
			found=false;
		else if (result.equals(""))
			found=false;
		else{
			found=true;
			String[] resultArray = result.split(" "); 
			for (int i =1;i <resultArray.length;i++)
			{
					System.out.print(resultArray[i] + "\t");
			}
				System.out.println("");
		}
		return found;
	}
	
	public boolean delete(){
		return outputQueue.deleteQueue();
	}

}
