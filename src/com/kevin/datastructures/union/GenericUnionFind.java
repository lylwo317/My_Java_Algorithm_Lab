package com.kevin.datastructures.union;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by: kevin
 * Date: 2021-03-01
 */
public class GenericUnionFind<V> {
    private static class Node<V> {
        V value;
        Node<V> parent = this;
        int rank = 1;

        public Node(V value) {
            this.value = value;
        }
    }

    private final Map<V, Node<V>> nodeMap = new HashMap<>();

    public void makeSet(V v) {
        if (nodeMap.containsKey(v)) {
            return;
        }
        nodeMap.put(v, new Node<>(v));
    }

    private Node<V> findRootNode(V v) {
        Node<V> node = nodeMap.get(v);
        if (node == null) {
            return null;
        }
        while (!Objects.equals(node.value, node.parent.value)) {//不是根节点
            node.parent = node.parent.parent;
            node = node.parent;
        }
        return node;//根节点
    }

    /*
     * 父节点就是根节点
     */
    public V findRootValue(V v) {
        Node<V> rootNode = findRootNode(v);
        return rootNode == null ? null : rootNode.value;
    }

    public void union(V v1, V v2) {
        Node<V> parent1 = findRootNode(v1);
        Node<V> parent2 = findRootNode(v2);
        if (parent1 == null || parent2 == null) {
            return;
        }

        if (Objects.equals(parent1, parent2)) {
            return;
        }

        if (parent1.rank > parent2.rank) {
            parent2.parent = parent1;
        } else if (parent1.rank < parent2.rank) {
            parent1.parent = parent2;
        } else {//ranks ==
            parent1.parent = parent2;
            parent2.rank += 1;
        }
    }

    public boolean isSame(V v1, V v2) {
        return Objects.equals(findRootNode(v1), findRootNode(v2));
    }
}
