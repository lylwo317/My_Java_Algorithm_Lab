package com.kevin.algorithms.recursion;

/**
 * 爬楼梯，只能用1，2步跨度上台阶。问上到n级台阶有多少种走法？
 * 思路：假设n级台阶的走法有f(n)种，那么有两种情况走到第n级台阶。
 * 一种是：还有1级台阶的距离，另一种是：还有2级台阶的距离。所以f(n)=f(n-2)+f(n-1)
 * 这样问题求解的规模就可以不断分解变小。
 *
 * [n [n-1 [n-2 [n-3] ]]]
 */
public class ClimbStairs {
    int climb1(int n) {//O(2^n) T(n) = T(n-2) + T(n-1) + O(1)
        if (n <= 2) {
            return n;
        }

        return climb1(n - 1) + climb1(n - 2);
    }

    /**
     * 时间复杂度：O(n)
     * 空间复杂度：O(2)
     * @param n
     * @return
     */
    int climb2(int n) {
        if (n <= 2) {
            return n;
        }

        int first = 1;
        int last = 2;
        for (int i = 3; i <= n; i++) {
            int tmp = last;
            last = first + last;
            first = tmp;
        }
        return last;
    }


    public static void main(String[] args) {
        ClimbStairs climbStairs = new ClimbStairs();
        test(climbStairs::climb2);
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
