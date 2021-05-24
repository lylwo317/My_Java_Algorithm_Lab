package leetcode.editor.cn;
//有台奇怪的打印机有以下两个特殊要求： 
//
// 
// 打印机每次只能打印由 同一个字符 组成的序列。 
// 每次可以在任意起始和结束位置打印新字符，并且会覆盖掉原来已有的字符。 
// 
//
// 给你一个字符串 s ，你的任务是计算这个打印机打印它需要的最少打印次数。 
// 
//
// 示例 1： 
//
// 
//输入：s = "aaabbb"
//输出：2
//解释：首先打印 "aaa" 然后打印 "bbb"。
// 
//
// 示例 2： 
//
// 
//输入：s = "aba"
//输出：2
//解释：首先打印 "aaa" 然后在第二个位置打印 "b" 覆盖掉原来的字符 'a'。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 100 
// s 由小写英文字母组成 
// 
// Related Topics 深度优先搜索 动态规划 
// 👍 162 👎 0

//https://leetcode-cn.com/problems/strange-printer/

class _664{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 当一个问题，可以通过小规模的解推导出大的解。那么这个问题，必然可以通过动态规划或者深度遍历来找到问题的答案
     *
     * 假设较小的字符串是axxxx，那么接下来的字符串是axxxxa的话，这两个字符串的最小打印次数是一样的。因为打印左边的a的时候，也可以顺便将右边的a
     * 打印。
     * 那么如果接下来的字符串是axxxxb的话，也就是左右两边是不一样的字符集的话，那么就得找出这里面哪两个子集的次数最小
     *
     * 状态定义:
     * dp[i][j] [i,j]最小打印次数
     *
     * 初始化状态：
     * dp[i][i] = 1 单个字符打印次数必然是1
     *
     * 状态转移方程:
     *
     * if(chars[i] == chars[j]){
     *     dp[i][j] = dp[i][j-1]
     * }else{
     *     int min = Integer.MAX_VALUE
     *     for(int k = i; k < j; k++){
     *          min = Math.min(min, dp[i][k] + dp[k+1][j]);
     *     }
     * }
     * @param s
     * @return
     */
    public int strangePrinter(String s) {
        chars = s.toCharArray();
        int n = chars.length;
        int[][] dp = new int[n][n];
        for (int i = n-1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (chars[i] == chars[j]) {
                    dp[i][j] = dp[i][j - 1];
                } else {
                    int min = Integer.MAX_VALUE;
                    for (int k = i; k < j; k++) {
                        min = Math.min(min, dp[i][k] + dp[k + 1][j]);
                    }
                    dp[i][j] = min;
                }
            }
        }
/*
        for (int j = 0; j < n; j++) {
            dp[j][j] = 1;
            for (int i = j - 1; i >= 0; i--) {
                if (chars[i] == chars[j]) {
                    dp[i][j] = dp[i][j - 1];
                } else {
                    int min = Integer.MAX_VALUE;
                    for (int k = i; k < j; k++) {
                        min = Math.min(min, dp[i][k] + dp[k + 1][j]);
                    }
                    dp[i][j] = min;
                }
            }
        }
*/
        return dp[0][n - 1];
    }

    char[] chars = null;
    public int strangePrinter2(String s) {
        chars = s.toCharArray();
        return dfs(0, chars.length - 1);
    }

    int dfs(int i, int j) {
        if (i == j) {
            return 1;
        }
        if (chars[i] == chars[j]) {
            return dfs(i, j - 1);
        } else {
            int min = Integer.MAX_VALUE;
            for (int k = i; k < j; k++) {
                min = Math.min(min, dfs(i, k) + dfs(k + 1, j));
            }
            return min;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _664 problem = new _664();
    }
}