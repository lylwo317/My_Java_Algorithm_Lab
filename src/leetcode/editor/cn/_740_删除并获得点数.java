package leetcode.editor.cn;
//给你一个整数数组 nums ，你可以对它进行一些操作。 
//
// 每次操作中，选择任意一个 nums[i] ，删除它并获得 nums[i] 的点数。之后，你必须删除每个等于 nums[i] - 1 或 nums[i] +
// 1 的元素。 
//
// 开始你拥有 0 个点数。返回你能通过这些操作获得的最大点数。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [3,4,2]
//输出：6
//解释：
//删除 4 获得 4 个点数，因此 3 也被删除。
//之后，删除 2 获得 2 个点数。总共获得 6 个点数。
// 
//
// 示例 2： 
//
// 
//输入：nums = [2,2,3,3,3,4]
//输出：9
//解释：
//删除 3 获得 3 个点数，接着要删除两个 2 和 4 。
//之后，再次删除 3 获得 3 个点数，再次删除 3 获得 3 个点数。
//总共获得 9 个点数。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 2 * 104 
// 1 <= nums[i] <= 104 
// 
// Related Topics 动态规划 
// 👍 252 👎 0

//https://leetcode-cn.com/problems/delete-and-earn/

import java.util.Arrays;

class _740_删除并获得点数{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    /**
     * 从规则不难看出，删除了一个数后，于这个数相同的都会成为点数。比如删除3,所以的3都会成为点数
     * 所以可以处理一下，将相同的数合并后，再处理。
     * sum[num] = num*count
     *
     * 所谓删除num - 1 或 num + 1，就是不能选择相邻的成为点数。
     *
     * 状态定义:
     * dp[n]前n号数选择中所获得的最大值。
     *
     * 状态转移：
     * dp[n] = max(dp[n-2]  + sum[n], dp[n-1])
     *
     * 初始状态:
     * dp[0] = 0
     *
     *
     * @param nums
     * @return
     */
    public int deleteAndEarn(int[] nums) {
        int maxInt = Arrays.stream(nums).max().getAsInt();
        int[] sum = new int[maxInt + 1];
        for (int num : nums) {
            sum[num] += num;
        }

        Arrays.sort(nums);

        int first = sum[0];
        int second = Math.max(sum[0], sum[1]);
        for (int i = 2; i < sum.length; i++) {
            int tmp = second;
            second = Math.max(first + sum[i], second);
            first = tmp;
        }
        return second;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _740_删除并获得点数 problem = new _740_删除并获得点数();
    }
}