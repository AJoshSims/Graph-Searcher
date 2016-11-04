package graphSearcher;

final class Vertex
{
	private Integer ID;
	
	private String color;
	
	Vertex(Integer ID, String color)
	{
		this.ID = ID;
		this.color = color;
	}
	
	@Override
	public String toString()
	{
		return "" + ID;
	}
}

