
public class ProcessEnvelope {
	
	public BaseProcess getProcess(ScanLineStore scanLine)
	{
		if (scanLine.getInputType()== null)
			return null;
		switch (scanLine.getInputType()) {
		case Constants.Sort:
			return new SortProcess(scanLine.getInputScanWords());
		case Constants.Result:
			return new ResultProcess(scanLine.getInputScanWords());
		case Constants.Quit:
			return new QuitProcess(scanLine.getInputScanWords());
		default:
			return null;
		}
	}
}
