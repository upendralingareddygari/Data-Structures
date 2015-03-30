/*
Name: Upendra
Date: 17th, Sep 2013
*/

import java.util.Scanner;
import org.omg.CORBA.Any;

/**Sep
 * LinkedList class implements a doubly-linked list.
 */
class MyLinkedList<AnyType> implements Iterable<AnyType> {
	private int theSize;
	private int modCount = 0;
	private Node<AnyType> beginMarker;
	private Node<AnyType> endMarker;

	/**
	 * Construct an empty LinkedList.
	 */
	public MyLinkedList( ) {
		doClear( );
	}

	private void clear( ) {
		doClear( );
	}

	/**
	 * Change the size of this collection to zero.
	 */
	public void doClear( ) 	{
		beginMarker = new Node<>( null, null, null );
		endMarker = new Node<>( null, beginMarker, null );
		beginMarker.next = endMarker;
		theSize = 0;
		modCount++;
	}

	/**
	 * Returns the number of items in this collection.
	 * @return the number of items in this collection.
	 */
	public int size( ) {
		return theSize;
	}

	/**
	 * Returns if the list is empty
	 */ 
	public boolean isEmpty( ) {
		return size( ) == 0;
	}

	/**
	 * Adds an item to this collection, at the end.
	 * @param x any object.
	 * @return true.
	 */
	public boolean add( AnyType x ) {
		add( size( ), x );   
		return true;         
	}

	/**
	 * Adds an item to this collection, at specified position.
	 * Items at or after that position are slid one position higher.
	 * @param x any object.
	 * @param idx position to add at.
	 * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
	 */
	public void add( int idx, AnyType x ) {
		addBefore( getNode( idx, 0, size( ) ), x );
	}

	/**
	 * Adds an item to this collection, at specified position p.
	 * Items at or after that position are slid one position higher.
	 * @param p Node to add before.
	 * @param x any object.
	 * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
	 */    
	private void addBefore( Node<AnyType> p, AnyType x ) {
		Node<AnyType> newNode = new Node<>( x, p.prev, p );
		newNode.prev.next = newNode;
		p.prev = newNode;         
		theSize++;
		modCount++;
	}   

	/**
	 * Returns the item at position idx.
	 * @param idx the index to search in.
	 * @throws IndexOutOfBoundsException if index is out of range.
	 */
	public AnyType get( int idx ) {
		return getNode( idx ).data;
	}

	/**
	 * Changes the item at position idx.
	 * @param idx the index to change.
	 * @param newVal the new value.
	 * @return the old value.
	 * @throws IndexOutOfBoundsException if index is out of range.
	 */
	public AnyType set( int idx, AnyType newVal ) {
		Node<AnyType> p = getNode( idx );
		AnyType oldVal = p.data;

		p.data = newVal;   
		return oldVal;
	}

	/**
	 * Gets the Node at position idx, which must range from 0 to size( ) - 1.
	 * @param idx index to search at.
	 * @return internal node corresponding to idx.
	 * @throws IndexOutOfBoundsException if idx is not between 0 and size( ) - 1, inclusive.
	 */
	private Node<AnyType> getNode( int idx ) {
		return getNode( idx, 0, size( ) - 1 );
	}

	/**
	 * Gets the Node at position idx, which must range from lower to upper.
	 * @param idx index to search at.
	 * @param lower lowest valid index.
	 * @param upper highest valid index.
	 * @return internal node corresponding to idx.
	 * @throws IndexOutOfBoundsException if idx is not between lower and upper, inclusive.
	 */    
	private Node<AnyType> getNode( int idx, int lower, int upper ) 	{
		Node<AnyType> p;
		if( idx < lower || idx > upper ) {
			throw new IndexOutOfBoundsException( "getNode index: " + idx + "; size: " + size( ) );
		}

		if( idx < size( ) / 2 ) {
			p = beginMarker.next;
			for( int i = 0; i < idx; i++ ) {
				p = p.next;            
			}
		} else {
			p = endMarker;
			for( int i = size( ); i > idx; i-- ) {
				p = p.prev;
			}
		} 

		return p;
	}

	/**
	 * Removes an item from this collection.
	 * @param idx the index of the object.
	 * @return the item was removed from the collection.
	 */
	public AnyType remove( int idx ) {
		return remove( getNode( idx ) );
	}

