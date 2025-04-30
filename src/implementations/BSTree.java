package implementations;
import utilities.BSTreeADT;
import utilities.Iterator;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E>, Serializable
{

	private static final long serialVersionUID = 1L;

	private BSTreeNode<E> root;
	
	public BSTree()
	{
		root = null;
	}
	
	@Override
	public BSTreeNode<E> getRoot() throws NullPointerException 
	{
		if (root == null)
		{
			throw new NullPointerException("Root is null)");
		}
		else
		{
			return root;
		}
	}

	@Override
	public int getHeight() 
	{
		if (root == null)
		{
			return 0;
		}
		
		return getHeightHelper(root);
	}
	
	private int getHeightHelper(BSTreeNode<E> node)
	{
		if (node == null)
		{
			return 0;
		}
		
			int leftHeight = getHeightHelper(node.left);

			int rightHeight = getHeightHelper(node.right);
		
		return Math.max(leftHeight, rightHeight) + 1;
	}

	@Override
	public int size() 
	{
		return sizeHelper(root);
	}
	
	private int sizeHelper(BSTreeNode<E> node)
	{
		if (node == null)
		{
			return 0;
		}
		
		return 1 + sizeHelper(node.left)+ sizeHelper(node.right); 
	}

	@Override
	public boolean isEmpty() 
	{		
		if (root == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void clear() {
		if (root != null)
		{
			clearHelper(root);
			root = null;
		}
		
	}
	
	private void clearHelper(BSTreeNode<E> node)
	{
		if (node == null)
		{
			return;
		}
		
		clearHelper(node.left);
		clearHelper(node.right);
		
		node.left = null;
		node.right = null;
	}

	@Override
	public boolean contains(E entry) throws NullPointerException 
	{
		if (entry == null)
		{
			throw new NullPointerException("Contains entry is null");
		}
		if (root == null)
		{
			return false;
		}
		
		return containsHelper(root, entry);
	}
	
	private boolean containsHelper(BSTreeNode<E> node, E value)
	{
		if (node == null)
		{
			return false;
		}
		
		if (value.compareTo(node.value) == 0)
		{
			return true;
		}
		
		if (value.compareTo(node.value) < 0)
		{
			return containsHelper(node.left, value);
		}
		
		return containsHelper(node.right, value);
	}

	@Override
	public BSTreeNode<E> search(E entry) throws NullPointerException 
	{
		if (entry == null)
		{
			throw new NullPointerException("Search entry is null");
		}
		
		if (root == null)
		{
			return null;
		}
		
		return searchHelper(root, entry);
	}
	
	private BSTreeNode<E> searchHelper(BSTreeNode<E> node, E value)
	{
		if (node == null)
		{
			return null;
		}
		
		if (value.compareTo(node.value) == 0 )
		{
			return node;
		}
		
		if (value.compareTo(node.value) < 0 )
		{
			return searchHelper(node.left, value);
		}
		
		return searchHelper(node.right, value);
	}

	@Override
	public boolean add(E newEntry) throws NullPointerException 
	{
		if (newEntry == null)
		{
			throw new NullPointerException("New entry is null");
		}
		
		if (root == null)
		{
			root = new BSTreeNode<>(newEntry);
			return true;
		}
		
		try
		{
			return addHelper(root, newEntry);
		}
		catch (ClassCastException e)
		{
			return false;
		}
	}
	
	private boolean addHelper(BSTreeNode<E> node, E value)
	{
		if (value.compareTo(node.value) == 0)
		{
			return false;
		}
		
		if (value.compareTo(node.value) < 0 )
		{
			if (node.left == null)
			{
				node.left = new BSTreeNode<>(value);
				return true;
			}
			return addHelper(node.left, value);
		}
		
		if (node.right == null)
		{
			node.right = new BSTreeNode<>(value);
			return true;
		}
		return addHelper(node.right, value);
	}

	@Override
	public BSTreeNode<E> removeMin() 
	{
		if (root == null)
		{
			return null;
		}
		if (root.left == null)
		{
			BSTreeNode<E> oldRoot = root;
			root = root.right;
			return oldRoot;
		}
		if (root.left.left == null)
		{
			BSTreeNode<E> oldLeft = root.left;
			root.left = root.left.right;
			return oldLeft;
		}
		return removeMinHelper(root.left);
	}
	
	private BSTreeNode<E> removeMinHelper(BSTreeNode<E> node)
	{
		if (node.left.left == null)
		{
			BSTreeNode<E> oldNode = node.left;
			node.left = node.left.right;
			return oldNode;
		}

		return removeMinHelper(node.left);	
	}

	@Override
	public BSTreeNode<E> removeMax() 
	{
		if (root == null)
		{
			return null;
		}
		if (root.right == null)
		{
			BSTreeNode<E> oldRoot = root;
			root = root.left;
			return oldRoot;
		}
		if (root.right.right == null)
		{
			BSTreeNode<E> oldRight = root.right;
			root.right = root.right.left;
			return oldRight;
		}
		return removeMaxHelper(root.right);
	}
	
	private BSTreeNode<E> removeMaxHelper(BSTreeNode<E> node)
	{
		if (node.right.right == null)
		{
			BSTreeNode<E> oldNode = node.right;
			node.right = node.right.left;
			return oldNode;
		}
		return removeMaxHelper(node.right);
	}
	
	private class BSTIterator implements Iterator<E>
	{
		private Queue<E> queue;
		
		public BSTIterator(Queue<E> queue)
		{
			this.queue = queue;
		}
		@Override
		public boolean hasNext() 
		{
			return !queue.isEmpty();
		}

		@Override
		public E next() throws NoSuchElementException 
		{
			if (!hasNext())
			{
				throw new NoSuchElementException("No more elements");
			}
			return queue.poll();
		}
	}
	
	@Override
	public Iterator<E> inorderIterator() 
	{
		Queue<E> queue = new LinkedList<>();
		inorderIteratorHelper(root, queue);
		return new BSTIterator(queue);
	}
	
	private void inorderIteratorHelper(BSTreeNode<E> node, Queue<E> queue) 
	{
		if (node != null)
		{
			inorderIteratorHelper(node.left, queue);
			queue.add(node.value);
			inorderIteratorHelper(node.right, queue);
		}
	}

	@Override
	public Iterator<E> preorderIterator() 
	{
		Queue<E> queue = new LinkedList<>();
		preorderIteratorHelper(root, queue);
		return new BSTIterator(queue);
	}
	
	private void preorderIteratorHelper(BSTreeNode<E> node, Queue<E> queue)
	{
		if (node != null)
		{
			queue.add(node.value);
			preorderIteratorHelper(node.left, queue);
			preorderIteratorHelper(node.right, queue);
		}
	}

	@Override
	public Iterator<E> postorderIterator() 
	{
		Queue<E> queue = new LinkedList<>();
		postorderIteratorHelper(root, queue);
		return new BSTIterator(queue);
	}
	
	private void postorderIteratorHelper(BSTreeNode<E> node, Queue<E> queue)
	{
		if (node != null)
		{
			postorderIteratorHelper(node.left, queue);
			postorderIteratorHelper(node.right, queue);
			queue.add(node.value);
		}
	}

}
