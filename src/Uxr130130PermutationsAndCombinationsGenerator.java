package testPackage;

/**
 * Permutations and Comibnation generator
 * @author uppi
 * 
 */
class Uxr130130PermutationsAndCombinationsGenerator {
	
	private static int permutationsCount;
	private static int combinationsCount;
	
	/**
	 * Entry point to the program
	 * @param args problem set sizes
	 */
	public static void main(String[] args) {
		int i = Integer.parseInt(args[0]);
		int r = Integer.parseInt(args[1]);
		int verbose = Integer.parseInt(args[2]);

		switch(verbose) {
			case 0:
				generatePermutations(i, r, 0);
				break;
			case 1:
				generateCombinations(i, r, 1);
				break;
			case 2:
				generatePermutations(i, r, 2);
				break;
			case 3:
				generateCombinations(i, r, 3);
				break;
		}
	}

	/**
	 * generate combinations for the given size
	 * @param i total number of elements
	 * @param r picking elements size
	 * @param v indicator to denote the printing or counting
	 */
	private static void generateCombinations(int i, int r, int v) {
		int[] auxilary = new int[i];
		setUpArray(auxilary);
		doCombinations(auxilary, i, r, v);
		if(v == 1) {
			System.out.println(combinationsCount);
		}
	}

	/**
	 * creating combintations
	 * @param auxilary array
	 * @param i total number of elements
	 * @param r picking elements size
	 * @param v indicator to denote the printing or counting
	 */
	private static void doCombinations(int[] auxilary, int i, int r, int v) {
		if(r == 0) {
			combinationsCount++;
			if(v == 3) {
				for(int l = 0; l < auxilary.length; l++) {
					if(auxilary[l] > 0) {
						System.out.print(auxilary[l] + " ");
					}
				}
				System.out.println();
			}
		} else if(i < r) {
			return;
		} else {
			auxilary[i - 1] = i;
			doCombinations(auxilary, i - 1, r - 1, v);
			auxilary[i - 1] = 0;
			doCombinations(auxilary, i - 1, r, v);
		}
	}

	/**
	 * generate permutations for the given size
	 * @param i total number of elements
	 * @param r picking elements size
	 * @param v indicator to denote the printing or counting
	 */
	private static void generatePermutations(int i, int r, int v) {
		int[] auxilary = new int[r];
		setUpArray(auxilary);
		doPermute(auxilary, i, v);
		if(v == 0) {
			System.out.println(permutationsCount);
		}
	}
	
	/**
	 * Setup the array with deafault values
	 * @param auxilary array
	 */
	private static void setUpArray(int[] auxilary) {
		for(int l = 0; l < auxilary.length; l++) {
			auxilary[l] = 0;
		}
	}

	/**
	 * creating permutations
	 * @param auxilary array
	 * @param i total number of elements to be picked
	 * @param v indicator to denote the printing or counting
	 */
	
	private static void doPermute(int[] A, int i, int v) {
		if(isValid(A)) {
			permutationsCount++;
			if(v == 2) {
				for(int l = 0; l < A.length; l++) {
					System.out.print(A[l] + " ");
				}
				System.out.println();
			}
		} else {
			for(int k = 0; k < A.length; k++) {
				if(A[k] == 0) {
					for(int y = i; y > 0; y--) {
						A[k] = y;
						doPermute(A, y - 1, v);
						A[k] = 0;
					}
				}
			}
		}
	}

	/**
	 * Check the validity of given arrangement
	 * @param A array
	 * @return flag of validity indicator
	 */
	private static boolean isValid(int[] A) {
		for(int i = 0; i < A.length; i++) {
			if(A[i] == 0) {
				return false;
			}
		}
		return true;		
	}
}
