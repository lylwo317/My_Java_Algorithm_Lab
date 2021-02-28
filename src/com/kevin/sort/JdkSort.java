package com.kevin.sort;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by: kevin
 * Date: 2021-02-28
 */
public class JdkSort<T extends Comparable<T>> extends Sort<T>{
    @Override
    protected void sort() {
        Arrays.sort(array);
    }
}
