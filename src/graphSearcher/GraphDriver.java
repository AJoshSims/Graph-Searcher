package graphSearcher;

// TODO check if each vertex has unique integer ID
// TODO check if graph file has 2 vertices per line format
// TODO negative int vertices should be ok right?
// TODO check for file that contains no text ended by newline
// TODO check if vertex ids are positive ints
// TODO check if vertex ids are only 1 apart (next to previous)
// TODO check if adj is listed twice?
// TODO dest not found case?
// TODO vertex larger than others error

// TODO constants for colors?

// TODO put errors in their own file?

// TODO change ID to id

// TODO invalidGraphFormat boolean instead of exiting everywhere?

public final class GraphDriver 
{
	private static final int ONE_ARG = 1;
	
	private static final int INDEX_OF_GRAPH_FILE = 0;
	
	private static final int INVALID_NUM_OF_ARGS = 1;
	
	static final int COULD_NOT_READ_GRAPH_FILE = 2;
	
	static final int INVALID_GRAPH_FILE_FORMAT = 3;
	
	static final int IO_EXCEPTION = 4;
	
	static final int INVALID_SOURCE_DEST_FORMAT = 5;
	
	static final int INVALID_SOURCE_DEST = 6;
	
	static final String USAGE_MESSAGE = 
		"\nUsage: <graph_file_path>";
	
	static final String INVALID_GRAPH_FILE_FORMAT_MESSAGE = 							
		"The format of the specified graph file is " + 
		"invalid" + 
		"\nThe following is an example of the valid format: " +
		"\n\n..." +
		"\n1 3" +
		"\n2 0" +
		"\n4 5" +
		"\n..." +
		"\n\nEach vertex ID must be a positive integer." +
		"\nThe difference between a vertex ID integer and the next highest " +
		"vertex ID integer should be no greater than 1." +
		"\nRepetitions of adjacencies are not permitted.";
	
	static final String COULD_NOT_READ_GRAPH_FILE_MESSAGE = 
		"The specified graph file could not be read.";
	
	// TODO change
	static final String INVALID_SOURCE_DEST_FORMAT_MESSAGE = 
		"Dont say source and dest like that.";

	// TODO change
	static final String INVALID_SOURCE_DEST_MESSAGE = 
		"Dat source and dest dont exist.";
	
	public static void main(String[] args)
	{
		if (args.length != ONE_ARG)
		{
			System.err.println(
				"An invalid number of command line arguments was passed." +
				USAGE_MESSAGE);
			System.exit(INVALID_NUM_OF_ARGS);
		}
		
		Graph graph = new Graph();
		
		String graphFilePath = args[INDEX_OF_GRAPH_FILE];
		graph.startGraph(graphFilePath);
	}
}
