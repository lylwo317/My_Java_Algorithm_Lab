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
        System.out.println(maxValueExactly2(values, weights, 10));
    }

    /**
     * 正好能放满的方案，否则返回-1
     *
     * 优化空间复杂度和时间复杂度
     *
     * 时间复杂度：O(n*k) n表示物品数量，k表示（最大容量 - 当前要判断的的物品i的重量）的差值
     * 空间复杂度：O(m) m表示最大容量
     *
     * @param values
     * @param weights
     * @param capacity
     * @return
     */
    private static int maxValueExactly2(int[] values, int[] weights, int capacity) {
        if (values == null || values.length == 0 ||
                weights == null || weights.length == 0 ||
                values.length != weights.length || capacity <= 0) {
            return 0;
        }
        int[] dp = new int[capacity + 1];
        for (int j = 1; j < dp.length; j++) {//dp[0] = 0, 其它初始化为-1。对于0件物品，正好能放满的容量就是0，所以dp[0]=0
            dp[j] = -1;
        }
        for (int i = 1; i <= values.length; i++) {
            for (int j = capacity; j >= weights[i-1]; j--) {//优化maxValue2中的continue
                if (dp[j - weights[i - 1]] == -1) {//放下i不能正好放满(因为放置前面i-1物品都不能正好填满[j - weights[i - 1]，所以放下i后也不能填满j)
                    dp[j] = dp[j];
                } else {
                    //放下i正好能放满
                    dp[j] = Math.max(
                            dp[j],//1. 能放满但是不如放下i来得价值大 2. 不能放满，那就选放i的方案，因为它能放满
                            values[i - 1] + dp[j - weights[i - 1]]);
                }
            }
        }

        return dp[capacity];
    }

    /**
     * 正好能放满的方案，否则返回-1
     *
     * 时间复杂度：O(n*m) n表示物品数量，m表示最大容量
     * 空间复杂度：O(n*m)
     *
     * @param values
     * @param weights
     * @param capacity
     * @return
     */
    private static int maxValueExactly1(int[] values, int[] weights, int capacity) {
        if (values == null || values.length == 0 ||
                weights == null || weights.length == 0 ||
                values.length != weights.length || capacity <= 0) {
            return 0;
        }
        int[][] dp = new int[values.length + 1][capacity + 1];
        for (int j = 1; j < dp.length; j++) {//dp[0] = 0, 其它初始化为-1。对于0件物品，正好能放满的容量就是0，所以dp[0]=0
            dp[0][j] = -1;
        }
        for (int i = 1; i <= values.length; i++) {
            for (int j = 1; j <= capacity; j++) {
                if (j < weights[i - 1] || dp[i - 1][j - weights[i - 1]] == -1) {
                    dp[i][j] = dp[i - 1][j];//容量不足以放下i 或者 放下i不能正好放满(因为放置前面i-1物品都不能正好填满[j - weights[i - 1]，所以放下i后也不能填满j)
                } else {
                    //放下i正好能放满

                    if (dp[i - 1][j] == -1) {//前i-1不能正好放满最大容量j，那就放i物品
                        dp[i][j] = values[i - 1] + dp[i - 1][j - weights[i - 1]];
                    } else {
                        //前i-1正好放满最大容量j，那就与放入i物品正好能放满最大容量j的价值进行比较
                        dp[i][j] = Math.max(
                                dp[i - 1][j],
                                values[i - 1] + dp[i - 1][j - weights[i - 1]]);//当i=1的时候，如果刚好能放下1物品，则dp[0][j-weights[i-1]]=dp[0][0]=0
                    }
                }
            }
        }

        return dp[values.length][capacity];
    }

    /**
     * 时间复杂度：O(n*k) n表示物品数量，k表示（最大容量 - 当前要判断的的物品重量）的差值
     * 空间复杂度：O(m) m表示最大容量
     *
     * @param values
     * @param weights
     * @param capacity
     * @return
     */
    private static int maxValue3(int[] values, int[] weights, int capacity) {
        if (values == null || values.length == 0 ||
                weights == null || weights.length == 0 ||
                values.length != weights.length || capacity <= 0) {
            return 0;
        }
        int[] dp = new int[capacity + 1];
        for (int i = 1; i <= values.length; i++) {
            for (int j = capacity; j >= weights[i-1]; j--) {//优化maxValue2中的continue
                dp[j] = Math.max(
                        dp[j],
                        values[i - 1] + dp[j - weights[i - 1]]);
            }
        }

        return dp[capacity];
    }

    /**
     * 时间复杂度：O(n*m) n表示物品数量，m表示最大容量
     * 空间复杂度：O(m)
     * @param values
     * @param weights
     * @param capacity
     * @return
     */
    private static int maxValue2(int[] values, int[] weights, int capacity) {
        if (values == null || values.length == 0 ||
                weights == null || weights.length == 0 ||
                values.length != weights.length || capacity <= 0) {
            return 0;
        }
        int[] dp = new int[capacity + 1];
        for (int i = 1; i <= values.length; i++) {
            for (int j = capacity; j >= 1; j--) {//为什么要从后面往前？因为这里会用到上一行的值比较，而且还比较靠前，所以从后面往前就不会覆盖了
                if (j < weights[i - 1]) {
//                    dp[j] = dp[j];//容量不足以放下i
                    continue;
                }
                dp[j] = Math.max(
                        dp[j],
                        values[i - 1] + dp[j - weights[i - 1]]);
            }
        }

        return dp[capacity];
    }

    /**
     * 时间复杂度：O(n*m) n表示物品数量，m表示最大容量
     * 空间复杂度：O(n*m)
     * @param values
     * @param weights
     * @param capacity
     * @return
     */
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
