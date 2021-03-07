package com.kevin.algorithms.recursion;


/**
 * 斐波那契数列
 *
 * 0 1 1 2 3 5 8 ...
 */
public class Fibonacci {

    public int fib1(int n){//O(2^n)  T(n) = T(n-1) + T(n-2) + O(1)
        if (n <= 1) {
            return n;
        }
        return fib1(n - 1) + fib1(n - 2);
    }

    public int fib2(int n){
        if (n <= 1) {
            return n;
        }
        int[] array = new int[n + 1];
        array[1] = 1;
        array[2] = 1;
        return fib2(n, array);
    }

    /**
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * @param n
     * @return
     */
    public int fib2(int n, int[] array) {
        if (array[n] == 0) {
            array[n] = fib2(n - 1, array) + fib2(n - 2, array);
        }
        return array[n];
    }

    /**
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * @param n
     * @return
     */
    public int fib3(int n) {
        int[] array = new int[2];
//        array[0] = 0;
        array[1] = 1;
        for (int i = 2; i <= n; i++) {
            array[i % 2] = array[(i - 1) % 2] + array[(i - 2) % 2];
        }
        return array[n%2];
    }

    /**
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * @param n
     * @return
     */
    public int fib4(int n) {
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
        test(fibonacci::fib2);
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
