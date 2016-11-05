package exceptions;

import perform.GraphDriver;

public class CannotReadGraphFileException extends Exception
{	
	public CannotReadGraphFileException()
	{
		super("The specified graph file could not be read." +
			"\n" + GraphDriver.USAGE_MESSAGE);
	}
}
