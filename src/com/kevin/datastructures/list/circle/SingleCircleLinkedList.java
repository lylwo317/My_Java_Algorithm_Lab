package com.kevin.datastructures.list.circle;

import com.kevin.datastructures.list.AbstractList;

/**
 * 单向循环链表
 * @param <E>
 */
public class SingleCircleLinkedList<E> extends AbstractList<E> {
    private Node<E> first;

    private static class Node<E>{
        E element;
        Node<E> next;

        @Override
        public String toString() {
            return element + "_" + (next != null ? next.element : "");
        }
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
        if (index == 0) {//如果index是0，会有问题，需要特殊处理
            if (size == 0) {
                newNode.next = newNode;
            } else {
                newNode.next = first;
            }
            first = newNode;
        } else {
            Node<E> prev;
            prev = node(index - 1);
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
            if (size == 1) {
                first = null;
            } else {
                Node<E> last = node(size - 1);
                first = first.next;
                last.next = first;
            }
        } else {
            Node<E> prev = node(index - 1);
            removeNode = prev.next;
            prev.next = removeNode.next;
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
        StringBuilder sb = new StringBuilder();
        sb.append("size = ").append(size).append(", [");

        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            if (i == 0) {

            } else {
                sb.append(", ");
            }

            sb.append(node);
            node = node.next;
        }

        sb.append("]");
        return sb.toString();
    }
}
