package leetcode.editor.cn;
//写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项（即 F(N)）。斐波那契数列的定义如下： 
//
// 
//F(0) = 0,   F(1) = 1
//F(N) = F(N - 1) + F(N - 2), 其中 N > 1. 
//
// 斐波那契数列由 0 和 1 开始，之后的斐波那契数就是由之前的两数相加而得出。 
//
// 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 2
//输出：1
// 
//
// 示例 2： 
//
// 
//输入：n = 5
//输出：5
// 
//
// 
//
// 提示： 
//
// 
// 0 <= n <= 100 
// 
// Related Topics 递归 
// 👍 138 👎 0

//https://leetcode-cn.com/problems/fei-bo-na-qi-shu-lie-lcof/

class _剑指_Offer_10_I_斐波那契数列{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int fib(int n) {
        int[] dp = new int[2];
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i & 1] = dp[(i - 1) & 1] + dp[(i - 2) & 1];
            if (dp[i&1] > (1e9+7)) {
                dp[i&1] = (int) (dp[i & 1] % (1e9+7));
            }
        }
        return dp[n & 1];
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _剑指_Offer_10_I_斐波那契数列 problem = new _剑指_Offer_10_I_斐波那契数列();

        System.out.println(problem.solution.fib(5));
    }
}