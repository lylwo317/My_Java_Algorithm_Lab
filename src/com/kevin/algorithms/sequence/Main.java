package com.kevin.algorithms.sequence;

import com.kevin.datastructures.Asserts;

/**
 * Created by: kevin
 * Date: 2021-03-14
 */
public class Main {
    public static void main(String[] args) {
        Asserts.test(BruteForce02.indexOf("Hello world", "or") == 7);
        Asserts.test(BruteForce02.indexOf("Hello world", "O") == -1);
    }

}
