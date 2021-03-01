package com.kevin.sort;

import com.kevin.utils.Integers;

import java.util.Arrays;

/**
 * 计数排序
 *
 *
 * 时间复杂度：
 * T(n) = O(4n) + O(k) = O(n + k)
 * k是整数取值范围
 *
 * 空间复杂度：O(n+k)，k是整数取值范围
 *
 * Created by: kevin
 * Date: 2021-03-01
 */
public class CountingSort extends Sort<Integer> {
    @Override
    protected void sort() {
        int max = array[0];
        int min = array[0];
        for (int i = 0; i < array.length; i++) {//O(n)
            if (max < array[i]) {
                max = array[i];
            }

            if (min > array[i]) {
                min = array[i];
            }
        }

        int[] counts = new int[max - min + 1];
        //统计
        for (int i = 0; i < array.length; i++) {//O(n)
            counts[array[i] - min]++;
        }
        //累加次数
        for (int i = 1; i < counts.length; i++) {//O(k)
            counts[i] = counts[i] + counts[i - 1];
        }

        //排序
        Integer[] newArray = new Integer[array.length];
        int index;
        for (int i = array.length - 1; i >= 0; i--) {//O(n)
//            index = counts[array[i] - min] - 1;
//            newArray[index] = array[i];
//            counts[array[i] - min] = index;

            newArray[--counts[array[i] - min]] = array[i];
        }

        // 将有序数组赋值到array
//        array = newArray;
        for (int i = 0; i < newArray.length; i++) {//O(n)
            array[i] = newArray[i];
        }
    }
}
