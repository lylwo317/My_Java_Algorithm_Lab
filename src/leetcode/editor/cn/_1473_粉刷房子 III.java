package leetcode.editor.cn;
//在一个小城市里，有 m 个房子排成一排，你需要给每个房子涂上 n 种颜色之一（颜色编号为 1 到 n ）。有的房子去年夏天已经涂过颜色了，所以这些房子不需要
//被重新涂色。 
//
// 我们将连续相同颜色尽可能多的房子称为一个街区。（比方说 houses = [1,2,2,3,3,2,1,1] ，它包含 5 个街区 [{1}, {2,2}
//, {3,3}, {2}, {1,1}] 。） 
//
// 给你一个数组 houses ，一个 m * n 的矩阵 cost 和一个整数 target ，其中： 
//
// 
// houses[i]：是第 i 个房子的颜色，0 表示这个房子还没有被涂色。 
// cost[i][j]：是将第 i 个房子涂成颜色 j+1 的花费。 
// 
//
// 请你返回房子涂色方案的最小总花费，使得每个房子都被涂色后，恰好组成 target 个街区。如果没有可用的涂色方案，请返回 -1 。 
//
// 
//
// 示例 1： 
//
// 输入：houses = [0,0,0,0,0], cost = [[1,10],[10,1],[10,1],[1,10],[5,1]], m = 5, n
// = 2, target = 3
//输出：9
//解释：房子涂色方案为 [1,2,2,1,1]
//此方案包含 target = 3 个街区，分别是 [{1}, {2,2}, {1,1}]。
//涂色的总花费为 (1 + 1 + 1 + 1 + 5) = 9。
// 
//
// 示例 2： 
//
// 输入：houses = [0,2,1,2,0], cost = [[1,10],[10,1],[10,1],[1,10],[5,1]], m = 5, n
// = 2, target = 3
//输出：11
//解释：有的房子已经被涂色了，在此基础上涂色方案为 [2,2,1,2,2]
//此方案包含 target = 3 个街区，分别是 [{2,2}, {1}, {2,2}]。
//给第一个和最后一个房子涂色的花费为 (10 + 1) = 11。
// 
//
// 示例 3： 
//
// 输入：houses = [0,0,0,0,0], cost = [[1,10],[10,1],[1,10],[10,1],[1,10]], m = 5, 
//n = 2, target = 5
//输出：5
// 
//
// 示例 4： 
//
// 输入：houses = [3,1,2,3], cost = [[1,1,1],[1,1,1],[1,1,1],[1,1,1]], m = 4, n = 3
//, target = 3
//输出：-1
//解释：房子已经被涂色并组成了 4 个街区，分别是 [{3},{1},{2},{3}] ，无法形成 target = 3 个街区。
// 
//
// 
//
// 提示： 
//
// 
// m == houses.length == cost.length 
// n == cost[i].length 
// 1 <= m <= 100 
// 1 <= n <= 20 
// 1 <= target <= m 
// 0 <= houses[i] <= n 
// 1 <= cost[i][j] <= 10^4 
// 
// Related Topics 动态规划 
// 👍 111 👎 0

//https://leetcode-cn.com/problems/paint-house-iii/

import java.util.HashMap;
import java.util.Map;

