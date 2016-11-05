package errors;

public class InvalidGraphFileFormatException extends Exception
{
	public InvalidGraphFileFormatException()
	{
		super(
			"The format of the specified graph file is " + 
				"invalid" + 
				"\nThe following is an example of the valid format: " +
				"\n\n..." +
				"\n1 3" +
				"\n2 0" +
				"\n4 5" +
				"\n..." +
				"\n\nEach vertex ID must be a positive integer." +
				"\nThe difference between a vertex ID integer and the next " +
				"highest vertex ID integer should be no greater than 1." +
				"\nRepetitions of adjacencies are not permitted.");
	}
}
