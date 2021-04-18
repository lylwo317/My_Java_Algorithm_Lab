package leetcode.editor.cn;
//给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。 
//
// 
//
// 示例 1: 
//
// 
//输入: s = "abcabcbb"
//输出: 3 
//解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
// 
//
// 示例 2: 
//
// 
//输入: s = "bbbbb"
//输出: 1
//解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
// 
//
// 示例 3: 
//
// 
//输入: s = "pwwkew"
//输出: 3
//解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
//     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
// 
//
// 示例 4: 
//
// 
//输入: s = ""
//输出: 0
// 
//
// 
//
// 提示： 
//
// 
// 0 <= s.length <= 5 * 104 
// s 由英文字母、数字、符号和空格组成 
// 
// Related Topics 哈希表 双指针 字符串 Sliding Window 
// 👍 5334 👎 0

//https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/

import java.util.HashMap;
import java.util.Map;

class _3_无重复字符的最长子串{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 时间复杂度：O(n)
     * 空间复杂度：O(字符集大小）
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] charArray = s.toCharArray();
        int start = 0;
        Map<Character, Integer> map = new HashMap<>();
        int ans = 0;
        for (int i = 0; i < charArray.length; i++) {
            Integer index = map.get(charArray[i]);
            if (index != null) {//前面出现过这个字符
                start = Math.max(index + 1, start);
            }
            ans = Math.max(i - start + 1, ans);
            map.put(charArray[i], i);
        }

        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _3_无重复字符的最长子串 problem = new _3_无重复字符的最长子串();
    }
}