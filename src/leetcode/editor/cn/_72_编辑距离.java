package leetcode.editor.cn;
//给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。 
//
// 你可以对一个单词进行如下三种操作： 
//
// 
// 插入一个字符 
// 删除一个字符 
// 替换一个字符 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：word1 = "horse", word2 = "ros"
//输出：3
//解释：
//horse -> rorse (将 'h' 替换为 'r')
//rorse -> rose (删除 'r')
//rose -> ros (删除 'e')
// 
//
// 示例 2： 
//
// 
//输入：word1 = "intention", word2 = "execution"
//输出：5
//解释：
//intention -> inention (删除 't')
//inention -> enention (将 'i' 替换为 'e')
//enention -> exention (将 'n' 替换为 'x')
//exention -> exection (将 'n' 替换为 'c')
//exection -> execution (插入 'u')
// 
//
// 
//
// 提示： 
//
// 
// 0 <= word1.length, word2.length <= 500 
// word1 和 word2 由小写英文字母组成 
// 
// Related Topics 字符串 动态规划 
// 👍 1565 👎 0

//https://leetcode-cn.com/problems/edit-distance/

class _72_编辑距离{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    /**
     * 状态定义：
     * dp[i][j]表示word1[0,i),到word2[0,j)的最小编辑距离
     *
     * 初始状态：
     * dp[0][j]表示word1("") -> word2[0,j)，执行新增操作
     * dp[i][0]表示word1[0,i) -> word2("")，执行删除操作
     *
     * 状态转移:
     * 如果dp[i][j]对应的word1[i-1]，word2[j-1]是最后一个需要编辑字符子串的最后一位
     *
     * 1. 当word1[i-1]==word2[j-1]，不需要编辑
     *    dp[i][j] = dp[i-1][j-1]
     *
     * 2. 当word1[i-1]!=word2[j-1]，需要编辑
     *    这里面word1[0,i-1] -> word2[0,j-1]有三种情况：
     *
     *    1. 已知word1[0,i-1]，word2[0,j-2]的编辑距离dp[i][j-1]。
     *        只需要执行完已知操作后，再在word2后面加上word2[j-1]
     *
     *    2. 已知word1[0,i-2]，word2[0,j-1]的编辑距离dp[i-1][j]。
     *        只需要在word1后面删除word1[i-1]，再执行前面已知操作
     *
     *    3. 已知word1[0,i-2]，word2[0,j-2]的编辑距离dp[i-1][j-1]。
     *        只需要执行完已知操作后，再将word1[i-1]替换成word2[j-1]
     *
     *   以上每种情况都只需要在已知的基础上增加一步就可以完成
     *   因此，dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1
     *
     * 时间复杂度：
     * O(nm) 其中 m 为 word1 的长度，n 为 word2 的长度
     * 空间复杂度：
     * O(nm) 我们需要大小为(m+1)*(n+1)的 dp 数组来记录状态值
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance(String word1, String word2) {
        //word1 -> word2
        char[] chars1 = word1.toCharArray();
        char[] chars2 = word2.toCharArray();
        int[][] dp = new int[chars1.length + 1][chars2.length + 1];

        //初始化
        for (int i = 1; i <= chars1.length; i++) {
            dp[i][0] = 1 + dp[i - 1][0];//删除操作
        }

        for (int j = 1; j <= chars2.length; j++) {
            dp[0][j] = 1 + dp[0][j - 1];//新增操作
        }

        for (int i = 1; i <= chars1.length; i++) {
            for (int j = 1; j <= chars2.length; j++) {
                if (chars1[i - 1] == chars2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];//不用操作
                } else {
                    //在替换，删除，新增三种操作中找出最小编辑距离，然后+1
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                }
            }
        }

        return dp[chars1.length][chars2.length];
    }

    /**
     * 通过使用一维数组来优化空间复杂度。
     *
     * 时间复杂度：
     * O(nm) 其中 m 为 word1 的长度，n 为 word2 的长度
     * 空间复杂度：
     * O(k) k = min(m, n) 使用一维数组来记录
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance1(String word1, String word2) {
        char[] chars1 = word1.toCharArray();
        char[] chars2 = word2.toCharArray();
        if (chars1.length < chars2.length) {
            char[] tmp = chars1;
            chars1 = chars2;
            chars2 = tmp;
        }

        int[] dp = new int[chars2.length + 1];

        //初始化
        for (int j = 1; j <= chars2.length; j++) {
            dp[j] = 1 + dp[j - 1];
        }

        int leftTop;
        for (int i = 1; i <= chars1.length; i++) {
            //初始化左上角的数据
            leftTop = dp[0];
            //计算dp[0]
            dp[0] = i;
            for (int j = 1; j <= chars2.length; j++) {
                //计算dp[1]...dp[j]
                //保留top变量，它将会成为dp[j+1]的leftTop
                int tmp = dp[j];
                if (chars1[i - 1] == chars2[j - 1]) {
                    dp[j] = leftTop;
                } else {
                    dp[j] = Math.min(leftTop, Math.min(dp[j]/*top*/, dp[j - 1]/*left*/)) + 1;
                }
                leftTop = tmp;
            }
        }

        return dp[chars2.length];
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _72_编辑距离 problem = new _72_编辑距离();
        System.out.println(problem.solution.minDistance("sea", "eat"));
    }
}