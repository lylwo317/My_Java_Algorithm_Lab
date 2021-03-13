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
        System.out.println(lengthOfLIS3(new int[] {10, 2, 2, 5, 1, 7, 101, 18}));
    }

    /**
     * 二分搜索 + 牌堆
     * 时间复杂度: 平均 O(nlog n)
     * 空间复杂度: O(n)
     * @param nums
     * @return
     */
    private static int lengthOfLIS3(int[] nums) {
        int[] top = new int[nums.length];
        int len = 0;
        for (int num : nums) {
            int begin = 0;
            int end = len;

            while (begin < end) {
                int mid = (begin + end) >>> 1;
                if (num <= top[mid]) {
                    //左边
                    end = mid;
                } else
                    begin = mid + 1;
            }
            if (begin == len) {
                len++;
            }
            top[begin] = num;
        }
        return len;
    }

    /**
     * 牌堆
     * 时间复杂度: 最坏O(n^2) 最好O(n) 平均O(n^2)
     * 空间复杂度: O(n)
     * @param nums
     * @return
     */
    private static int lengthOfLIS2(int[] nums) {
        int[] top = new int[nums.length];
        int len = 0;
        for (int num : nums) {
            for (int j = 0; j < top.length; j++) {
                if (j < len) {//在已有的牌堆里面遍历
                    //更新
                    if (top[j] >= num) {//更新堆顶
                        top[j] = num;
                        break;//取下一个num
                    }
                    //往下一个top遍历
                } else {
                    //新建牌堆
                    top[j] = num;
                    len++;
                    break;
                    //往下一个num遍历
                }
            }
        }
        return len;
    }

    /**
     * 动态规划
     * 时间复杂度: O(n^2)
     * 空间复杂度: O(n)
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
