import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class ECLCEdgeNDistance
{
	private int _vertex;
	private int _weight;

	public ECLCEdgeNDistance(int vertex, int weight) 
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

class ECLCVertex
{
	private int _faceValue;
	private int _distance;
	private int _predecessor;
	private boolean _known;
	private ArrayList<ECLCEdgeNDistance> adjList = null;
	private int _inDegree = 0;
	private int _outDegree = 0;
	private int _top = -1;

	public ECLCVertex(int faceValue, int distance, int predecessor, boolean known) 
	{
		_faceValue = faceValue;
		_distance = distance;
		_predecessor = predecessor;
		_known = known;
		adjList = new ArrayList<ECLCEdgeNDistance>();
	}

	public ArrayList getAdjacentList()
	{
		return adjList;
	}

	public void add(ECLCEdgeNDistance edge)
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

	public int getInDegree()
	{
		return _inDegree;
	}

	public int getOutDegree()
	{
		return _outDegree;
	}

	public int getTop()
	{
		return _top;
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

	public void setInDegree(int inDegree)
	{
		_inDegree = inDegree;
	}

	public void setOutDegree(int outDegree)
	{
		_outDegree = outDegree;
	}

	public void setTop(int top)
	{
		_top = top;
	}
}

class ECLCGraph
{
	private int _noOfVertices = -1;

	public ECLCGraph(int numOfVertices) 
	{
		_noOfVertices = numOfVertices;
	}

	public ECLCVertex[] sortOnTopReverse(ECLCVertex[] v)
	{
		mergeSort1(v);
		return v;
	}

	public ECLCVertex[] sortOnFaceValue(ECLCVertex[] v)
	{
		mergeSort2(v);
		return v;	
	}
	
	public ECLCVertex[] sortOnTop(ECLCVertex[] v)
	{		
		mergeSort(v);
		return v;
	}

	private void mergeSort( ECLCVertex [] a )
	{
		ECLCVertex [] tmpArray = new ECLCVertex[a.length];
		mergeSort( a, tmpArray, 0, a.length - 1 );
	}

	private void mergeSort1( ECLCVertex [] a )
	{
		ECLCVertex [] tmpArray = new ECLCVertex[a.length];
		mergeSort1( a, tmpArray, 0, a.length - 1 );
	}
	
	private void mergeSort2( ECLCVertex [] a )
	{
		ECLCVertex [] tmpArray = new ECLCVertex[a.length];
		mergeSort2( a, tmpArray, 0, a.length - 1 );
	}


	private void mergeSort( ECLCVertex [] a, ECLCVertex[ ] tmpArray, int left, int right )
	{
		if( left < right )
		{
			int center = ( left + right ) / 2;
			mergeSort( a, tmpArray, left, center );
			mergeSort( a, tmpArray, center + 1, right );
			merge( a, tmpArray, left, center + 1, right );
		}
	}

	private void mergeSort1( ECLCVertex [] a, ECLCVertex[ ] tmpArray, int left, int right )
	{
		if( left < right )
		{
			int center = ( left + right ) / 2;
			mergeSort1( a, tmpArray, left, center );
			mergeSort1( a, tmpArray, center + 1, right );
			merge1( a, tmpArray, left, center + 1, right );
		}
	}

	private void mergeSort2( ECLCVertex [] a, ECLCVertex[ ] tmpArray, int left, int right )
	{
		if( left < right )
		{
			int center = ( left + right ) / 2;
			mergeSort2( a, tmpArray, left, center );
			mergeSort2( a, tmpArray, center + 1, right );
			merge2( a, tmpArray, left, center + 1, right );
		}
	}

	private void merge(ECLCVertex [] a, ECLCVertex [] tmpArray, int leftPos, int rightPos, int rightEnd )
	{
		int leftEnd = rightPos - 1;
		int tmpPos = leftPos;
		int numElements = rightEnd - leftPos + 1;

		// Main loop
		while( leftPos <= leftEnd && rightPos <= rightEnd )
		{
			if( a[ leftPos ].getTop() <=  a[ rightPos ].getTop() )
				tmpArray[ tmpPos++ ] = a[ leftPos++ ];
			else
				tmpArray[ tmpPos++ ] = a[ rightPos++ ];
		}
		while( leftPos <= leftEnd )   // Copy rest of first half
		{
			tmpArray[ tmpPos++ ] = a[ leftPos++ ];
		}
		while( rightPos <= rightEnd )  // Copy rest of right half
		{
			tmpArray[ tmpPos++ ] = a[ rightPos++ ];
		}
		// Copy tmpArray back
		for( int i = 0; i < numElements; i++, rightEnd-- )
		{
			a[ rightEnd ] = tmpArray[ rightEnd ];
		}
	}

	private void merge1(ECLCVertex [] a, ECLCVertex [] tmpArray, int leftPos, int rightPos, int rightEnd )
	{
		int leftEnd = rightPos - 1;
		int tmpPos = leftPos;
		int numElements = rightEnd - leftPos + 1;

		// Main loop
		while( leftPos <= leftEnd && rightPos <= rightEnd )
		{
			if( a[ leftPos ].getTop() >=  a[ rightPos ].getTop() )
				tmpArray[ tmpPos++ ] = a[ leftPos++ ];
			else
				tmpArray[ tmpPos++ ] = a[ rightPos++ ];
		}
		while( leftPos <= leftEnd )   // Copy rest of first half
		{
			tmpArray[ tmpPos++ ] = a[ leftPos++ ];
		}
		while( rightPos <= rightEnd )  // Copy rest of right half
		{
			tmpArray[ tmpPos++ ] = a[ rightPos++ ];
		}
		// Copy tmpArray back
		for( int i = 0; i < numElements; i++, rightEnd-- )
		{
			a[ rightEnd ] = tmpArray[ rightEnd ];
		}
	}

	private void merge2(ECLCVertex [] a, ECLCVertex [] tmpArray, int leftPos, int rightPos, int rightEnd )
	{
		int leftEnd = rightPos - 1;
		int tmpPos = leftPos;
		int numElements = rightEnd - leftPos + 1;

		// Main loop
		while( leftPos <= leftEnd && rightPos <= rightEnd )
		{
			if( a[ leftPos ].getFaceValue() <=  a[ rightPos ].getFaceValue() )
				tmpArray[ tmpPos++ ] = a[ leftPos++ ];
			else
				tmpArray[ tmpPos++ ] = a[ rightPos++ ];
		}
		while( leftPos <= leftEnd )   // Copy rest of first half
		{
			tmpArray[ tmpPos++ ] = a[ leftPos++ ];
		}
		while( rightPos <= rightEnd )  // Copy rest of right half
		{
			tmpArray[ tmpPos++ ] = a[ rightPos++ ];
		}
		// Copy tmpArray back
		for( int i = 0; i < numElements; i++, rightEnd-- )
		{
			a[ rightEnd ] = tmpArray[ rightEnd ];
		}
	}

	public void doDFS(ECLCVertex[] vertices)
	{
		for(int i = 0; i < vertices.length; i++)
		{
			if(!vertices[i].isKnown())
			{
				DFS(vertices, i);
			}
		}		
	}

	private void DFS(ECLCVertex[] vertices, int vertLocation)
	{
		vertices[vertLocation].setKnown(true);
		if(vertices[vertLocation].getAdjacentList() != null)
		{
			for(int i = 0; i < vertices[vertLocation].getAdjacentList().size(); i++)
			{		
				int adjVertLocatation = 0;
				if(((ECLCEdgeNDistance)vertices[vertLocation].getAdjacentList().get(i)).getVertexValue() != Integer.MAX_VALUE)
				{
					adjVertLocatation = ((ECLCEdgeNDistance)vertices[vertLocation].getAdjacentList().get(i)).getVertexValue()+1;
				}
				else
				{
					adjVertLocatation = vertices.length-1;
				}
				if(!vertices[adjVertLocatation].isKnown())
				{
					DFS(vertices, adjVertLocatation);
					vertices[adjVertLocatation].setPredecessor(vertices[vertLocation].getFaceValue());					
				}
			}
		}		
		vertices[vertLocation].setTop(_noOfVertices--);
	}

	private void quickSort(ECLCVertex[] A, int left, int right)
	{
		if(left < right)
		{
			int pivot = getPivot(A, left, right);
			quickSort(A, left, pivot-1);
			quickSort(A, pivot+1, right);
		}
	}

	private int getPivot(ECLCVertex[] arr, int left, int right) 
	{
		ECLCVertex x = arr[left];
		int i = left-1 ;
		int j = right+1 ;

		while (true) 
		{
			i++;
			while ( i < right && arr[i].getTop() < x.getTop())
			{
				i++;
			}
			j--;
			while (j > left && arr[j].getTop() > x.getTop())
			{
				j--;
			}
			if (i < j)
			{
				swap(arr, i, j);
			}
			else
			{
				return j;
			}
		}
	}

	private void swap(ECLCVertex[] arr, int a, int b)
	{
		ECLCVertex temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}	

	public void TopoUsingQueue(ECLCVertex[] temp)
	{	
		int next = 1;
		ECLCQueue tempQueue = new ECLCQueue();
		for(int i = 0; i < temp.length; i++)
		{
			if(temp[i].getInDegree() == 0)
			{
				tempQueue.enQueue(temp[i]);
			}
		}
		while(tempQueue._size != 0)
		{
			ECLCVertex dgV = tempQueue.deQueue();
			dgV.setTop(next++);
			for(int j = 0; j < dgV.getAdjacentList().size(); j++)
			{
				if(((ECLCEdgeNDistance)dgV.getAdjacentList().get(j)).getVertexValue() != Integer.MAX_VALUE)
				{
					temp[((ECLCEdgeNDistance)dgV.getAdjacentList().get(j)).getVertexValue()+1].setInDegree(temp[((ECLCEdgeNDistance)dgV.getAdjacentList().get(j)).getVertexValue()+1].getInDegree()-1);
					if(temp[((ECLCEdgeNDistance)dgV.getAdjacentList().get(j)).getVertexValue()+1].getInDegree() == 0)
					{
						tempQueue.enQueue(temp[((ECLCEdgeNDistance)dgV.getAdjacentList().get(j)).getVertexValue()+1]);
					}					
				}
				else
				{
					temp[temp.length-1].setInDegree(temp[temp.length-1].getInDegree()-1);
					if(temp[temp.length-1].getInDegree() == 0)
					{
						tempQueue.enQueue(temp[temp.length-1]);
					}					

				}
			}
		}

	}
}

class ECLCQueue
{
	int arrSize = 10;
	ECLCVertex[] array = new ECLCVertex[arrSize];

	int _size = 0;
	int front = -1;
	int rear = 0;

	public int getSize()
	{
		return _size;
	}

	public void enQueue(ECLCVertex x)
	{
		if(_size == arrSize)
		{
			Resize();
		}		
		array[rear%array.length] = x;
		rear = (rear+1)%array.length;
		_size++;
		if(front == -1)
		{
			front = 0;
		}
	}

	public void Resize()
	{
		arrSize *= 2;
		ECLCVertex[] newArry = new ECLCVertex[arrSize];

		for(int i = 0; i < array.length;i++)
		{
			newArry[i] = array[front%array.length];
			front++;
		}

		rear = array.length;
		front = 0;
		array = newArry;
	}

	public boolean isEmpty()
	{
		return _size > 0 ? false:true; 
	}

	public ECLCVertex deQueue()
	{
		ECLCVertex temp = array[front];
		_size--;
		front=(front+1)%array.length;
		return temp;
	}

}

class ECLCnSlackCaliculations
{
	private ECLCVertex[] _vertexArray = null;
	private int[] referenceArray = null;

	public ECLCnSlackCaliculations(ECLCVertex[] v) 
	{
		_vertexArray = v;
	}

	public int[] getECs()
	{
		int[] result = null;
		for(int i = 1; i < _vertexArray.length-1; i++)
		{
			if(result == null)
			{
				result = new int[_vertexArray.length-2];
			}
			result[_vertexArray[i].getFaceValue()] = _vertexArray[i].getDistance();
		}
		return result;
	}	

	public int[] getLCs(int cpLength)
	{
		int[] result = null;
		for(int i = 1; i < _vertexArray.length-1; i++)
		{
			if(referenceArray == null)
			{
				referenceArray = new int[_vertexArray.length-2];
			}
			referenceArray[_vertexArray[i].getFaceValue()] = i;
		}

		for(int i = 1; i < _vertexArray.length-1; i++)
		{
			int minValue = Integer.MAX_VALUE;
			int adjacentVert;
			int adjacentWeight;
			int lcAdjacent;
			for(int j = 0; j < _vertexArray[i].getAdjacentList().size(); j++)
			{
				adjacentVert = ((ECLCEdgeNDistance)_vertexArray[i].getAdjacentList().get(j)).getVertexValue();
				adjacentWeight = ((ECLCEdgeNDistance)_vertexArray[i].getAdjacentList().get(j)).getWeightValue();

				if(adjacentVert == Integer.MAX_VALUE)
				{
					lcAdjacent = cpLength;
				}
				else
				{
					lcAdjacent = result[adjacentVert];
				}
				minValue = Math.min(lcAdjacent-adjacentWeight, minValue);

			}
			if(result == null)
			{
				result = new int[_vertexArray.length-2];
			}
			result[_vertexArray[i].getFaceValue()] = minValue;
		}

		return result;

	}


}


public class EarliestLatestAndSlackForAGivenTaskInProject 
{

	public static void main(String[] args) throws FileNotFoundException 
	{
		String name = "Lingareddygari Upendra Reddy";
		String firstLine = null;
		Scanner input = new Scanner(new File("C:\\Users\\uppi\\Documents\\Code Snippets\\AllTest\\src\\ex.txt"));	//Please have the path of the input file updated here
		int numberOfVertex = 0;
		ECLCVertex[] vertexList = null;
		String edgeDetails = null;
		String tasksLine = null;
		String[] tasks = null;
		int[] taskDurations = null;
		int fromVertex, toVertex, weight;
		ECLCVertex targetVertex = null;
		int criticalPathLength = 0;
		int i = 0;

		if(input.hasNextLine())
		{
			while(input.hasNextLine())
			{
				if(firstLine == null)
				{
					firstLine = input.nextLine();
				}

				if(numberOfVertex == 0)
				{
					numberOfVertex = Integer.parseInt(input.nextLine());
					taskDurations = new int[numberOfVertex];
					vertexList = new ECLCVertex[numberOfVertex];
				}

				if(tasksLine == null)
				{
					tasksLine = input.nextLine();	
					tasks = tasksLine.split(" ");
					for(i = 0; i < tasks.length; i++)
					{
						taskDurations[i] = Integer.parseInt(tasks[i]);
					}
				}

				while(input.hasNextLine())
				{
					edgeDetails = input.nextLine();			
					fromVertex = Integer.parseInt(edgeDetails.substring(0, edgeDetails.indexOf(" ")));
					toVertex = Integer.parseInt(edgeDetails.substring(edgeDetails.indexOf(" ")+1));
					weight = taskDurations[toVertex];
					if(vertexList[fromVertex] == null)
					{
						vertexList[fromVertex] = new ECLCVertex(fromVertex, Integer.MAX_VALUE, -1, false);
					}
					if(vertexList[toVertex] == null)
					{
						vertexList[toVertex] = new ECLCVertex(toVertex, Integer.MAX_VALUE, -1, false);
					}					
					vertexList[fromVertex].add(new ECLCEdgeNDistance(toVertex, weight));					
					vertexList[fromVertex].setOutDegree(vertexList[fromVertex].getOutDegree()+1);
					vertexList[toVertex].setInDegree(vertexList[toVertex].getInDegree()+1);
				}		

				for(i = 0; i < numberOfVertex; i++)
				{
					if(vertexList[i] == null)
					{
						vertexList[i] = new ECLCVertex(i, Integer.MAX_VALUE, -1, false);
					}
				}
			}
		}

		ECLCVertex[] temp = null;
		temp = new ECLCVertex[vertexList.length+2];
		for(i = 0; i < vertexList.length; i++)
		{
			if(vertexList[i].getInDegree() == 0)
			{
				if(temp[0] == null)
				{
					temp[0] = new ECLCVertex(-1, 0, -1, false);
				}
				temp[0].add(new ECLCEdgeNDistance(i, taskDurations[i]));
				vertexList[i].setInDegree(vertexList[i].getInDegree() + 1);
				temp[0].setOutDegree(temp[0].getOutDegree() + 1);
			}

			if(vertexList[i].getOutDegree() == 0)
			{
				if(temp[vertexList.length+1] == null)
				{
					temp[vertexList.length+1] = new ECLCVertex(Integer.MAX_VALUE, 0, i, false);
				}
				vertexList[i].add(new ECLCEdgeNDistance(Integer.MAX_VALUE, 0));
				vertexList[i].setOutDegree(vertexList[i].getOutDegree() + 1);
				temp[vertexList.length+1].setInDegree(temp[vertexList.length+1].getInDegree() + 1);
			}

			temp[i+1] = vertexList[i];
		}

		vertexList = temp;


		ECLCGraph g = new ECLCGraph(vertexList.length);
		//g.doDFS(vertexList);
		g.TopoUsingQueue(vertexList);

		g.sortOnTop(vertexList);

		houseKeeping1(vertexList);

		int adjacentVert = 0;
		int adjacentWeight = 0;

		for(i = 0; i < vertexList.length; i++)
		{
			for(int j = 0; j < vertexList[i].getAdjacentList().size(); j++)
			{
				adjacentVert = ((ECLCEdgeNDistance)vertexList[i].getAdjacentList().get(j)).getVertexValue();
				adjacentWeight = ((ECLCEdgeNDistance)vertexList[i].getAdjacentList().get(j)).getWeightValue();
				int vertLocation = getLocation(vertexList, adjacentVert);

				if(adjacentVert != Integer.MAX_VALUE)
				{					
					if(vertexList[vertLocation].getDistance() > vertexList[i].getDistance() + adjacentWeight)
					{
						vertexList[vertLocation].setDistance(vertexList[i].getDistance() + adjacentWeight);
						vertexList[vertLocation].setPredecessor(vertexList[i].getFaceValue());
					}
				}
				else
				{
					if(vertexList[vertLocation].getDistance() > vertexList[i].getDistance() + adjacentWeight)
					{
						vertexList[vertLocation].setDistance(vertexList[i].getDistance() + adjacentWeight);
						vertexList[vertLocation].setPredecessor(vertexList[i].getFaceValue());
						targetVertex = vertexList[vertLocation];
					}
				}
			}
		}

		criticalPathLength = targetVertex.getDistance();
		criticalPathLength *= -1;

		houseKeeping2(vertexList);

		g.sortOnTopReverse(vertexList);

		ECLCnSlackCaliculations eclcCalc = new ECLCnSlackCaliculations(vertexList);

		int[] ecs = eclcCalc.getECs();

		int[] lcs = eclcCalc.getLCs(criticalPathLength);
		
		System.out.println(firstLine);
		System.out.println(name);
		System.out.println();
		System.out.println("Project		EC		LC");

		for(i = 0; i < lcs.length; i++)
		{
			System.out.println(i + "		" + ecs[i] + "		" + lcs[i]);
		}		

		g.sortOnFaceValue(vertexList);
		int slack;
		System.out.println("\nSlack of edges:");
		for(i = 1; i < vertexList.length - 1; i++)
		{
			for(int j = 0; j < vertexList[i].getAdjacentList().size(); j++)
			{
				adjacentVert = ((ECLCEdgeNDistance)vertexList[i].getAdjacentList().get(j)).getVertexValue();
				adjacentWeight = ((ECLCEdgeNDistance)vertexList[i].getAdjacentList().get(j)).getWeightValue();
				if(adjacentVert != Integer.MAX_VALUE)
				{
					slack = (lcs[adjacentVert] - adjacentWeight) - ecs[vertexList[i].getFaceValue()];
					System.out.println("(" + vertexList[i].getFaceValue() + "," + adjacentVert + ") " + slack);
				}
			}
		}

	}

	private static String getPath(ECLCVertex v, ECLCVertex[] vList)
	{
		String result = null;

		while(v.getPredecessor() != -1)
		{
			if(result == null)
			{
				result = new String();
			}
			result += v.getPredecessor() + " "; 

			v = vList[v.getPredecessor() + 1];
		}
		result = result.trim();

		char[] temp = result.toCharArray();		
		char x;
		for(int i = 0; i < temp.length/2; i++)
		{			
			x = temp[i];
			temp[i] = temp[temp.length-i-1];
			temp[temp.length-i-1] = x;
		}

		result = new String();
		for(int i = 0; i < temp.length; i++)
		{
			result += temp[i];
		}

		return result;
	}

	private static void houseKeeping1(ECLCVertex[] v)
	{
		for(int i = 0; i < v.length; i++)
		{
			if(v[i].getFaceValue() == -1)
			{
				v[i].setDistance(0);
			}
			else
			{
				v[i].setDistance(Integer.MAX_VALUE);
			}
			for(int j = 0; j < v[i].getAdjacentList().size(); j++)
			{
				((ECLCEdgeNDistance)v[i].getAdjacentList().get(j)).setWeightValue(-1 * ((ECLCEdgeNDistance)v[i].getAdjacentList().get(j)).getWeightValue());
			}
		}
	}

	private static void houseKeeping2(ECLCVertex[] v)
	{
		for(int i = 0; i < v.length; i++)
		{
			if(v[i].getFaceValue() == -1)
			{
				v[i].setDistance(0);
			}
			else
			{
				v[i].setDistance(-1 * v[i].getDistance());
			}
			for(int j = 0; j < v[i].getAdjacentList().size(); j++)
			{
				((ECLCEdgeNDistance)v[i].getAdjacentList().get(j)).setWeightValue(-1 * ((ECLCEdgeNDistance)v[i].getAdjacentList().get(j)).getWeightValue());
			}
		}
	}

	private static int getLocation(ECLCVertex[] vertex, int value)
	{
		int result = -1;
		for(int i = 0; i < vertex.length; i++)
		{
			if(vertex[i].getFaceValue() == value)
			{
				result = i;
				break;
			}
		}
		return result;
	}

}
