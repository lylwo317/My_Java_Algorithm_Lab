package leetcode.editor.cn;
//给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,1,2]
//输出：
//[[1,1,2],
// [1,2,1],
// [2,1,1]]
// 
//
// 示例 2： 
//
// 
//输入：nums = [1,2,3]
//输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 8 
// -10 <= nums[i] <= 10 
// 
// Related Topics 回溯算法 
// 👍 684 👎 0

//https://leetcode-cn.com/problems/permutations-ii/

import java.util.ArrayList;
import java.util.List;

class _47_全排列_II{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    List<List<Integer>> ans = new ArrayList<>();
    public List<List<Integer>> permuteUnique(int[] nums) {
        dfs(nums, 0);
        return ans;
    }

    private void dfs(int[] nums, int idx) {
        if (idx == nums.length) {
            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                result.add(nums[i]);
            }
            ans.add(result);
            return;
        }
        for (int i = idx; i < nums.length; i++) {
            if (check(nums, idx, i)) {
                continue;
            }
            swap(nums, idx, i);
            dfs(nums, idx + 1);
            swap(nums, idx, i);
        }
    }

    private boolean check(int[] nums, int idx, int i) {
        while (idx < i) {
            if (nums[i] == nums[idx]) {
                return true;
            }
            idx++;
        }
        return false;
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _47_全排列_II problem = new _47_全排列_II();
    }
}