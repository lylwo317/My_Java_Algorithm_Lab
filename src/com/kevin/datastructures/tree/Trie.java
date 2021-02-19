package com.kevin.datastructures.tree;

import com.kevin.datastructures.map.HashMap;

/**
 * 前缀树
 * Created by: kevin
 * Date: 2021-02-18
 */
public class Trie<V> {
    private static class Node<E>{
        Node<E> parent;
        HashMap<Character, Node<E>> children;
        E value = null;
        boolean isWord = false;

        public Node(Node<E> parent) {
            this.parent = parent;
        }
    }

    private int size = 0;
    private Node<V> root = null;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        root = null;
    }

    public boolean startWith(String prefix) {
        Node<V> node = findNode(prefix);
        return node != null;
    }

    public V get(String key) {
        Node<V> node = findNode(key);
        if (node == null) {
            return null;
        } else {
            return node.value;
        }
    }

    private Node<V> findNode(String key) {
        if (root == null || key == null) {
            return null;
        }

        Node<V> current = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            Node<V> tmp = current.children != null ? current.children.get(c) : null;
            if (tmp == null) {
                return null;
            }
            current = tmp;
        }

        return current;
    }

    public boolean contains(String key) {
        keyCheck(key);
        Node<V> node = findNode(key);
        return node != null && node.isWord;
    }

    private void keyCheck(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("key must not be empty");
        }
    }

    public V add(String key, V value) {
        keyCheck(key);

        if (root == null) {
            root = new Node<>(null);
        }

        //find and create
        Node<V> node = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            boolean childrenIsNull = node.children == null;
            Node<V> children = childrenIsNull ? null : node.children.get(c);
            if (children == null) {
                children = new Node<>(node);
                if (childrenIsNull) {
                    node.children = new HashMap<>();
                }
                node.children.put(c, children);
            }
            node = children;
        }

        V oldValue = node.value;
        //替换value
        node.value = value;
        if (!node.isWord) {
            node.isWord = true;
            size++;
        }
        return oldValue;
    }

    public V remove(String key) {
        keyCheck(key);

        Node<V> node = findNode(key);
        V oldValue = null;
        if (node != null && node.isWord) {//开始一个一个的删除
            node.isWord = false;
            oldValue = node.value;
            node.value = null;
            size--;
            for (int i = key.length() - 1; i >= 0; i--) {
                if ((node.children == null || node.children.isEmpty()) && !node.isWord) {//说明这不是其它单词的前缀
                    node.parent.children.remove(key.charAt(i));
                    node = node.parent;
                } else {
                    break;
                }
            }
        }
        return oldValue;
    }
}
