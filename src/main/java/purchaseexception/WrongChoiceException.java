package purchaseexception;

public class WrongChoiceException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	public WrongChoiceException() 
	{
		super("Enter The Correct Choice");
	}

}
