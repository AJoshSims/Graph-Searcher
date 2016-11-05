package exceptions;

public class InvalidEdgeException extends Exception
{
	public InvalidEdgeException()
	{
		super(
			"The format of at least one edge, represented by the specified " +
			"graph file, is invalid." + 
			"\nThe following is an example of the valid format: " +
			"\n\n..." +
			"\n1 3" +
			"\n2 0" +
			"\n4 5" +
			"\n..." +
			"\n\nEach vertex ID must be an integer between 0 and " +
			"(the number of vertices - 1)." +
			"\nRepetitions of adjacencies are not permitted.");
	}
}
