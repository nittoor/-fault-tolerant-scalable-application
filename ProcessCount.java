import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;


public class ProcessCount {
	String FileName;
	long head;
	int size;
	int processNumber;
	FileChannel fileChannel;
	File file;
	public ProcessCount(String inputFile){
		head = 0;
		FileName = inputFile;

		file = new File(FileName);
		
        
        try {
        	if (!file.exists()){
        		Constants.checkCustomFolder();
        		file.createNewFile();
        		fileChannel = new RandomAccessFile(FileName, "rw").getChannel();
        		processNumber= (int) incHead();
        	}
        	else{
        		fileChannel = new RandomAccessFile(FileName, "rw").getChannel();
        		//System.out.println("getHead :"+ getHead());
        		processNumber= (int) incHead();
        	}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public long incHead()
	{
		long incValue = 0;
		try {
			FileLock lock = null;
	        lock = fileChannel.lock();
	        incValue = getHead()+1;
		
		//	System.out.println("headValue" + headValue);
			String headString = String.format("%06d",incValue )+System.getProperty("line.separator");
			fileChannel.position(0);
        //	System.out.println("headString" + headString);
			ByteBuffer buf = ByteBuffer.allocate(headString.length());
			buf.clear();
			buf.put(headString.getBytes());
			buf.flip();
			
			while (buf.hasRemaining())
				fileChannel.write(buf);
        
			fileChannel.force(false);
			
			lock.release();
			} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return incValue;
	}
	
	public long decHead()
	{
		long decValue = 0;
		try {
			FileLock lock = null;
	        lock = fileChannel.lock();
			decValue= getHead()-1;
		//	System.out.println("headdlValue" + headValue);
			String headString = String.format("%06d", decValue)+System.getProperty("line.separator");
			fileChannel.position(0);
        //	System.out.println("headString" + headString);
			ByteBuffer buf = ByteBuffer.allocate(headString.length());
			buf.clear();
			buf.put(headString.getBytes());
			buf.flip();
			
			while (buf.hasRemaining())
				fileChannel.write(buf);
        
			fileChannel.force(false);
			
			lock.release();
			} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return decValue;
	}
	
	
	
	public long getHead()
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
			else
				headLine="";
			
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		if (headLine.equals(""))
			return 0;
		return Long.parseLong(headLine);
	}
	
	public boolean  deleteFile(){
		file.setWritable(true);
		return file.delete();
	}
}
