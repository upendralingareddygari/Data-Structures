package testPackage;

import java.util.Scanner;

/**
 * 
 * @author uppi
 *	Lexicographic generator
 */
class Uxr130130LexicographicOrderGeneration {
	
	private static int permutationCount = 1;
	
	/**
	 * Entry point to the program
	 * @param args problem set size
	 */
	public static void main(String[] args) {
		
		int totalNumberOfElements = Integer.parseInt(args[0]);
		int verbose = Integer.parseInt(args[1]);
		int[] auxilary = new int[totalNumberOfElements + 1];
		auxilary[0] = -1;		
		Scanner input = new Scanner(System.in);
		System.out.println("Please print the numbers: ");
		while(input.hasNextLine()) {
			for(int i = 1; i < auxilary.length; i++) {
				auxilary[i] = input.nextInt();
			}
			break;
		}
		input.close();
		mergeSortBasic(auxilary, 0, auxilary.length - 1);
		if(verbose > 0) {
			printPermutation(auxilary);
		}
		long start = System.currentTimeMillis();
		doGenerate(auxilary, verbose);
		long end = System.currentTimeMillis();
		System.out.println(permutationCount + " " + (end - start));
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
	 * generate the permutations
	 * @param input array input
	 * @param verbose indicating printing the whole set or just total number
	 */
	private static void doGenerate(int[] input, int verbose) {
		do{
			int j = getMaxFromRight(input);
			if(j == 0) {
				break;
			}
			int l = getMaxFromLeft(input, j);
			swap(input, j, l);
			reverse(input, j + 1);
			visit(input, verbose);
		}while(true);
	}

	/**
	 * get the max index where A[j] < A[j+1]
	 * @param input array
	 * @return index 
	 */
	private static int getMaxFromRight(int[] input) {
		for(int i = input.length - 2; i >= 0; i--) {
			if(input[i] < input[i + 1]) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * get max index
	 * @param input input array
	 * @param j the index from the previous method
	 * @return max index such that A[j] < A[l];
	 */
	private static int getMaxFromLeft(int[] input, int j) {
		for(int i = input.length -1; i >= 0; i--) {
			if(input[j] < input[i]) {
				return i;
			}
		}
		return - 1;
	}

	/**
	 * Reverse the array from given index
	 * @param input array
	 * @param l index from where the array needs to be reversed
	 */
	private static void reverse(int[] input, int l) {
		int j = input.length - 1;
		while(l < j) {
			swap(input, l, j);
			l++;
			j--;
		}
	}

	/**
	 * Swap two elements in the given array
	 * @param input array
	 * @param i index
	 * @param j index
	 */
	private static void swap(int[] input, int i, int j) {
		input[i] = input[i] ^ input[j];
		input[j] = input[i] ^ input[j];
		input[i] = input[i] ^ input[j];
	}
	
	/**
	 * Visit the permutation and do the necessary based on the verbose flag
	 * @param input array
	 * @param verbose indicate to print
	 */
	private static void visit(int[] input, int verbose) {
		if(verbose > 0) {
			printPermutation(input);
		}
		permutationCount++;
	}

	/**
	 * print the given combintations
	 * @param input
	 */
	private static void printPermutation(int[] input) {
		for(int i = 1; i < input.length; i++) {
			System.out.print(input[i] + " ");
		}
		System.out.println();
	}
}

