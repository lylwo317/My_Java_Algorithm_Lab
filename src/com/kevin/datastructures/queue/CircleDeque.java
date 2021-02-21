package com.kevin.datastructures.queue;

/**
 *
 */
public class CircleDeque<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private int front;
    private int rear;

    private int size;
    private E[] elements;

    public CircleDeque() {
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
        rear = 0;
        size = 0;
    }

    private int index(int offset) {
        int index = front + offset;
        if (index < 0) {
            index = elements.length - (-index % elements.length);
        } else {
            index = index % elements.length;
        }
        return index;
    }

    public void enQueueRear(E element) {
        ensureCapacity(size + 1);
        elements[index(size)] = element;
        rear = index(size);
        size++;
    }

    public E deQueueRear() {
        E element = null;
        if (size > 0) {
            trim();
            element = elements[index(size - 1)];
            elements[index(size - 1)] = null;
            rear = index(size - 2);
            size--;
        }
        return element;
    }

    public void enQueueFront(E element) {
        ensureCapacity(size + 1);
        elements[index(-1)] = element;
        front = index(-1);
        size++;
    }

    public E deQueueFront() {
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

    //

    public E front() {
        return elements[front];
    }

    public E rear() {
        return elements[rear];
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
            rear = size;
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
            rear = size;
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("capcacity=").append(elements.length)
                .append(" size=").append(size)
                .append(" front=").append(front)
                .append(" rear=").append(rear)
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
