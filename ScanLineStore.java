public class ScanLineStore {
	
	private String[] arrayOfInputWords;
	
	public ScanLineStore(String inputScanLine)
	{
		arrayOfInputWords =  inputScanLine.split(" ");
	}
	
	public void printInputScanWords(){
		for (String x : arrayOfInputWords)
			System.out.print(x + "\t");
		System.out.println();
	}
	
	public String[] getInputScanWords(){
		return arrayOfInputWords;
	}
	
	public String getInputType()
	{
		if (arrayOfInputWords.length==0 )
		{
			System.out.println("The input Line was empty");
			return null;
		}
		else if (arrayOfInputWords[0].equals(Constants.Sort)){
			return Constants.Sort;
		}
		else if (arrayOfInputWords[0].equals(Constants.Result)){
			return Constants.Result;
		}
		else if (arrayOfInputWords[0].equals(Constants.Quit)){
			return Constants.Quit;
		}
		else{
			System.out.println("Unknown input command.." + arrayOfInputWords[0]);
			return null;
		}
	}

}
