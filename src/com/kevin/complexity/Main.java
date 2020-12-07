package com.kevin.complexity;

public class Main {

    /**
     * 0 1 1 2 3 5 8 13 ...
     * @param args
     */

    public static void main(String[] args) {
        System.out.println(fib2(0));
        System.out.println(fib2(1));
        System.out.println(fib2(2));
        System.out.println(fib2(3));
        System.out.println(fib2(4));
        System.out.println(fib2(5));
        System.out.println(fib2(6));
        System.out.println(fib2(7));
        System.out.println(fib2(64));
    }

    /**
     * 递归
     * @param n
     * @return
     */
    public static int fib(int n) {
        if (n <= 1) {
            return n;
        }

        return fib(n - 2) + fib(n - 1);
    }


    /**
     * 非递归
     * @param n
     * @return
     */
    public static long fib2(int n) {
        if (n <= 1) {
            return n;
        }

        long sum = 0;
        long first = 0;
        long second = 1;

        for (int i = 2; i <= n; i++) {
            sum = first + second;
            first = second;
            second = sum;
        }

        return sum;
    }
}
