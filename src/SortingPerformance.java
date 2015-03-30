import java.util.Random;
import java.util.Scanner;

/*
 * 
 * 	Lingareddygari Upendra Reddy
 * 	java version "1.7.0_25"
	Java(TM) SE Runtime Environment (build 1.7.0_25-b17)
	Java HotSpot(TM) 64-Bit Server VM (build 23.25-b01, mixed mode)
 */


public class SortingPerformance 
{

	public static void main(String[] args) 
	{
		Scanner input = null;
		// TODO Auto-generated method stub
		System.out.println("Available Options!! Enter the sorting option");
		System.out.println("1. Shell Sort");
		System.out.println("2. Quick Sort");
		System.out.println("3. Heap Sort");
		System.out.println("4. Merge Sort");
		System.out.println("5. Quit");
		input = new Scanner(System.in);
		while(input.hasNextInt())
		{			
			int menuOption = input.nextInt();
			System.out.println("Enter no of elements");
			int totalNumberOfElements = input.nextInt();				

			switch(menuOption)
			{
			case 1:		
				System.out.println(getShellSortTime(totalNumberOfElements));
				break;
			case 2:
				System.out.println(getQuickSortTime(totalNumberOfElements));
				break;
			case 3:
				System.out.println(getHeapSortTime(totalNumberOfElements));
				break;
			case 4:
				System.out.println(getMergeSortTime(totalNumberOfElements));
				break;
			case 5:
				System.exit(0);
				break;
			default:
				System.out.println("Please choose correct option");
				System.out.println("Available Options!!");
				System.out.println("1. Shell Sort");
				System.out.println("2. Quick Sort");
				System.out.println("3. Heap Sort");
				System.out.println("4. Merge Sort");
				System.out.println("5. Quit");
				break;
			}
		}


	}

	private static long getShellSortTime(int n)
	{
		long start, end;
		int arr[] = null;
		start = System.currentTimeMillis();
		for(int i = 0; i < 1000; i++)
		{
			arr = creationOfArray(n);
			arr = randomizeArray(arr);			
			shellsort(arr);
		}
		end = System.currentTimeMillis();
		return (end-start)/1000;
	}

	private static long getQuickSortTime(int n)
	{
		long start, end;
		int[] arr = null;
		start = System.currentTimeMillis();		
		for(int i = 0; i < 1000; i++)
		{
			arr = creationOfArray(n);
			arr = randomizeArray(arr);			
			quicksort(arr);
		}
		end = System.currentTimeMillis();
		return (end-start)/1000;
	}

	private static long getHeapSortTime(int n)
	{
		long start, end;
		int[] arr = null;
		start = System.currentTimeMillis();		
		for(int i = 0; i < 1000; i++)
		{
			arr = creationOfArray(n);
			arr = randomizeArray(arr);			
			heapsort(arr);
		}
		end = System.currentTimeMillis();
		return (end-start)/1000;
	}

	private static long getMergeSortTime(int n)
	{
		long start, end;
		int[] arr = null;
		start = System.currentTimeMillis();		
		for(int i = 0; i < 1000; i++)
		{
			arr = creationOfArray(n);
			arr = randomizeArray(arr);			
			quicksort(arr);
		}
		end = System.currentTimeMillis();
		return (end-start)/1000;
	}


	private static int[] creationOfArray(int n)
	{
		int[] arr = new int[n];

		for(int i = 0; i < n; i++)
		{
			arr[i] = i;
		}

		return arr;

	}

	private static int[] randomizeArray(int[] arr)
	{
		int rand;
		Random r = new Random();
		for(int i = arr.length-1; i >= 0; i--)
		{
			rand = r.nextInt(arr.length);
			swap(arr, rand, arr.length-rand-1);
		}
		return arr;
	}

