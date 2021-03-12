package com.kevin.algorithms.dp;

/**
 * 最长公共子串
 *
 * 状态定义：
 * dp[i,j]表示表示strA中以i-1结尾和strB中以j-1结尾的公共子串长度
 *
 * 初始状态：
 * dp[0,j] = 0, dp[i,0] = 0
 *
 * 状态转移方程：
 * if(strA[i - 1] == strB[j - 1]){
 *     dp[i, j] = dp[i-1, j-1] + 1;
 * }else{
 *     dp[i, j] = 0
 * }
 *
 * 问题的解：
 * max(dp[i, j])
 */
public class LCSubstring {

    public static void main(String[] args) {
		System.out.println(lcs4("ABDCBA", "ABBA"));
    }

    /**
     * 通过一维数组，和选择最短的数组作为列减少空间复杂度
     * @param strA
     * @param strB
     * @return 最长公共子串长度
     */
    private static int lcs4(String strA, String strB) {
        if (strA == null || strB == null) {
            return 0;
        }

        char[] rows = strA.toCharArray();
        char[] cols = strB.toCharArray();
        if (rows.length < cols.length) {
            rows = cols;
            cols = rows;
        }

        int max = 0;
        int[] dp = new int[cols.length + 1];
        for (int i = 1; i <= rows.length; i++) {
            int cur = 0;
            for (int j = 1; j <= cols.length; j++) {
                int leftTop = cur;
                cur = dp[j];
                if (rows[i - 1] == cols[j - 1]) {
                    dp[j] = leftTop + 1;
                    max = Math.max(max, dp[j]);
                } else {
                    dp[j] = 0;
                }
            }
        }
        return max;
    }

}
