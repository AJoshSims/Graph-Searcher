package graphSearcher;

import java.util.ArrayList;

public class MessnAround
{
	public static void main(String[] args)
	{
//		String edge = "1 3";
//		
//		String[] vertices = edge.split(" ");
//		
//		for (String vertex : vertices)
//		{
//			System.out.println(vertex);			
//		}
		
		
		
		
//		int negNine = Integer.parseInt("-9");
//		
//		System.out.println(negNine);
		
		
		
//		ArrayList<Object> arrayList = new ArrayList<Object>(10);
//		
//		System.out.println(arrayList.size());
//		
//		arrayList.add(null);
//		
//		System.out.print(arrayList.size());
		
		
		
		boolean[] a = {true, false, true, false};
		
		boolean[] b = {false, false, true, true};
		
		for (int i = 0; i < a.length; ++i)
		{
			if ((a[i] == true) && (b[i] == false))
			{
				System.out.println(i);
			}
		}

		
	}
}
