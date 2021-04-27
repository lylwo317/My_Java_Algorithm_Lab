package leetcode.editor.cn;
//给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。 
//
// 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。 
//
// 
//
// 
//
// 示例 1： 
//
// 
//输入：digits = "23"
//输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
// 
//
// 示例 2： 
//
// 
//输入：digits = ""
//输出：[]
// 
//
// 示例 3： 
//
// 
//输入：digits = "2"
//输出：["a","b","c"]
// 
//
// 
//
// 提示： 
//
// 
// 0 <= digits.length <= 4 
// digits[i] 是范围 ['2', '9'] 的一个数字。 
// 
// Related Topics 深度优先搜索 递归 字符串 回溯算法 
// 👍 1279 👎 0

//https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/

import java.util.ArrayList;
import java.util.List;

class _17_电话号码的字母组合{
    Solution solution = new Solution();



//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    private char[][] lettersArray = new char[][]{{'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'}, {'j', 'k', 'l'},
            {'m', 'n', 'o'}, {'p', 'q', 'r', 's'}, {'t', 'u', 'v'}, {'w', 'x', 'y', 'z'}};

    char[] result;
    List<String> ans = new ArrayList<>();
    public List<String> letterCombinations(String digits) {
        if (digits.length() == 0) {
            return new ArrayList<>();
        }
        result = new char[digits.length()];
        dfs(digits.toCharArray(), 0);
        return ans;
    }

    private void dfs(char[] digits, int idx) {
        if (idx == digits.length) {
            ans.add(new String(result));
            return;
        }

        char[] chars = lettersArray[digits[idx] - '2'];
        for (char aChar : chars) {
            result[idx] = aChar;
            dfs(digits, idx + 1);
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {








    }
}