package graphSearcher.errors;

public class InvalidSourceDestFormatException extends Exception
{
	public InvalidSourceDestFormatException()
	{
		super(
			"You specified the source and destination vertices in an " +
			"invalid format." +
			"\nThe valid format: <source> <dest>");
	}
}
