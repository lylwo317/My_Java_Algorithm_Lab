package com.kevin.sort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 希尔排序
 *
 * 时间复杂度
 *
 * 最好：n
 * 最坏：依赖 stepSequence。shellStepSequence(O(n^2))
 * 平均：n*log(n)^2 or n^(4/3)
 *
 * 空间复杂度
 * O(1)
 *
 * Created by: kevin
 * Date: 2021-02-28
 */
public class ShellSort<T extends Comparable<T>> extends Sort<T> {

    @Override
    protected void sort() {
        List<Integer> integers = shellStepSequence();
//        List<Integer> integers = sedgewickStepSequence();
        for (Integer step : integers) {
            sort(step);
        }
    }

    private void sort(int step) {
        for (int col = 0; col < step; col++) {//遍历每一列
            //col , col + step , col + 2*step
            // 1, 2, 3
            for (int begin = col + step; begin < array.length; begin += step) {//对列进行插入排序

                //search
                T element = array[begin];

                int cur = begin;
                while (cur > col && (compare(array[cur - step], element) > 0)) {
                    array[cur] = array[cur - step];
                    cur-=step;
                }

                //insert
                array[cur] = element;
            }
        }
    }

    private List<Integer> shellStepSequence() {
        List<Integer> stepSequence = new ArrayList<>();
        int step = array.length;
        while ((step >>= 1) > 0) {//不断除以2
            stepSequence.add(step);
        }
        return stepSequence;
    }

    private List<Integer> sedgewickStepSequence() {
        List<Integer> stepSequence = new LinkedList<>();
        int k = 0, step = 0;
        while (true) {
            if (k % 2 == 0) {
                int pow = (int) Math.pow(2, k >> 1);
                step = 1 + 9 * (pow * pow - pow);
            } else {
                int pow1 = (int) Math.pow(2, (k - 1) >> 1);
                int pow2 = (int) Math.pow(2, (k + 1) >> 1);
                step = 1 + 8 * pow1 * pow2 - 6 * pow2;
            }
            if (step >= array.length) break;
            stepSequence.add(0, step);
            k++;
        }
        return stepSequence;
    }
}