	/**
	 * Removes the object contained in Node p.
	 * @param p the Node containing the object.
	 * @return the item was removed from the collection.
	 */
	private AnyType remove( Node<AnyType> p ) {
		p.next.prev = p.prev;
		p.prev.next = p.next;
		theSize--;
		modCount++;
		return p.data;
	}

	/**
	 * Swaps the object at indexa with the object at indexb.
	 * @param indexa the index of first element
	 * @param indexb the index of second element
	 * @return true or false indicating the success/failure of the swap.
	 */
	public boolean swap(int indexa, int indexb) {
		boolean result = false;
		if((indexa >= 0 && indexa <= size()-1) && (indexb >= 0 && indexb <= size()-1)) 	{
			if(indexa != indexb) {
				AnyType temp = get(indexa);
				getNode(indexa).data = getNode(indexb).data;
				getNode(indexb).data = temp;
				result=true;
			} else {
				result = true;
			}
		} else {
			if(indexa<0 || indexa>size()-1) {
				throw new IndexOutOfBoundsException( "getNode index: " + indexa );
			} 
			if(indexb<0 || indexb>size()-1) {
				throw new IndexOutOfBoundsException( "getNode index: " + indexb );
			}
		}
		return result;
	}

	/**
	 * Reverse the linkedlist
	 */
	public void reverse() {
		int low =0;
		int high = size()-1;
		if(low !=high) {
			while (low <= (size()-1)/2) {
				swap(low++, high--);
			}
		}
	}

	/**
	 * Deleted the elements from the given index
	 *@param idx the index from which deletion starts
	 *@param noOfElements number of elements to be deleted
	 *@return if the deletion process was a success/failure
	 */
	public boolean erase(int idx, int noOfElements) {
		boolean result = false;
		if(idx >= 0 && (idx + noOfElements) <= size() ) {
			if(idx + noOfElements == size()) {
				if(idx == 0) {
					doClear();
				} else {
					Node<AnyType> current = getNode(idx).prev;
					current.next = endMarker;
					endMarker.prev =current;
					theSize -= noOfElements;
				}
			} else {
				Node<AnyType> current = getNode(idx).prev;
				Node<AnyType> elementAfterRemoval = getNode(idx+noOfElements);
				current.next = elementAfterRemoval;
				elementAfterRemoval.prev = current;
				theSize -= noOfElements;
			}
			modCount += noOfElements;
			result = true;
		} else {
			throw new IndexOutOfBoundsException();
		}
		return result;
	}

	/**
	 * Insert a list at the given index
	 *@param list list of elements to be inserted
	 *@param idx the index from which insertion starts
	 *@return if the insertion process was a success/failure
	 */
	public boolean insertList(MyLinkedList<AnyType> ListToBeInserted, int idx) {
		boolean result = false;
		if(idx >= 0 && idx <= size()) {
			if(idx == 0) {
				Node<AnyType> lastNodeFromList = ListToBeInserted.getNode(ListToBeInserted.size()-1);
				lastNodeFromList.next = beginMarker.next;
				beginMarker.next.prev = lastNodeFromList;
				beginMarker.next = ListToBeInserted.getNode(0);
				ListToBeInserted.getNode(0).prev = beginMarker;
			} else if(idx == size()) {
				endMarker.prev.next = ListToBeInserted.getNode(0);
				ListToBeInserted.getNode(0).prev = endMarker.prev;
				endMarker.prev = ListToBeInserted.getNode(ListToBeInserted.size()-1);
			} else {
				Node<AnyType> nodeBeforeIndex = getNode(idx-1);
				Node<AnyType> lastNodeFromList = ListToBeInserted.getNode(ListToBeInserted.size()-1);
				lastNodeFromList.next = nodeBeforeIndex.next;
				nodeBeforeIndex.next.prev = lastNodeFromList;
				nodeBeforeIndex.next = ListToBeInserted.getNode(0);
				ListToBeInserted.getNode(0).prev = nodeBeforeIndex;
			} 
			theSize += ListToBeInserted.size();
			modCount += ListToBeInserted.size();
			result = true;
		} else {
			throw new IndexOutOfBoundsException();
		}
		return result;
	}

	/**
	 * Returns a String representation of this collection.
	 */
	public String toString( ) {
		StringBuilder sb = new StringBuilder( "[ " );
		Node<AnyType> current = beginMarker.next;
		if(current.data != null) {
			int x = 0;
			while(current.data != null) {
				x = Integer.parseInt(current.data.toString());
				sb.append( x + " " );
				current = current.next;
			}
		}
		sb.append( "]" );
		return new String( sb );
	}

