package com.kevin.datastructures.tree;

public class BST {
    private int size;

    private static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        public Node(E element) {
            this.element = element;
        }
    }
}
