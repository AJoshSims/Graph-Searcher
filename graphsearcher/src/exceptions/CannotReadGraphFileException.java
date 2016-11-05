package exceptions;

import perform.GraphDriver;

/**
 * Indicates that the specified text file which represents the directed graph
 * cannot be read.
 * 
 * @author Joshua Sims
 * @version 29 October 2016
 */
public final class CannotReadGraphFileException extends Exception
{	
	/**
	 * Initializes the exception with a helpful error message.
	 */
	public CannotReadGraphFileException()
	{
		super("The specified graph file could not be read." +
			"\n" + GraphDriver.USAGE_MESSAGE);
	}
}
