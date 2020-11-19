package com.kevin.datastructures.list;

public abstract class AbstractList<T> implements List<T> {

    private int size;

    @Override
    public int size(){
       return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(T element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }


    /**
     * 添加元素到尾部
     * @param element
     */
    @Override
    public void add(T element) {
        add(size, element);
    }

    protected void outOfBounds(int index) {
        throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
    }

    private void rangeForCheck(int index) {
        if (index < 0 || index >= size) {
            outOfBounds(index);
        }
    }

    private void rangeForAddCheck(int index) {
        if (index < 0 || index > size) {
            outOfBounds(index);
        }
    }

//    @Override
//    public String toString() {
//        return "ArrayList{" +
//                "elements=" + Arrays.toString(elements) +
//                '}';
//    }

}
