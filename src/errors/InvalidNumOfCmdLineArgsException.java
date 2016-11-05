package errors;

import graphSearcher.GraphDriver;

public class InvalidNumOfCmdLineArgsException extends Exception
{
	public InvalidNumOfCmdLineArgsException()
	{
		super("You specified an invalid number of command line arguments." +
			"\n" + GraphDriver.USAGE_MESSAGE);
	}
}
