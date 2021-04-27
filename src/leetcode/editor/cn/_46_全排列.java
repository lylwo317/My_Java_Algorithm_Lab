package leetcode.editor.cn;
//给定一个 没有重复 数字的序列，返回其所有可能的全排列。 
//
// 示例: 
//
// 输入: [1,2,3]
//输出:
//[
//  [1,2,3],
//  [1,3,2],
//  [2,1,3],
//  [2,3,1],
//  [3,1,2],
//  [3,2,1]
//] 
// Related Topics 回溯算法 
// 👍 1316 👎 0

//https://leetcode-cn.com/problems/permutations/

import java.util.ArrayList;
import java.util.List;

class _46_全排列{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    boolean[] used;
    List<List<Integer>> ans = new ArrayList<>();
    public List<List<Integer>> permute(int[] nums) {
        List<Integer> result = new ArrayList<>();
        used = new boolean[nums.length];
        dfs(nums, result);
        return ans;
    }

    private void dfs(int[] nums, List<Integer> result) {
        if (result.size() == nums.length) {
            ans.add(new ArrayList<>(result));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }

            used[i] = true;

            result.add(nums[i]);
            dfs(nums,result);
            result.remove(result.size() - 1);

            used[i] = false;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _46_全排列 problem = new _46_全排列();
        problem.solution.permute(new int[]{1, 2, 3});
    }
}