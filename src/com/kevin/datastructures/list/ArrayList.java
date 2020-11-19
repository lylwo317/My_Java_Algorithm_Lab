package com.kevin.datastructures.list;

import java.util.Arrays;

public class ArrayList<T> extends AbstractList<T> {
    private static final int ELEMENT_NOT_FOUND = -1;
    private int size;//已添加的属性
    private Object[] elements = new Object[0];

    private static final int DEFAULT_CAPACITY = 10;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int capacity) {
        elements = new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public T remove(int index){
        rangeForCheck(index);
        T old = (T) elements[index];
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[size - 1] = null;
        size--;
        trim();
        return old;
    }

    private void trim() {
        //30
        int oldCapacity = elements.length;
        //15
        int newCapacity = oldCapacity >> 1;

        if (size > newCapacity || oldCapacity <= DEFAULT_CAPACITY) {
            return;
        }

        Object[] newElement = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElement[i] = elements[i];
        }

        elements = newElement;

        System.out.println(oldCapacity + "缩容为" + newCapacity);
    }

    @Override
    public void add(T element){
        add(size, element);
    }

    /**
     *
     * @param element
     */
    @Override
    public void add(int index, T element) {
        //1.添加前先检查是否越界
        rangeForAddCheck(index);//不符合就抛出异常

        //2.确认容量是否满足，不然就扩容
        ensureCapacity(size + 1);

        //3.添加。如果不是最后一个元素，就进行移动
        for (int i = size - 1; i >= index ; i--) {
            elements[i + 1] = elements[i];
        }

        elements[index] = element;

        size++;
    }

    @Override
    public boolean contains(T element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    @Override
    public int indexOf(T element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elements[i])) {
                    return i;
                }
            }
        }

        return ELEMENT_NOT_FOUND;
    }

    @Override
    public T get(int index){
        rangeForCheck(index);
        return (T) elements[index];
    }

    @Override
    public T set(int index, T element){
        rangeForCheck(index);
        T old = (T) elements[index];
        elements[index] = element;
        return old;
    }

    private void ensureCapacity(int newSize) {
        Object[] oldArray = elements;
        int oldCapacity = oldArray.length;

        if (newSize > oldCapacity) {
            int newCapacity = (int) (oldCapacity * 1.5);
            elements = new Object[newCapacity];
            System.arraycopy(oldArray,0,elements,0,oldArray.length);
            System.out.println(oldCapacity + "扩容为" + newCapacity);
        }
    }
    private void rangeForCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index = " + index + ", " + "Size = " + size);
        }
    }

    private void rangeForAddCheck(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index = " + index + ", " + "Size = " + size);
        }
    }

    @Override
    public String toString() {
        return "ArrayList{" +
                "elements=" + Arrays.toString(elements) +
                '}';
    }
}
