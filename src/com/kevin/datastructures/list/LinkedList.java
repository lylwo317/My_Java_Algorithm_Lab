package com.kevin.datastructures.list;

public class LinkedList<E> extends AbstractList<E> {
    private Node<E> first;

    private static class Node<E>{
        E element;
        Node<E> next;
    }

    @Override
    public void clear() {
        size = 0;
        first = null;
    }

    @Override
    public E get(int index) {
        return node(index).element;
    }

    @Override
    public E set(int index, E element) {
        rangeForCheck(index);

        Node<E> node = node(index);
        E old = node.element;
        node.element = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        rangeForAddCheck(index);

        //找到index - 1元素，然后将该元素的next连接到新的node节点，新的node节点的next连接到原来的index节点
        Node<E> newNode = new Node<>();
        newNode.element = element;
        if (index == 0) {
            newNode.next = first;
            first = newNode;
        } else {
            Node<E> prev;
            prev = node(index - 1);//这里如果是0，会有问题，需要特殊处理
            newNode.next = prev.next;
            prev.next = newNode;
        }

        size++;
    }

    @Override
    public E remove(int index) {
        rangeForCheck(index);
        Node<E> removeNode;
        if (index == 0) {
            removeNode = first;
            first = first.next;
        } else {
            Node<E> prev = node(index - 1);
            removeNode = prev.next;
            prev.next = prev.next.next;
        }
        size--;
        return removeNode.element;
    }

    @Override
    public int indexOf(E element) {
        if (element == null) {
            Node<E> node = first;
            for (int i = 0; i < size; i++) {
                if (node.element == null) {
                    return i;
                }
                node = node.next;
            }

        } else {
            Node<E> node = first;
            for (int i = 0; i < size; i++) {
                if (element.equals(node.element)) {
                    return i;
                }
                node = node.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    /**
     * 获取index对应的node
     * @param index
     * @return
     */
    private Node<E> node(int index){
        rangeForCheck(index);
        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("size = " + size + ", [");
        Node<E> node = first;
        while (node != null) {
            s.append(node.element);
            if (node.next != null) {
                s.append(",");
            }
            node = node.next;
        }
        s.append("]");
        return s.toString();
    }
}
