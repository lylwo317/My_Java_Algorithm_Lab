package leetcode.editor.cn;
//给你一个字符串 s，找到 s 中最长的回文子串。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "babad"
//输出："bab"
//解释："aba" 同样是符合题意的答案。
// 
//
// 示例 2： 
//
// 
//输入：s = "cbbd"
//输出："bb"
// 
//
// 示例 3： 
//
// 
//输入：s = "a"
//输出："a"
// 
//
// 示例 4： 
//
// 
//输入：s = "ac"
//输出："a"
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 1000 
// s 仅由数字和英文字母（大写和/或小写）组成 
// 
// Related Topics 字符串 动态规划 
// 👍 3543 👎 0

//https://leetcode-cn.com/problems/longest-palindromic-substring/

class _5_最长回文子串{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 动态规划的有很多没有意义的求解
     *
     * 可以考虑由中心向外扩展的方法
     *
     * 1. 首先由一个字符为中心向外扩展，如果扩展的两边的字符相等，则继续扩展，直到不相等为止。这就是以这个字符为中心的最长回文子串
     * 2. 将上面求出的所有回文字串中的最长的返回
     *
     * 时间复杂度：O(n^2)，其中 n 是字符串的长度。长度为 1 和 2 的回文中心分别有 n 和 n-1 个，每个回文中心最多会向外扩展 O(n) 次。
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 1) {
            return s;
        }

        char[] chars = s.toCharArray();
        int len = s.length();

        int maxLen = 1;
        int start = 0;
        int end = 0;
        for (int i = 0; i < len; i++) {
            int len1 = maxLenInRange(chars, i, i);
            int len2 = maxLenInRange(chars, i, i + 1);

            int lenX = Math.max(len1, len2);

            if (lenX > maxLen) {
                maxLen = lenX;
                start = i - (lenX - 1) / 2;
                end = i + lenX / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private int maxLenInRange(char[] chars, int start, int end) {
        while (start >= 0 && end < chars.length && chars[start] == chars[end]) {
            start--;
            end++;
        }
        return end - start - 1;
    }

    /**
     * 动态规划
     *
     * 状态定义：
     * dp[i][j]表示[i,j]是回文串
     *
     * 初始化状态:
     * dp[i][i] = true，每一个字符本身就是一个回文串
     *
     * 状态转移方程：
     * if c[i] == c[j] && dp[i+1][j-1] == true
     * dp[i][j] = true
     *
     * if c[i] != c[j]
     * dp[i][j] = false
     *
     * 要特殊处理长度为1的情况
     *
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(n^2)
     *
     * @param s
     * @return
     */
    public String longestPalindrome1(String s) {
        if (s == null || s.length() == 1) {
            return s;
        }

        int len = s.length();

        char[] chars = s.toCharArray();
        boolean[][] dp = new boolean[len][len];//n^2
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = true;
        }

        int maxLen = 1;
        int start = 0;
        int end = 0;

        for (int L = 2; L <= len; L++) {
            for (int i = 0; i < len; i++) {
                int j = L + i - 1;

                if (j >= len) {
                    break;
                }

                if (j - i < 3) {
                    dp[i][j] = chars[i] == chars[j];
                } else {
                    dp[i][j] = chars[i] == chars[j] && dp[i + 1][j - 1];
                }

                if (dp[i][j] && j - i + 1 > maxLen) {
                    maxLen = j - i + 1;
                    start = i;
                    end = j;
                }
            }
        }
        return s.substring(start, end + 1);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _5_最长回文子串 problem = new _5_最长回文子串();
        System.out.println(problem.solution.longestPalindrome("bb"));
    }
}