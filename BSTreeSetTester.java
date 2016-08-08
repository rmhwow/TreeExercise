///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  SetTesterMain.java
// File:            BSTreeSetTester.java, 
// Semester:         CS 367 Spring 2016
//
// Author:           Morgan O'Leary
// Email:            oleary4@wisc.edu
// CS Login:         o-leary
// Lecturer's Name:  Jim Skrentny
// Lecture Number:   3
//

//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * SetTesterADT implementation using a Binary Search Tree (BST) as the data
 * structure.
 *
 * <p>The BST rebalances if a specified threshold is exceeded (explained below).
 * If rebalanceThreshold is non-positive (&lt;=0) then the BST DOES NOT
 * rebalance. It is a basic BStree. If the rebalanceThreshold is positive
 * then the BST does rebalance. It is a BSTreeB where the last B means the
 * tree is balanced.</p>
 *
 * <p>Rebalancing is triggered if the absolute value of the balancedFfactor in
 * any BSTNode is &gt;= to the rebalanceThreshold in its BSTreeSetTester.
 * Rebalancing requires the BST to be completely rebuilt.</p>
 *
 * @author CS367
 */
public class BSTreeSetTester <K extends Comparable<K>> implements SetTesterADT<K>{

    /** Root of this tree */
    BSTNode<K> root;

    /** Number of items in this tree*/
    int numKeys;

    /**
     * rebalanceThreshold &lt;= 0 DOES NOT REBALANCE (BSTree).<br>
     * rebalanceThreshold  &gt;0 rebalances the tree (BSTreeB).
      */
    int rebalanceThreshold;


    /**
     * True iff tree is balanced, i.e., if rebalanceThreshold is NOT exceeded
     * by absolute value of balanceFactor in any of the tree's BSTnodes.Note if
     * rebalanceThreshold is non-positive, isBalanced must be true.
     */
    boolean isBalanced;
    

    /**
     * Constructs an empty BSTreeSetTester with a given rebalanceThreshold.
     *
     * @param rbt the rebalance threshold
     */
    public BSTreeSetTester(int rbt) {
    	// set the rebalanceThreshold
        rebalanceThreshold = rbt;
    }

    /**
     * Add node to binary search tree. Remember to update the height and
     * balancedFfactor of each node. Also rebalance as needed based on
     * rebalanceThreshold.
     *
     * @param key the key to add into the BST
     * @throws IllegalArgumentException if the key is null
     * @throws DuplicateKeyException if the key is a duplicate
     */
    public void add(K key) throws DuplicateKeyException {
    	//if the key is null throw an Argument Exception        
    	if(key == null){
        	throw new IllegalArgumentException();
        }
    	//create a new node with the key value 
    	BSTNode<K> addNode = new BSTNode<K>(key);
    	//assign the root to the recursive call to add companion method    	
    	root = add(root, addNode);
    	//update the keys
    	numKeys++;
    	//update the height
    	updateHeight(root);
    	//if the tree isn't balanced, call rebalance method    	
    	if(isBalanced == false){
    		rebalance();
    	}
	
    }
    /**
     * Companion method to add node to binary search tree and set the isBalanced 
     * boolean depending on the height vs rebalanceThreshold
     *
     * @param parent the parent node to search part into the BST
     * @param newNode the node to add to the tree
     * @throws DuplicateKeyException if the key is a duplicate
     */
    private BSTNode<K> add(BSTNode<K> parent, BSTNode<K> newNode){    	
    	//if the parent is equal to null, return the newNode and set it's height    	
    	if(parent == null){
    		newNode.setHeight(1);
    		return newNode;
    	}
    	//if the parent key equals the newNode key throw an Exception    	
    	if(parent.getKey().equals(newNode.getKey())){
        	throw new DuplicateKeyException();
        }
    	//if the new node key is greater than the parent key   	
    	else if(newNode.getKey().compareTo(parent.getKey()) >0){
    		//set the parent's right child to the the newNode recursively 		
    		parent.setRightChild(add(parent.getRightChild(),newNode));
    	}
    	//if the new node key is less than the parent key    	
    	else if(newNode.getKey().compareTo(parent.getKey()) < 0){
    		//set the parent's left child to the the newNode recursively 		
    		parent.setLeftChild(add(parent.getLeftChild(),newNode));
    	}
    	//update the height of the tree passing in the parent node    	
    	updateHeight(parent);
    	
    	//if the rebalanceThreshold is greater than zero    	
    	if(rebalanceThreshold > 0){	
    		//if the absolute value of the balanceFactor is greater than the 
    		//threshold set isBalanced to false    		
    		if(Math.abs(parent.getBalanceFactor()) > rebalanceThreshold){
    			isBalanced = false;
    		}
    	}
    	//return node     	
    	return parent;
    }
    /**
     * private method that updates the height of the tree
     *
     * @param parent the parent node to update the height of the subtree
     */   
private void updateHeight(BSTNode<K> parent){
	// if both children are not null	
	if(parent.getLeftChild() != null && parent.getRightChild() != null){
		// set the parent node's height to the max height of the children plus 1		
		parent.setHeight(Math.max(parent.getLeftChild().getHeight(),
				parent.getRightChild().getHeight()) +1);
		// set the parent node's balance factor to the left child's height minus
		// the right child's height
		parent.setBalanceFactor(parent.getLeftChild().getHeight() - 
				parent.getRightChild().getHeight());
	}
	// if the left child is null but the right is not	
	if(parent.getLeftChild() == null && parent.getRightChild() != null){
		//set the parent height to the right child's height plus one		
		parent.setHeight(parent.getRightChild().getHeight() + 1 );
		//	set the balance factor to 0 - right child's height	
		parent.setBalanceFactor(0 - parent.getRightChild().getHeight());
	} 
	// if the right child is null and the left is not	
	if(parent.getRightChild() == null && parent.getLeftChild() != null){
		// set the parent's height to the left child's height plus one 		
		parent.setHeight(parent.getLeftChild().getHeight() +1);
		// 	set the balance factor to the difference of the left child and zero	
		parent.setBalanceFactor(parent.getLeftChild().getHeight() -0);
	}
	//if the right child and left child are null	
	if(parent.getRightChild() == null && parent.getLeftChild() == null){
		//set the balance factor to zero		
		parent.setBalanceFactor(0);
	}
	
	
}

