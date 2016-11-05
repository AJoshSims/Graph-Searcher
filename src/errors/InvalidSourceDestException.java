package errors;

public class InvalidSourceDestException extends Exception
{
	public InvalidSourceDestException()
	{
		super(
			"You specified an invalid source or an invalid destination." +
			"\nThe source and destination must be integers between 0 and " +
			"(the number of vertices - 1).");
	}
}
