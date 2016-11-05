package graphSearcher;

import errors.InvalidNumOfCmdLineArgsException;

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

/**
 * Reads a specified text file which represents a directed graph, performs a
 * depth-first search from the specified source vertex to the specified 
 * destination vertex, prints the order of discovery of the vertices and the
 * first discovered path to the destination vertex to the console if the 
 * destination was found, determines the transitive closure of the graph, 
 * prints the transitive closure edges to the console, determines if the graph
 * is cyclic, and prints to the console whether or not the graph is cyclic.
 * 
 * @author Joshua Sims
 * @version 29 October 2016
 */
public final class GraphDriver 
{
	// Usage message
	public static final String USAGE_MESSAGE = "\nUsage: <graph_file_path>";
	
	// Possible numbers of command line arguments
	/**
	 * Indicates that one command line argument was specified.
	 */
	private static final int ONE_ARG = 1;
	
	// Indices of command line arguments
	/**
	 * The index of the command line argument which specifies the path of the 
	 * text file which represents the directed graph.
	 */
	private static final int INDEX_OF_GRAPH_FILE_PATH_ARG = 0;
	
	// Exit codes
	/**
	 * Exit code indicating that an invalid number of command line arguments
	 * was specified.
	 */
	private static final int INVALID_NUM_OF_CMD_LINE_ARGS = 1;
	
	/**
	 * Exit code indicating that the specified text file, which represents the 
	 * directed graph, could not be read.
	 */
	static final int COULD_NOT_READ_GRAPH_FILE = 2;
	
	/**
	 * Exit code indicating that the specified text file does not represent
	 * the directed graph in the correct format.
	 */
	static final int INVALID_GRAPH_FILE_FORMAT = 3;
	
	/**
	 * Exit code indicating that the source and destination vertices were not
	 * specified in the correct format.
	 */
	static final int INVALID_SOURCE_DEST_FORMAT = 4;
	
	/**
	 * Exit code indicating that the source or destination vertex is not an
	 * integer between 0 and (the number of vertices - 1).
	 */
	static final int INVALID_SOURCE_DEST = 5;
	
	/**
	 * Exit code indicating an error related to an IO event.
	 */
	static final int IO_EXCEPTION = 6;
	
	// Methods
	/**
	 * Wrapper method for startGraph.
	 * 
	 * @param args - <graph_file_path>
	 */
	public static void main(String[] args)
	{
		try
		{
			examineCmdLineArgs(args);
		}
		catch (InvalidNumOfCmdLineArgsException e)
		{
			System.err.println(e.getMessage());
			System.exit(INVALID_NUM_OF_CMD_LINE_ARGS);
		}
		
		Graph graph = new Graph();
		
		String graphFilePath = args[INDEX_OF_GRAPH_FILE_PATH_ARG];
		graph.startGraph(graphFilePath);
	}
	
	/**
	 * Examines the specified command line arguments for validity.
	 * 
	 * @param args - <graph_file_path>
	 * 
	 * @throws InvalidNumOfCmdLineArgsException - if more than 1 command line
	 *     argument was passed.
	 */
	private static void examineCmdLineArgs(String[] args)
		throws InvalidNumOfCmdLineArgsException
	{
		if (args.length != ONE_ARG)
		{
			throw new InvalidNumOfCmdLineArgsException();
		}
	}
}
