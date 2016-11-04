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
	
	private boolean[][] adjMatrix;
	
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
			
			vertexList = new ArrayList<Vertex>();
			ArrayList<String[]> edges = new ArrayList<String[]>();
			String edge = "";
			String[] adjVertices = null;
			Vertex vertexFrom = null;
			Vertex vertexTo = null;
			Integer vertexFromID = -1;
			Integer vertexToID = -1;
			try (
				BufferedReader graphFileReader = new BufferedReader(
				new FileReader(graphFile));)
			{	
				while ((edge = graphFileReader.readLine()) != null)
				{
					adjVertices = edge.split(" ");
					
					edges.add(adjVertices);
					
					if (adjVertices.length != TWO_VERTICES_PER_EDGE)
					{
						System.err.println(
							GraphDriver.INVALID_GRAPH_FILE_FORMAT_MESSAGE);
						System.exit(GraphDriver.INVALID_GRAPH_FILE_FORMAT);
					}
					
					vertexFromID = 
						Integer.parseInt(adjVertices[VERTEX_FROM]);
					vertexToID = 
						Integer.parseInt(adjVertices[VERTEX_TO]);
					
					if (vertexFromID < 0 || vertexToID < 0)
					{
						System.err.println(
							GraphDriver.INVALID_GRAPH_FILE_FORMAT_MESSAGE);
						System.exit(GraphDriver.INVALID_GRAPH_FILE_FORMAT);
					}
					
					vertexFrom = new Vertex(vertexFromID, "white");
					vertexTo = new Vertex(vertexToID, "white");

					if (vertexList.contains(vertexFrom) == false)
					{
						vertexList.add(vertexFrom);						
					}
					if (vertexList.contains(vertexTo) == false)
					{
						vertexList.add(vertexTo);						
					}
				}
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
				
			Collections.sort(vertexList);
			
			int numOfVertices = vertexList.size();
			
			adjList = new ArrayList<ArrayList<Integer>>(numOfVertices);		
			for (
				int verticesToAdd = numOfVertices; 
				verticesToAdd > 0;
				--verticesToAdd)
			{
				adjList.add(new ArrayList<Integer>());
			}
			adjMatrix = new boolean[numOfVertices][numOfVertices];
			
			
			ArrayList<Integer> vertexFromAdjs = null;
			for (String[] adjVerticesRename : edges)
			{
				vertexFromID = 
					Integer.parseInt(adjVerticesRename[VERTEX_FROM]);
				vertexToID =
					Integer.parseInt(adjVerticesRename[VERTEX_TO]);
				
				vertexFromAdjs = adjList.get(vertexFromID);
				if (vertexFromAdjs.contains(vertexToID) == false)
				{
					vertexFromAdjs.add(vertexToID);
				}
				
				adjMatrix[vertexFromID][vertexToID] = true;
			}
			
			for (ArrayList<Integer> vertexFromAdjsRename : adjList)
			{
				Collections.sort(vertexFromAdjsRename);
			}
		}
		
		// TODO remove
//		System.out.println(vertexList);
//		System.out.println("");
//		System.out.println(adjList);
//		System.out.println("");
//		for (boolean[] vertexFrom : adjMatrix)
//		{
//			for (boolean vertexTo : vertexFrom)
//			{
//				System.out.print(vertexTo + " ");
//			}
//			System.out.println("");
//		}
		
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
