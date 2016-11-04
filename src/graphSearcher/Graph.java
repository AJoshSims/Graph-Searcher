package graphSearcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

final class Graph 
{
	private static final int TWO_VERTICES_PER_EDGE = 2;
	
	private static final int VERTEX_FROM = 0;
	
	private static final int VERTEX_TO = 1;
	
	private static final int NOT_CONTAINED = -1;
	
	private ArrayList<Vertex> vertexList;
	
	private ArrayList<ArrayList<Integer>> adjList;
	
	private int[][] adjMatrix;
	
	Graph()
	{
		vertexList = new ArrayList<Vertex>();
		adjList = new ArrayList<ArrayList<Integer>>();
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
				BufferedReader graphFileReader = new BufferedReader(
				new FileReader(graphFile));)
			{	
				ArrayList<String> edges = new ArrayList<String>();
				String edge = "";
				String[] vertices = null;
				Vertex vertexFrom = null;
				Vertex vertexTo = null;
				Integer vertexFromIndex = -1;
				Integer vertexToIndex = -1;
				while ((edge = graphFileReader.readLine()) != null)
				{
					edges.add(edge);
					
					vertices = edge.split(" ");
					
					if (vertices.length != TWO_VERTICES_PER_EDGE)
					{
						System.err.println(
							GraphDriver.INVALID_GRAPH_FILE_FORMAT_MESSAGE);
						System.exit(GraphDriver.INVALID_GRAPH_FILE_FORMAT);
					}
					
					vertexFromIndex = Integer.parseInt(vertices[VERTEX_FROM]);
					vertexToIndex = Integer.parseInt(vertices[VERTEX_TO]);
					
					if (vertexFromIndex < 0 || vertexToIndex < 0)
					{
						System.err.println(
							GraphDriver.INVALID_GRAPH_FILE_FORMAT_MESSAGE);
						System.exit(GraphDriver.INVALID_GRAPH_FILE_FORMAT);
					}
					
					vertexFrom = new Vertex(vertexFromIndex, "white");
					vertexTo = new Vertex(vertexToIndex, "white");

					if (vertexList.contains(vertexFrom) == false)
					{
						vertexList.add(vertexFrom);						
					}
					if (vertexList.contains(vertexTo) == false)
					{
						vertexList.add(vertexTo);						
					}
				}	
				
				Collections.sort(vertexList);
				
				// TODO remove
				System.out.println(vertexList);
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
