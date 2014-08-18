
public class QuitProcess implements BaseProcess{
	
	private String[] inputScanWords;
	
	public QuitProcess(String[] inputWords){
		inputScanWords=inputWords;
	}

	@Override
	public boolean runProcess() {
		// TODO Auto-generated method stub
		//System.out.println("Quit Process");
		return false;
	}
	
	public String describe(){
		return null;
	}

}
