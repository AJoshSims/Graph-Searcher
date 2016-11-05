package graphSearcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

final class Graph 
{
	private static final int TWO_VERTICES = 2;
	
	private static final int VERTEX_FROM = 0;
	
	private static final int VERTEX_TO = 1;
	
	private static final int NOT_CONTAINED = -1;
	
	private static final int SOURCE_DEST = 0;
	
	private static final int ORDER_OF_DISCOVERY = 1;
	
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
		
		int[] sourceDest = readSourceDest();
		
		String[] sourceDestPathAndOrderOfDiscovery = dfsSearch(sourceDest);
		
		// TODO remove
		System.out.println("OoD: " + sourceDestPathAndOrderOfDiscovery[ORDER_OF_DISCOVERY]);
		
		// TODO remove
		System.out.println("Cycle: " + cycleSearch());
		
		// TODO change
		String whatever = transitiveClosure();
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
					
					if (adjVertices.length != TWO_VERTICES)
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
		System.out.println(vertexList);
		System.out.println("");
		System.out.println(adjList);
		System.out.println("");
		for (boolean[] vertexFrom : adjMatrix)
		{
			for (boolean vertexTo : vertexFrom)
			{
				System.out.print(vertexTo + " ");
			}
			System.out.println("");
		}
		
		// TODO keep?
//		if (vertexList.isEmpty())
//		{
//			System.err.println(GraphDriver.INVALID_GRAPH_FILE_FORMAT_MESSAGE);
//			System.exit(GraphDriver.INVALID_GRAPH_FILE_FORMAT);
//		}
	}
	
	private int[] readSourceDest()
	{
		Scanner userInput = new Scanner(System.in);
		String[] maybeSourceDest = userInput.nextLine().split(" ");
		if (maybeSourceDest.length != TWO_VERTICES)
		{
			System.err.println(GraphDriver.INVALID_SOURCE_DEST_FORMAT_MESSAGE);
			System.exit(GraphDriver.INVALID_SOURCE_DEST_FORMAT);
		}
		
		Integer sourceID = null;
		Integer destID = null;
		try
		{
			sourceID = Integer.parseInt(maybeSourceDest[VERTEX_FROM]);
			destID = Integer.parseInt(maybeSourceDest[VERTEX_TO]);
		}
		catch (NumberFormatException e)
		{
			// TODO change
			System.err.println("dat aint no integer.");
			System.exit(1);
		}
		
		Vertex source = new Vertex(sourceID, "white");
		Vertex dest = new Vertex(destID, "white");
		if (
			(vertexList.contains(source) == false) 
			|| (vertexList.contains(dest) == false))
		{
			return null;
		}
		
		int[] sourceDest = {sourceID, destID};
		
		return sourceDest;
	}
	
	private String[] dfsSearch(int[] sourceDest)
	{
		Integer source = sourceDest[VERTEX_FROM];
		Integer dest = sourceDest[VERTEX_TO];
		
		String orderOfDiscovery = source + " ";
		LinkedList<Integer> discovered = new LinkedList<Integer>();
		Integer discoveredVertex = source;
		vertexList.get(source).setColor("black");
		discovered.push(discoveredVertex);
		
		String sourceDestPath = "";
		
		Integer examinedVertexId = null;
		Vertex adjVertex = null;
		boolean popExaminedVertex = true;
		while (discovered.isEmpty() == false)
		{
			examinedVertexId = discovered.peek();
			popExaminedVertex = true;
			
			examineAdjs:
			for (Integer adjVertexId : adjList.get(examinedVertexId))
			{
				adjVertex = vertexList.get(adjVertexId);
				
				examineColorOfAdj:
				switch (adjVertex.getColor())
				{
					case "white":
						if (adjVertexId == dest)
						{
							// TODO remove
							LinkedList<Integer> reversed = new LinkedList<Integer>();
							while (discovered.peek() != null)
							{
								reversed.addFirst(discovered.pop());
							}
							String reversedString = reversed.toString();
							reversedString = reversedString.substring(1, reversedString.length() - 1);
							reversedString = reversedString.replace(",", " ->");
							reversedString += " -> " + dest;
							System.out.println(reversedString);
						}
						orderOfDiscovery += adjVertexId + " ";
						adjVertex.setColor("black");
						discovered.push(adjVertexId);
						
						popExaminedVertex = false;
						break examineAdjs;
					case "black":
						// Do nothing.
				}
			}
			
			if (popExaminedVertex == true)
			{
				discovered.pop();
			}
		}
		
		for (Vertex vert : vertexList)
		{
			vert.setColor("white");
		}
		
		// TODO change
		String[] sourceDestPathAndOrderOfDiscovery = 
			{"bleh", orderOfDiscovery};
		return  sourceDestPathAndOrderOfDiscovery;
	}
	
	private String transitiveClosure()
	{
		int numOfVertices = vertexList.size();
		
		boolean[] ands = new boolean[numOfVertices];
		for (int i = 0; i < numOfVertices; ++i)
		{
			for (int k = 0; k < numOfVertices; ++k)
			{
				for (int l = 0; l < numOfVertices; ++l)
				{
					if ((k != i) && (l != i))
					{
						adjMatrix[l][k] = 
							(adjMatrix[l][k] 
							|| (adjMatrix[l][i] && adjMatrix[i][k]));
					}
				}
			}
		}
		
		for (int n = 0; n < numOfVertices; ++n)
		{
			for (int m = 0; m < numOfVertices; ++m)
			{
				if (adjMatrix[n][m] == true)
				{
					// TODO change
					System.out.println(n + " " + m);
				}
			}
		}
		
		// TODO change
		return null;
	}
	
	private boolean cycleSearch()
	{	
		boolean cycleExists = false;
		
		LinkedList<Integer> discovered = new LinkedList<Integer>();
		vertexList.get(0).setColor("white");
		discovered.push(0);
		
		Integer examinedVertexId = null;
		Vertex adjVertex = null;
		boolean popExaminedVertex = true;
		while (discovered.isEmpty() == false)
		{
			examinedVertexId = discovered.peek();
			popExaminedVertex = true;
			
			examineAdjs:
			for (Integer adjVertexId : adjList.get(examinedVertexId))
			{
				adjVertex = vertexList.get(adjVertexId);
				
				examineColorOfAdj:
				switch (adjVertex.getColor())
				{
					case "white":
						adjVertex.setColor("grey");
						discovered.push(adjVertexId);
						
						popExaminedVertex = false;
						break examineAdjs;
					case "grey":
						return cycleExists = true;
					case "black":
						// Do nothing.
				}
			}
			
			if (popExaminedVertex == true)
			{
				vertexList.get(examinedVertexId).setColor("black");
				discovered.pop();
			}
		}
		
		return cycleExists = false;
	}
//	
//	private void printGraphStats()
//	{
//		
//	}
}
