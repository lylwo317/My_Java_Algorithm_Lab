package com.kevin.algorithms.recursion;

/**
 * 尾部调用
 *
 * Created by: kevin
 * Date: 2021-03-07
 */
public class TailCall {
    public static void main(String[] args) {
        TailCall tailCall = new TailCall();

        int count = 10;

        System.out.println(factorial(count));
        System.out.println(factorial2(count));
        System.out.println(factorialTailCall(count, 1L));
        System.out.println(factorialIterator(count));

        System.out.println();

        System.out.println(fib(10));
        System.out.println(fibTailCall(10));
    }

    public static long factorial(long n) {
        if (n <= 1) {
            return n;
        }
        return n * factorial(n - 1);
    }

    /**
     * 尾递归
     * 其实本质就是迭代的递归写法
     * @param n
     * @param result
     * @return
     */
    public static long factorialTailCall(long n, long result) {
        if (n <= 1) {
            return result;
        }
        return factorialTailCall(n - 1, result * n);
    }

    /**
     * 等价于 factorialTailCall
     * @param n
     * @return
     */
    public static long factorialIterator(long n) {
        long result = 1;
        while (n >= 1) {
            result = result * n;
            n--;
        }
        return result;
    }

    public static long factorial2(int n) {
        if (n <= 1) {
           return 1;
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }


    public static int fib(int n){//O(2^n)  T(n) = T(n-1) + T(n-2) + O(1)
        if (n <= 1) {
            return n;
        }
        return fib(n - 1) + fib(n - 2);
    }

    public static int fibTailCall(int n) {
        return fibTailCall(n, 1, 1);
    }

    /**
     * 尾递归调用
     * 其实就是迭代的递归写法
     * @param n
     * @param first
     * @param second
     * @return
     */
    public static int fibTailCall(int n , int first, int second) {
        if (n <= 1) {
            return first;
        }
        return fibTailCall(n - 1, second, first + second);
    }

    /**
     * 等价于 fibTailCall
     * @param n
     * @return
     */
    public static int fibIterator(int n) {
        if (n <= 1) {
            return n;
        }
        int first = 1;
        int last = 1;
        for (int i = 2; i < n; i++) {
            int tmp = last;
            last = first + last;
            first = tmp;
        }
        return last;
    }
}
