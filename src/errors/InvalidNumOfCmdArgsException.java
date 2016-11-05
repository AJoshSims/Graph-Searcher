package errors;

public class InvalidNumOfCmdArgsException extends Exception
{
	public InvalidNumOfCmdArgsException()
	{
		super("")
	}
	public InvalidNumOfCmdArgsException(String message)
	{
		super(message);
	}

	public InvalidNumOfCmdArgsException(Throwable cause)
	{
		super(cause);
	}

	public InvalidNumOfCmdArgsException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public InvalidNumOfCmdArgsException(String message, Throwable cause,
		boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
