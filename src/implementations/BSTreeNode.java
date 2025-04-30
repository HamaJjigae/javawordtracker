package implementations;
import java.io.Serializable;

public class BSTreeNode<E extends Comparable<? super E>> implements Serializable 
{
	private static final long serialVersionUID = 1L;
	E value;
	BSTreeNode<E> left, right;
	
	public BSTreeNode(E value)
	{
		this.value = value;
		left = right = null;
	}

	public E getElement() 
	{
		return value;
	}
}
