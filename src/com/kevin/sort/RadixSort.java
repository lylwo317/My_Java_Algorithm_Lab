package com.kevin.sort;

/**
 * 基数排序
 *
 * 时间复杂度
 * O(n) = O(d * (n+k))
 * d是最大位数
 * k是进制
 *
 * 空间复杂度
 * O(n) = O(n+k)
 * k是进制
 *
 * Created by: kevin
 * Date: 2021-03-01
 */
public class RadixSort extends Sort<Integer> {
    @Override
    protected void sort() {

        //千位数:4578 / 1000 % 10 = 4
        //百位数:4578 / 100 % 10  = 5
        //十位数:4578 / 10 % 10   = 7
        //个位数:4578 / 1 % 10    = 8


        int max = array[0];
        for (int i = 0; i < array.length; i++) {//O(n)
            if (max < array[i]) {
                max = array[i];
            }
        }


        for (int divider = 1; divider <= max; divider*=10) {//O(d * (n+k))
            countingSort(divider);
        }

    }

    //O(n+k)
    protected void countingSort(int base) {
        int[] counts = new int[10];
        //统计
        for (int i = 0; i < array.length; i++) {//O(n)
            counts[array[i] / base % 10]++;
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

            newArray[--counts[array[i] / base % 10]] = array[i];
        }

        // 将有序数组赋值到array
//        array = newArray;
        for (int i = 0; i < newArray.length; i++) {//O(n)
            array[i] = newArray[i];
        }
    }
}
