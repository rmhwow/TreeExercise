import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class Main {
//	builed a tree of BSTNode's in here
	public static void main(String[]args){
	BSTreeSetTester<Integer> tree = new BSTreeSetTester<Integer>(2);
		tree.add(6);
		tree.add(9);
		tree.add(4);
		tree.add(10);
		tree.add(7);
		tree.add(5);
		tree.add(2);
//		inorder(tree.root);
		
		Iterator<Integer> newiter = tree.iterator();
//		why only print the first two? the smallest two?
		while(newiter.hasNext()){
			System.out.print(newiter.next() + " ");
		}
//		
		System.out.println(tree.subSet(2,6));
	}
	
	public static void inorder(BSTNode<Integer> node){
		if(node == null)
			return;
		inorder(node.getLeftChild());
		System.out.println(node.getKey());
		inorder(node.getRightChild());
	}
}
