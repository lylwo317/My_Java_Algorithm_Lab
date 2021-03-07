package com.kevin.algorithms.recursion;


public class Fibonacci {

    public int fib(int n){
        if (n <= 1) {
            return n;
        }
        return fib(n - 1) + fib(n - 2);
    }

    public int fib1(int n){
        if (n <= 1) {
            return n;
        }
        int[] array = new int[n + 1];
        array[1] = 1;
        array[2] = 1;
        return fib1(n, array);
    }

    public int fib1(int n, int[] array) {
        if (array[n] == 0) {
            array[n] = fib1(n - 1, array) + fib1(n - 2, array);
        }
        return array[n];
    }

    public int fib2(int n) {
        int[] array = new int[2];
//        array[0] = 0;
        array[1] = 1;
        for (int i = 2; i <= n; i++) {
            array[i % 2] = array[(i - 1) % 2] + array[(i - 2) % 2];
        }
        return array[n%2];
    }

    public int fib3(int n) {
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

    public static void main(String[] args) {
        Fibonacci fibonacci = new Fibonacci();
        test(fibonacci::fib1);
    }

    private static void test(Callable callable) {
        System.out.println(callable.call(0));
        System.out.println(callable.call(1));
        System.out.println(callable.call(2));
        System.out.println(callable.call(3));
        System.out.println(callable.call(4));
        System.out.println(callable.call(5));
        System.out.println(callable.call(64));
    }

    public interface Callable {
        int call(int n);
    }
}
