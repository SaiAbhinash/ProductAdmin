package purchaseexception;

public class FailedToPurchaseException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	public FailedToPurchaseException()
	{
		super("Failed To Purchase");
	}
}
