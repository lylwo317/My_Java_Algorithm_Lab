package com.kevin.datastructures.heap;

import com.kevin.datastructures.tree.printer.BinaryTreeInfo;

import java.util.*;

/**
 * Created by: kevin
 * Date: 2021-02-15
 */
public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo {
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap(Collection<E> elements, Comparator<E> comparator) {
        super(comparator);
        if (elements == null || elements.size() == 0) {
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            size = elements.size();
            int capacity = Math.max(elements.size(), DEFAULT_CAPACITY);
            this.elements = (E[]) new Object[capacity];
            int i = 0;
            for (E element : elements) {
                this.elements[i++] = element;
            }
            heapify();
        }
    }


    @SuppressWarnings("unchecked")
    public BinaryHeap(E[] elements, Comparator<E> comparator) {
        super(comparator);
        if (elements == null || elements.length == 0) {
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            size = elements.length;
            int capacity = Math.max(elements.length, DEFAULT_CAPACITY);
            this.elements = (E[]) new Object[capacity];
            for (int i = 0; i < elements.length; i++) {
                this.elements[i] = elements[i];
            }
            heapify();
        }
    }

    public BinaryHeap(E[] elements) {
        this(elements, null);
    }

    public BinaryHeap(Comparator<E> comparator) {
        super(comparator);
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public BinaryHeap() {
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void addAll(Collection<E> collection) {
        if (collection == null) {
            return;
        }

        for (E e : collection) {
            add(e);
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    private void ensureCapacity(int newSize) {
        E[] oldArray = elements;
        int oldCapacity = oldArray.length;

        if (newSize > oldCapacity) {
            int newCapacity = (int) (oldCapacity * 1.5);
            elements = (E[]) new Object[newCapacity];
            System.arraycopy(oldArray,0,elements,0,oldArray.length);
            //System.out.println(oldCapacity + "扩容为" + newCapacity);
        }
    }

    private void trim() {
        //30
        int oldCapacity = elements.length;
        //15
        int newCapacity = oldCapacity >> 1;

        if (size > newCapacity || oldCapacity <= DEFAULT_CAPACITY) {
            return;
        }

        E[] newElement = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElement[i] = elements[i];
        }

        elements = newElement;

//        System.out.println(oldCapacity + "缩容为" + newCapacity);
    }

    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }

    /**
     * 删除堆顶元素
     * 拿到最后一个
     * 复杂度：O(log n)
     * @return
     */
    @Override
    public E remove() {
        emptyCheck();
        int lastIndex = size - 1;
        E remove = elements[0];
        elements[0] = elements[lastIndex];
        elements[lastIndex] = null;
        size--;
        siftDown(0);
        trim();
        return remove;
    }


    /**
     * 复杂度：O(log n)
     * @param element
     * @return
     */
    @Override
    public E replace(E element) {
        elementNotNullCheck(element);
        E old = null;
        if (size == 0) {
            elements[0] = element;
            size++;
        } else {
            old = elements[0];
            elements[0] = element;
            siftDown(0);
        }
        return old;
    }

    /**
     * 复杂度：O(log n)
     * @param element
     */
    @Override
    public void add(E element) {
        elementNotNullCheck(element);
        ensureCapacity(size + 1);
        elements[size++] = element;
        siftUp(size - 1);
    }

    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }

    private void emptyCheck() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }
    }

    /**
     * 批量建堆
     *
     * 自下而上的siftDown
     * 复杂度：所有节点的高度之和
     * 2n − log(n+1) = O(n)
     *
     * 自上而下的siftUp
     * 复杂度：所有节点的深度之和
     * 仅仅是底层叶子节点就达到了O(nlog n)了
     * O(nlog n)
     */
    private void heapify() {
        for (int i = (size - 1 - 1) >> 1 ; i >= 0; i--) {
            siftDown(i);
        }
    }

    //往上筛选
    private void siftUp(int index) {
        E element = elements[index];
        while (index > 0) {
            int parentIndex = (index - 1) >> 1;
            E parent = elements[parentIndex];
            if (compare(element, parent) <= 0) {
                break;
            }
            elements[index] = parent;
            index = parentIndex;
        }
        elements[index] = element;
    }

    private void siftDown(int index) {
        E element = elements[index];

        //优化保证index是非叶子节点可以节省不必要的判断。只要算出最后一个节点的父节点。index大于这个父节点都是叶子节点
        int lastIndexParent = (size >> 1) - 1;
        while (index <= lastIndexParent) {
            int leftChild = (index << 1) + 1;
            int rightChild = leftChild + 1;

            int swapIndex = 0;
            if (rightChild < size && compare(elements[leftChild], elements[rightChild]) < 0) {
                swapIndex = rightChild;
            } else {
                swapIndex = leftChild;
            }

            if (compare(element, elements[swapIndex]) < 0) {
                elements[index] = elements[swapIndex];
                index = swapIndex;
            } else {
                break;
            }
        }
        elements[index] = element;

        //实现一：
/*
        while (index < size) {
            int leftChild = (index << 1) + 1;
            if (leftChild > size - 1) {//index节点是叶子节点
                break;
            }
            int rightChild = Math.min(leftChild + 1, size - 1);

            int swapIndex = 0;
            if (compare(elements[leftChild], elements[rightChild]) < 0) {
                //left < right
//                elements[leftChild] =
                swapIndex = rightChild;
            } else {
                //
                swapIndex = leftChild;
            }

            if (compare(element, elements[swapIndex]) < 0) {
                elements[index] = elements[swapIndex];
                index = swapIndex;
            } else {
                break;
            }
        }
        elements[index] = element;
*/
    }

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        int index = ((int)node << 1) + 1;
        return index >= size ? null : index;
    }

    @Override
    public Object right(Object node) {
        int index = ((int)node << 1) + 2;
        return index >= size ? null : index;
    }

    @Override
    public Object string(Object node) {
        E element = elements[(int) node];
        if (element == null) {
            return "Empty";
        } else {
            return element;
        }
    }
}
