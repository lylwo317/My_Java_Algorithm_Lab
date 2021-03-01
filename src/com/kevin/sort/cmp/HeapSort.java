package com.kevin.sort.cmp;

import com.kevin.sort.Sort;

/**
 * 堆排序
 * 最好：O(nlog n)
 * 最坏：O(nlog n)
 * 平均：O(nlog n)
 * @param <T>
 */
public class HeapSort<T extends Comparable<T>> extends Sort<T> {

    @Override
    protected void sort() {
        heapify();//O(nlog n)
        while (heapSize > 1) {////T(n) = O(log n) + ... + O(log 2) + O(log 1) = O(log n!) = O(nlog n)
            swap(0, heapSize - 1);
            heapSize--;
            siftDown(0);
        }
    }

    private int heapSize;

    private void siftDown(int index) { //T(n) = O(log n)
        T element = array[index];
        while (index <= heapSize / 2 - 1) {
            int leftChildIndex = index * 2 + 1;
            int rightChildIndex = index * 2 + 2;
            if (rightChildIndex >= heapSize) {
                rightChildIndex = leftChildIndex;
            }

            int maxChildIndex = leftChildIndex;
            if (compare(maxChildIndex, rightChildIndex) < 0) {
                maxChildIndex = rightChildIndex;
            }

            if (compare(element, array[maxChildIndex]) < 0) {
                array[index] = array[maxChildIndex];
                index = maxChildIndex;
            } else {
                break;
            }
        }

        array[index] = element;
    }

    private void heapify() {//T(n) = O(log 1) + O(log 2) + ... + O(log (n/2)) = O(log (n/2)!) = O(n log n)
        heapSize = array.length;
        //倒数第2行开始下滤
        for (int i = (heapSize / 2) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

}
