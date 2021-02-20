package com.kevin.datastructures.list.circle;

import com.kevin.datastructures.list.AbstractList;

import java.util.Objects;

/**
 * 双向循环链表
 * Created by: kevin
 * Date: 2021-02-20
 */
public class CircleLinkedList<E> extends AbstractList<E> {
    private Node<E> first;
    private Node<E> last;
    private Node<E> current;

    private static class Node<E>{
        Node<E> prev;
        Node<E> next;
        E element;

        public Node(Node<E> prev, Node<E> next, E element) {
            this.prev = prev;
            this.next = next;
            this.element = element;
        }

        @Override
        public String toString() {
            return (prev != null ? prev.element : "") + "_" + element + "_" + (next != null ? next.element : "");
        }
    }

    public void reset() {
        current = first;
    }

    public E next() {
        if (current == null) {
            return null;
        }
        current = current.next;
        return current.element;
    }

    public E remove() {
        if (current == null) {
            return null;
        }

        Node<E> next = current.next;
        E element = remove(current);
        if (size != 0) {
            current = next;
        }
        return element;
    }

    @Override
    public void clear() {
        size = 0;
        first = null;
        last = null;
        current = null;
    }

    @Override
    public E get(int index) {
        checkRange(index);
        Node<E> node = findNode(index);
        return node != null ? node.element : null;
    }

    private Node<E> findNode(int index) {
        checkRange(index);
        //根据index的位置是靠前还是靠后来决定是从first开始找，还是从last开始找
        Node<E> current;
        if (index >= size / 2) {
            //from last
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        } else {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        }

        return current;
    }

    private void checkRange(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Size = " + size + ", Index = " + index);
        }
    }

    @Override
    public E set(int index, E element) {
        Node<E> node = findNode(index);
        E old;
        if (node != null) {
            old = node.element;
            node.element = element;
            return old;
        } else {
            return null;
        }
    }

    @Override
    public void add(int index, E element) {
        if (index == size) {
            Node<E> newNode = new Node<>(last, null, element);
            if (last == null) {//添加第一个节点
                last = newNode;
                first = last;
            } else {//添加到last之后
                last.next = newNode;
                last = newNode;
            }
            first.prev = last;
            newNode.next = first;
        } else {
            Node<E> current = findNode(index);

            Node<E> newNode = new Node<>(current.prev, current, element);
            if (current == first) {
                first = newNode;
            }
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public E remove(int index) {
        return remove(findNode(index));
    }

    private E remove(Node<E> node) {
        if (node == null) {
            return null;
        }

        Node<E> prev = node.prev;
        Node<E> next = node.next;

        if (first == last) {
            clear();
        }else{
            if (node == last) {
                last = prev;
            }

            if (node == first) {
                first = next;
            }

            next.prev = prev;

            prev.next = next;

            size--;
        }
        return node.element;
    }


    @Override
    public int indexOf(E element) {
        //遍历链表，逐个匹配

        Node<E> current = first;
        int index = 0;
        do {
//            if( (element == element.v) || (a != null && a.equals(b)))
            if (Objects.equals(element, current.element)) {
                return index;
            }
            current = current.next;
            index++;
        } while (current != first);

        return ELEMENT_NOT_FOUND;

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
