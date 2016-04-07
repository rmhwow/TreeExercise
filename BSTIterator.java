import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * The Iterator for Binary Search Tree (BST) that is built using Java's Stack
 * class. This iterator steps through the items BST using an INORDER traversal.
 *
 * @author CS367
 */
public class BSTIterator<K> implements Iterator<K> {

    /** Stack to track where the iterator is in the BST*/
    Stack<BSTNode<K>> stack;

    /**
     * Constructs the iterator so that it is initially at the smallest
     * value in the set. Hint: Go to farthest left node and push each node
     * onto the stack while stepping down the BST to get there.
     *
     * @param n the root node of the BST
     */
    public BSTIterator(BSTNode<K> n){
        stack = new Stack<BSTNode<K>>();
        
        BSTNode<K> current = n;
        while(current != null){
        	stack.push(current);
        	current = current.getLeftChild();
        }
  

    }

    /**
     * Returns true iff the iterator has more items.
     *
     * @return true iff the iterator has more items
     */
    public boolean hasNext() {
        if(stack.isEmpty()){
        	return false;
        }
        return true;
    }

    /**
     * Returns the next item in the iteration.
     *
     * @return the next item in the iteration
     * @throws NoSuchElementException if the iterator has no more items
     */
    public K next() {
        if(!hasNext()){
        	throw new NoSuchElementException();
        }
     
//        return stack something- can I convert like that?
      BSTNode<K> root = stack.pop();
      K key = root.getKey();
//      trying to figure out this ordering
      if(root.getRightChild() != null){
      	stack.push(root.getRightChild());
      	root = root.getRightChild();
      	while(root.getLeftChild() != null){
      		stack.push(root.getLeftChild());
      		root = root.getLeftChild();
      	}
      
      }
      return key;
      
    }

    /**
     * Not Supported
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
