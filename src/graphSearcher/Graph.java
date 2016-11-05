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
	
	private static final int FIRST_VERTEX = 0;
		
	private static final int SOURCE = 0;
	
	private static final int DEST = 1;
	
	private static final int SOURCE_DEST_PATH = 0;
	
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
		
		String[] sourceDestPathAndOrderOfDiscovery = 
			dfsSearch(readSourceDest());
		String sourceDestPath = 
			sourceDestPathAndOrderOfDiscovery[SOURCE_DEST_PATH];
		String orderOfDiscovery = 
			sourceDestPathAndOrderOfDiscovery[ORDER_OF_DISCOVERY];
		
		String transitiveClosureEdges = transitiveClosure();
		
		boolean cycleExists = cycleSearch();
		
		printGraphStats(
			sourceDest,
			sourceDestPath, 
			orderOfDiscovery, 
			transitiveClosureEdges,
			cycleExists);
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
			sourceID = Integer.parseInt(maybeSourceDest[SOURCE]);
			destID = Integer.parseInt(maybeSourceDest[DEST]);
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
		Integer sourceId = sourceDest[SOURCE];
		Integer destId = sourceDest[DEST];
		
		String sourceDestPath = "";
		String orderOfDiscovery = sourceId + ", ";
		
		LinkedList<Integer> discovered = new LinkedList<Integer>();
		Integer discoveredVertex = sourceId;
		vertexList.get(sourceId).setColor("black");
		discovered.push(discoveredVertex);
		
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
						orderOfDiscovery += adjVertexId + ", ";
						
						if (adjVertexId == destId)
						{
							LinkedList<Integer> reversedDiscovered = 
								new LinkedList<Integer>();
							while (discovered.peek() != null)
							{
								reversedDiscovered.addFirst(discovered.pop());
							}
							sourceDestPath = 
								reversedDiscovered.toString();
							sourceDestPath = 
								sourceDestPath.substring(
								1, sourceDestPath.length() - 1);
							sourceDestPath = 
								sourceDestPath.replace(",", " ->");
							sourceDestPath += 
								" -> " + destId;
							
							orderOfDiscovery = 
								orderOfDiscovery.substring(
								0, orderOfDiscovery.length() - 2);
							
							String[] sourceDestPathAndOrderOfDiscovery = 
								{sourceDestPath, orderOfDiscovery};
							return sourceDestPathAndOrderOfDiscovery;
						}
						
						adjVertex.setColor("black");
						discovered.push(adjVertexId);
						
						popExaminedVertex = false;
						break examineAdjs;
						
					case "black":
						break examineColorOfAdj;
				}
			}
			
			if (popExaminedVertex == true)
			{
				discovered.pop();
			}
		}
		
		String[] sourceDestPathAndOrderOfDiscovery = {"", ""};
		return  sourceDestPathAndOrderOfDiscovery;
	}
	
	private String transitiveClosure()
	{

		
		int numOfVertices = vertexList.size();
		
		// TODO remove
		for (int v = 0; v < vertexList.size(); ++v)
		{
			for (int w = 0; w < vertexList.size(); ++w)
			{
				System.out.print(adjMatrix[v][w] + " ");
			}
			System.out.println("");
		}
		
//		boolean[][] transitiveClosureMatrix = 
//			new boolean[numOfVertices][numOfVertices];
//		
//		
//		
//		System.arraycopy(
//			adjMatrix, FIRST_VERTEX, 
//			transitiveClosureMatrix, FIRST_VERTEX, 
//			numOfVertices);
		
		// TODO remove
		boolean a = false;
		boolean b = false;
		boolean c = false;
		
		for (int vertex = 0; vertex < numOfVertices; ++vertex)
		{
			// TODO remove
			System.out.println("vertex: " + vertex + "| ");
			
			for (int vertexFrom = 0; vertexFrom < numOfVertices; ++vertexFrom)
			{
				// TODO remove
				System.out.print("vertexFrom: " + vertexFrom + "| ");
			
				for (int vertexTo = 0; vertexTo < numOfVertices; ++vertexTo)
				{	
					// TODO remove
					System.out.print("(" + vertexTo + ":");
					
					if ((vertexFrom != vertex) && (vertexTo != vertex))
					{
						adjMatrix[vertexFrom][vertexTo] = 
							(adjMatrix[vertexFrom][vertexTo] 
							|| 
							(adjMatrix[vertexFrom][vertex] 
							&& adjMatrix[vertex][vertexTo]));
						
						// TODO remove
						System.out.print(adjMatrix[vertexFrom][vertexTo]);
					}
					
					// TODO remove
					else
					{
						System.out.print("*" + adjMatrix[vertexFrom][vertexTo] + "*");
					}
					
					// TODO remove
					System.out.print(")");
				}
				
				// TODO remove
				System.out.println("");
			}
			
			// TODO remove
			System.out.println("");
		}
		
//		// TODO remove
//		for (int v = 0; v < vertexList.size(); ++v)
//		{
//			for (int w = 0; w < vertexList.size(); ++w)
//			{
//				System.out.print(adjMatrix[v][w] + " ");
//			}
//			System.out.println("");
//		}
		
		String transitiveClosureEdges = "";
		String indentation = "                ";
		for (int n = 0; n < numOfVertices; ++n)
		{
			for (int m = 0; m < numOfVertices; ++m)
			{				
				if ((adjMatrix[n][m] == false))
				{
					if (transitiveClosureEdges != "")
					{
						transitiveClosureEdges += indentation;
					}
					transitiveClosureEdges += 	
						n + " " + m + "\n";
				}
			}
		}
		
		transitiveClosureEdges = 
			transitiveClosureEdges.substring(
			0, transitiveClosureEdges.length() - 1);

		return transitiveClosureEdges;
	}
	
	private boolean cycleSearch()
	{	
		// Clean up in preparation of the cycle search.
		for (Vertex vert : vertexList)
		{
			vert.setColor("white");
		}
				
		boolean cycleExists = false;
		
		LinkedList<Integer> discovered = new LinkedList<Integer>();
		discovered.push(FIRST_VERTEX);
		
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
						cycleExists = true;
						return cycleExists;
					case "black":
						break examineColorOfAdj;
				}
			}
						
			if (popExaminedVertex == true)
			{
				vertexList.get(examinedVertexId).setColor("black");
				discovered.pop();
			}
		}
		
		cycleExists = false;
		return cycleExists;
	}
	
	private void printGraphStats(
		int[] sourceDest,
		String sourceDestPath, 
		String orderOfDiscovery, 
		String transitiveClosureEdges,
		boolean cycleExists)
	{
		int source = sourceDest[SOURCE];
		int dest = sourceDest[DEST];
		
		String cycleExistsString = null;
		if (cycleExists == true)
		{
			cycleExistsString = "Cycle Exists";
		}
		else
		{
			cycleExistsString = "Cycle Does Not Exist";
		}
		
		System.out.println(
			"[DFS Discovered Vertices: " + 
			source + ", " + dest + "] " + orderOfDiscovery);
		System.out.println(
			"[DFS Path: " + 
			source + ", " + dest + "] " + sourceDestPath);
		System.out.println(
			"[TC: New Edges] " + 
			transitiveClosureEdges);
		System.out.println(
			"[Cycle]: " + cycleExistsString);
	}
}
