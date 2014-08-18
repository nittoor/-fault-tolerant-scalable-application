import java.util.ArrayList;
import java.util.Collections;


public class SortProcess implements BaseProcess {
	
	private String[] inputScanWords;
	public ArrayList<Integer> integerArray;
	
	public SortProcess(String[] inputWords){
		inputScanWords=inputWords;
		integerArray= new ArrayList<Integer> ();
		/*
		System.out.println("Before Sorting");
		for (int i:integerArray)
			System.out.print(i + "\t");
		System.out.println("");
		*/
		for (int i=1;i<inputScanWords.length;i++)
			integerArray.add(Integer.parseInt(inputScanWords[i]));
	}
	@Override
	public boolean runProcess() {
		// TODO Auto-generated method stub
		//System.out.println("Sort Process");
		
		
		
		try {
			Thread.sleep(10000);
			Collections.sort(integerArray);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}
		
		/*
		System.out.println("After Sorting ");
		for (int i:integerArray)
			System.out.print(i + "\t");
			*/
		return true;
	}
	
	public String describe(){
		String newString = new String();
		if (integerArray.size()>0)
			newString += Integer.toString(integerArray.get(0));
		for (int i =1; i< integerArray.size() ; i++ )
			newString += ( " " +Integer.toString(integerArray.get(i)) ); 
		return newString;
	}

}
