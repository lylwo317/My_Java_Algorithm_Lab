package leetcode.editor.cn;
//给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。 
//
// 有效字符串需满足： 
//
// 
// 左括号必须用相同类型的右括号闭合。 
// 左括号必须以正确的顺序闭合。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "()"
//输出：true
// 
//
// 示例 2： 
//
// 
//输入：s = "()[]{}"
//输出：true
// 
//
// 示例 3： 
//
// 
//输入：s = "(]"
//输出：false
// 
//
// 示例 4： 
//
// 
//输入：s = "([)]"
//输出：false
// 
//
// 示例 5： 
//
// 
//输入：s = "{[]}"
//输出：true 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 104 
// s 仅由括号 '()[]{}' 组成 
// 
// Related Topics 栈 字符串 
// 👍 2341 👎 0

//https://leetcode-cn.com/problems/valid-parentheses/

import java.util.ArrayDeque;
import java.util.Deque;

class _20_有效的括号{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        int length = s.length();
        char[] chars = s.toCharArray();
        if (length <= 1) {
            return false;
        }
        for (char aChar : chars) {
            if (!stack.isEmpty()) {
                switch (aChar) {
                    case ')':
                        if ('(' == stack.peek()) {
                            stack.pop();
                            continue;
                        }
                        break;
                    case ']':
                        if ('[' == stack.peek()) {
                            stack.pop();
                            continue;
                        }
                        break;
                    case '}':
                        if ('{' == stack.peek()) {
                            stack.pop();
                            continue;
                        }
                        break;

                }
            }
            stack.push(aChar);
        }
        return stack.isEmpty();
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _20_有效的括号 problem = new _20_有效的括号();
        problem.solution.isValid("({[)");
    }
}