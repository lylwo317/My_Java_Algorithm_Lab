package com.kevin.algorithms.sequence;

import com.kevin.datastructures.Asserts;

/**
 * Created by: kevin
 * Date: 2021-03-14
 */
public class Main {
    public static void main(String[] args) {
        Asserts.test(KMP.indexOf("Hello worlddgsgsgsdsgsgdsdsdfsfs", "DBCDEDBCEABCDEABCDFBG") == -1);
//        Asserts.test(KMP.indexOf("Hello world", "O") == -1);
    }

}