    /**
     * Rebalances the tree by:
     * 1. Copying all keys in the BST in sorted order into an array.
     *    Hint: Use your BSTIterator.
     * 2. Rebuilding the tree from the sorted array of keys.
     */
    public void rebalance() {
        K[] keys = (K[]) new Comparable[numKeys];
        //instantiate a new iterator        
        Iterator<K> newiter = iterator();
        //set a counter variable        
        int i = 0;
        //while there are keys in the tree and i is less than the array length        
        while(newiter.hasNext() && i < numKeys){      	
        	//assign the next element in the array to the next key        	
        	keys[i] = newiter.next();
        	//increase the counter        	
        	i++;
        }
        //assign root to the node returned by the sortedArray method        
        root = sortedArrayToBST(keys, 0, numKeys-1);
        //set isbalanced to true        
        isBalanced = true;
    }

    /**
     * Recursively rebuilds a binary search tree from a sorted array.
     * Reconstruct the tree in a way similar to how binary search would explore
     * elements in the sorted array. The middle value in the sorted array will
     * become the root, the middles of the two remaining halves become the left
     * and right children of the root, and so on. This can be done recursively.
     * Remember to update the height and balanceFactor of each node.
     *
     * @param keys the sorted array of keys
     * @param start the first index of the part of the array used
     * @param stop the last index of the part of the array used
     * @return root of the new balanced binary search tree
     */
    private BSTNode<K> sortedArrayToBST(K[] keys, int start, int stop) {    	
    	//assign a node to the value of the root    	
    	BSTNode<K> node = root;
    	//create a base case. If the start index and stop are the same    	
    	if(start == stop ){
    		//return the node and assign it to a new Node with the Key from the
    		//first element in the array    		
    		return node = new BSTNode<K>(keys[start]);
    	}
    	// if the start value is bigger than the stop value return null	
    	if(start > stop){
    		return null;
    	}
    	//create a center value    	
    	int center = (start + stop)/2;
    	
    	// assign the node to a new node with the Key value from center of array    	
    	node = new BSTNode<K>(keys[center]);
    	//  set the left child to the recursive call with the start as the start
    	//  and the stop as the end of the front half of the original array  	
    	node.setLeftChild(sortedArrayToBST(keys, start, center-1));
    	//  set the right child to the recursive call with the start as the 
    	//   start of the second half of the original array and the stop as the
    	// original stop 
    	node.setRightChild(sortedArrayToBST(keys, center+1, stop));
    	// update the height    	
    	updateHeight(node);
    	//return node    	
        return node;
    }

    /**
     * Returns true iff the key is in the binary search tree.
     *
     * @param key the key to search
     * @return true if the key is in the binary search tree
     * @throws IllegalArgumentException if key is null
     */
    public boolean contains(K key) {
    	//if the key is null, throw an IllegalArgument Exception    	
    	if(key == null){
        	throw new IllegalArgumentException();
        }
    	//create a new node and assign it the value of the root    	
    	BSTNode<K> traverse = root;
    	//while the traverse node is not null    	
        while(traverse != null){
        	//   if the node's key is larger than the key parameter     	
        		if(traverse.getKey().compareTo(key) > 0){
        			// if the node's left child exists        			
        			if(traverse.getLeftChild() != null){
        				//assign the node to the left child        				
        				traverse = traverse.getLeftChild();
        			}
        			// otherwise return false        			
        			else{
        				return false;
        			}
        		}
        		//  if the key is bigger than the traverse's key       		
        		if(traverse.getKey().compareTo(key) < 0){
        			//if the right child exists        			
        			if(traverse.getRightChild() != null){
        				//assign the node to it's right child        				
        				traverse = traverse.getRightChild();
        			}	
        			// otherwise return false        			
        			else{
        				return false;
        			}
        		}
        		//the traverse key is the same value as the
        		// param key return true   
        		if(traverse.getKey().compareTo(key) == 0){
        			return true;
        		}
        }
        
        return false;
        
    }

