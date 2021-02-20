package com.kevin.datastructures.list.singlelinked;

import com.kevin.datastructures.list.AbstractList;

/**
 * 虚拟头节点
 * @param <E>
 */
public class SingleLinkedList2<E> extends AbstractList<E> {
    private Node<E> first = new Node<>();

    private static class Node<E>{
        E element;
        Node<E> next;
    }

    @Override
    public void clear() {
        size = 0;
        first.next = null;
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

        Node<E> prev;
        prev = (index == 0 ? first : node(index - 1));
        newNode.next = prev.next;
        prev.next = newNode;

        size++;
    }

    @Override
    public E remove(int index) {
        rangeForCheck(index);

        Node<E> removeNode;
        Node<E> prev = index == 0 ? first : node(index - 1);
        removeNode = prev.next;
        prev.next = removeNode.next;
        size--;
        return removeNode.element;
    }

    @Override
    public int indexOf(E element) {
        Node<E> node = first.next;
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (node.element == null) {
                    return i;
                }
                node = node.next;
            }

        } else {
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
        Node<E> node = first.next;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("size = " + size + ", [");
        Node<E> node = first.next;
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
