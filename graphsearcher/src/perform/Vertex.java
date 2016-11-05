package perform;

/**
 * Models a vertex within a graph.
 * 
 * @author Joshua Sims
 * @version 29 October 2016
 */
final class Vertex implements Comparable<Vertex>
{
	/**
	 * The unique integer which identifies this vertex. The integer may be 
	 * between 0 and (the number of vertices - 1).
	 */
	private Integer id;
	
	/**
	 * The color of this vertex as relevant to a graphical search algorithm.
	 */
	private String color;
	
	/**
	 * Initializes this vertex's ID and color.
	 * 
	 * @param id - the unique integer which identifies this vertex. The integer 
	 *     may be between 0 and (the number of vertices - 1)
	 * @param color - the color of this vertex as relevant to a graphical 
	 *     search algorithm
	 */
	Vertex(Integer id, String color)
	{
		this.id = id;
		this.color = color;
	}
	
	/**
	 * Returns this vertex's ID.
	 * 
	 * @return id - the unique integer which identifies this vertex. The 
	 *     integer may be between 0 and (the number of vertices - 1).
	 */
	Integer getID()
	{
		return id;
	}
	
	/**
	 * Returns this vertex's color.
	 * 
	 * @return color - the color of this vertex as relevant to a graphical 
	 *     search algorithm
	 */
	String getColor()
	{
		return color;
	}
	
	/**
	 * Replaces this vertex's color.
	 * 
	 * @param color - the color of this vertex as relevant to a graphical 
	 *     search algorithm
	 */
	void setColor(String color)
	{
		this.color = color;
	}
	
	/**
	 * Determines the equality of another object and this vertex. For equality,
	 * the other object must be a vertex with an ID equal to this vertex's ID.
	 * 
	 * @param object - the other object being compared to this vertex.
	 */
	@Override
	public boolean equals(Object object)
	{
		if (object instanceof Vertex)
		{
			Vertex otherVertex = (Vertex) object;
			if (otherVertex.getID() == this.id)
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Compares another vertex with this vertex based on both vertices' IDs.
	 */
	@Override
	public int compareTo(Vertex otherVertex)
	{
		int comparison = this.id - otherVertex.getID();
		return comparison;
	}
	
	/**
	 * Returns the ID of this vertex as a string.
	 */
	@Override
	public String toString()
	{
		return "" + id;
	}
}