    /**
     * Returns the sorted list of keys in the tree that are in the specified
     * range (inclusive of minValue, exclusive of maxValue). This can be done
     * recursively.
     *
     * @param minValue the minimum value of the desired range (inclusive)
     * @param maxValue the maximum value of the desired range (exclusive)
     * @return the sorted list of keys in the specified range
     * @throws IllegalArgumentException if either minValue or maxValue is
     * null, or minValue is larger than maxValue
     */
    public List<K> subSet(K minValue, K maxValue) {
    	//if either key is null throw an Illegal Argument Exception        
    	if(minValue == null || maxValue == null){
        	throw new IllegalArgumentException();
        }
    	// if the minValue key is greater than the maxValue key 
    	// throw an Illegal Argument Exception    	
        if(minValue.compareTo(maxValue) > 0 ){
        	throw new IllegalArgumentException();
        }
       
        //create a new array list to store the values        
        List<K> subSetList = new ArrayList<K>();
        // assign the root to a new node        
        BSTNode<K> find = root;
        // call the subset method        
        subSet(subSetList, find, minValue, maxValue);
        
        return subSetList;   
    }
    /**
     * Companion method that traverses the nodes and adds them to the sorted 
     * list of keys in the tree that are in the specified
     * range (inclusive of minValue, exclusive of maxValue). 
     *
     * @param minValue the minimum value of the desired range (inclusive)
     * @param maxValue the maximum value of the desired range (exclusive)
     * @param subSetList the sorted list of keys in the specified range
	 * @param parent the original node passed in and used to traverse the tree
     */
	private void subSet(List<K> subSetList,BSTNode<K> parent, K minValue, K maxValue){
		// the node is null return       
		if(parent == null){
        	 return;
         }
		// if the node's key is less than the minValue        
        if(parent.getKey().compareTo(minValue) < 0){
        	// traverse the right side of the tree       	
        	subSet(subSetList, parent.getRightChild(), minValue, maxValue);
        	return;
        }
        // if the node's key is greater than the maxValue        
        if(parent.getKey().compareTo(maxValue) >= 0){
        	//traverse the left side of the tree        	
        	subSet(subSetList, parent.getLeftChild(), minValue, maxValue );
        	return;
        }
        // if the node's key is equal to the minValue        
        if(parent.getKey().equals(minValue)){
        	//add the key to the list       	
        	subSetList.add(parent.getKey());
        	//traverse the right side of the tree        	
        	subSet(subSetList, parent.getRightChild(), minValue, maxValue);
        	return;
        }
        //if the node's key is greater than the minValue and the node is less 
        // than the max value        
        if(parent.getKey().compareTo(minValue) > 0 
        		&& parent.getKey().compareTo(maxValue) < 0 ){
        	// traverse the left child        	
        	subSet(subSetList, parent.getLeftChild(), minValue, maxValue );
        	// add the node to the list        	
        	subSetList.add(parent.getKey());
        	// traverse the right child      	
        	subSet(subSetList, parent.getRightChild(), minValue, maxValue);
        	return;
        } 
       
		
	}
    /**
     * Return an iterator for the binary search tree.
     * @return the iterator
     */
    public Iterator<K> iterator() {
    	//instantiate a new Iterator    	
        return new BSTIterator<K>(root);
    }

    /**
     * Clears the tree, i.e., removes all the keys in the tree.
     */
    public void clear() {
    	// assign the root to null     	
        root = null;
        // assign number of keys to zero        
        numKeys = 0;
    }

    /**
     * Returns the number of keys in the tree.
     *
     * @return the number of keys
     */
    public int size() {
        return numKeys;
    }

    /**
     * Displays the top maxNumLevels of the tree. DO NOT CHANGE IT!
     *
     * @param maxDisplayLevels from the top of the BST that will be displayed
     */
    public void displayTree(int maxDisplayLevels) {
        if (rebalanceThreshold > 0) {
            System.out.println("---------------------------" +
                    "BSTreeBSet Display-------------------------------");
        } else {
            System.out.println("---------------------------" +
                    "BSTreeSet Display--------------------------------");   
        }
        displayTreeHelper(root, 0, maxDisplayLevels);
    }

    private void displayTreeHelper(BSTNode<K> n, int curDepth,
                                   int maxDisplayLevels) {
        if(maxDisplayLevels <= curDepth) return;
        if (n == null)
            return;
        for (int i = 0; i < curDepth; i++) {
            System.out.print("|--");
        }
        System.out.println(n.getKey() + "[" + n.getHeight() + "]{" +
                n.getBalanceFactor() + "}");
        displayTreeHelper(n.getLeftChild(), curDepth + 1, maxDisplayLevels);
        displayTreeHelper(n.getRightChild(), curDepth + 1, maxDisplayLevels);
    }
}
