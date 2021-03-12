package com.kevin.algorithms.dp;

/**
 * 0-1背包问题
 * 主要关心两个点。在同样容量的情况下，一件物品装进去的价值与不装进去的哪个价值大？
 *
 * 状态定义：
 * dp[i,j] i表示前i个物品，j表示最大容量。表示在最大容量j的限制下，装前i件物品所得到的最大价值
 *
 * 初始状态：
 * dp[0,j] = 0,没有物品可装，价值为0； dp[i,0] = 0, 没有容量可装, 价值为0
 *
 * 状态转移方程：
 * dp[i,j] = max( dp[i - 1, j], value[i] + dp[i - 1][j - weights[i]] )
 * //dp[i-1, j] 在最大容量值j下， 只装前面i-1个物品的最大价值
 * //dp[i][j - weights[i]] + value[i] 最大容量值j - weights[i]下，看加上物品i的最大价值
 * //这里其实隐含了以下两种情况:
 * 1. 前i-1件物品并没有达到最大容量j，剩余容量足够放下i物品。则dp[i - 1, j] < value[i] + dp[i - 1][j - weights[i]]
 * 2. 前i-1件物品放置完成后（只是说动作完成，但是不表示每一件都放进去了，可能有一些没放进去），
 * 已经没有足够的空间放置i，导致需要取出放置的物品来腾出空间（weights[i]）放置物品i。
 *
 * 若放置的i比取出的物品价值要大，则dp[i - 1, j] > value[i] + dp[i - 1][j - weights[i]]。
 * 否则dp[i - 1, j] < value[i] + dp[i - 1][j - weights[i]]
 *
 * 问题的解：
 * dp[goods.length, capacity]
 *
 * Created by: kevin
 * Date: 2021-03-12
 */
public class Knapsack {
    public static void main(String[] args) {
        int[] values = {6, 3, 5, 4, 6};
        int[] weights = {2, 2, 6, 5, 4};
        System.out.println(maxValue1(values, weights, 10));
    }

    private static int maxValue1(int[] values, int[] weights, int capacity) {
        if (values == null || values.length == 0 ||
                weights == null || weights.length == 0 ||
                values.length != weights.length || capacity <= 0) {
            return 0;
        }
        int[][] dp = new int[values.length + 1][capacity + 1];
        for (int i = 1; i <= values.length; i++) {
            for (int j = 1; j <= capacity; j++) {
                if (j < weights[i - 1]) {
                    dp[i][j] = dp[i - 1][j];//容量不足以放下i
                } else {
                    dp[i][j] = Math.max(
                            dp[i - 1][j],
                            values[i - 1] + dp[i - 1][j - weights[i - 1]]);
                }
            }
        }

        return dp[values.length][capacity];
    }

}
