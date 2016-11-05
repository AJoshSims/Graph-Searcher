package errors;

import graphSearcher.GraphDriver;

public class CouldNotReadGraphFileException extends Exception
{	
	public CouldNotReadGraphFileException()
	{
		super("The specified graph file could not be read." +
			"\n" + GraphDriver.USAGE_MESSAGE);
	}
}
