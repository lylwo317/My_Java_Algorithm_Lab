package leetcode.editor.cn;
//给定一个字符串，逐个翻转字符串中的每个单词。
//
// 说明：
//
//
// 无空格字符构成一个 单词 。
// 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
// 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
//
//
//
//
// 示例 1：
//
// 输入："the sky is blue"
//输出："blue is sky the"
//
//
// 示例 2：
//
// 输入："  hello world!  "
//输出："world! hello"
//解释：输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
//
//
// 示例 3：
//
// 输入："a good   example"
//输出："example good a"
//解释：如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
//
//
// 示例 4：
//
// 输入：s = "  Bob    Loves  Alice   "
//输出："Alice Loves Bob"
//
//
// 示例 5：
//
// 输入：s = "Alice does not even like bob"
//输出："bob like even not does Alice"
//
//
//
//
// 提示：
//
//
// 1 <= s.length <= 104
// s 包含英文大小写字母、数字和空格 ' '
// s 中 至少存在一个 单词
//
//
//
//
//
//
//
// 进阶：
//
//
// 请尝试使用 O(1) 额外空间复杂度的原地解法。
//
// Related Topics 字符串
// 👍 310 👎 0

//https://leetcode-cn.com/problems/reverse-words-in-a-string/

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class _151_翻转字符串里的单词{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 使用Java的Api来解决
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }

        s = s.trim();
        List<String> split = Arrays.asList(s.split("\\s+"));
        Collections.reverse(split);
        return String.join(" ", split);
    }

    /**
     * 先去除空格，然后整个字符串翻转，接着再一个一个单词翻转
     * @param s
     * @return
     */
    public String reverseWords1(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }


        char[] charArray = s.toCharArray();

        //去除空格
        boolean isSpace = true;//-1 is space
        int start = -1;
        for (int i = 0; i < charArray.length; i++) {
            if (isSpace) {
                //start的下一个必须放一个非空的字符
                if (charArray[i] != ' ') {
                    charArray[++start] = charArray[i];
                    isSpace = false;
                }
            } else{
                if (charArray[i] == ' ') {
                    isSpace = true;
                }
                charArray[++start] = charArray[i];
            }
        }

        //翻转字符串
        if (isSpace) {//最后面一串都是空格，丢弃
            start--;
        }
        int count = start + 1;
        revertCharArray(charArray, 0, count);

        //翻转单词
        int begin = 0;
        for (int i = 0; i < count; i++) {
            if (charArray[i] == ' ') {
                revertCharArray(charArray, begin, i);
                begin = i + 1;
            }
        }
        revertCharArray(charArray, begin, count);

        return new String(charArray, 0, count);
    }

    /**
     * 翻转字符串
     * [begin, end)
     * @param charArray
     * @param begin
     * @param end
     */
    private void revertCharArray(char[] charArray, int begin, int end) {
        end--;
        while (begin <= end) {
            char tmp = charArray[begin];
            charArray[begin] = charArray[end];
            charArray[end] = tmp;
            begin++;
            end--;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _151_翻转字符串里的单词 problem = new _151_翻转字符串里的单词();
        System.out.println(problem.solution.reverseWords("  Bob    Loves  Alice   "));
        System.out.println(problem.solution.reverseWords("the sky is blue"));
    }
}