package leetcode.editor.cn;
//给你一个由 不同 整数组成的数组 nums ，和一个目标整数 target 。请你从 nums 中找出并返回总和为 target 的元素组合的个数。 
//
// 题目数据保证答案符合 32 位整数范围。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3], target = 4
//输出：7
//解释：
//所有可能的组合为：
//(1, 1, 1, 1)
//(1, 1, 2)
//(1, 2, 1)
//(1, 3)
//(2, 1, 1)
//(2, 2)
//(3, 1)
//请注意，顺序不同的序列被视作不同的组合。
// 
//
// 示例 2： 
//
// 
//输入：nums = [9], target = 3
//输出：0
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 200 
// 1 <= nums[i] <= 1000 
// nums 中的所有元素 互不相同 
// 1 <= target <= 1000 
// 
//
// 
//
// 进阶：如果给定的数组中含有负数会发生什么？问题会产生何种变化？如果允许负数出现，需要向题目中添加哪些限制条件？ 
// Related Topics 动态规划 
// 👍 360 👎 0

//https://leetcode-cn.com/problems/combination-sum-iv/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class _377_组合总和_Ⅳ{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int combinationSum4(int[] nums, int target) {
//        return combinationSum4_dp(nums, target);
        return combinationSum4_backtracking(nums, target);
    }
    /**
     * 类似于39 组合总和。这里不同的是，不同排列算一种可能。也就是每一次都从第一个开始遍历
     * 可以看做是爬楼梯的变种，nums相当于每次可以爬的阶梯数的种数
     * @param nums
     * @param target
     * @return
     */
    public int combinationSum4_backtracking(int[] nums, int target) {
        int[] dp = new int[target + 1];
        Arrays.fill(dp, -1);
        dp[0] = 1;
        return dfs(nums, target, dp);
    }

    private int dfs(int[] nums, int target, int[] dp) {
        if (target < 0) {
            return 0;
        }

        if (dp[target] != -1) {
            return dp[target];
        }

        //初始化dp[0]==1
//        if (target == 0) {
//            return 1;
//        }

        int res = 0;
        for (int num : nums) {
            res += dfs(nums, target - num, dp);
        }
        dp[target] = res;
        return res;
    }


    public int combinationSum4_dp(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;

        for (int t = 1; t <= target; t++) {
            for (int num : nums) {
                if (t >= num) {
                    // dp[t-num] + num == target
                    // 类似于还差num步就爬到target处
                    dp[t] += dp[t - num];
                }
            }
        }
        return dp[target];
    }

}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _377_组合总和_Ⅳ problem = new _377_组合总和_Ⅳ();
    }
}