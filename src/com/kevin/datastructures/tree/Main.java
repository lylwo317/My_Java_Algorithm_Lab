package com.kevin.datastructures.tree;


import com.kevin.datastructures.Asserts;
import com.kevin.datastructures.tree.printer.BinaryTrees;
import com.sun.xml.internal.ws.spi.db.BindingInfo;

@SuppressWarnings("unused")
public class Main {
	
	static void testHeight() {
		BinarySearchTree<Integer> bst1 = new BinarySearchTree<>();
		for (int i = 0; i < 30; i++) {
			bst1.add((int) (Math.random() * 100));
		}
		BinaryTrees.println(bst1);

		System.out.println("height = " + bst1.height());
		bst1.height();
	}

	public static void testVisitor() {
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 5, 8, 11, 3, 12, 1
		};

		BinarySearchTree<Integer> bst1 = new BinarySearchTree<>();
		for (Integer datum : data) {
			bst1.add(datum);
		}

		BinaryTrees.println(bst1);

		System.out.println("preorderRecursion");
		bst1.preorderRecursion(new BinarySearchTree.Visitor<Integer>(){

			@Override
			boolean visit(Integer element) {
				System.out.print(element + "_");
				return element == 5;
			}
		});
		System.out.println();

		System.out.println("preorderIteration");
		bst1.preorderIteration(new BinarySearchTree.Visitor<Integer>() {
			@Override
			boolean visit(Integer element) {
				System.out.print(element + "_");
				return element == 5;
			}
		});
		System.out.println();

		System.out.println("inorderRecursion");
		bst1.inorderRecursion(new BinarySearchTree.Visitor<Integer>() {
			@Override
			boolean visit(Integer element) {
				System.out.print(element + "_");
				return element == 5;
			}
		});
		System.out.println();

		System.out.println("inorderIteration");
		bst1.inorderIteration(new BinarySearchTree.Visitor<Integer>() {
			@Override
			boolean visit(Integer element) {
				System.out.print(element + "_");
				return element == 5;
			}
		});
		System.out.println();

		System.out.println("postorderRecursion");
		bst1.postorderRecursion(new BinarySearchTree.Visitor<Integer>() {
			@Override
			boolean visit(Integer element) {
				System.out.print(element + "_");
				return element == 5;
			}
		});
		System.out.println();

		System.out.println("postorderIteration");
		bst1.postorderIteration(new BinarySearchTree.Visitor<Integer>() {
			@Override
			boolean visit(Integer element) {
				System.out.print(element + "_");
				return element == 5;
			}
		});
		System.out.println();
	}

	private static void testIsCompleteTree() {
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 5, 8, 11, 3, 1
		};

		BinarySearchTree<Integer> bst1 = new BinarySearchTree<>();
		for (Integer datum : data) {
			bst1.add(datum);
		}

		BinaryTrees.println(bst1);

		System.out.println("isComplete = " + bst1.isCompleteTree());
	}

	private static void testRemove() {
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 5, 8, 11, 3, 1
		};

		BinarySearchTree<Integer> bst1 = new BinarySearchTree<>();
		for (Integer datum : data) {
			bst1.add(datum);
		}

		BinaryTrees.println(bst1);

		System.out.println("remove 7");
		bst1.remove(7);//删除度为2的根节点
		BinaryTrees.println(bst1);

		System.out.println("remove 1");
		bst1.remove(1);//删除度为0的节点
		BinaryTrees.println(bst1);

		System.out.println("remove 2");
		bst1.remove(2);//删除度为1的节点
		BinaryTrees.println(bst1);

//		System.out.println("isComplete = " + bst1.isCompleteTree());
	}


	private static void testAVL() {
		Integer data[] = new Integer[] {
				12, 87, 44, 49, 95, 52, 54, 28, 93, 46, 19, 84, 88, 55, 24, 90, 45, 80
		};

		BinarySearchTree<Integer> bst1 = new AVLTree<>();
		for (Integer datum : data) {
			bst1.add(datum);
		}
		Asserts.test(bst1.size() == data.length);

//		for (Integer datum : data) {
//			bst1.remove(datum);
//		}
//		Asserts.test(bst1.size() == 0);


		bst1.remove(54);
		Asserts.test(bst1.size() == data.length - 1);

		bst1.remove(44);

		BinaryTrees.println(bst1);
	}

	private static void testRedBlackTree() {
		BinarySearchTree<Integer> rbtree = new RBTree<>();
		Integer data[] = new Integer[]{
				1, 97, 5, 67, 52, 19, 62, 84, 73, 66, 59, 37, 77, 86, 9
		};

		for (Integer datum : data) {
			rbtree.add(datum);
		}
		BinaryTrees.println(rbtree);
		rbtree.remove(86);
		BinaryTrees.println(rbtree);
		rbtree.remove(73);
		BinaryTrees.println(rbtree);
		rbtree.remove(97);
		BinaryTrees.println(rbtree);
		rbtree.remove(77);
		BinaryTrees.println(rbtree);
		rbtree.remove(84);
		BinaryTrees.println(rbtree);
		rbtree.remove(67);
		BinaryTrees.println(rbtree);
		rbtree.remove(66);
		BinaryTrees.println(rbtree);
	}

	public static void main(String[] args) {
/*
	    testHeight();
		testVisitor();
		testIsCompleteTree();
		testRemove();
		testAVL();
*/
        testRedBlackTree();
	}

}