class _1473_粉刷房子_III{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    /**
     * 动态规划
     *
     * 状态定义：
     * dp[h][color][target], h <= m. 表示[1,h]号房子选择color号颜色，且处于target街区的涂装价格
     *
     * 初始化状态：
     * dp[0,m][0,n][0] = INF
     *
     * 状态转移方程：
     * 如果本来已经涂装了颜色c1则
     * dp[h][c1][t] = min( all dp[h-1][c!=c1][t-1], dp[h-1][c == c1][t]) )
     * dp[h][c!=c1][t] = INF
     *
     * 如果没有涂装有颜色则
     * c1 = [1,n] 遍历所有颜色
     * dp[h][c1][t] = min( all dp[h-1][c != c1][t-1], dp[h-1][c == c1][t]) ) + cost[h][c1]
     *
     * @param hs
     * @param cost
     * @param m
     * @param n
     * @param target
     * @return
     */
    public int minCost(int[] hs, int[][] cost, int m, int n, int target) {
        int[][][] dp = new int[m + 1][n + 1][target + 1];

        for (int i = 0; i <= m; i++) {//不存在0街区
            for (int j = 0; j <= n; j++) {
                dp[i][j][0] = INF;
            }
        }

        //从1街区开始递推
        for (int i = 1; i <= m; i++) {
            int curColor = hs[i - 1];
            for (int j = 1; j <= n; j++) {
                for (int k = 1; k <= target; k++) {
                    if (k > i) {//分区数大于房子数量是无效的
                        dp[i][j][k] = INF;
                        break;
                    }

                    //根据是否已涂装来区分处理
                    if (curColor != 0) {//已涂装
                        if (j != curColor) {
                            dp[i][j][k] = INF;//与以涂装颜色不同的无效
                        } else {//与已涂装颜色相同
                            //前一个房子是同一街区(颜色)的
                            int preSameCost = dp[i-1][curColor][k];//当这里i=k时，dp[i-1][curColor][k] = dp[k-1][curColor][k] = INF
                            //前一个房子不是同一街区(颜色)的
                            int preDiffCost = INF;
                            for (int p = 1; p <= n; p++) {
                                if (p != curColor) {
                                    preDiffCost = Math.min(dp[i - 1][p][k - 1], preDiffCost);
                                }
                            }
                            dp[i][j][k] = Math.min(preSameCost, preDiffCost);
                        }
                    } else {//没有涂装
                        //前一个房子是同一街区(颜色)的
                        int preSameCost = dp[i-1][j][k];
                        //前一个房子不是同一街区(颜色)的
                        int preDiffCost = INF;
                        for (int p = 1; p <= n; p++) {
                            if (p != j) {
                                preDiffCost = Math.min(dp[i - 1][p][k - 1], preDiffCost);
                            }
                        }
                        dp[i][j][k] = Math.min(preSameCost, preDiffCost) + cost[i - 1][j - 1];
                    }
                }
            }
        }

        // 从「考虑所有房间，并且形成街区数量为 target」的所有方案中找答案
        int minCost = INF;
        for (int i = 1; i <= n; i++) {
            minCost = Math.min(minCost, dp[m][i][target]);
        }

        return minCost == INF ? -1 : minCost;
    }

    int[] houses;
    int[][] cost;
    int m;
    int n;
    int INF = Integer.MAX_VALUE / 2;
    Map<String, Integer> cache = new HashMap<>();

    /**
     * DFS + 记忆化搜索
     * 查找满足街区的解，然后在这些解里面找到花费最小的方案
     * 所谓的DFS，就是不断做选择前进的过程。
     * 这里需要选择就是
     *
     * 确定i房子需不需要涂颜色，如果已经有颜色的就不需要，否则就需要计算每一种涂装后的最小花销。
     * 而要知道知道每一种涂装的最小花销，还需要知道[i+1,m-1]的最小花销，这样递归下去求解即可
     *
     * @param houses
     * @param cost
     * @param m
     * @param n
     * @param target
     * @return
     */
    public int minCost2(int[] houses, int[][] cost, int m, int n, int target) {
        this.houses = houses;
        this.cost = cost;
        this.m = m;
        this.n = n;
        int ans = dfs(0, -1,  target);
        return ans == INF ? -1 : ans;
    }

    /**
     *
     * 时间复杂度：O(m*n^2*t)
     * 空间复杂度：O(m*n*t)
     *
     * @param i i号房
     * @param preColor i-1的颜色
     * @param preTarget i-1所属的街区
     * @return [i,m-1]的最小花费
     */
    private int dfs(int i, int preColor, int preTarget) {
        String key = i + "#" + preColor + "#" + preTarget;//[i, m-1]房子的最小花费
        Integer cacheMin = cache.get(key);
        if (cacheMin != null) {
            return cacheMin;
        }

        if (preTarget < 0 || preTarget > m - i) {//不满足target街区
            return INF;
        }

        if (i == m) {//到终点了，说明这条路径是符合target的，那就返回最小花销
            return 0;
        }

        int min;
        if (houses[i] != 0) {
            min = dfs(i + 1, houses[i], houses[i] == preColor ? preTarget : preTarget - 1);
        } else {
            min = INF;
            for (int j = 1; j <= n; j++) {
                int res = dfs(i + 1, j, (j == preColor) ? preTarget : preTarget - 1);
                if (res == INF) {
                    continue;
                }
                min = Math.min(min, res + cost[i][j - 1]);
            }
        }
        cache.put(key, min);
        return min;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _1473_粉刷房子_III problem = new _1473_粉刷房子_III();
    }
}