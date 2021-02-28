package com.kevin.sort;

import com.kevin.datastructures.Asserts;
import com.kevin.utils.Integers;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        Integer[] array = Integers.random(100, 1, 100);
//        Integer[] array = {7, 3, 5, 8, 6, 7, 4, 5};
        testSort(array,
                new BubbleSort<>(),
                new HeapSort<>(),
                new SelectionSort<>(),
                new InsertionSort1<>(),
                new InsertionSort2<>(),
                new MergeSort<>()
        );
    }

    private static void testSort(Integer[] array, Sort<Integer>... sort) {
        for (Sort<Integer> integerSort : sort) {
//            System.out.println("Class : " + integerSort.getClass().getSimpleName());
            Integer[] newArray = Integers.copy(array);
            integerSort.sort(newArray);
//            Integers.println(newArray);
            Asserts.test(Integers.isAscOrder(newArray));
        }

        Arrays.sort(sort);

        for (Sort<Integer> integerSort : sort) {
            System.out.println(integerSort);
        }
    }
}
