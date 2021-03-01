package com.kevin.sort.cmp;

import com.kevin.sort.Sort;

/**
 * 插入排序
 *
 * 将无序部分的第一个值拿出来，插入到前面有序的部分。相对于选择排序，不要求是最大值，比较次数相对较少，但是移动元素次数相对较多
 *
 * 最好：O(n)
 * 最坏：O(n^2)
 * 平均：O(n^2)
 *
 * @param <T>
 */
public class InsertionSort2<T extends Comparable<T>> extends Sort<T> {

    @Override
    protected void sort() {
        for (int begin = 1; begin < array.length; begin++) {
            int cur = begin;
            T tmp = array[cur];
            int insertIndex = search(cur);

            //挪动
            while (cur > insertIndex) {
                array[cur] = array[cur - 1];
                cur--;
            }

            array[insertIndex] = tmp;
        }
    }

    /**
     * 二分搜索
     * 从index往前找。这里的index前面都是有序的数组
     * 时间复杂度：
     * T(n) = O(log n)
     * @param index
     * @return
     */
    private int search(int index){
        int begin = 0;
        int end = index;
        while (begin < end) {
            int mid = (begin + end) >>> 1;
            if (compare(index, mid) < 0) {
                end = mid;
            } else {//index >= mid
                begin = mid + 1;
            }
        }

        return begin;
    }


}
