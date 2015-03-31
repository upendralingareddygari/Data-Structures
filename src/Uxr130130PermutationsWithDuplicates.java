package testPackage;

import java.util.Scanner;

public class Uxr130130PermutationsWithDuplicates {
	
	public static void main(String[] args) {
		System.out.println("Enter Some Numbers:");
		Scanner inputScanner = new Scanner(System.in);
		int totalNumberOfObjects;
		int verbose;
		int[] objects;
		while(inputScanner.hasNext()) {
			totalNumberOfObjects = inputScanner.nextInt();
			objects = new int[totalNumberOfObjects + 1];
			objects[0] = -1;
			System.out.println("totalNumberOfObjects" + totalNumberOfObjects);
			verbose = inputScanner.nextInt();
			System.out.println("verbose" + verbose);
			
			for(int i = 1; i <= totalNumberOfObjects; i++) {
				objects[i] = inputScanner.nextInt();
			}
		}
		inputScanner.close();		
	}
	
	private static void sortThemUp(int[] input) {
		
	}
}
