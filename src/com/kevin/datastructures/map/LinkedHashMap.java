package com.kevin.datastructures.map;


import java.util.Objects;

public class LinkedHashMap<K, V> extends HashMap<K, V> {

    private LinkedNode<K, V> first;
    private LinkedNode<K, V> last;


    @Override
    public void clear() {
        super.clear();
        first = null;
        last = null;
    }

    @Override
    public boolean containsValue(V value) {
        LinkedNode<K, V> node = first;
        while (node != null) {
            if (Objects.equals(value, node.value)) return true;
            node = node.next;
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) {
           return;
        }

        LinkedNode<K, V> node = first;
        while (node != null) {
            boolean stop = visitor.visit(node.key, node.value);
            if (stop) {
                break;
            }
            node = node.next;
        }

    }

    @Override
    protected void afterRemove(Node<K, V> wantRemoveNode, Node<K, V> actualRemoveNode) {
        LinkedNode<K, V> node1 = (LinkedNode<K, V>) wantRemoveNode;
        LinkedNode<K, V> node2 = (LinkedNode<K, V>) actualRemoveNode;

        if (node1 != node2) {//说明是删除度为2的节点，需要交换两者在链表中的位置，这样才能恢复两者原来的顺序
            swap(node1, node2);
        }

        //处理实际删除节点的前后关系
        LinkedNode<K, V> realRemoveNode = ((LinkedNode<K, V>) actualRemoveNode);
        LinkedNode<K, V> prev = realRemoveNode.prev;
        LinkedNode<K, V> next = realRemoveNode.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
    }

    private void swap(LinkedNode<K, V> node1, LinkedNode<K, V> node2) {
        //交换两个节点在链表中的位置

        //交换prev
        LinkedNode<K, V> tmp = node1.prev;
        node1.prev = node2.prev;
        node2.prev = tmp;
        if (node1.prev == null) {
            first = node1;
        } else {
            node1.prev.next = node1;
        }

        if (node2.prev == null) {
            first = node2;
        } else {
            node2.prev.next = node2;
        }

        //交换next
        tmp = node1.next;
        node1.next = node2.next;
        node2.next = tmp;
        if (node1.next == null) {
            last = node1;
        } else {
            node1.next.prev = node1;
        }

        if (node2.next == null) {
            last = node2;
        } else {
            node2.next.prev = node2;
        }
    }

    @Override
    public Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        LinkedNode<K, V> node = new LinkedNode<>(key, value, parent, getHashcode(key));

        if (first == null) {
            first = node;
            last = first;
        } else {
            last.next = node;
            node.prev = last;
            last = node;
        }
        return node;
    }

    private static class LinkedNode<K, V> extends Node<K, V> {
        LinkedNode<K, V> prev;
        LinkedNode<K, V> next;
        public LinkedNode(K key, V value, Node<K, V> parent, int hashcode) {
            super(key, value, parent, hashcode);
        }
    }
}
