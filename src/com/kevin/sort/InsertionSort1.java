package com.kevin.sort;

/**
 * 插入排序
 *
 * 将无序部分的第一个值拿出来，插入到前面有序的部分。相对于选择排序，不要求是最大值，比较次数相对较少，但是移动元素次数相对较多
 *
 * @param <T>
 */
public class InsertionSort1<T extends Comparable<T>> extends Sort<T> {

    /**
     * 时间复杂度
     *
     * 最好时间复杂度：O(n)
     * 最坏时间复杂度：O(n^2)
     * 平均时间复杂度：O(n^2)
     *
     * 空间复杂度：O(1)
     */
    @Override
    protected void sort() {
        for (int begin = 1; begin < array.length; begin++) {//O(n)
            int cur = begin;
            T v = array[cur];
            while (cur > 0 && compare(v, array[cur - 1]) < 0) {//最坏：O(n), 最好：O(1), 平均：O(n)
                array[cur] = array[cur - 1];
                cur--;
            }
            array[cur] = v;
        }
    }
}
