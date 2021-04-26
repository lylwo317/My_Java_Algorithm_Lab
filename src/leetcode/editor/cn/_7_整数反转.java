package leetcode.editor.cn;
//给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。 
//
// 如果反转后整数超过 32 位的有符号整数的范围 [−231, 231 − 1] ，就返回 0。 
//假设环境不允许存储 64 位整数（有符号或无符号）。
//
// 
//
// 示例 1： 
//
// 
//输入：x = 123
//输出：321
// 
//
// 示例 2： 
//
// 
//输入：x = -123
//输出：-321
// 
//
// 示例 3： 
//
// 
//输入：x = 120
//输出：21
// 
//
// 示例 4： 
//
// 
//输入：x = 0
//输出：0
// 
//
// 
//
// 提示： 
//
// 
// -231 <= x <= 231 - 1 
// 
// Related Topics 数学 
// 👍 2720 👎 0

//https://leetcode-cn.com/problems/reverse-integer/

class _7_整数反转{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 取余，然后除以10，模拟弹出栈
     * 乘以10+取余，模拟入栈
     * 这里最主要还需要判断越界的情况
     * @param x
     * @return
     */
    public int reverse(int x) {
        int rev = 0;
        while (x != 0) {
//            int pop = x % 10;
            if (x > 0 && (rev > (Integer.MAX_VALUE - x % 10) / 10)) {
//            if (x > 0 && (rev * 10 + x %10 > Integer.MAX_VALUE)) {
                return 0;
            }
            if (x < 0 && (rev < (Integer.MIN_VALUE - x % 10) / 10)) {
                return 0;
            }
            rev = rev * 10 + x % 10;
            x = x / 10;
        }

        return rev;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _7_整数反转 problem = new _7_整数反转();
        System.out.println(problem.solution.reverse(Integer.MAX_VALUE));
    }
}