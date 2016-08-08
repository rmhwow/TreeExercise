///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  SetTesterMain.java
// File:             BSTIterator.java, 
// Semester:         CS 367 Spring 2016
//
// Author:           Morgan O'Leary
// Email:            oleary4@wisc.edu
// CS Login:         o-leary
// Lecturer's Name:  Jim Skrentny
// Lecture Number:   3
//

//////////////////////////// 80 columns wide //////////////////////////////////
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
    	//  assign the stack      
    	stack = new Stack<BSTNode<K>>();
    	// assign the parameter to a new node        
        BSTNode<K> current = n;
        // while the node is not null       
        while(current != null){
        	//push the node onto the stack        	
        	stack.push(current);
        	//assign the node to the node's left child        	
        	current = current.getLeftChild();
        }
    }

    /**
     * Returns true iff the iterator has more items.
     *
     * @return true iff the iterator has more items
     */
    public boolean hasNext() {
    	//if the stack is empty return false        
    	if(stack.isEmpty()){
        	return false;
        }
    	// otherwise return true    	
        return true;
    }

    /**
	 * Returns the next item in the iteration.
	 *
	 * @return the next item in the iteration
	 * @throws NoSuchElementException
	 *             if the iterator has no more items
	 */
	public K next() {
		// if hasNext returns false throw a NoSuchElementException		
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		// assign the root to the element popped off the stack
		BSTNode<K> root = stack.pop();
		// assign the key to the root's key		
		K key = root.getKey();
		// if the right child isn't null, push the right child on the stack 
		// and assign the root to the right child
		if (root.getRightChild() != null) {
			stack.push(root.getRightChild());
			root = root.getRightChild();
			// while the left child is not null			
			while (root.getLeftChild() != null) {
				// push the left child onto the stack				
				stack.push(root.getLeftChild());
				// assign the root to the left child				
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
