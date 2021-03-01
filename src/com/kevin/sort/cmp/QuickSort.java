package com.kevin.sort.cmp;

import com.kevin.sort.Sort;

/**
 * 快速排序
 *
 * 时间复杂度
 *
 * 最好：O(nlog n)
 * 最坏：O(n^2)
 * 平均：O(nlog n)
 *
 *
 * 空间复杂度
 *
 * O(n) = O(log n)
 *
 * Created by: kevin
 * Date: 2021-02-28
 */
public class QuickSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    protected void sort() {
//        quickSort1(0, array.length);
        quickSort2();
//        quickSort3(0, array.length - 1);
    }

    /**
     * 非递归版本
     */
    private void quickSort2() {
        if (array.length <= 1) {
            return;
        }

        int[] stack = new int[array.length];
        int top = -1;
        stack[++top] = array.length;
        stack[++top] = 0;

        while (top >= 0) {
            int begin = stack[top--];
            int end = stack[top--];
            int mid = pivotIndex(begin, end);
            if (mid + 1 < end) {
                stack[++top] = end;
                stack[++top] = mid + 1;
            }
            if (begin < mid) {
                stack[++top] = mid;
                stack[++top] = begin;
            }
        }
/*
        Deque<Integer> stack = new LinkedList<>(); // 用栈模拟递归的方法调用栈
        stack.push(array.length);//end
        stack.push(0);//start


        while (!stack.isEmpty()) {
            int begin = stack.pop();
            int end = stack.pop();
            int mid = pivotIndex(begin, end);
            if (mid + 1 < end) {
                stack.push(end);
                stack.push(mid + 1);
            }
            if (begin < mid) {
                stack.push(mid);
                stack.push(begin);
            }
        }
*/
    }

    /**
     * 递归版本
     *
     * 平均
     *
     * 如果轴点比较中间
     * T(n) = 2 * T(n/2) + O(n) = O(nlog n) + O(n)
     *
     * 最坏时间复杂度
     * 如果轴点在的位置在最右边，类似选择排序
     * T(n) = T(n-1) + O(n) = O(n^2)
     *      = O(n-1) + O(n-2) + ... + O(1) = O(n^2)
     *
     * @param begin
     * @param end
     */
    private void quickSort1(int begin, int end) {
        if (end - begin < 2) {//至少有两个才需要排序
           return;
        }

        int mid = pivotIndex(begin, end);//先执行，然后再分成两部分执行

        quickSort1(begin, mid);
        quickSort1(mid + 1, end);
    }

    /**
     * O(n)
     * @param begin
     * @param end
     * @return
     */
    private int pivotIndex(int begin, int end) {
        swap(begin, begin + (int)(Math.random() * (end - begin)));
        T pivot = array[begin];
        end--;

        while (begin < end) {//
            while (begin < end) {//右边
                if (compare(array[end], pivot) > 0) {//注意这里等于也要移动，不然轴点数据就不够均匀
                    end--;
                } else {//>=
                    //移动到左边
                    array[begin++] = array[end];
                    break;
                }
            }

            while (begin < end) {//左边
                if (compare(array[begin], pivot) < 0) {
                    begin++;
                } else {//>=
                    //移动到右边
                    array[end--] = array[begin];
                    break;
                }
            }
        }

        //begin == end
        array[begin] = pivot;
        return begin;
    }
}
