import java.util.Scanner;

class SplayNode
{
	private int _data;
	private SplayNode _left = null;
	private	SplayNode _right = null;
	private SplayNode _parent = null;

	public SplayNode(int a, SplayNode left, SplayNode right, SplayNode parent) 
	{
		_data = a;
		_left = left;
		_right = right;
		_parent = parent;
	}

	public int getData()
	{
		return _data;
	}

	public void setData(int value)
	{
		_data = value;
	}

	public SplayNode getLeft()
	{
		return _left;
	}

	public void setLeft(SplayNode value)
	{
		_left = value;
	}

	public SplayNode getRight()
	{
		return _right;
	}

	public void setRight(SplayNode value)
	{
		_right = value;
	}

	public SplayNode getParent()
	{
		return _parent;
	}

	public void setParent(SplayNode value)
	{
		_parent = value;
	}
}


class SplayTreeMain
{
	private SplayNode _root = null;

	public SplayNode add(int value)
	{
		if(_root == null)
		{
			_root = insrt(null, value);
		}
		else
		{
			insrt(_root, value);
		}
		SplayNode theChosenOne = findTheChosenOne(_root, value);
		return Splay(theChosenOne);
	}

	public boolean find(int value)
	{
		if(contains(_root, value))
		{
			SplayNode theChosenOne = findTheChosenOne(_root, value);
			Splay(theChosenOne);
			return true;
		}
		else
		{
			SplayNode theChosenOne = findThePredecessor(_root, value);
			Splay(theChosenOne);
			return false;
		}
	}

	public void remove(int value)
	{
		if(find(value))
		{
			if(_root != null)
			{	
				SplayNode topMost = _root;
				SplayNode rightTree = topMost.getRight();
				SplayNode leftTree = topMost.getLeft();
				if(rightTree!=null)
				{
					rightTree.setParent(null);
				}
				if(leftTree != null)
				{
					leftTree.setParent(null);
				}

				topMost.setLeft(null);
				topMost.setRight(null);

				if(leftTree != null)
				{
					SplayNode leftMax = findMax(leftTree);
					leftMax = Splay(leftMax);
					if(rightTree != null)
					{
						leftMax.setRight(rightTree);
						rightTree.setParent(leftMax);
					}
					_root = leftMax;
				}
				else if(rightTree != null)
				{
					rightTree.setParent(null);
					_root = rightTree;
				}
				else
				{
					if(topMost != null)
					{
						_root = null;
					}
				}

			}
		}
	}

	public int leafCount()
	{
		return numberOfLeaves(_root);
	}

	public int leafSum()
	{
		return sumOfLeaves(_root);
	}

	public String toString()
	{
		StringBuffer temp = new StringBuffer();
		temp = preOrderTraversal(_root, temp);
		return temp.toString().replaceAll("null", "");
	}

	private StringBuffer preOrderTraversal(SplayNode T, StringBuffer sb)
	{
		if(T != null)
		{
			sb = new StringBuffer();
			sb.append(T.getData()+" ");
			if(preOrderTraversal(T.getLeft(),sb) != null)
			{
				sb.append(preOrderTraversal(T.getLeft(),sb));
			}
			if(preOrderTraversal(T.getRight(),sb) != null)
			{
				sb.append(preOrderTraversal(T.getRight(),sb));
			}
			return sb;
		}
		else
		{
			return null;
		}
	}

	private int sumOfLeaves(SplayNode T)
	{
		SplayNode temp = T;
		if(temp==null)
		{
			return 0;
		}
		else
		{
			if(temp.getLeft() == null && temp.getRight() == null)
			{
				return temp.getData();
			}
			else
			{
				return sumOfLeaves(temp.getLeft()) + sumOfLeaves(temp.getRight());
			}
		}
	}

	private int numberOfLeaves(SplayNode T)
	{
		SplayNode temp = T;
		if(temp==null)
		{
			return 0;
		}
		else
		{
			if(temp.getLeft() == null && temp.getRight() == null)
			{
				return 1;
			}
			else
			{
				return numberOfLeaves(temp.getLeft()) + numberOfLeaves(temp.getRight());
			}
		}
	}

	private SplayNode findMax(SplayNode T)
	{
		if(T == null)
		{
			return null;
		}
		else
		{
			if(T.getRight() == null)
			{
				return T;
			}
			else
			{
				return findMax(T.getRight());
			}
		}
	}

