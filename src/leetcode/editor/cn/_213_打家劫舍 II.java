package leetcode.editor.cn;
//你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的
//房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。 
//
// 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，能够偷窃到的最高金额。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [2,3,2]
//输出：3
//解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
// 
//
// 示例 2： 
//
// 
//输入：nums = [1,2,3,1]
//输出：4
//解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
//     偷窃到的最高金额 = 1 + 3 = 4 。 
//
// 示例 3： 
//
// 
//输入：nums = [0]
//输出：0
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 100 
// 0 <= nums[i] <= 1000 
// 
// Related Topics 动态规划 
// 👍 525 👎 0

//https://leetcode-cn.com/problems/house-robber-ii/

class _213_打家劫舍_II{
    Solution solution = new Solution();

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    /**
     * 状态定义：
     * dp[i]表示前i间房屋能偷窃到的最高总金额
     *
     * 初始状态：
     * dp[0] = nums[0]
     * dp[1] = max(nums[0], nums[1])
     *
     * 状态转移方程：
     * dp[i] = max(dp[i-1], dp[i-2] + nums[i])
     *
     * 当不偷窃最后一间房间时：
     * 房间范围 [0, i-1]
     *
     * 当偷窃最后一间房间时：
     * 房间范围 [1, i]
     *
     * 比较这两个范围中最大的dp，就是所能偷取的最大金额
     *
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
//        if (nums.length == 0) {
//            return 0;
//        }
        if (nums.length == 1) {
            return nums[0];
        }

        return Math.max(robWithRange(nums, 0, nums.length - 1),
                robWithRange(nums, 1, nums.length));
    }

    //[start, end)
    private int robWithRange(int[] nums, int start, int end) {
        if (start >= end) {
            return 0;
        }
        if (end - start == 1) {
            return nums[start];
        }
        int[] dp = new int[end - start];
        dp[0] = nums[start];
        dp[1] = Math.max(nums[start], nums[start + 1]);

        for (int i = 2; i < end - start; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[start + i]);
        }

        return dp[end - start - 1];
    }


}
//leetcode submit region end(Prohibit modification and deletion)
public static void main(String[] args) {
    _213_打家劫舍_II s = new _213_打家劫舍_II();
    System.out.println(s.solution.rob(new int[]{1}));
}
}