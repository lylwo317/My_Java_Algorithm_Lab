package leetcode.editor.cn;
//给出一个字符串 s（仅含有小写英文字母和括号）。 
//
// 请你按照从括号内到外的顺序，逐层反转每对匹配括号中的字符串，并返回最终的结果。 
//
// 注意，您的结果中 不应 包含任何括号。 
//
// 
//
// 示例 1： 
//
// 输入：s = "(abcd)"
//输出："dcba"
// 
//
// 示例 2： 
//
// 输入：s = "(u(love)i)"
//输出："iloveu"
// 
//
// 示例 3： 
//
// 输入：s = "(ed(et(oc))el)"
//输出："leetcode"
// 
//
// 示例 4： 
//
// 输入：s = "a(bcdefghijkl(mno)p)q"
//输出："apmnolkjihgfedcbq"
// 
//
// 
//
// 提示： 
//
// 
// 0 <= s.length <= 2000 
// s 中只有小写英文字母和括号 
// 我们确保所有括号都是成对出现的 
// 
// Related Topics 栈 
// 👍 126 👎 0

//https://leetcode-cn.com/problems/reverse-substrings-between-each-pair-of-parentheses/

import java.util.Deque;
import java.util.LinkedList;

class _1190_反转每对括号间的子串{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 栈
     *
     * 时间复杂度：O(n^2)
     *
     * 空间复杂度：O(n)
     * @param s
     * @return
     */
    public String reverseParentheses1(String s) {
        StringBuilder sb = new StringBuilder();
        char[] chars = s.toCharArray();
        Deque<String> stack = new LinkedList<>();
        for (char aChar : chars) {//O(n)
            if (aChar == '(') {
                //进入下一层
                stack.push(sb.toString());//保存到栈先，然后在遇到')'的时候，再出栈
                sb.setLength(0);//clear
            } else if (aChar == ')') {
                //返回上一层
                sb.reverse();//O(n)
                sb.insert(0, stack.pop());
            } else {
                sb.append(aChar);
            }
        }
        return sb.toString();
    }

    /**
     * 仔细观察可以发现，我们可以这样一层一层的遍历下去。
     * 遇到左括号，表示该左括号到配对的右括号对应的是下一层。
     * 当进入下一层的时候，只需要跳到配对的另一个括号，往当前括号方向遍历，即可。
     * 当然如果又遇到下一层，就继续按照上面的方案去遍历
     *
     * 时间复杂度：O(n)
     *
     * 空间复杂度：O(n)
     * @param s
     * @return
     */
    public String reverseParentheses(String s) {
        StringBuilder sb = new StringBuilder();
        char[] chars = s.toCharArray();
        Deque<Integer> stack = new LinkedList<>();
        int n = chars.length;
        int[] locate = new int[n];
        for (int i = 0; i < n; i++) {
            if (chars[i] == '(') {
                stack.push(i);
            } else if (chars[i] == ')') {
                Integer left = stack.pop();
                locate[i] = left;
                locate[left] = i;
            }
        }
        stack.clear();

        int i = 0;
        int step = 1;
        while (i < n) {
            if (chars[i] == '(' || chars[i] == ')') {
                i = locate[i];
                step = -step;
            } else {
                sb.append(chars[i]);
            }
            i += step;
        }
        return sb.toString();
    }

}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _1190_反转每对括号间的子串 problem = new _1190_反转每对括号间的子串();
    }
}