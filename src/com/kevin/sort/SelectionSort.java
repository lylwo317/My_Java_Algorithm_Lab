package com.kevin.sort;

/**
 * 选择排序
 * [0,end]内找出最大值，然后将最大值与end交换，end--
 *
 * 最好：O(n^2)
 * 最坏：O(n^2)
 * 平均：O(n^2)
 * 一样的原因是因为比较次数不论数组的顺序如何都一样
 *
 * @param <T>
 */
public class SelectionSort<T extends Comparable<T>> extends Sort<T>{
    @Override
    protected void sort() {
/*
        //不稳定的排序
        for (int end = array.length - 1; end > 0 ; end--) {
            int max = 0;
            for (int begin = 1; begin <= end; begin++) {
                if (compare(max, begin) < 0) {
                    max = begin;
                }
            }
            swap(max, end);
        }
*/

        // 最坏： T(n) = n + (n-1) + (n-2) + (n-3) + ... + 1 = (n+1)n/2 = O(n^2)
        // 最好： T(n) = n + (n-1) + (n-2) + (n-3) + ... + 1 = (n+1)n/2 = O(n^2)
        // 平均： T(n) = n + (n-1) + (n-2) + (n-3) + ... + 1 = (n+1)n/2 = O(n^2)
        //稳定的排序
        for (int end = array.length - 1; end > 0 ; end--) {
            int max = end;
            for (int begin = end - 1; begin >= 0; begin--) {
                if (compare(max, begin) < 0) {
                    max = begin;
                }
            }
            swap(max, end);
        }
    }
}
