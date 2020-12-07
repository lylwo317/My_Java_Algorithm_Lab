package com.kevin.datastructures.tree;


import com.kevin.datastructures.tree.printer.BinaryTrees;

@SuppressWarnings("unused")
public class Main {
	
	static void test1() {
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 5, 8, 11, 3, 12, 1
		};
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (Integer datum : data) {
			bst.add(datum);
		}
		
		BinaryTrees.println(bst);
	}
	

	
	public static void main(String[] args) {
		test1();
	}
}
