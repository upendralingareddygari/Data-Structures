import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class EdgeNDistance
{
	private int _vertex;
	private int _weight;

	public EdgeNDistance(int vertex, int weight) 
	{
		_vertex = vertex;
		_weight = weight;
	}

	public int getVertexValue()
	{
		return _vertex;
	}

	public int getWeightValue()
	{
		return _weight;
	}

	public void setVertexValue(int vertex)
	{
		_vertex = vertex;
	}

	public void setWeightValue(int weight)
	{
		_weight = weight;
	}
}

class Vertex
{
	private int _faceValue;
	private int _distance;
	private int _predecessor;
	private boolean _known;
	private ArrayList<EdgeNDistance> adjList = null;

	public Vertex(int faceValue, int distance, int predecessor, boolean known) 
	{
		_faceValue = faceValue;
		_distance = distance;
		_predecessor = predecessor;
		_known = known;
		adjList = new ArrayList<EdgeNDistance>();
	}

	public ArrayList getAdjacentList()
	{
		return adjList;
	}

	public void add(EdgeNDistance edge)
	{
		adjList.add(edge);
	}

	public int getFaceValue()
	{
		return _faceValue;
	}

	public int getDistance()
	{
		return _distance;
	}

	public int getPredecessor()
	{
		return _predecessor;
	}

	public boolean isKnown()
	{
		return _known;
	}

	public void setDistance(int distance)
	{
		_distance = distance;
	}

	public void setPredecessor(int predecessor)
	{
		_predecessor = predecessor;
	}

	public void setKnown(boolean known)
	{
		_known = known;
	}

	public void setFaceValue(int faceValue)
	{
		_faceValue = faceValue;
	}
}

class PriorityQueueWithWeights
{
	Vertex[] _vertexArray = null;
	int _size = 0;
	int[] referenceArray = null;

	public PriorityQueueWithWeights(Vertex[] vertexArray) 
	{		
		int temp = 0;
		_size = vertexArray.length;
		if(_vertexArray == null)
		{
			_vertexArray = new Vertex[_size + 1];
		}
		for(int i = 0; i < _size; i++)
		{
			temp = i+1;
			_vertexArray[temp] = vertexArray[i];
			if(referenceArray == null)
			{
				referenceArray = new int[_size+1];
			}
			//System.out.println(temp);
			//System.out.println(_vertexArray[temp].getFaceValue());
			if(_vertexArray[temp] != null)
			{
				referenceArray[_vertexArray[temp].getFaceValue()] = temp;
			}
		}
	}

	public Vertex deleteMin()
	{
		Vertex result = null;		
		result = _vertexArray[1];
		referenceArray[result.getFaceValue()] = -1;
		referenceArray[_vertexArray[_size].getFaceValue()] = -1;
		_vertexArray[1] = _vertexArray[_size];
		_size--;
		referenceArray[_vertexArray[1].getFaceValue()] = 1;
		percolateDown(1);
		return result;
	}

	public void decreaseKey(int faceValue, int distance, int predecessor)
	{
		int locationInQueue = referenceArray[faceValue];
		_vertexArray[locationInQueue].setDistance(distance);
		_vertexArray[locationInQueue].setPredecessor(predecessor);
		percolateUp(locationInQueue);
	}	

	private void percolateUp(int hole)
	{

		while(hole/2 >= 1 && _vertexArray[hole/2].getDistance() > _vertexArray[hole].getDistance())
		{
			swap(hole/2, hole);
			hole = hole/2;
		}
	}

	private void percolateDown(int hole)
	{
		int child;
		Vertex tmp = _vertexArray[ hole ];

		for( ; hole * 2 <= _size; hole = child )
		{
			child = hole * 2;
			if( child != _size && _vertexArray[ child + 1 ].getDistance() < _vertexArray[ child ].getDistance() )
			{
				child++;
			}
			if( _vertexArray[ child ].getDistance() <  tmp.getDistance())
			{
				_vertexArray[ hole ] = _vertexArray[ child ];
				referenceArray[_vertexArray[ hole ].getFaceValue()] = hole;
			}
			else
			{
				break;
			}
		}
		_vertexArray[ hole ] = tmp;
		referenceArray[_vertexArray[ hole ].getFaceValue()] = hole;
	}

