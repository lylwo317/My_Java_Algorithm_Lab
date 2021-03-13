package com.kevin.datastructures.map;

import java.util.Comparator;

/**
 * 跳表
 * 复杂度： O(log n)
 * Created by: kevin
 * Date: 2021-03-13
 */
public class SkipListMap<K, V> {

    private Comparator<K> comparator;
    private static final int MAX_LEVEL = 32;
    private static final double P = 0.25;

    /**
     * 有效层数
     */
    private int level;

    private Node<K, V> first;

    @SuppressWarnings("unchecked")
    public SkipListMap(Comparator<K> comparator) {
        this.comparator = comparator;
        first = new Node<>(null, null, MAX_LEVEL);
        first.nexts = new Node[MAX_LEVEL];
    }

    public SkipListMap() {
        this(null);
    }

    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int randomLevel() {
        int level = 1;
        while (Math.random() < P && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    public V put(K key, V value) {
        checkNull(key);
        Node<K, V> node = first;
        Node<K, V>[] preNodes = new Node[level];
        for (int i = level - 1; i >= 0; i--) {
            int cmp = -1; // <
            while (node.nexts[i] != null && (cmp = compare(key, node.nexts[i].key)) > 0) {
                node = node.nexts[i];
            }
            if (cmp == 0) {
                V old = node.nexts[i].value;
                node.nexts[i].value = value;
                return old;
            }
            preNodes[i] = node;
        }

        int newLevel = randomLevel();
        Node<K, V> newNode = new Node<>(key, value, newLevel);
        for (int i = 0; i < newLevel; i++) {
            if (i < level) {
                newNode.nexts[i] = preNodes[i].nexts[i];
                preNodes[i].nexts[i] = newNode;
            } else {
                first.nexts[i] = newNode;
            }
        }
        size++;

        level = Math.max(level, newLevel);

        return null;
    }

    public V remove(K key) {
        checkNull(key);
        Node<K, V> node = first;
        Node<K, V>[] preNodes = new Node[level];
        boolean isExist = false;
        for (int i = level - 1; i >= 0; i--) {
            int cmp = -1; // <
            while (node.nexts[i] != null && (cmp = compare(key, node.nexts[i].key)) > 0) {
                node = node.nexts[i];
            }
            if (cmp == 0) {
                isExist = true;
                preNodes[i] = node;
            }
        }

        if (!isExist) {
            return null;
        }

        Node<K, V> removeNode = node.nexts[0];
        for (int i = 0; i < removeNode.nexts.length; i++) {
            preNodes[i].nexts[i] = removeNode.nexts[i];
        }

        for (int i = level - 1; i >= 0; i--) {
            if (first.nexts[i] == null) {
                level--;
            } else {
                break;
            }
        }

        size--;
        return removeNode.value;
    }

    public V get(K key) {
        checkNull(key);
        Node<K, V> node = first;
        for (int i = level - 1; i >= 0; i--) {
            int cmp = -1; // <
            while (node.nexts[i] != null && (cmp = compare(key, node.nexts[i].key)) > 0) {
                node = node.nexts[i];
            }
            if (cmp == 0) {
                return node.nexts[i].value;
            }
        }
        return null;
    }


    private void checkNull(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null");
        }
    }

    @SuppressWarnings("unchecked")
    public int compare(K k1, K k2) {
        return comparator != null ? comparator.compare(k1, k2) : ((Comparable<K>) k1).compareTo(k2);
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V>[] nexts;

        public Node(K key, V value, int level) {
            this.key = key;
            this.value = value;
            nexts = new Node[level];
        }

        @Override
        public String toString() {
            return key + ":" + value + "_" + nexts.length;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("一共").append(level).append("层").append("\n");
        for (int i = level - 1; i >= 0; i--) {
            Node<K, V> node = first;
            while (node.nexts[i] != null) {
                sb.append(node.nexts[i]);
                sb.append(" ");
                node = node.nexts[i];
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
