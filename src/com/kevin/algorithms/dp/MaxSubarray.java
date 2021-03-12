package com.kevin.algorithms.dp;

/**
 * 最大连续子序列和
 *
 * 定义状态：
 * dp[i]表示以i-1结尾的最大连续子序列和
 *
 * 初始状态：
 * dp[0]=0, dp[1]=0
 *
 * 状态转移方程:
 * if(dp[i-1] <= 0){
 *      dp[i]=nums[i-1]
 * }else{
 *     dp[i]=nums[i-1] + dp[i-1]
 * }
 *
 * 问题的解：
 * max(dp[i])
 */
public class MaxSubarray {
    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4 };
        System.out.println(maxSubArray2(nums));
    }

    /**
     * 进一步降低空间复杂度
     * @param nums
     * @return
     */
    private static int maxSubArray2(int[] nums) {
        int dp = 0;
        int max = 0;
        for (int i = 1; i <= nums.length; i++) {
            if (dp <= 0) {//<=0
                dp = nums[i - 1];
            } else {//>0
                dp = nums[i - 1] + dp;
            }
            max = Math.max(dp, max);
        }
        return max;
    }


    private static int maxSubArray1(int[] nums) {
        int[] dp = new int[nums.length + 1];
        int max = 0;
        for (int i = 1; i <= nums.length; i++) {
            if (dp[i - 1] <= 0) {//<=0
                dp[i] = nums[i - 1];
            } else {//>0
                dp[i] = nums[i - 1] + dp[i - 1];
            }
            max = Math.max(dp[i], max);
        }
        return max;
    }
}
