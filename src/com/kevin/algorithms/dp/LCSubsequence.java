package com.kevin.algorithms.dp;

/**
 * 最长公共子序列
 *
 * 状态定义：
 * dp[i, j]表示numsA的前i个序列和numbsB前j个序列的最长公共子序列
 *
 * 初始状态:
 * dp[0,i] = 0, dp[j][0] = 0
 *
 * 状态转移方程：
 * if(numsA[i - 1] == numsB[j - 1]){
 *     dp[i,j] = dp[i-1][dp-j] + 1
 * }else{
 *     dp[i,j] = Max(dp[i-1][j],dp[i][j-1])
 * }
 *
 * 问题的解：
 * dp[numsA.length, numsB.length]
 */
public class LCSubsequence {
    public static void main(String[] args) {
        int len = lcs4(new int[] {1, 3, 5, 9, 10}, new int[] {1, 4, 9, 10});
//        int len = lcs1(new int[] {}, new int[] {});
        System.out.println(len);
    }

    /**
     * 通过一维数组，和选择最短的数组作为列减少空间复杂度
     *
     * 时间复杂度：O(n*m) n表示数组A的长度， m表示数组B的长度
     * 空间复杂度：O(k) k = min(n, m)
     *
     * @param numsA
     * @param numsB
     * @return
     */
    private static int lcs4(int[] numsA, int[] numsB) {

        int[] rows = numsA;
        int[] cols = numsB;
        if (numsA.length < numsB.length) {
            rows = numsB;
            cols = numsA;
        }

        int[] dp = new int[cols.length + 1];
        for (int i = 1; i <= rows.length; i++) {
            int cur = 0;
            for (int j = 1; j <= cols.length; j++) {
                int leftTop = cur;
                cur = dp[j];
                if (rows[i - 1] == cols[j - 1]) {
                    dp[j] = leftTop + 1;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
            }
        }
        return dp[cols.length];
    }

    /**
     * 通过一维数组减少空间复杂度
     *
     * 时间复杂度：O(n*m) n表示数组A的长度， m表示数组B的长度
     * 空间复杂度：O(k) k = m 或者 n
     *
     * @param numsA
     * @param numsB
     * @return
     */
    private static int lcs3(int[] numsA, int[] numsB) {
        int[] dp = new int[numsB.length + 1];
        for (int i = 1; i <= numsA.length; i++) {
            int cur = 0;
            for (int j = 1; j <= numsB.length; j++) {
                int leftTop = cur;
                cur = dp[j];
                if (numsA[i - 1] == numsB[j - 1]) {
                    dp[j] = leftTop + 1;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
            }
        }
        return dp[numsB.length];
    }

    /**
     * 通过滚动数组减少空间复杂度
     *
     * 时间复杂度：O(n*m) n表示数组A的长度， m表示数组B的长度
     * 空间复杂度：O(2k) k = m 或者 n
     *
     * @param numsA
     * @param numsB
     * @return
     */
    private static int lcs2(int[] numsA, int[] numsB) {
        int[][] dp = new int[2][numsB.length + 1];
        for (int i = 1; i <= numsA.length; i++) {
            for (int j = 1; j <= numsB.length; j++) {
                if (numsA[i - 1] == numsB[j - 1]) {
                    dp[i & 1][j] = dp[(i - 1) & 1][j - 1] + 1;
                } else {
                    dp[i & 1][j] = Math.max(dp[(i - 1) & 1][j], dp[i & 1][j - 1]);
                }
            }
        }
        return dp[numsA.length & 1][numsB.length];
    }

    /**
     * 时间复杂度：O(n*m) n表示数组A的长度， m表示数组B的长度
     * 空间复杂度：O(n*m)
     */
    private static int lcs1(int[] numsA, int[] numsB) {
        int[][] dp = new int[numsA.length + 1][numsB.length + 1];
        for (int i = 1; i <= numsA.length; i++) {
            for (int j = 1; j <= numsB.length; j++) {
                if (numsA[i - 1] == numsB[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[numsA.length][numsB.length];
    }

    private static int lcs(int[] numsA, int[] numsB) {
        return lcs(numsA, numsA.length, numsB, numsB.length);
    }

    /**
     * 递归
     * 查看dp[i - 1,j - 1]
     *
     * 时间复杂度：O(2^n) 当n=m时
     * 空间复杂度：O(k) k = min(n, m)，n、m是两个数组的长度
     *
     * @param numsA
     * @param i
     * @param numsB
     * @param j
     * @return
     */
    private static int lcs(int[] numsA, int i, int[] numsB, int j) {
        if (i == 0 || j == 0) {
            return 0;
        }
        if (numsA[i - 1] == numsB[j - 1]) {
            return lcs(numsA, i - 1, numsB, j - 1) + 1;
        } else {
            return Math.max(lcs(numsA, i, numsB, j - 1),
                    lcs(numsA, i - 1, numsB, j));
        }
    }
}
