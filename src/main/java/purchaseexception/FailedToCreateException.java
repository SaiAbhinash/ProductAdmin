package purchaseexception;

public class FailedToCreateException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public FailedToCreateException() 
	{
		super("Failed To Create.");
	}

}
