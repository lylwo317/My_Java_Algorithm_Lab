package leetcode.editor.cn;
//假设你正在爬楼梯。需要 n 阶你才能到达楼顶。 
//
// 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？ 
//
// 注意：给定 n 是一个正整数。 
//
// 示例 1： 
//
// 输入： 2
//输出： 2
//解释： 有两种方法可以爬到楼顶。
//1.  1 阶 + 1 阶
//2.  2 阶 
//
// 示例 2： 
//
// 输入： 3
//输出： 3
//解释： 有三种方法可以爬到楼顶。
//1.  1 阶 + 1 阶 + 1 阶
//2.  1 阶 + 2 阶
//3.  2 阶 + 1 阶
// 
// Related Topics 动态规划 
// 👍 1616 👎 0

//https://leetcode-cn.com/problems/climbing-stairs/

class _70_爬楼梯{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 经典动态规划
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        int[] dp = new int[2];
        dp[0] = 1;
        dp[1] = 2;
        for (int i = 2; i < n; i++) {
            dp[i & 1] = dp[(i - 1) & 1] + dp[(i - 2) & 1];
        }
        return dp[(n - 1) & 1];
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _70_爬楼梯 problem = new _70_爬楼梯();
    }
}