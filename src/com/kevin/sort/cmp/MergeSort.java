package com.kevin.sort.cmp;

import com.kevin.sort.Sort;

/**
 * 归并排序
 *
 * 最好：O(nlog n)
 * 最坏：O(nlog n)
 * 平均：O(nlog n)
 *
 * Created by: kevin
 * Date: 2021-02-27
 */
public class MergeSort<T extends Comparable<T>> extends Sort<T> {
    private T[] leftArray;

    @Override
    protected void sort() {
        leftArray = (T[]) new Comparable[array.length >> 1];
//        sort1(0, array.length);
        sort2();
    }

    /**
     * 非递归版本
     *
     *
     * 空间复杂度:
     * O(n/2)
     *
     */
    private void sort2(){
        int begin = 0;
        int mid = array.length >> 1;
        int end = array.length;
        sortArray(begin, mid);
        sortArray(mid, end);
        merge(begin, mid, end);
    }

    /**
     * 时间复杂度：
     * T(n) = O(log n + 1) * O(n) = O(nlog n) + O(n)
     * @param arrayBegin
     * @param arrayEnd
     */
    private void sortArray(int arrayBegin, int arrayEnd) {
        int arrayLen = arrayEnd - arrayBegin;//n
        for (int len = 1; len <= arrayLen; len *= 2) {//每一组的长度(1, 2, 4, 8, 16, ...)  复杂度 O(log n  + 1)

            for (int begin = arrayBegin; begin + len <= arrayEnd; begin += len * 2) {//两组两组的进行merge。第一二组合并，第三四组合并...
                // 这里遍历了所有元素。2*len + 2*len + ... = n
                // 所以这里累计的复杂度是O(n)

                //Math.min 的目的是处理 整个待排序数组为奇数的情况
                int mid = begin + len;
                int end = Math.min(begin + 2 * len, arrayEnd);
                //一共两组[begin, mid),[mid,end)

                merge(begin, mid, end);
            }
        }

        //综合以上可以得到复杂度T(n) = O(log n + 1) * O(n) = O(nlog n) + O(n)
    }

    /**
     * 递归版本
     *
     * 时间复杂度：
     * T(n) = 2 * T(n/2) + O(n) = O(nlog n) + O(n)
     *
     * 空间复杂度:
     * O(n/2 + log n) 多了个栈空间
     *
     * [begin,end)
     * @param begin
     * @param end
     */
    private void sort1(int begin, int end) {
        if (end - begin < 2) {
            return;
        }

        int mid = (begin + end) >>> 1;
        sort1(begin, mid);
        sort1(mid, end);
        merge(begin, mid, end);
    }

    /**
     * 时间复杂度：
     * O(n)
     * @param begin
     * @param mid
     * @param end
     */
    private void merge(int begin, int mid, int end) {
        int leftBegin = 0;
        int leftEnd = mid - begin;

        int rightBegin = mid;
        int rightEnd = end;

        int current = begin;

        //备份左边数组
        for (int i = 0; i < leftEnd; i++) {
            leftArray[i] = array[begin + i];
        }

        //左边数组还有元素
        while (leftBegin < leftEnd) {
            if (rightBegin < rightEnd && compare(array[rightBegin], leftArray[leftBegin]) < 0) {//右边还有元素，并且小于左边数组元素
                array[current++] = array[rightBegin++];//拷贝右边元素
            } else {
                array[current++] = leftArray[leftBegin++];//拷贝左边元素
            }
        }
    }
}
