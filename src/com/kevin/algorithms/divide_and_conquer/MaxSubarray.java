package com.kevin.algorithms.divide_and_conquer;

/**
 * 最长
 */
public class MaxSubarray {

    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4 };
        System.out.println(maxSubArray(nums));
//        System.out.println(maxSubArray1(nums));
    }

    /**
     * 分治。
     * 序列[begin, end)
     * 将序列分解成两个序列来， [begin, mid), [mid, end)
     *
     * @param nums
     * @return
     */
    private static int maxSubArray(int[] nums) {
        return maxSubArray(nums, 0, nums.length);
    }

    private static int maxSubArray(int[] nums, int begin, int end) {
        if (end - begin <= 1) {
            return nums[begin];
        }

        int mid = begin + end >>> 1;
        int leftMax = 0;
        int leftSum = 0;
        for (int i = mid - 1; i >= 0; i--) {
            leftSum += nums[i];
            leftMax = Math.max(leftMax, leftSum);
        }

        int rightMax = 0;
        int rightSum = 0;
        for (int i = mid; i < end; i++) {
            rightSum += nums[i];
            rightMax = Math.max(rightMax, rightSum);
        }
        int max = leftMax + rightMax;

        return Math.max(max,
                Math.max(maxSubArray(nums, begin, mid), maxSubArray(nums, mid, end)));
    }

    private static int maxSubArray1(int[] nums) {
        int maxSum = 0;
        for (int begin = 0; begin < nums.length; begin++) {
            int sum = 0;
            for (int end = begin; end < nums.length; end++) {
                sum += nums[end];
                maxSum = Math.max(maxSum, sum);
            }
        }

        return maxSum;
    }

}
