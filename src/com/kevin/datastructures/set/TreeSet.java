package com.kevin.datastructures.set;

import com.kevin.datastructures.tree.BinarySearchTree;
import com.kevin.datastructures.tree.BinaryTree;
import com.kevin.datastructures.tree.RBTree;

import java.util.Comparator;

/**
 * 红黑树实现Set
 */
public class TreeSet<E> implements Set<E> {
    private BinarySearchTree<E> tree;

    public TreeSet() {
        tree = new RBTree<>();
    }

    public TreeSet(Comparator<E> comparator) {
        tree = new RBTree<>(comparator);
    }

    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {
        tree.clear();
    }

    @Override
    public boolean contains(E element) {
        return tree.contains(element);
    }

    @Override
    public void add(E element) {
        tree.add(element);//树本身就不会允许comparable相等的两个值
    }

    @Override
    public void remove(E element) {
        tree.remove(element);
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        if (visitor == null) {
            return;
        }

        tree.inorderIteration(new BinaryTree.Visitor<E>() {
            @Override
            public boolean visit(E element) {
                return visitor.visit(element);
            }
        });
    }
}
