package leetcode.editor.cn;
//数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 3
//输出：["((()))","(()())","(())()","()(())","()()()"]
// 
//
// 示例 2： 
//
// 
//输入：n = 1
//输出：["()"]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 8 
// 
// Related Topics 字符串 回溯算法 
// 👍 1758 👎 0

//https://leetcode-cn.com/problems/generate-parentheses/

import java.util.ArrayList;
import java.util.List;

class _22_括号生成{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    List<String> ans = new ArrayList<>();
    /**
     * 通过画图，可以发现当左右括号相等时，只能选左括号，这样才能保证括号配对
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        int left = n;
        int right = n;
        char[] result = new char[n * 2];
        dfs(n, n, 0, result);
        return ans;
    }

    private void dfs(int left, int right, int idx, char[] result) {
        if (idx == result.length) {
            if (left == 0 && right == 0) {
                ans.add(new String(result));
            }
            return;
        }

        if (left == right) {
            result[idx] = '(';
            dfs(left - 1, right, idx + 1, result);
        } else {
            result[idx] = '(';
            dfs(left - 1, right, idx + 1, result);

            result[idx] = ')';
            dfs(left, right - 1, idx + 1, result);
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _22_括号生成 problem = new _22_括号生成();
        System.out.println(problem.solution.generateParenthesis(1));
    }
}