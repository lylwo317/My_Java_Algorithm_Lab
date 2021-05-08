package leetcode.editor.cn;
//给你一个整数数组 jobs ，其中 jobs[i] 是完成第 i 项工作要花费的时间。 
//
// 请你将这些工作分配给 k 位工人。所有工作都应该分配给工人，且每项工作只能分配给一位工人。工人的 工作时间 是完成分配给他们的所有工作花费时间的总和。请你
//设计一套最佳的工作分配方案，使工人的 最大工作时间 得以 最小化 。 
//
// 返回分配方案中尽可能 最小 的 最大工作时间 。 
//
// 
//
// 示例 1： 
//
// 
//输入：jobs = [3,2,3], k = 3
//输出：3
//解释：给每位工人分配一项工作，最大工作时间是 3 。
// 
//
// 示例 2： 
//
// 
//输入：jobs = [1,2,4,7,8], k = 2
//输出：11
//解释：按下述方式分配工作：
//1 号工人：1、2、8（工作时间 = 1 + 2 + 8 = 11）
//2 号工人：4、7（工作时间 = 4 + 7 = 11）
//最大工作时间是 11 。 
//
// 
//
// 提示： 
//
// 
// 1 <= k <= jobs.length <= 12 
// 1 <= jobs[i] <= 107 
// 
// Related Topics 递归 回溯算法 
// 👍 98 👎 0

//https://leetcode-cn.com/problems/find-minimum-time-to-finish-all-jobs/

class _1723{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    int[] jobs;
    int k;
    int maxWorkTime = Integer.MAX_VALUE;
    int[] workTime;

    public int minimumTimeRequired(int[] jobs, int k) {
        this.jobs = jobs;
        this.k = k;
        workTime = new int[k];
//        dfs1(0, 0);
        dfs2(0, 0, 0);
        return maxWorkTime;
    }

    /**
     *
     * @param jIdx
     * @param used
     * @param max
     */
    private void dfs2(int jIdx, int used, int max) {
        if (max >= maxWorkTime) {//这里要把等于的情况也排除掉
            return;
        }

        if (jIdx == jobs.length) {
            maxWorkTime = max;
            return;
        }

        if (used < k) {//尝试分配给空闲的工人
            workTime[used] = jobs[jIdx];
//            System.out.println("selected j=" + jIdx + " k=" + used);
            dfs2(jIdx + 1, used + 1, Math.max(max, workTime[used]));
            workTime[used] = 0;
        }

        for (int i = 0; i < used; i++) {//尝试分配给已经分配过工作的人
            workTime[i] += jobs[jIdx];
//            System.out.println("selected j=" + jIdx + " k=" + i);
            dfs2(jIdx + 1, used, Math.max(max, workTime[i]));
            workTime[i] -= jobs[jIdx];
        }
    }

    private void dfs1(int jIdx, int max) {
        if (max >= maxWorkTime) {//这里要把等于的情况也排除掉
            return;
        }

        if (jIdx == jobs.length) {
            maxWorkTime = max;
            return;
        }

//        System.out.println("selected j=" + jIdx + " k=" + kIdx);

        for (int i = 0; i < k; i++) {
            workTime[i] += jobs[jIdx];
            dfs1(jIdx + 1,  Math.max(max, workTime[i]));
            workTime[i] -= jobs[jIdx];
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _1723 problem = new _1723();

        problem.solution.minimumTimeRequired(new int[]{254,256,256,254,251,256,254,253,255,251,251,255}, 10);
    }
}