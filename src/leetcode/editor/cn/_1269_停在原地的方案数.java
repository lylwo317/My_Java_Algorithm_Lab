package leetcode.editor.cn;
//有一个长度为 arrLen 的数组，开始有一个指针在索引 0 处。 
//
// 每一步操作中，你可以将指针向左或向右移动 1 步，或者停在原地（指针不能被移动到数组范围外）。 
//
// 给你两个整数 steps 和 arrLen ，请你计算并返回：在恰好执行 steps 次操作以后，指针仍然指向索引 0 处的方案数。 
//
// 由于答案可能会很大，请返回方案数 模 10^9 + 7 后的结果。 
//
// 
//
// 示例 1： 
//
// 输入：steps = 3, arrLen = 2
//输出：4
//解释：3 步后，总共有 4 种不同的方法可以停在索引 0 处。
//向右，向左，不动
//不动，向右，向左
//向右，不动，向左
//不动，不动，不动
// 
//
// 示例 2： 
//
// 输入：steps = 2, arrLen = 4
//输出：2
//解释：2 步后，总共有 2 种不同的方法可以停在索引 0 处。
//向右，向左
//不动，不动
// 
//
// 示例 3： 
//
// 输入：steps = 4, arrLen = 2
//输出：8
// 
//
// 
//
// 提示： 
//
// 
// 1 <= steps <= 500 
// 1 <= arrLen <= 10^6 
// 
// Related Topics 动态规划 
// 👍 154 👎 0

//https://leetcode-cn.com/problems/number-of-ways-to-stay-in-the-same-place-after-some-steps/

import java.util.HashMap;
import java.util.Map;

class _1269_停在原地的方案数{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    int steps = 0;
    int mod = (int) (1E9 + 7);
    int len = 0;
    public int numWays(int steps, int arrLen) {
        this.steps = steps;
        len = arrLen;
//        return dfsMem(steps, 0);
//        return dpSolution(steps, arrLen);
        return dpZipSolution(steps, arrLen);
//        return dpZipSolution2(steps, arrLen);
    }

    /**
     * DFS (TLE)
     * @param step
     * @param idx
     * @return
     */
    private int dfs(int step, int idx) {
        if (idx < 0 || idx >= len) {
            return 0;
        }
        if (step == 0) {
            if (idx == 0) {
                return 1;
            } else {
                return 0;
            }
        }

        int left = dfs(step - 1, idx - 1);
        int local = dfs(step - 1, idx);
        int right = dfs(step - 1, idx + 1);

        return (left + right + local) % mod;
    }

    Map<String, Integer> cacheMap = new HashMap<>();

    /**
     * DFS + 记忆化
     * @param step
     * @param idx
     * @return
     */
    private int dfsMem(int step, int idx) {
        if (idx < 0 || idx >= len) {
            return 0;
        }

        if (step == 0) {
            if (idx == 0) {
                return 1;
            } else {
                return 0;
            }
        }

        //check  cache
        String key = idx + "_" + step;
        Integer integer = cacheMap.get(key);
        if (integer != null) {
            return integer;
        }

        int left = dfsMem(step - 1, idx - 1);
        int local = dfsMem(step - 1, idx);
        int right = dfsMem(step - 1, idx + 1);
        //add  cache
//        int i = (left + right + local) % mod;
        int i = ((left + right) % mod + local) % mod;
        cacheMap.put(key, i);
        return i;
    }

    /**
     * dp[i][j] 表示在 i 步操作之后，指针位于下标 j 的方案数。
     * 其中，i 的取值范围是 0 <= i <= steps，
     * j 的取值范围是 0 <= j <= arrLen - 1
     * 由于一共执行 steps 步操作，因此指针所在下标一定不会超过 steps，
     * 可以将 j 的取值范围进一步缩小到0 <= j <= min(steps, arrLen-1)
     *
     * dp[step][idx] = (dp[step-1][idx-1] + dp[step-1][idx] + dp[step-1][idx+1]) % mod
     * @param steps
     * @param arrLen
     * @return
     */
    public int dpSolution(int steps, int arrLen){
        // 核心优化点。
        // 如果走到超过steps/2的索引下，那就再也不可能走回到索引0了。
        // 所以能走回到索引0的方案中，不会包含索引 > steps/2的结果
        // 所以这里可以进一步缩小steps
//        int maxColumn = Math.min(steps, arrLen - 1);
        int maxColumn = Math.min(steps/2,  arrLen - 1);
        int[][] dp = new int[steps + 1][maxColumn + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= steps; i++) {
            for (int j = 0; j <= maxColumn; j++) {
                dp[i][j] = dp[i - 1][j];//local
                if (j - 1 >= 0) {
                    dp[i][j] = (dp[i][j] + dp[i - 1][j - 1]) % mod;// ( + left) % mod
                }
                if (j + 1 <= maxColumn) {
                    dp[i][j] = (dp[i][j] + dp[i - 1][j + 1]) % mod;// ( + right) % mod
                }
            }
        }
        return dp[steps][0];
    }

    /**
     * dp[step][idx] = (dp[step-1][idx-1] + dp[step-1][idx] + dp[step-1][idx+1]) % mod
     * @param steps
     * @param arrLen
     * @return
     */
    public int dpZipSolution(int steps, int arrLen){
        int maxColumn = Math.min(steps / 2, arrLen - 1);

        int[][] dp = new int[2][maxColumn + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= steps; i++) {
            int[] dpCur = dp[i & 1];
            int[] dpPre = dp[(i - 1) & 1];
            for (int j = 0; j <= maxColumn; j++) {
//                dp[i & 1][j] = dp[(i - 1) & 1][j];//local
                dpCur[j] = dpPre[j];
                if (j - 1 >= 0) {
//                    dp[i & 1][j] = (dp[i & 1][j] + dp[(i - 1) & 1][j - 1]) % mod;// ( + left) % mod
                    dpCur[j] = (dpCur[j] + dpPre[j - 1]) % mod;
                }
                if (j + 1 <= maxColumn) {
//                    dp[i & 1][j] = (dp[i & 1][j] + dp[(i - 1) & 1][j + 1]) % mod;// ( + right) % mod
                    dpCur[j] = (dpCur[j] + dpPre[j + 1]) % mod;
                }
            }
        }
        return dp[steps & 1][0];
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _1269_停在原地的方案数 problem = new _1269_停在原地的方案数();
        System.out.println(problem.solution.numWays(27, 7));
    }
}