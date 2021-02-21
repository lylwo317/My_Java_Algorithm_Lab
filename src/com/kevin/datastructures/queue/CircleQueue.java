package com.kevin.datastructures.queue;

/**
 * 循环队列
 * 基于循环数组
 * @param <E>
 */
public class CircleQueue<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private int front;

    private int size;
    private E[] elements;

    public CircleQueue() {
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[index(i)] = null;
        }
        front = 0;
        size = 0;
    }

    private int index(int offset) {
        int index = front + offset;
        index = index % elements.length;
        return index;
    }

    public void enQueue(E element) {
        ensureCapacity(size + 1);
        elements[index(size)] = element;
        size++;
    }

    public E deQueue() {
        E element = null;
        if (size > 0) {
            trim();
            element = elements[index(0)];
            elements[index(0)] = null;
            front = index(1);
            size--;
        }
        return element;
    }

    public E front() {
        return elements[front];
    }

    private void trim() {
        int capacity = elements.length;
        int bound = capacity >> 1;
        if (size < bound && capacity > DEFAULT_CAPACITY) {//
//            int newCapacity = capacity >> 1;
            E[] newElements = (E[]) new Object[bound];

            for (int i = 0; i < size; i++) {
                newElements[i] = elements[index(i)];
            }

            elements = newElements;

            front = 0;
        }
    }

    private void ensureCapacity(int requireCapacity) {
        int oldCapacity = elements.length;
        if (requireCapacity > oldCapacity) {//扩容
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            E[] newElements = (E[]) new Object[newCapacity];

            for (int i = 0; i < size; i++) {
                newElements[i] = elements[index(i)];
            }

            elements = newElements;

            front = 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("capcacity=").append(elements.length)
                .append(" size=").append(size)
                .append(" front=").append(front)
                .append(", [");
        for (int i = 0; i < elements.length; i++) {
            if (i != 0) {
                string.append(", ");
            }

            string.append(elements[i]);
        }
        string.append("]");
        return string.toString();
    }
}
