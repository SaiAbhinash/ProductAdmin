package purchaseexception;

public class FailedToUpdateException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	public FailedToUpdateException() 
	{
		super("Failed To Upadte!");
	}

}
