package purchaseexception;

public class WrongInputException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public WrongInputException(String e) 
	{
		super(e);
	}

}
