package leetcode.editor.cn;
//给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。 
//
// candidates 中的数字可以无限制重复被选取。 
//
// 说明： 
//
// 
// 所有数字（包括 target）都是正整数。 
// 解集不能包含重复的组合。 
// 
//
// 示例 1： 
//
// 输入：candidates = [2,3,6,7], target = 7,
//所求解集为：
//[
//  [7],
//  [2,2,3]
//]
// 
//
// 示例 2： 
//
// 输入：candidates = [2,3,5], target = 8,
//所求解集为：
//[
//  [2,2,2,2],
//  [2,3,3],
//  [3,5]
//] 
//
// 
//
// 提示： 
//
// 
// 1 <= candidates.length <= 30 
// 1 <= candidates[i] <= 200 
// candidate 中的每个元素都是独一无二的。 
// 1 <= target <= 500 
// 
// Related Topics 数组 回溯算法 
// 👍 1311 👎 0

//https://leetcode-cn.com/problems/combination-sum/

import java.util.ArrayList;
import java.util.List;

class _39_组合总和{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    /**
     * 回溯
     *
     * 由于可以重复，并且没有重复的元素。从第一个元素开始进行如下操作
     *
     * 1. 不断的选择当前的元素。
     *    1. ==target。记录。然后删除最后选择的元素。跳过当前元素去选择下一个为当前元素，重复1操作
     *    2. >target。删除最后选择的元素。跳过当前元素去选择下一个为当前元素，重复1操作
     * 上面就是dfs
     *
     * 所以展开来看，对当前元素只有两种操作，选择和跳过
     *
     * 这里不要选择前面一个原因是，因为这里考察的是组合的情况。
     * 假设有[A,B,C,D]，如果当前元素是B，那么之前肯定已经尝试过以A为当前元素选择B的情况，这里就不要再往前选择A了，直接往下一个C去尝试
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> combine = new ArrayList<>();
        dfs(candidates, target, combine, ans, 0);
        return ans;
    }

    public void dfs(int[] candidates, int target, List<Integer> combine, List<List<Integer>> ans, int beginIndex) {
        if (beginIndex == candidates.length) {
            return;
        }

        if (target == 0) {
            ans.add(new ArrayList<>(combine));
            return;
        }

        //路径1. 选择当前元素
        if (target - candidates[beginIndex] >= 0) {
            //记录选择
            combine.add(candidates[beginIndex]);
            dfs(candidates, target - candidates[beginIndex], combine, ans, beginIndex);
            //删除选择。回到未选择当前元素的状态
            combine.remove(combine.size() - 1);
        }

        //路径2. 跳过当前元素
        dfs(candidates, target, combine, ans, beginIndex + 1);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _39_组合总和 problem = new _39_组合总和();
    }
}