	private SplayNode Splay(SplayNode theChosenOne)
	{
		SplayNode grandParent = null;
		SplayNode parent = null;

		while(theChosenOne.getParent() != null)
		{
			parent = theChosenOne.getParent();
			if(parent.getParent() != null)
			{
				grandParent = parent.getParent();
				if(parent.getRight() != null && grandParent.getRight() != null && grandParent.getRight().equals(parent) && parent.getRight().equals(theChosenOne))
				{
					theChosenOne = rotateWhenXisRightChildOfRightChild(theChosenOne);
				} 
				else if(parent.getLeft() != null && grandParent.getLeft() != null && grandParent.getLeft().equals(parent) && parent.getLeft().equals(theChosenOne))
				{
					theChosenOne = rotateWhenXisLeftChildOfLeftChild(theChosenOne);
				}
				else if(parent.getRight() != null && grandParent.getLeft() != null && grandParent.getLeft().equals(parent) && parent.getRight().equals(theChosenOne))
				{
					theChosenOne = rotateWhenXisRightChildOfLeftChild(theChosenOne);
				}
				else
				{
					theChosenOne = rotateWhenXisLeftChildOfRightChild(theChosenOne);
				}
			}
			else
			{
				if(parent.getRight() != null && parent.getRight().equals(theChosenOne))
				{
					theChosenOne = rotateWhenRightOfRoot(theChosenOne);
				}
				else
				{
					if(parent.getLeft() != null && parent.getLeft().equals(theChosenOne))
					{
						theChosenOne = rotateWhenLeftOfRoot(theChosenOne);
					}
				}
			}
		}
		_root = theChosenOne;
		return theChosenOne;
	}

	private SplayNode findTheChosenOne(SplayNode T, int value)
	{
		if(T== null)
		{
			return null;
		}
		else
		{
			if(T.getData() > value)
			{
				return findTheChosenOne(T.getLeft(), value);
			}
			else if(T.getData() < value)
			{
				return findTheChosenOne(T.getRight(), value);
			}
			else
			{
				return T;
			}
		}
	}

	private SplayNode findThePredecessor(SplayNode T, int value)
	{
		if(T==null)
		{
			return null;
		}
		else
		{
			if(T.getData() > value)
			{
				if(T.getLeft() == null)
				{
					return T;
				}
				else
				{
					return findThePredecessor(T.getLeft(), value);
				}
			}
			else
			{
				if(T.getRight() == null)
				{
					return T;
				}
				else
				{
					return findThePredecessor(T.getRight(), value);
				}
			}
		}
	}

	private SplayNode insrt(SplayNode T, int value)
	{
		if(T == null)
		{
			SplayNode temp = new SplayNode(value, null, null, null);
			return temp;
		}
		else
		{
			if(T.getData() > value)
			{
				SplayNode temp = insrt(T.getLeft(), value);
				T.setLeft(temp);
				temp.setParent(T);
			}
			else if(T.getData() < value)
			{
				SplayNode temp = insrt(T.getRight(), value);
				T.setRight(temp);
				temp.setParent(T);
			}
			else
			{
				//
			}
			return T;
		}
	}

	private boolean contains(SplayNode T, int value)
	{
		if(T == null)
		{
			return false;
		}
		else
		{
			if(T.getData() > value)
			{
				return contains(T.getLeft(), value);
			}
			else if(T.getData() < value)
			{
				return contains(T.getRight(), value);
			}
			else
			{
				return true;
			}
		}
	}

	private SplayNode rotateWhenLeftOfRoot(SplayNode X)
	{
		//SplayNode A = X.getLeft();
		SplayNode B = X.getRight();
		SplayNode P = X.getParent();
		//SplayNode C = P.getRight();

		if(P!=null)
		{
			P.setLeft(B);
		}
		if(B!=null)
		{
			B.setParent(P);
		}

		if(P!=null && X != null)
		{
			X.setParent(P.getParent());
			if(P.getParent() != null)
			{
				if(P.getParent().getLeft()!=null && P.getParent().getLeft().equals(P))
				{
					P.getParent().setLeft(X);

				}
				else
				{
					if(P.getParent().getRight()!=null && P.getParent().getRight().equals(P))
					{
						P.getParent().setRight(X);
					}
				}
			}
			P.setParent(X);
			X.setRight(P);
		}
		return X;
	}

