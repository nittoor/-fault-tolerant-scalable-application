import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;


public class FileQueue {
	String FileName;
	long head;
	int size;
	FileChannel fileChannel;
	File file;
	public FileQueue(String inputFile){
		head = 0;
		FileName = inputFile;

		file = new File(FileName);
		
        
        try {
        	if (!file.exists()){
        		Constants.checkCustomFolder();
        		file.createNewFile();
        		fileChannel = new RandomAccessFile(FileName, "rw").getChannel();
        		addHead(7);
        	}
        	else{
        		fileChannel = new RandomAccessFile(FileName, "rw").getChannel();
        		//System.out.println("getHead :"+ getHead());
        		if (getHead()==0)
        			addHead(7);
        	}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public boolean add(String x){
		x+=System.getProperty("line.separator");
        try {
        	//get an exclusive lock on this channel
            FileLock lock = null;
        	lock = fileChannel.lock();
			fileChannel.position(fileChannel.size());
            ByteBuffer buf = ByteBuffer.allocate(x.length());
            buf.clear();
            buf.put(x.getBytes());
            buf.flip();
            while (buf.hasRemaining())
                fileChannel.write(buf);
            
            fileChannel.force(false);
            lock.release();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        		return true;
	}
	
	public String remove(){
		String nextLine = new String();
		try {
        	//get an exclusive lock on this channel
            FileLock lock = null;
        	lock = fileChannel.lock();
        	head=getHead();
			fileChannel.position(head);
			long fileSize = fileChannel.size();
	        ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
	        
	        if(fileChannel.read(buffer) != -1) {
	                buffer.flip();
	                for (int i = 0; i < fileSize; i++)
	                {
	                	char c = (char) buffer.get();
	                	if (c==System.getProperty("line.separator").charAt(0)){
	                    	head+=(i+1);
	                    	break;
	                    }
	                	else{
	                		nextLine+=c;
	                	}
	                		
	                }
	                //char newLine = nextLine.charAt(nextLine.length()-1); 
	                buffer.clear();
	            }
	        //head=fileChannel.position();
	        addHead(head);
	        
            lock.release();
		} 
		catch (ClosedChannelException x){
			//System.out.println("ClosedChannelException in File Queue remove");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nextLine;
	}
	
	public String getFirst(){
		String nextLine = new String();
		FileLock lock = null;
		try {
        	//get an exclusive lock on this channel
        	lock = fileChannel.lock();
        	head=getHead();
			fileChannel.position(head);
			long fileSize = fileChannel.size();
	        ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
	        
	        if(fileChannel.read(buffer) != -1) {
	                buffer.flip();
	                for (int i = 0; i < fileSize; i++)
	                {
	                	char c = (char) buffer.get();
	                	if (c==System.getProperty("line.separator").charAt(0)){
	                    	head+=(i+1);
	                    	break;
	                    }
	                	else{
	                		nextLine+=c;
	                	}
	                		
	                }
	                //char newLine = nextLine.charAt(nextLine.length()-1); 
	                buffer.clear();
	            }
	        //head=fileChannel.position();
	        
            lock.release();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			try {
				lock.release();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				//e1dl.printStackTrace();
			}
		}
		return nextLine;
	}
	
	public String checkEntry(int Id){
		String nextLine = new String();
		try {
        	//get an exclusive lock on this channel
            FileLock lock = null;
        	lock = fileChannel.lock();
        	head=getHead();
        	while (head < fileChannel.size()) 
        	{
        		nextLine ="";
        		fileChannel.position(head);
        		long fileSize = fileChannel.size();
        		ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
        		
        		if(fileChannel.read(buffer) != -1) {
	                	buffer.flip();
	                	for (int i = 0; i < fileSize; i++)
	                	{
	                		char c = (char) buffer.get();
	                		if (c==System.getProperty("line.separator").charAt(0)){
	                			head+=(i+1);
	                			break;
	                		}
	                		else{
	                			nextLine+=c;
	                		}
	                		
	                	}
	                //	char newLine = nextLine.charAt(nextLine.length()-1); 
	                	buffer.clear();
	            	}
        		String[] chompedString = nextLine.split(" "); 
        		if (Integer.parseInt(chompedString[0])==Id)
        		{
        			lock.release();
        			return nextLine;
        		}
        	}
        	lock.release();
        	return null;
	        //	head=fileChannel.position();
        	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nextLine;
	}
	
	
	
	public String checkEntryInFullQueue(int Id){
		String nextLine = new String();
		try {
        	//get an exclusive lock on this channel
            FileLock lock = null;
        	lock = fileChannel.lock();
        	head=7;
        	while (head < fileChannel.size()) 
        	{
        		nextLine ="";
        		fileChannel.position(head);
        		long fileSize = fileChannel.size();
        		ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
        		
        		if(fileChannel.read(buffer) != -1) {
	                	buffer.flip();
	                	for (int i = 0; i < fileSize; i++)
	                	{
	                		char c = (char) buffer.get();
	                		if (c==System.getProperty("line.separator").charAt(0)){
	                			head+=(i+1);
	                			break;
	                		}
	                		else{
	                			nextLine+=c;
	                		}
	                		
	                	}
	                //	char newLine = nextLine.charAt(nextLine.length()-1); 
	                	buffer.clear();
	            	}
        		String[] chompedString = nextLine.split(" "); 
        		if (Integer.parseInt(chompedString[0])==Id)
        		{
        			lock.release();
        			return nextLine;
        		}
        	}
        	lock.release();
        	return null;
	        //	head=fileChannel.position();
        	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nextLine;
	}
	
	public void printQueue(){
		String nextLine = new String();
		try {
        	//get an exclusive lock on this channel
            FileLock lock = null;
        	lock = fileChannel.lock();
        	head=getHead();
        	while (head < fileChannel.size()) 
        	{
        		nextLine ="";
        		fileChannel.position(head);
        		long fileSize = fileChannel.size();
        		ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
        		
        		if(fileChannel.read(buffer) != -1) {
	                	buffer.flip();
	                	for (int i = 0; i < fileSize; i++)
	                	{
	                		char c = (char) buffer.get();
	                		if (c==System.getProperty("line.separator").charAt(0)){
	                			head+=(i+1);
	                			break;
	                		}
	                		else{
	                			nextLine+=c;
	                		}
	                		
	                	}
	                //	char newLine = nextLine.charAt(nextLine.length()-1); 
	                	buffer.clear();
	            	}
        		System.out.println(nextLine);
        	}
        	lock.release();
	        //	head=fileChannel.position();
        	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private boolean addHead(long headValue)
	{
		try {
			fileChannel.position(0);
		
		//System.out.println("headValue" + headValue);
        String headString = String.format("%06d", headValue)+System.getProperty("line.separator");
        //System.out.println("headString" + headString);
        ByteBuffer buf = ByteBuffer.allocate(headString.length());
        buf.clear();
        buf.put(headString.getBytes());
        buf.flip();
        
        while (buf.hasRemaining())
            fileChannel.write(buf);
        
        fileChannel.force(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean isEmpty()
	{
        	//get an exclusive lock on this channel
            FileLock lock = null;
        	try {
				lock = fileChannel.lock();
        	head=getHead();
			fileChannel.position(head);
			long fileSize = fileChannel.size();
	        ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
	        
	        if(fileChannel.read(buffer) != -1) {
	        	lock.release();
	        	String nextLine = new String();
	        	buffer.flip();
            		for (int i = 0; i < fileSize; i++)
            		{
            			char c = (char) buffer.get();
            			if (c==System.getProperty("line.separator").charAt(0)){
            				head+=(i+1);
            				break;
            			}
            			else{
            				nextLine+=c;
            			}
            		
            		}
            		//System.out.println("isempty : '"+nextLine+"'");
            		if (nextLine.equals(""))
            			return true;
	        	return false;
	        	}
	        else{
	        	lock.release();
	        	return true;
	        	}
			} catch (IOException e) {
				try {
					lock.release();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
        	return false;

	        
	}
	
	private long getHead()
	{
		String headLine = new String();
		try {
			fileChannel.position(0);
		
			long fileSize = fileChannel.size();
			ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
			
			if(fileChannel.read(buffer) != -1) {
                	buffer.flip();
                	for (int i = 0; i < fileSize; i++)
                	{
                		char c = (char) buffer.get();
                		if (c==System.getProperty("line.separator").charAt(0)){
                			break;
                		}
                		else{
                			headLine +=c;
                		}
                		
                	}
                //	char newLine = nextLine.charAt(nextLine.length()-1); 
                	buffer.clear();
            	}
			}
		catch (ClosedByInterruptException x)
		{
			//System.out.println("Caught ClosedByInterruptException in getHead");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		if (headLine.equals(""))
			return 0;
		return Long.parseLong(headLine);
	}
	
	public boolean  deleteQueue(){
		file.setWritable(true);
		return file.delete();
	}
}
