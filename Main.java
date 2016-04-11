import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class Main {
//	builed a tree of BSTNode's in here
	public static void main(String[]args){
	BSTreeSetTester<Integer> tree = new BSTreeSetTester<Integer>(2);
		tree.add(1);
		tree.add(2);
		tree.add(3);
		tree.add(7);
		tree.add(8);
		tree.add(4);
		tree.add(5);
		

		Iterator<Integer> newiter = tree.iterator();
////		why only print the first two? the smallest two?

		while(newiter.hasNext()){
			System.out.println(newiter.next());
		}
		
	
	

}
}
