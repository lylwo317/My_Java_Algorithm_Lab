package leetcode.editor.cn;
//给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。 
//
// 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。 
//
// 
// 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。 
// 
//
// 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。 
//
// 
//
// 示例 1： 
//
// 
//输入：text1 = "abcde", text2 = "ace" 
//输出：3  
//解释：最长公共子序列是 "ace" ，它的长度为 3 。
// 
//
// 示例 2： 
//
// 
//输入：text1 = "abc", text2 = "abc"
//输出：3
//解释：最长公共子序列是 "abc" ，它的长度为 3 。
// 
//
// 示例 3： 
//
// 
//输入：text1 = "abc", text2 = "def"
//输出：0
//解释：两个字符串没有公共子序列，返回 0 。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= text1.length, text2.length <= 1000 
// text1 和 text2 仅由小写英文字符组成。 
// 
// Related Topics 动态规划 
// 👍 552 👎 0

//https://leetcode-cn.com/problems/longest-common-subsequence/

class _1143{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 状态定义:
     * dp[i][j]表示text1[0,i-1]与text2[0,j-1]对最长公共子序列
     *
     * 初始状态:
     * dp[0,i] = 0, dp[j][0] = 0
     *
     * 状态转移:
     * if(text1[i - 1] == text2[j - 1]){
     *     dp[i,j] = dp[i-1][j-1] + 1
     * }else{
     *     dp[i,j] = Max(dp[i-1][j],dp[i][j-1])
     * }
     *
     * @param text1
     * @param text2
     * @return
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int text1Length = text1.length();
        int text2Length = text2.length();
        int[][] dp = new int[text1Length + 1][text2Length + 1];
        char[] char1 = text1.toCharArray();
        char[] char2 = text2.toCharArray();
        for (int i = 1; i <= text1Length; i++) {
            for (int j = 1; j <= text2Length; j++) {
                if (char1[i - 1] == char2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[text1Length][text2Length];
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _1143 problem = new _1143();
    }
}