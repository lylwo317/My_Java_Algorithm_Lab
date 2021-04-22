package leetcode.editor.cn;
//给你一个由 无重复 正整数组成的集合 nums ，请你找出并返回其中最大的整除子集 answer ，子集中每一元素对 (answer[i], answer[
//j]) 都应当满足：
// 
// answer[i] % answer[j] == 0 ，或 
// answer[j] % answer[i] == 0 
// 
//
// 如果存在多个有效解子集，返回其中任何一个均可。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3]
//输出：[1,2]
//解释：[1,3] 也会被视为正确答案。
// 
//
// 示例 2： 
//
// 
//输入：nums = [1,2,4,8]
//输出：[1,2,4,8]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 1000 
// 1 <= nums[i] <= 2 * 109 
// nums 中的所有整数 互不相同 
// 
// Related Topics 数学 动态规划 
// 👍 220 👎 0

//https://leetcode-cn.com/problems/largest-divisible-subset/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class _368_最大整除子集{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        Arrays.sort(nums);
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        int maxValue = nums[0];
        int maxSize = 1;
        for (int j = 1; j < nums.length; j++) {
            for (int i = 0; i < j; i++) {
                if (nums[j] % nums[i] == 0) {
                    dp[j] = Math.max(dp[j], dp[i] + 1);
                }
            }
            if (dp[j] > maxSize) {
                maxSize = dp[j];
                maxValue = nums[j];
            }
        }


        List<Integer> list = new ArrayList<>();
        if (maxSize == 1) {
            list.add(nums[0]);
            return list;
        }
        for (int i = nums.length - 1; i >= 0 && maxSize > 0; i--) {
            if (dp[i] == maxSize && maxValue % nums[i] == 0) {
                list.add(nums[i]);
                maxValue = nums[i];
                maxSize--;
            }
        }
        return list;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _368_最大整除子集 problem = new _368_最大整除子集();
        System.out.println(
                problem.solution.largestDivisibleSubset(new int[]{3, 17})
        );
    }
}