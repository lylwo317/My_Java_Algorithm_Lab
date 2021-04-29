package leetcode.editor.cn;
//实现 pow(x, n) ，即计算 x 的 n 次幂函数（即，xn）。 
//
// 
//
// 示例 1： 
//
// 
//输入：x = 2.00000, n = 10
//输出：1024.00000
// 
//
// 示例 2： 
//
// 
//输入：x = 2.10000, n = 3
//输出：9.26100
// 
//
// 示例 3： 
//
// 
//输入：x = 2.00000, n = -2
//输出：0.25000
//解释：2-2 = 1/22 = 1/4 = 0.25
// 
//
// 
//
// 提示： 
//
// 
// -100.0 < x < 100.0 
// -231 <= n <= 231-1 
// -104 <= xn <= 104 
// 
// Related Topics 数学 二分查找 
// 👍 642 👎 0

//https://leetcode-cn.com/problems/powx-n/

class _50_Pow{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 非递归
     * 快速幂
     *
     * 21 = 10101
     *
     * 3^21 = 3^(1*2^4) * 3^(0*2^3) * 3^(1*2^2) * 3^(0*2^1) * 3^(1*2^0)
     * 3^21 = 3^(1*16) * 3^(0*8) * 3^(1*4) * 3^(0*2) * 3^(1*1)
     * 3^21 = 3^16 * 3^4 * 3*1
     *
     * 3^2 = 3^1 * 3^1
     * 3^4 = 3^2 * 3^2
     * 3^8 = 3^4 * 3^4
     *
     * 时间复杂度：O(log n)
     * 空间复杂度：O(1)
     *
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        boolean neg = n < 0;
        long m = neg ? -((long)n) : n;

        double result = 1.0;
        while (m > 0) {//循环取二进制下一位
            if ((m&1) == 1) {
                result *= x;
            }
            x*=x;//下一位对应的
            m >>= 1;
        }
        return neg ? 1 / result : result;
    }

    /**
     * 递归
     * 3^4 = 3^2 * 3^2
     * 3^5 = 3^2 * 3^2 * 3
     *
     * 时间复杂度：O(log n)
     * 空间复杂度：O(log n)
     * @param x
     * @param n
     * @return
     */
    public double myPow1(double x, int n) {
        if (n == 0) {
            return 1;
        }

        //因为-1 >> n 必然是-1
        if (n == -1) {
            return 1 / x;
        }

        boolean odd = (n & 1) == 1;

        double half = myPow(x, n >> 1/* n / 2 */);
        half *= half;

        return odd ? half * x : half;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
    }
}