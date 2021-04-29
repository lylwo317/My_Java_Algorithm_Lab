package leetcode.editor.cn;
//一只青蛙想要过河。 假定河流被等分为若干个单元格，并且在每一个单元格内都有可能放有一块石子（也有可能没有）。 青蛙可以跳上石子，但是不可以跳入水中。 
//
// 给你石子的位置列表 stones（用单元格序号 升序 表示）， 请判定青蛙能否成功过河（即能否在最后一步跳至最后一块石子上）。 
//
// 开始时， 青蛙默认已站在第一块石子上，并可以假定它第一步只能跳跃一个单位（即只能从单元格 1 跳至单元格 2 ）。 
//
// 如果青蛙上一步跳跃了 k 个单位，那么它接下来的跳跃距离只能选择为 k - 1、k 或 k + 1 个单位。 另请注意，青蛙只能向前方（终点的方向）跳跃。
// 
//
// 
//
// 示例 1： 
//
// 
//输入：stones = [0,1,3,5,6,8,12,17]
//输出：true
//解释：青蛙可以成功过河，按照如下方案跳跃：跳 1 个单位到第 2 块石子, 然后跳 2 个单位到第 3 块石子, 接着 跳 2 个单位到第 4 块石子, 然
//后跳 3 个单位到第 6 块石子, 跳 4 个单位到第 7 块石子, 最后，跳 5 个单位到第 8 个石子（即最后一块石子）。 
//
// 示例 2： 
//
// 
//输入：stones = [0,1,2,3,4,8,9,11]
//输出：false
//解释：这是因为第 5 和第 6 个石子之间的间距太大，没有可选的方案供青蛙跳跃过去。 
//
// 
//
// 提示： 
//
// 
// 2 <= stones.length <= 2000 
// 0 <= stones[i] <= 231 - 1 
// stones[0] == 0 
// 
// Related Topics 动态规划 
// 👍 168 👎 0

//https://leetcode-cn.com/problems/frog-jump/

import com.kevin.datastructures.map.HashMap;

import java.util.HashSet;
import java.util.Set;

class _403_青蛙过河{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 青蛙要过河。
     * 假设上一块石头是通过k步到达。到这一块石头的距离为s，如果s∈[k-1,k,k+1]，则就可以跳到这块石头
     * @param stones
     * @return
     */
    public boolean canCross(int[] stones) {
//        return dfsMemory(stones, 0, 0);
        return dpSolution(stones);
    }

    /**
     * 状态定义：
     * dp[i][k]表示是否能通过k个单位跳到stones[i]，true表示可以，false表示不可以
     *
     * 初始化状态
     * dp[0][0] = true
     *
     * 状态转移:
     * 遍历前面每一块石头，计算dp状态数组
     * dp[i][k]=dp[j][k] || dp[j][k-1] || dp[j][k+1]
     * @param stones
     * @return
     */
    public boolean dpSolution(int[] stones) {
        int n = stones.length;
        boolean[][] dp = new boolean[n][n + 1];
        for (int i = 1; i < stones.length; i++) {
            if (stones[i] - stones[i - 1] > i) {
                return false;
            }
        }
        dp[0][0] = true;
        for (int i = 1; i <stones.length; i++) {
            //在i前的石头中查找合适的解
            for (int j = i - 1; j >= 0; j--) {
                int k = stones[i] - stones[j];
                // 因为每一跳一块石头最多+1，当跳到j石头是，步伐最大就是j个单位，
                // 这个时候从j石头跳到下个石头的最大步伐只能是j + 1
                if (k > j + 1) {
                    break;//不可能跳过
                }
                dp[i][k] = dp[j][k + 1] || dp[j][k] || dp[j][k - 1];
                if (i == n - 1 && dp[i][k]) {
                    return true;
                }
            }
        }
        return false;
    }

    Set<String> hashPass = new HashSet<>();
    /**
     * 深度遍历（回溯）+ 记忆化搜索
     * 时间复杂度：O(n^2) 状态总数
     * @param stones
     * @param k
     * @param index
     * @return
     */
    public boolean dfsMemory(int[] stones, int k, int index) {
        if (hashPass.contains(k + "#" + index)) {
            //遇到重复的，说明前面分支（路径）没有能走到终点
            return false;
        } else {
            //记录当前走过的节点
            hashPass.add(k + "#" + index);
        }
        for (int i = index + 1; i < stones.length; i++) {
            //尝试跳到下一块石头，如果能跳，就跳过去继续尝试。直到跳不过去，或者跳到终点为止
            int gap = stones[i] - stones[index];
            if (gap >= k - 1 && gap <= k + 1) {
                if (dfsMemory(stones, gap, i)) {//下一块石头
                    return true;
                }
            } else if (gap > k + 1) {
                break;
            }
        }
        return index == stones.length - 1;//true说明已经跳到终点
    }

    /**
     * 深度遍历（回溯）
     * @param stones
     * @param k
     * @param index
     * @return
     */
    public boolean dfs(int[] stones, int k, int index) {
        for (int i = index + 1; i < stones.length; i++) {
            //尝试跳到下一块石头，如果能跳，就跳过去继续尝试。直到跳不过去，或者跳到终点为止
            int gap = stones[i] - stones[index];
            if (gap >= k - 1 && gap <= k + 1) {
                if (dfs(stones, gap, i)) {
                    return true;
                }
            } else if (gap > k + 1) {
                break;
            }
        }
        return index == stones.length - 1;//true说明已经跳到终点
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _403_青蛙过河 problem = new _403_青蛙过河();
        problem.solution.dpSolution(new int[]{0, 1, 3, 6, 10, 15, 23, 28});
    }
}