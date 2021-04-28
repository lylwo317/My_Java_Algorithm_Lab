package leetcode.editor.cn;
//给定一个非负整数 c ，你要判断是否存在两个整数 a 和 b，使得 a2 + b2 = c 。 
//
// 
//
// 示例 1： 
//
// 输入：c = 5
//输出：true
//解释：1 * 1 + 2 * 2 = 5
// 
//
// 示例 2： 
//
// 输入：c = 3
//输出：false
// 
//
// 示例 3： 
//
// 输入：c = 4
//输出：true
// 
//
// 示例 4： 
//
// 输入：c = 2
//输出：true
// 
//
// 示例 5： 
//
// 输入：c = 1
//输出：true 
//
// 
//
// 提示： 
//
// 
// 0 <= c <= 231 - 1 
// 
// Related Topics 数学 
// 👍 206 👎 0

//https://leetcode-cn.com/problems/sum-of-square-numbers/

class _633_平方数之和{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean judgeSquareSum1(int c) {
        int sqrtC = (int) Math.sqrt(c);
        int a = 0;
        while (a <= sqrtC) {
            double b = Math.sqrt(c - a * a);
            if (b == (int)b) {
                return true;
            }
            a++;
        }
        return false;
    }

    public boolean judgeSquareSum(int c) {
        int sqrtC = (int) Math.sqrt(c);
        int a = 0;
        int b = sqrtC;
        while (a <= b) {//有可能会等于b，例如1^2 + 1^2 = 2
            int sum = a * a + b * b;
            if (sum == c) {
                return true;
            }
            if (sum > c) {
                b--;
            } else {
                a++;
            }
        }
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _633_平方数之和 problem = new _633_平方数之和();
        problem.solution.judgeSquareSum(3);
    }
}