	private void swap(int a, int b)
	{
		Vertex temp = _vertexArray[a];
		_vertexArray[a] = _vertexArray[b];
		_vertexArray[b] = temp;
		referenceArray[_vertexArray[a].getFaceValue()] = a;
		referenceArray[_vertexArray[b].getFaceValue()] = b;
	}
}


public class DijkstrasShorteshPath
{

	public static void main(String[] args) throws FileNotFoundException 
	{
		// TODO Auto-generated method stub
		String name = "Lingareddygari Upendra Reddy, Julakanti Naveen Reddy";
		String firstLine = null;
		Scanner input = new Scanner(new File("C:\\Users\\uppi\\Documents\\Code Snippets\\AllTest\\src\\ex.txt")); //Please have the path of the input file updated here
		String graphVertxnSource = null;
		int numberOfVertex = 0;
		int sourceVertex = -1;
		Vertex[] vertexList = null;
		String edgeDetails = null;
		int fromVertex, toVertex, weight;
		String[] fromToWeight = null;		

		int i;

		if(input.hasNextLine())
		{
			while(input.hasNextLine())
			{
				if(firstLine == null)
				{
					firstLine = input.nextLine();
				}

				if(graphVertxnSource == null)
				{
					graphVertxnSource = input.nextLine();
					numberOfVertex = Integer.parseInt(graphVertxnSource.substring(0, graphVertxnSource.indexOf(" ")));
					sourceVertex = Integer.parseInt(graphVertxnSource.substring(graphVertxnSource.indexOf(" ")+1));
				}

				if(numberOfVertex > 0)
				{
					vertexList = new Vertex[numberOfVertex];	
					for(i = 0; i < numberOfVertex; i++)
					{
						vertexList[i] = new Vertex(i, Integer.MAX_VALUE, -1, false);
					}						
				}

				while(input.hasNextLine())
				{
					edgeDetails = input.nextLine();
					fromToWeight = edgeDetails.split(" ");
					fromVertex = Integer.parseInt(fromToWeight[0]);
					toVertex = Integer.parseInt(fromToWeight[1]);
					weight = Integer.parseInt(fromToWeight[2]);
					vertexList[fromVertex].add(new EdgeNDistance(toVertex, weight));
				}
			}
		}

		PriorityQueueWithWeights queue =  new PriorityQueueWithWeights(vertexList);
		queue.decreaseKey(sourceVertex, 0, -1);
		int start = 0;
		//Vertex adjacent = null;
		int adjacentVert = -1;
		int adjacentWeight = Integer.MAX_VALUE;
		while(queue._size > 0)
		{
			Vertex vert = queue.deleteMin();	
			vert.setKnown(true);
			vertexList[vert.getFaceValue()] = vert;
			if(vertexList[vert.getFaceValue()] != null)
			{
				for(i = 0; i < vertexList[vert.getFaceValue()].getAdjacentList().size(); i++)
				{
					adjacentVert = ((EdgeNDistance)vertexList[vert.getFaceValue()].getAdjacentList().get(i)).getVertexValue();
					adjacentWeight = ((EdgeNDistance)vertexList[vert.getFaceValue()].getAdjacentList().get(i)).getWeightValue();

					if(!vertexList[adjacentVert].isKnown() && vertexList[adjacentVert].getDistance() > vert.getDistance() + adjacentWeight)
					{
						vertexList[adjacentVert].setDistance(vert.getDistance() + adjacentWeight);
						vertexList[adjacentVert].setPredecessor(vert.getFaceValue());
						queue.decreaseKey(adjacentVert, vertexList[adjacentVert].getDistance(), vertexList[adjacentVert].getPredecessor());							
					}
				}
			}
		}

		System.out.println(name);
		System.out.println(firstLine);
		for(i = 0; i < vertexList.length; i++)
		{
			if(vertexList[i].getDistance() != Integer.MAX_VALUE)
			{
				System.out.println(vertexList[i].getFaceValue() + " " + vertexList[i].getDistance()+ "  " + (vertexList[i].getPredecessor() == -1? "-":vertexList[i].getPredecessor()));
			}
			else
			{
				System.out.println("No Path from " + sourceVertex + "to " + vertexList[i].getFaceValue());
			}
		}

	}

}
