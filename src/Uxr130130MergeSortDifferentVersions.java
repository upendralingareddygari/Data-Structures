package testPackage;

/**
 * 
 * @author Lingareddygari Upendra Reddy
 *
 */
public class Uxr130130MergeSortDifferentVersions {

	/**
	 * Entry point to the program
	 * @param String 
	 * 		count of the problem set
	 */
	public static void main(String[] args) {
		//Initializations
		int count = Integer.parseInt(args[0]);
		int[] input =new int[count];
						
		//basic mergeSort
		for(int j = 0; j < count; j++) {
			input[j] = count - j;
		}
		long start = System.currentTimeMillis();
		mergeSortBasic(input, 0, count - 1);
		long end = System.currentTimeMillis();
		validateSort(input);			
		System.out.println("Average time taken by Basic mergesort for " + count +" numbers is: " + (end - start));
		
		//merge sort with Global array 
		int[] auxiliary = new int[count];
		for(int j = 0; j < count; j++) {
			input[j] = count - j;
		}		
		start = System.currentTimeMillis();
		mergeSortWithGlobalArray(input, auxiliary, 0, count - 1);
		end = System.currentTimeMillis();
		validateSort(input);
		System.out.println("Average time taken by mergesort with global array for " + count +" numbers is: " + (end - start));
		
		//Even better merge sort		
		for(int j = 0; j < count; j++) {
			input[j] = count - j;
		}
		start = System.currentTimeMillis();
		int height = mergeSortAdvanced(input, auxiliary, 0, count - 1);
		end = System.currentTimeMillis();
		if(height % 2 == 0) {
			validateSort(input);
		} else {
			validateSort(auxiliary);
		}
		System.out.println("Average time taken by advanced merge sort " + count +" numbers is: " + (end - start));
		
	}
	
	/**
	 * Basic merge sort. Dynamically creates the arrays internally
	 * @param input Array of ints
	 * @param low   start index in the array
	 * @param high  end index in the array
	 */
	private static void mergeSortBasic(int[] input, int low, int high) {
		if(low < high) {
			if(high - low > 11) {
				int mid = (low + high) / 2;
				mergeSortBasic(input, low, mid);
				mergeSortBasic(input, mid + 1, high);
				mergeBasic(input, low, mid, high);
			} else {
				for(int i=low, j=i; i<high; j=++i) {
		   			int ai = input[i+1];
		    			while(ai < input[j]) {
		    				input[j+1] = input[j];
		    				if (j-- == low) {
			    				break;
		    				}
		    			}	
		    			input[j+1] = ai;
	 			}
			}
		}
	}
	
	/**
	 * merge procedure for basic merge sort process
	 * @param input array of ints
	 * @param low start index in the array
	 * @param mid middle index in the array
	 * @param high end index in the array
	 */
	private static void mergeBasic(int[] input, int low, int mid, int high) {
		int leftSubArraySize = (mid - low + 1);
		int[] leftSubArray = new int[leftSubArraySize];
		for(int i = low; i <= mid; i++) {
			leftSubArray[i - low] = input[i];
		}

		int rightSubArraySize = (high - mid);
		int[] rightSubArray = new int[rightSubArraySize];
		for(int i = mid + 1; i <= high; i++) {
			rightSubArray[i - (mid + 1)] = input[i];
		}
		
		int a = 0;
		int b = 0;
		for(int i = low; i <= high; i++) {
			if (b == rightSubArraySize || (a < leftSubArraySize && leftSubArray[a] <= rightSubArray[b])) {
				input[i] = leftSubArray[a++];
			} else { 
				input[i] = rightSubArray[b++];
			}
		}
	}
	
	/**
	 * merge sort procedure with global array
	 * @param input array with ints
	 * @param auxiliary array with ints
	 * @param low start index in the arrays
	 * @param high end index in the arrays
	 */
	private static void mergeSortWithGlobalArray(int[] input, int[] auxiliary, int low, int high) {
		if( low < high ) {			
				int mid = ( low + high ) / 2;
				mergeSortWithGlobalArray(input, auxiliary, low, mid);
				mergeSortWithGlobalArray(input, auxiliary, mid + 1, high);
				mergeWithGlobalArray(input, auxiliary, low, mid, high);
		}
	}
	
	/**
	 * merge procedure with global array
	 * @param input array with ints
	 * @param auxiliary array with ints
	 * @param low start index in the arrays
	 * @param mid middle index in the arrays
	 * @param high end index in the arrays
	 */
	private static void mergeWithGlobalArray(int[] input, int[] auxiliary, int low, int mid, int high) {
		for( int i = low; i <= high; i++ ) {
			auxiliary[i] = input[i];
		}		

		int a = low;
		int b = mid + 1;

		for(int i = low; i <= high; i++) {
			if( b > high || ( a < mid && auxiliary[a] <= auxiliary[b] )) {
				input[i] = auxiliary[a++]; 
			} else {
				input[i] = auxiliary[b++];
			}
		}
	}

	/**
	 * mergeSort advanced procedure
	 * @param input array of ints
	 * @param aux array of ints
	 * @param low start index in the arrays
	 * @param high end index in the arrays
	 * @return
	 */
	private static int mergeSortAdvanced(int[] input, int[] aux, int low, int high) {
		if(low < high) {			
			int mid = (low + high) / 2;
			int height1 = mergeSortAdvanced(input, aux, low, mid); //heights calcualtion and merging.
			int height2 = mergeSortAdvanced(input, aux, mid + 1, high);
			if(height1 == height2) {
				if((height1 & 1) == 0) {
					mergeArraysWithEqualHeights(input, aux, low, mid, high);
				} else {
					mergeArraysWithEqualHeights(aux, input, low, mid, high);
				}
			} else {
				if((height1 & 1) == 0) {
					mergeArraysWithUnequalHeights(aux, input, low, mid, high);
				} else {
					mergeArraysWithUnequalHeights(input, aux, low, mid, high);
				}
			}
			return ++height1; //Shifting of elements happen in here
		} else {
			return 0; //base case
		}
	}

	/**
	 * mergeArraysWithEqualHeights merging arrays with equal heights
	 * @param source array of ints
	 * @param destination array of ints
	 * @param low start index in the arrays
	 * @param mid mid index in the arrays
	 * @param high high index in the arrays
	 */
	private static void mergeArraysWithEqualHeights(int[] source, int[] destination, int low, int mid, int high) {
		int i = low;
		int j = mid + 1;
		for(int x = low; x <= high; x++) {
			if(j > high || (i <= mid && source[i] < source[j])) {
				destination[x] = source[i++];
			} else {
				destination[x] = source[j++];
			}
		}
	}

	/**
	 * mergeArraysWithUnequalHeights merging arrays with equal heights
	 * @param source array of ints
	 * @param destination array of ints
	 * @param low start index in the arrays
	 * @param mid mid index in the arrays
	 * @param high high index in the arrays
	 */
	private static void mergeArraysWithUnequalHeights(int[] destination, int[] source, int low, int mid, int high) {
		int i = low;
		int j = mid + 1;
		for(int x = low; x <= high; x++) {
			if(j > high || (i <= mid && source[i] < destination[j])) {
				destination[x] = source[i++];
			} else {
				destination[x] = destination[j++];
			}
		}
	}

	/**
	 * Validate the order of the given array
	 * @param input arrays of ints
	 */
	private static void validateSort(int[] input) {
		for(int i = 0; i < input.length - 1; i++) {
			if(input[i] > input[i + 1]) {
				System.out.println("Sorting Failure");
				return;
			}
		}
	}
}
