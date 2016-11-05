package graphSearcher.perform;

final class Vertex implements Comparable<Vertex>
{
	private Integer ID;
	
	private String color;
	
	Vertex(Integer ID, String color)
	{
		this.ID = ID;
		this.color = color;
	}
	
	Integer getID()
	{
		return ID;
	}
	
	String getColor()
	{
		return color;
	}
	
	void setColor(String color)
	{
		this.color = color;
	}
	
	@Override
	public boolean equals(Object object)
	{
		if (object instanceof Vertex)
		{
			Vertex otherVertex = (Vertex) object;
			if (otherVertex.getID() == this.ID)
			{
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public int compareTo(Vertex otherVertex)
	{
		int comparison = this.ID - otherVertex.getID();
		return comparison;
	}
	
	@Override
	public String toString()
	{
		return "" + ID;
	}
}

