package com.kevin.sort;

public class BubbleSort<T extends Comparable<T>> extends Sort<T>{

    public T[] sort1() {
        for (int end = array.length - 1; end > 0; end--) {
            for (int begin = 1; begin <= end; begin++) {
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
     * 优化后的
     */
    @Override
    public void sort() {
        for (int end = array.length - 1; end > 0; end--) {
            int sortedIndex = 1;
            for (int begin = 1; begin <= end; begin++) {
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
