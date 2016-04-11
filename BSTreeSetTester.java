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
//    the difference allowed in heights between the subtrees 

    /**
     * True iff tree is balanced, i.e., if rebalanceThreshold is NOT exceeded
     * by absolute value of balanceFactor in any of the tree's BSTnodes.Note if
     * rebalanceThreshold is non-positive, isBalanced must be true.
     */
    boolean isBalanced;
//balance factor is the height of the left subtree - height of the right subtree
    

    /**
     * Constructs an empty BSTreeSetTester with a given rebalanceThreshold.
     *
     * @param rbt the rebalance threshold
     */
    public BSTreeSetTester(int rbt) {
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
        if(key == null){
        	throw new IllegalArgumentException();
        }
  
    	BSTNode<K> addNode = new BSTNode<K>(key);
//    	this has to be root correct?
    	root = add(root, addNode);
//    	update the keys
    	numKeys++;
    	
//    	update the height and the balance factor!!!
    	updateHeight(root);
    	
    	if(isBalanced == false){
    		rebalance();
    	}
    	
//    	
    }
    
    private BSTNode<K> add(BSTNode<K> parent, BSTNode<K> newNode){    	
    	if(parent == null){
    		newNode.setHeight(1);
    		return newNode;
    	}
    	if(parent.getKey().equals(newNode.getKey())){
        	throw new DuplicateKeyException();
        }else if(newNode.getKey().compareTo(parent.getKey()) >0){
    		parent.setRightChild(add(parent.getRightChild(),newNode));
    	}else if(newNode.getKey().compareTo(parent.getKey()) < 0){
    		parent.setLeftChild(add(parent.getLeftChild(),newNode));
    	}
    	updateHeight(parent);
    	
    	if(rebalanceThreshold > 0){	
//    		parent or root
    		if(Math.abs(parent.getBalanceFactor()) > rebalanceThreshold){
    			isBalanced = false;
    		}
    	}
    	return parent;
    }
    
private void updateHeight(BSTNode<K> parent){
	if(parent.getLeftChild() != null && parent.getRightChild() != null){
		parent.setHeight(Math.max(parent.getLeftChild().getHeight(),
				parent.getRightChild().getHeight()) +1);
		parent.setBalanceFactor(parent.getLeftChild().getHeight() - 
				parent.getRightChild().getHeight());
	}	
	if(parent.getLeftChild() == null && parent.getRightChild() != null){
		parent.setHeight(parent.getRightChild().getHeight() + 1 );
		parent.setBalanceFactor(0 - parent.getRightChild().getHeight());
	} 
	if(parent.getRightChild() == null && parent.getLeftChild() != null){
		parent.setHeight(parent.getLeftChild().getHeight() +1);
		parent.setBalanceFactor(parent.getLeftChild().getHeight() -0);
	}
	if(parent.getRightChild() == null && parent.getLeftChild() == null){
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
        Iterator<K> newiter = iterator();
        int i = 0;
        while(newiter.hasNext() && i < numKeys){      	
        	keys[i] = newiter.next();
        	i++;
        }
        root = sortedArrayToBST(keys, 0, numKeys-1);
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
    	BSTNode<K> node = root;
    	
    	if(start == stop ){
    		return node = new BSTNode<K>(keys[start]);
    	}
    	if(start > stop){
    		return null;
    	}
    	
    	int center = (start + stop)/2;
//    	do you have to assign things to the root here?
    	node = new BSTNode<K>(keys[center]);
    	
    	node.setLeftChild(sortedArrayToBST(keys, start, center-1));

    	node.setRightChild(sortedArrayToBST(keys, center+1, stop));
    	
    	updateHeight(node);
    	
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
//        boolean found = false;
    	if(key == null){
        	throw new IllegalArgumentException();
        }
    	BSTNode<K> traverse = root;
        while(traverse != null){
        		if(traverse.getKey().compareTo(key) > 0){
        			if(traverse.getLeftChild() != null){
        				traverse = traverse.getLeftChild();
        			}else{
        				return false;
        			}
        		}
        		if(traverse.getKey().compareTo(key)< 0){
        			if(traverse.getRightChild() != null){
        				traverse = traverse.getRightChild();
        			}else{
        				return false;
        			}
        		}
     
        	if(traverse.getKey().compareTo(key) == 0){
        		return true;
        	}
        }
//        can I just leave this return out there?
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
        if(minValue == null || maxValue == null){
        	throw new IllegalArgumentException();
        }
        if(minValue.compareTo(maxValue) > 0 ){
        	throw new IllegalArgumentException();
        }
        Iterator<K> iter = iterator();

        List<K> subSetList = new ArrayList<K>();
        
        while(iter.hasNext()){
        	K currentKey = iter.next();
        	if(currentKey == minValue){
        		subSetList.add(currentKey);
        	}
//        	 not sure if this is correct! 
        	if(currentKey.compareTo(minValue) > 0 && 
        			currentKey.compareTo(maxValue) < 0){
        		subSetList.add(currentKey);
        	}
        } 
        return subSetList;
    }

    /**
     * Return an iterator for the binary search tree.
     * @return the iterator
     */
    public Iterator<K> iterator() {
        return new BSTIterator<K>(root);
    }

    /**
     * Clears the tree, i.e., removes all the keys in the tree.
     */
    public void clear() {
        root = null;
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
