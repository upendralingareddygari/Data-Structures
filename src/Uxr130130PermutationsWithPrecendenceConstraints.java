package testPackage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Generates permutations satisfying given precedence constraints
 * 
 * @author Uxr130130
 * 
 */
public class Uxr130130PermutationsWithPrecendenceConstraints {

	private static int permutationsCount;
	
	/**
	 * Entry point of the program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		int numberOfDistinctObjects = Integer.parseInt(args[0]);
		int numberOfPrecedenceConstraints = Integer.parseInt(args[1]);
		int verbose = Integer.parseInt(args[2]);

		Node[] nodes = new Node[numberOfDistinctObjects];
		for (int i = 0; i < numberOfDistinctObjects; i++) {
			nodes[i] = new Node(new Integer(i + 1).toString());
		}

		HashSet<String> actualPrecedenceSet = new HashSet<String>();
		Scanner input = new Scanner(System.in);
		System.out.println("Enter precedents");
		
		while (input.hasNext()) {
			for (int i = 0; i < numberOfPrecedenceConstraints; i++) {
				String precedenceConstraint = input.nextLine();
				actualPrecedenceSet.add(precedenceConstraint);
				String fromNode = precedenceConstraint.substring(0, precedenceConstraint.indexOf(' '));
				String toNode = precedenceConstraint .substring(precedenceConstraint.indexOf(' ') + 1);
				nodes[Integer.parseInt(fromNode) - 1].addEdge(nodes[Integer.parseInt(toNode) - 1]);
			}
			break;
		}
		input.close();

		ArrayList<Node> L = new ArrayList<Node>();

		// S <- Set of all nodes with no incoming edges
		HashSet<Node> S = new HashSet<Node>();
		for (Node n : nodes) {
			if (n.inEdges.size() == 0) {
				S.add(n);
			}
		}

		while (!S.isEmpty()) {
			// remove a node n from S
			Node n = S.iterator().next();
			S.remove(n);

			// insert n into L
			L.add(n);

			// for each node m with an edge e from n to m do
			for (Iterator<Edge> it = n.outEdges.iterator(); it.hasNext();) {
				// remove edge e from the graph
				Edge e = it.next();
				Node m = e.to;
				it.remove();// Remove edge from n
				m.inEdges.remove(e);// Remove edge from m

				// if m has no other incoming edges then insert m into S
				if (m.inEdges.isEmpty()) {
					S.add(m);
				}
			}
		}

		// Check to see if all edges are removed
		boolean cycle = false;
		for (Node n : nodes) {
			if (!n.inEdges.isEmpty()) {
				cycle = true;
				break;
			}
		}

		if (cycle) {
			System.out.println("Cycle present, topological sort not possible");
			return;
		}
		
		int[] auxilary = new int[numberOfDistinctObjects + 1];
		int[] actualInput = new int[numberOfDistinctObjects];
		
		for(int i = 0; i < nodes.length; i++) {
			actualInput[i] = Integer.parseInt(L.get(i).name);
		}		

		for(int i = 0; i <= nodes.length; i++) {
			auxilary[i] = i;
		}		
		
		HashSet<String> auxilarySet = new HashSet<String>();		
		Iterator<String> iterator = actualPrecedenceSet.iterator();		
		while(iterator.hasNext()) {
			String precedence = (String) iterator.next();
			String fromNode = precedence.substring(0, precedence.indexOf(' '));
			String toNode = precedence.substring(precedence.indexOf(' ') + 1);
			int from = getIndex(actualInput, fromNode);
			int to = getIndex(actualInput, toNode);			
			auxilarySet.add(from + " " + to);
		}
		
		for(int i = 1; i <= numberOfDistinctObjects; i++) {
			auxilarySet.add("0 " + i);
		}
		
		long start = System.currentTimeMillis();
		generatePermutationsWithPrecedence(auxilary, actualInput, auxilarySet, verbose);
		long end = System.currentTimeMillis();
		
		System.out.println(permutationsCount + " " + (end - start) + "ms");
		
	}
	
	/**
	 * Generates permutations for the given constraints
	 * @param input input array with sequence numbers 
	 * @param actualInput Actual input sequence
	 * @param auxiliarySet set which maintains the precedence rules
	 * @param verbose verbose, representing type of output
	 */
	private static void generatePermutationsWithPrecedence(int[] input, int[] actualInput, HashSet<String> auxiliarySet, int verbose) {
		int[] inputPrime = new int[input.length];
		for(int i = 0; i < input.length; i++) {
			inputPrime[i] = input[i];
		}
		int n = input.length - 1;
		while (true) {
			visit(input, actualInput, verbose);		
			int k = n;
			while (true) {
				int j = inputPrime[k];
				int l = input[j - 1];
				if (!auxiliarySet.contains(l + " " + k)) {
					input[j - 1] = k;
					input[j] = l;
					inputPrime[k] = j - 1;
					inputPrime[l] = j;
					break;
				}
				while (j < k) {
					l = input[j + 1];
					input[j] = l;
					inputPrime[l] = j;
					j += 1;
				}
				input[k] = inputPrime[k] = k;
				k -= 1;
				if (k <= 0)
					return;
			}		
		}
	}
 
	/**
	 * procedure which calculates the permutations count also prints the permutations if needed
	 * @param auxilary auxilary sequence
	 * @param actualInput actualInput sequence 
	 * @param n 
	 * @param verbose
	 */
	private static void visit(int[] auxilary, int[] actualInput, int verbose) {
		permutationsCount++;
		if(verbose == 1) {
			mapAndPrint(auxilary, actualInput);
		}
	}

	/**
	 * Do the mapping of original numbers and print them
	 * @param auxilary auxilary sequence
	 * @param actualInput actualInput sequence 
	 */
	private static void mapAndPrint(int[] auxilary, int[] actualInput) {
		for(int i = 1; i < auxilary.length; i++) {
			System.out.print(actualInput[auxilary[i] - 1] + " ");
		}
		System.out.println();
	}

	/**
	 * Gets the index of a particular value from an array
	 * @param actualInput input sequence
	 * @param value value for the index to check for
	 * @return index value
	 */
	private static int getIndex(int[] actualInput, String value) {
		for(int i = 0; i < actualInput.length; i++) {
			if(actualInput[i] == Integer.parseInt(value)) {
				return i + 1;
			}
		}
		return -1;
	}

}

/**
 * Node representations
 * @author uxr130130
 *
 */
class Node {

	public final String name;
	public final HashSet<Edge> inEdges;
	public final HashSet<Edge> outEdges;

	public Node(String name) {
		this.name = name;
		inEdges = new HashSet<Edge>();
		outEdges = new HashSet<Edge>();
	}

	/**
	 * Addition of edge to the given node
	 * @param node
	 * @return
	 */
	public Node addEdge(Node node) {
		Edge e = new Edge(this, node);
		outEdges.add(e);
		node.inEdges.add(e);
		return this;
	}

	@Override
	public String toString() {
		return name;
	}
}

/**
 * Edge representation
 * @author uxr130130
 *
 */
class Edge {

	public final Node from;
	public final Node to;

	public Edge(Node from, Node to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public boolean equals(Object obj) {
		Edge e = (Edge) obj;
		return e.from == from && e.to == to;
	}
}
