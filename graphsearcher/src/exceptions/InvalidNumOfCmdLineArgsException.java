package exceptions;

import perform.GraphDriver;

/**
 * Indicates that an invalid number of command lines arguments was specified.
 * 
 * @author Joshua Sims
 * @version 29 October 2016
 */
public final class InvalidNumOfCmdLineArgsException extends Exception
{
	/**
	 * Initializes the exception with a helpful error message.
	 */
	public InvalidNumOfCmdLineArgsException()
	{
		super("You specified an invalid number of command line arguments." +
			"\n" + GraphDriver.USAGE_MESSAGE);
	}
}
