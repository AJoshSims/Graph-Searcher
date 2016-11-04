package graphSearcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

final class Graph 
{
	private static final int TWO_VERTICES_PER_EDGE = 2;
	
	private static final int VERTEX_FROM = 0;
	
	private static final int VERTEX_TO = 1;
	
	private ArrayList<Vertex> vertexList;
	
	private ArrayList<ArrayList<Integer>> adjList;
	
	private int[][] adjMatrix;
	
	Graph()
	{
		vertexList = null;
		adjList = null;
		adjMatrix = null;
	}
	
	void startGraph(String graphFilePath)
	{
		readInputGraph(graphFilePath);
		
		
	}
	
	private void readInputGraph(String graphFilePath)
	{	
		if (graphFilePath != null)
		{
			File graphFile = new File(graphFilePath);
			
			try (
				BufferedReader lineCountReader = 
				new BufferedReader(new FileReader(graphFile));
				BufferedReader graphFileReader = new BufferedReader(
				new FileReader(graphFile));)
			{
				int linesInGraphFile = 0;
				while (lineCountReader.readLine() != null) 
				{
					++linesInGraphFile;
				}
				lineCountReader.close();
				
				if (linesInGraphFile == 0)
				{
					System.err.println(
						GraphDriver.INVALID_GRAPH_FILE_FORMAT_MESSAGE);
					System.exit(GraphDriver.INVALID_GRAPH_FILE_FORMAT);
				}
				
				vertexList = new ArrayList<Vertex>(linesInGraphFile);
				adjList = new ArrayList<ArrayList<Integer>>(linesInGraphFile);
				for (int elementsAdded = 0; elementsAdded <= linesInGraphFile;
					++elementsAdded)
				{
					vertexList.add(null);
					adjList.add(null);
				}
				
				// TODO remove
				System.out.println(vertexList + "\n\n\n" + adjList);
				
				String edge = "";
				String[] vertices = null;
				int vertexFrom = -1;
				int vertexTo = -1;
				while ((edge = graphFileReader.readLine()) != null)
				{
					vertices = edge.split(" ");
					
					if (vertices.length != TWO_VERTICES_PER_EDGE)
					{
						System.err.println(
							GraphDriver.INVALID_GRAPH_FILE_FORMAT_MESSAGE);
						System.exit(GraphDriver.INVALID_GRAPH_FILE_FORMAT);
					}
					
					vertexFrom = Integer.parseInt(vertices[VERTEX_FROM]);
					vertexTo = Integer.parseInt(vertices[VERTEX_TO]);
					
					// TODO remove
					System.out.println("vertexFrom: " + vertexFrom);
					System.out.println("vertexTo: " + vertexTo);
					
					if (vertexFrom < 0 || vertexTo < 0)
					{
						System.err.println(
							GraphDriver.INVALID_GRAPH_FILE_FORMAT_MESSAGE);
						System.exit(GraphDriver.INVALID_GRAPH_FILE_FORMAT);
					}
					
					if (vertexList.get(vertexFrom) == null)
					{
						vertexList.add(vertexFrom, new Vertex(vertexFrom, "white"));						
					}
					
					if (vertexList.get(vertexTo) == null)
					{
						vertexList.add(vertexTo, new Vertex(vertexTo, "white"));						
					}
					
					if (adjList.get(vertexFrom) == null)
					{
						adjList.add(vertexFrom, new ArrayList<Integer>());
					}
					if (adjList.get(vertexFrom).contains(vertexTo))
					{
						System.err.println(
							GraphDriver.INVALID_GRAPH_FILE_FORMAT_MESSAGE);
						System.exit(GraphDriver.INVALID_GRAPH_FILE_FORMAT);
					}
					adjList.get(vertexFrom).add(vertexTo);
				}	
				
				// TODO remove
				System.out.println(vertexList + "\n\n\n" + adjList);
				
				// TODO remove
				System.out.println("\n\n\n" + vertexList.get(6));
			}
			catch (FileNotFoundException e)
			{
				System.err.println(
					GraphDriver.COULD_NOT_READ_GRAPH_FILE_MESSAGE +
					GraphDriver.USAGE_MESSAGE);
				System.exit(GraphDriver.COULD_NOT_READ_GRAPH_FILE);
			}
			catch (IOException e)
			{
				System.err.println(e.getMessage());
				System.exit(GraphDriver.IO_EXCEPTION);
			}
		}
		
		// TODO keep?
//		if (vertexList.isEmpty())
//		{
//			System.err.println(GraphDriver.INVALID_GRAPH_FILE_FORMAT_MESSAGE);
//			System.exit(GraphDriver.INVALID_GRAPH_FILE_FORMAT);
//		}
	}
	
//	private boolean readSourceDest()
//	{
//		
//	}
//	
//	private String dfsSearch()
//	{
//		
//	}
//	
//	private String transitiveClosure()
//	{
//		
//	}
//	
//	private String cycleSearch()
//	{
//		
//	}
//	
//	private void printGraphStats()
//	{
//		
//	}
}