	private static void shellsort(int[] a )
	{
		int j;

		for( int gap = a.length / 2; gap > 0; gap /= 2 )
		{
			for( int i = gap; i < a.length; i++ )
			{
				int tmp = a[ i ];
				for( j = i; j >= gap && tmp < ( a[ j - gap ] ); j -= gap )
				{
					a[ j ] = a[ j - gap ];
				}
				a[ j ] = tmp;
			}
		}
	}

	private static void heapsort( int[] a )
	{
		for( int i = a.length / 2 - 1; i >= 0; i-- )  /* buildHeap */
		{
			percDown( a, i, a.length );
		}
		for( int i = a.length - 1; i > 0; i-- )
		{
			swap( a, 0, i );                /* deleteMax */
			percDown( a, 0, i );
		}
	}

	private static void swap(int[] arr, int a, int b)
	{
		arr[a] = arr[a]+ arr[b];
		arr[b] = arr[a] - arr[b];
		arr[a] = arr[a] - arr[b];
	}

	private static void percDown( int[] a, int i, int n )
	{
		int child;
		int tmp;

		for( tmp = a[ i ]; leftChild( i ) < n; i = child )
		{
			child = leftChild( i );
			if( child != n - 1 && a[ child ] < ( a[ child + 1 ] ) )
			{
				child++;
			}
			if( tmp <( a[ child ] ) )
			{
				a[ i ] = a[ child ];
			}
			else
			{
				break;
			}
		}
		a[ i ] = tmp;
	}

	private static int leftChild( int i )
	{
		return 2 * i + 1;
	}

	private static void mergeSort( int [] a )
	{
		int [] tmpArray = new int[a.length];

		mergeSort( a, tmpArray, 0, a.length - 1 );
	}

	private static void mergeSort( int [] a, int[ ] tmpArray, int left, int right )
	{
		if( left < right )
		{
			int center = ( left + right ) / 2;
			mergeSort( a, tmpArray, left, center );
			mergeSort( a, tmpArray, center + 1, right );
			merge( a, tmpArray, left, center + 1, right );
		}
	}

	private static void merge(int [] a, int [] tmpArray, int leftPos, int rightPos, int rightEnd )
	{
		int leftEnd = rightPos - 1;
		int tmpPos = leftPos;
		int numElements = rightEnd - leftPos + 1;

		// Main loop
		while( leftPos <= leftEnd && rightPos <= rightEnd )
		{
			if( a[ leftPos ] <=  a[ rightPos ] )
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

	private static void quicksort(int [] a )
	{
		quicksort( a, 0, a.length - 1 );
	}

	private static void quicksort(int[] a, int left, int right )
	{
		if( left + 3 <= right )
		{
			int pivot = median3( a, left, right );

			// Begin partitioning
			int i = left, j = right - 1;
			for( ; ; )
			{
				while( a[ ++i ] <  pivot )  { }
				while( a[ --j ] >  pivot )  { }
				if( i < j )
				{
					swap( a, i, j );
				}
				else
				{
					break;
				}
			}

			swap( a, i, right - 1 );   // Restore pivot

			quicksort( a, left, i - 1 );    // Sort small elements
			quicksort( a, i + 1, right );   // Sort large elements
		}
		else  // Do an insertion sort on the subarray
		{
			insertionSort( a, left, right );
		}
	}

	private static int median3( int[] a, int left, int right )
	{
		int center = ( left + right ) / 2;
		if( a[ center ] <  a[ left ] ) 
		{
			swap( a, left, center );
		}
		if( a[ right ] <  a[ left ] )
		{
			swap( a, left, right );
		}
		if( a[ right ] < a[ center ] ) 
		{
			swap( a, center, right );
		}

		// Place pivot at position right - 1
		swap( a, center, right - 1 );
		return a[ right - 1 ];
	}

	private static void insertionSort(int[] a, int left, int right )
	{
		for( int p = left + 1; p <= right; p++ )
		{
			int tmp = a[ p ];
			int j;

			for( j = p; j > left && tmp < a[ j - 1 ] ; j-- )
			{
				a[ j ] = a[ j - 1 ];
			}
			a[ j ] = tmp;
		}
	}



}
