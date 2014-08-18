import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class FileForQueue {
	
	String InputFile;
	BufferedReader     fileReader;
	PrintWriter        fileWriter;
	
	FileForQueue(String inputFile){
		InputFile=inputFile;
		System.out.println("input file queue =" +InputFile);
		
	}
	public void addRequestId(int Id){
		System.out.println("Adding "+ Id+" to file : "+ InputFile);
		try {
			fileWriter = new PrintWriter(new FileWriter(InputFile,true));
			fileWriter.print(Id);
			fileWriter.println();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addOutputRequest(OutputWrapper output){
		System.out.println("Adding "+ output.requestId +" to file : "+ InputFile);
		try {
			fileWriter = new PrintWriter(new FileWriter(InputFile,true));
			fileWriter.print(output.requestId);
			for (Integer x : output.sortedList){
				fileWriter.print(" "+x);
			}
			fileWriter.println();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean findRequestId(int Id , String[] outputStringArray){
		try {
			fileReader = new BufferedReader(new FileReader(InputFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String line;
		boolean found=false;
		try {
			while((line = fileReader.readLine()) != null) {
				String[] stringArray = line.split(" "); 
				if(Id==Integer.parseInt(stringArray[0])){
					found=true;
					outputStringArray=stringArray;
					break;
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return found;
	}
	
	public boolean deleteFile(){
		return (new File(InputFile)).delete();
	}
}
