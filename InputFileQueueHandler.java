import java.io.File;

public class InputFileQueueHandler {
	private FileQueue ServiceRequests;
	
	public InputFileQueueHandler (){
		ServiceRequests = new FileQueue(Constants.WorkSpacePath+  File.separator + Constants.queueFile); 
	}
	
	public synchronized void addToQueue(BaseProcessWrapper x){
		//System.out.println( "addingToQueue" + x.baseProcessRef.describe());
		ServiceRequests.add(x.requestId+" "+x.baseProcessRef.describe());
	}
	public synchronized String deleteFromQueue(){
		return ServiceRequests.remove();
	}
	
	
	public synchronized BaseProcessWrapper getHeadOfQueue(){
		String metadataBPWrapper = ServiceRequests.remove();
		//System.out.println("metadataBPWrapper from head of queue: '"+ metadataBPWrapper+"'");
		if (metadataBPWrapper.equals(""))
			return null;
		if (metadataBPWrapper ==null)
			return null;
		String[] stringArray = metadataBPWrapper.split(" ");
		if (stringArray.length!=0)
			return new BaseProcessWrapper (new SortProcess(stringArray),Integer.parseInt(stringArray[0]));
		else
			return null;
	}
	
	
	public synchronized boolean isEmpty(){
		return ServiceRequests.isEmpty();
	}
	public synchronized boolean checkInQueue(int Id){
		String check = ServiceRequests.checkEntry(Id);
		return (check!=null);
	}
	
	public synchronized boolean checkInWholeQueue(int Id){
		String check = ServiceRequests.checkEntryInFullQueue(Id);
		return (check!=null);
	}
	
	public void printQueue(){
		ServiceRequests.printQueue();
	}
	
	public boolean delete(){
		return ServiceRequests.deleteQueue();
	}
}
