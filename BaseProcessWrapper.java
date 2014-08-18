
public class BaseProcessWrapper {
	public BaseProcess baseProcessRef;
	public int requestId;
	public BaseProcessWrapper (BaseProcess baseRef,int req)
	{
		baseProcessRef=baseRef;
		requestId=req;
	}
	
	@Override 
	public String toString(){
		String stringObject = new String();
		stringObject += requestId;
		stringObject += baseProcessRef.toString();
		return stringObject;
	}
}
