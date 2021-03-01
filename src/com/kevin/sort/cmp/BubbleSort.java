package com.kevin.sort.cmp;

import com.kevin.sort.Sort;

/**
 * 冒泡排序
 *
 * 最好：O(n)
 * 最坏：O(n^2)
 * 平均：O(n^2)
 *
 * @param <T>
 */
public class BubbleSort<T extends Comparable<T>> extends Sort<T> {

    /**
     * 优化前
     * 最好：O(n^2)
     * @return
     */
    public T[] sort1() {
        //T(n) = O(n) + O(n-1) + ... + O(1) = O(n^2)
        for (int end = array.length - 1; end > 0; end--) {//O(n)
            for (int begin = 1; begin <= end; begin++) {//O(n)
                if (array[begin - 1].compareTo(array[begin]) > 0) {
                    T t = array[begin - 1];
                    array[begin - 1] = array[begin];
                    array[begin] = t;
                }
            }
        }

        return array;
    }

    /**
     * 优化后
     * 最好：O(n)
     */
    @Override
    public void sort() {
        //T(n) = O(n) + O(n-1) + ... + O(1) = O(n^2)
        for (int end = array.length - 1; end > 0; end--) {//O(n)
            int sortedIndex = 1;
            for (int begin = 1; begin <= end; begin++) {//O(n)
//                if (array[begin - 1].compareTo(array[begin]) > 0) {
                if (compare(begin-1, begin) > 0) {
                    swap(begin-1, begin);
//                    T t = array[begin - 1];
//                    array[begin - 1] = array[begin];
//                    array[begin] = t;
                    sortedIndex = begin;
                }
            }
            end = sortedIndex;
        }

//        return array;
    }
}