	/**
	 * Obtains an Iterator object used to traverse the collection.
	 * @return an iterator positioned prior to the first element.
	 */
	public java.util.Iterator<AnyType> iterator( ) {
		return new LinkedListIterator( );
	}

	/**
	 * This is the implementation of the LinkedListIterator.
	 * It maintains a notion of a current position and of
	 * course the implicit reference to the MyLinkedList.
	 */
	private class LinkedListIterator implements java.util.Iterator<AnyType> {
		private Node<AnyType> current = beginMarker.next;
		private int expectedModCount = modCount;
		private boolean okToRemove = false;

		public boolean hasNext( ) {
			return current != endMarker;
		}

		public AnyType next( ) {
			if( modCount != expectedModCount ) {
				throw new java.util.ConcurrentModificationException( );
			}
			if( !hasNext( ) ) {
				throw new java.util.NoSuchElementException( ); 
			}

			AnyType nextItem = current.data;
			current = current.next;
			okToRemove = true;
			return nextItem;
		}

		public void remove( ) {
			if( modCount != expectedModCount ) {
				throw new java.util.ConcurrentModificationException( );
			}
			if( !okToRemove ) {
				throw new IllegalStateException( );
			}

			MyLinkedList.this.remove( current.prev );
			expectedModCount++;
			okToRemove = false;       
		}
	}

	/**
	 * This is the doubly-linked list node.
	 */
	private static class Node<AnyType> {
		public Node( AnyType d, Node<AnyType> p, Node<AnyType> n ) {
			data = d; prev = p; next = n;
		}
		public AnyType data;
		public Node<AnyType>   prev;
		public Node<AnyType>   next;
	}
}

class TestLinkedList {
	public static void main( String [ ] args ) {
		System.out.println("Enter the numbers you want to add to linked list. Press any alphabet once you are done inputting the numbers");
		Scanner input = new Scanner(System.in);
		MyLinkedList<Integer> lst = null;
		while (input.hasNextInt()) {
			if(lst == null) {
				lst = new MyLinkedList<>();
			}
			lst.add(input.nextInt());
		}
		System.out.print("The list has the elements as follows");
		System.out.println(lst);
		String skipString = input.next();
		System.out.println("Please enter the menu options listed i.e numbers 1 for swap, 2 for reverse, 3 for deletion, 4 for insertion, 5 for exit");
		while(input.hasNextInt()) {
			int menuOption = input.nextInt();
			int index;
			if(menuOption== 1) {
				System.out.println("Enter the two indices");
				int a = input.nextInt();
				int b = input.nextInt();
				System.out.println("List before the swap operation");
				System.out.println(lst);
				if(lst.swap(a,b)) {
					System.out.println("List after the swap operation");
					System.out.println(lst);
				} else {
					System.out.println("Please try with valid inputs");
				}
			} else if(menuOption== 2) {
				System.out.println("List before the reverse operation");
				System.out.println(lst);
				lst.reverse();
				System.out.println("List after the reverse operation");
				System.out.println(lst);
			} else if(menuOption== 3) {
				System.out.println("Enter the index and the number of elements to be removed");
				index = input.nextInt();
				int noOfElements = input.nextInt();
				System.out.println("List before the swap operation");
				System.out.println(lst);
				if(lst.erase(index, noOfElements)) {
					System.out.println("List after the erase operation");
					System.out.println(lst);
				} else {
					System.out.println("Please try with valid inputs");
				}
			} else if(menuOption== 4) {
				System.out.println("Enter the elements that needs to be added. Press any alphabet when you are done");
				MyLinkedList<Integer> tempList = null;
				while (input.hasNextInt()) {
					if(tempList == null) {
						tempList = new MyLinkedList<>();
					}
					tempList.add(input.nextInt());
				}
				System.out.println("Now enter the index you want the list to be inserted");
				skipString = input.next();
				index = input.nextInt();
				System.out.println("List before the insert operation");
				System.out.println(lst);
				if(lst.insertList(tempList, index)) {
					System.out.println("List after the insert operation");
					System.out.println(lst);
				} else {
					System.out.println("Please enter a valid index");
				}
			} else if(menuOption == 5) {
				System.out.println("Thank You, Have a great day!!");
				break;
			}
			System.out.println("Please enter the menu options listed i.e numbers 1 for swap, 2 for reverse, 3 for deletion, 4 for insertion, 5 for exit");
		}
	}
}