	private SplayNode rotateWhenRightOfRoot(SplayNode X)
	{
		SplayNode P = X.getParent();
		SplayNode B = X.getLeft();
		//SplayNode C = X.getRight();

		if(P!=null)
		{
			P.setRight(B);
		}
		if(B!=null)
		{
			B.setParent(P);
		}
		if(P!=null && X != null)
		{
			X.setParent(P.getParent());
			if(P.getParent() != null)
			{
				if(P.getParent().getLeft()!=null && P.getParent().getLeft().equals(P))
				{
					P.getParent().setLeft(X);

				}
				else
				{
					if(P.getParent().getRight()!=null && P.getParent().getRight().equals(P))
					{
						P.getParent().setRight(X);
					}
				}
			}
			P.setParent(X);
			X.setLeft(P);
		}
		return X;
	}

	private SplayNode rotateWhenXisRightChildOfLeftChild(SplayNode X)
	{
		//SplayNode G = X.getParent().getParent();
		X = rotateWhenRightOfRoot(X);
		//G.setLeft(X);
		X = rotateWhenLeftOfRoot(X);
		return X;
	}

	private SplayNode rotateWhenXisLeftChildOfRightChild(SplayNode X)
	{
		//SplayNode G = X.getParent().getParent();
		X = rotateWhenLeftOfRoot(X);
		//G.setRight(X);
		X = rotateWhenRightOfRoot(X);		
		return X;
	}

	private SplayNode rotateWhenXisLeftChildOfLeftChild(SplayNode X)
	{
		SplayNode P = X.getParent();
		//SplayNode G = P.getParent();
		//SplayNode G = P.getParent();
		P = rotateWhenLeftOfRoot(P);
		X = rotateWhenLeftOfRoot(X);
		//G.setLeft(X);
		return X;
	}

	private SplayNode rotateWhenXisRightChildOfRightChild(SplayNode X)
	{
		SplayNode P = X.getParent();
		//SplayNode G = P.getParent();
		P = rotateWhenRightOfRoot(P);
		X = rotateWhenRightOfRoot(X);
		//G.setRight(X);
		return X;

	}

}

public class SplayTree {

	//static SplayNode root = null;

	public static void main(String[] args) throws Exception
	{
		System.out.println("Enter the numbers you want to add to Splay Tree. Press any alphabet once you are done inputting the numbers. The tree will be print in PreOrderTraversal after every insert");

		Scanner input = new Scanner(System.in);
		SplayTreeMain sTree=null;
		while(input.hasNextInt())
		{
			if(sTree == null)
			{
				sTree = new SplayTreeMain();
			}
			sTree.add(input.nextInt());
			System.out.println(sTree);
		}

		System.out.println("Please enter the menu options listed i.e numbers 1 for add, 2 for remove, 3 for find, 4 for leafcount, 5 for leafSum, 6 for preOrderTraversal, 7 for exit");
		String skipString = input.next();
		while(input.hasNextInt())
		{
			int menuOption = input.nextInt();

			if(menuOption== 1)
			{
				System.out.println("Enter the number");
				int a = input.nextInt();
				//int b = input.nextInt();
				System.out.println("Tree before the add operation");
				System.out.println(sTree);
				sTree.add(a);
				System.out.println("Tree after the add operation");
				System.out.println(sTree);
			}
			else if(menuOption== 2)
			{
				System.out.println("Enter the number you want to be removed");
				int a = input.nextInt();
				//int b = input.nextInt();
				System.out.println("Tree before the remove operation");
				System.out.println(sTree);
				sTree.remove(a);
				System.out.println("Tree after the remove operation");
				System.out.println(sTree);
			}
			else if(menuOption== 3)
			{
				System.out.println("Enter the number you want to be checked for its existence");
				int a = input.nextInt();
				//int b = input.nextInt();
				System.out.println("Tree before the find operation");
				System.out.println(sTree);
				sTree.find(a);
				System.out.println("Tree after the find operation");
				System.out.println(sTree);
			}
			else if(menuOption== 4)
			{
				System.out.println("Leaf count of the tree is "+sTree.leafCount());
			}
			else if(menuOption == 5)
			{
				System.out.println("Leaf sum of the tree is "+sTree.leafSum());
			}
			else if(menuOption == 6)
			{
				System.out.println(sTree);
			}
			else if(menuOption == 7)
			{
				System.out.println("Thank You, Have a great day!!");
				input.close();
				break;
			}
			System.out.println("Please enter the menu options listed i.e numbers 1 for add, 2 for remove, 3 for find, 4 for leafcount, 5 for leafSum, 6 for preOrderTraversal, 7 for exit");

		}

	}
}
