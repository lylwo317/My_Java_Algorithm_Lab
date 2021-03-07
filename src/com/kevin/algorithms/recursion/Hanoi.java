package com.kevin.algorithms.recursion;

/**
 * 汉诺塔问题
 *
 * Date: 2021-03-07
 */
public class Hanoi {

    /**
     * 将n从p1 移动到 p3，p2是中间柱子
     *
     * 时间复杂度：T(n) = 2*T(n-1) + O(1) = O(2^n)
     * 空间复杂度：O(n)
     *
     * @param n 多少个碟子
     * @param p1 当前碟子所在的柱子
     * @param p2 中间的柱子
     * @param p3 要移动到的柱子
     */
    public void hanoi(int n, String p1, String p2, String p3) {
        if (n == 1) {
            move(n, p1, p3);
            return;
        }
        hanoi(n - 1, p1, p3, p2);
        move(n, p1, p3);
        hanoi(n - 1, p2, p1, p3);
    }

    private void move(int n, String p1, String p3) {
        System.out.println("No." + n + " from " + p1 + " move to " + p3);
    }


    public static void main(String[] args) {
        Hanoi hanoi = new Hanoi();
        hanoi.hanoi(3, "A", "B", "C");
    }
}
