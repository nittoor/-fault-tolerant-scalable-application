import java.util.LinkedList;


public class InputQueueHandler {
	private LinkedList<BaseProcessWrapper> ServiceRequests;
	
	public InputQueueHandler (){
		ServiceRequests = new LinkedList<BaseProcessWrapper>(); 
	}
	
	public synchronized void addToQueue(BaseProcessWrapper x){
		ServiceRequests.add(x);
	}
	public synchronized BaseProcessWrapper deleteFromQueue(){
		return ServiceRequests.remove();
	}
	
	public synchronized BaseProcessWrapper getHeadOfQueue(){
		return ServiceRequests.getFirst();
	}
	public synchronized int sizeOfQueue(){
		return ServiceRequests.size();
	}
	public synchronized boolean checkInQueue(int Id){
		boolean check = false;
		for (BaseProcessWrapper z:ServiceRequests)
			if (z.requestId==Id)
				check = true;
		return check;
	}
	public void printQueue(){
		for(BaseProcessWrapper x : ServiceRequests)
			System.out.print(x+"\t");
	}
}
