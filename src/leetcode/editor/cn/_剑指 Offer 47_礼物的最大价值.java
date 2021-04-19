package leetcode.editor.cn;
//在一个 m*n 的棋盘的每一格都放有一个礼物，每个礼物都有一定的价值（价值大于 0）。你可以从棋盘的左上角开始拿格子里的礼物，并每次向右或者向下移动一格、直
//到到达棋盘的右下角。给定一个棋盘及其上面的礼物的价值，请计算你最多能拿到多少价值的礼物？ 
//
// 
//
// 示例 1: 
//
// 输入: 
//[
//  [1,3,1],
//  [1,5,1],
//  [4,2,1]
//]
//输出: 12
//解释: 路径 1→3→5→2→1 可以拿到最多价值的礼物 
//
// 
//
// 提示： 
//
// 
// 0 < grid.length <= 200 
// 0 < grid[0].length <= 200 
// 
// Related Topics 动态规划 
// 👍 129 👎 0

//https://leetcode-cn.com/problems/li-wu-de-zui-da-jie-zhi-lcof/

class _剑指_Offer_47_礼物的最大价值{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     *
     *
     *
     * @param grid
     * @return
     */
    public int maxValue(int[][] grid) {

        int row = grid.length;
        int col = grid[0].length;

        int[][] dp = new int[row][col];

        dp[0][0] = grid[0][0];
        for (int i = 1; i < row; i++) {
            dp[i][0] = grid[i][0] + dp[i - 1][0];
        }
        for (int i = 1; i < col; i++) {
            dp[0][i] = grid[0][i] + dp[0][i - 1];
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        return dp[row - 1][col - 1];
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _剑指_Offer_47_礼物的最大价值 problem = new _剑指_Offer_47_礼物的最大价值();
        problem.solution.maxValue(new int[][]{{1, 2}, {3, 4}, {5, 6}});
    }
}