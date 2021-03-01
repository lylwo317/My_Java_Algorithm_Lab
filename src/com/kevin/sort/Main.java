package com.kevin.sort;

import com.kevin.datastructures.Asserts;
import com.kevin.sort.cmp.*;
import com.kevin.utils.Integers;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

//        Integer[] array = Integers.random(10000, 1, 100);
//        Integer[] array = {7, 3, 5, 8, 6, 7, 4, 5};
        Integer[] array = {7234, 32, 532, 8, 6, 76, 43, 5234};
        testSort(array,
//                new BubbleSort<>(),
//                new JdkSort<>(),
//                new QuickSort<>(),
//                new HeapSort<>(),
//                new SelectionSort<>(),
//                new InsertionSort1<>(),
//                new InsertionSort2<>(),
//                new MergeSort<>(),
//                new CountingSort(),
                new RadixSort()
//                new ShellSort<>()
        );
    }

    private static void testSort(Integer[] array, Sort<Integer>... sort) {
        for (Sort<Integer> integerSort : sort) {
//            System.out.println("Class : " + integerSort.getClass().getSimpleName());
            Integer[] newArray = Integers.copy(array);
            integerSort.sort(newArray);
            Integers.println(newArray);
            Asserts.test(Integers.isAscOrder(newArray));
        }

        Arrays.sort(sort);

        for (Sort<Integer> integerSort : sort) {
            System.out.println(integerSort);
        }
    }
}
