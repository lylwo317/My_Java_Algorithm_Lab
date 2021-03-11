package com.kevin.algorithms.dp;

public class LCS {
    public static void main(String[] args) {
        int len = lcs1(new int[] {1, 3, 5, 9, 10}, new int[] {1, 4, 9, 10});
//        int len = lcs1(new int[] {}, new int[] {});
        System.out.println(len);
    }

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
//        int[][] dp = new int[numsA.length + 1][numsB.length + 1];

//        if (nu) {
//        }
        return lcs(numsA, numsA.length, numsB, numsB.length);
    }

    /**
     * 递归
     * 查看dp[i - 1,j - 1]
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
