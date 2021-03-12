package com.kevin.algorithms.dp;

/**
 * 最长上升子序列
 *
 * 状态定义：
 * dp[i]表示表示array中以i-1结尾的最长上升子序列
 *
 * 初始状态：
 * dp[0] = 0, dp[1] = 1
 *
 * 状态转移方程：
 * for (int i = 1; i <= nums.length; i++) {
 *     dp[i] = 1;
 *     for (int preI = 1; preI < i; preI++) {
 *         if (nums[preI - 1]  < nums[i - 1]) {
 *             dp[i] = Math.max(dp[i], dp[preI] + 1);
 *         }
 *     }
 * }
 *
 * 问题的解：
 * max(dp[i])
 */
public class LISubSequence {
    public static void main(String[] args) {
        System.out.println(lengthOfLIS1(new int[] {10, 2, 2, 5, 1, 7, 101, 18}));
    }

    /**
     * 动态规划
     * @param nums
     * @return
     */
    private static int lengthOfLIS1(int[] nums) {
        int[] dp = new int[nums.length + 1];
        dp[0] = 0;
        int max = 0;
        for (int i = 1; i <= nums.length; i++) {
            dp[i] = 1;
            for (int preI = 1; preI < i; preI++) {
                if (nums[preI - 1]  < nums[i - 1]) {
                    dp[i] = Math.max(dp[i], dp[preI] + 1);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
