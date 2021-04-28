package leetcode.editor.cn;
//实现 int sqrt(int x) 函数。 
//
// 计算并返回 x 的平方根，其中 x 是非负整数。 
//
// 由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。 
//
// 示例 1: 
//
// 输入: 4
//输出: 2
// 
//
// 示例 2: 
//
// 输入: 8
//输出: 2
//说明: 8 的平方根是 2.82842..., 
//     由于返回类型是整数，小数部分将被舍去。
// 
// Related Topics 数学 二分查找 
// 👍 667 👎 0

//https://leetcode-cn.com/problems/sqrtx/

class _69_x_的平方根{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int mySqrt(int x) {
        //不能使用左闭右开区间来二分。因为测试用例有x=Integer.MAX_VALUE，+1就会导致越界
//        int l = 0, r = x + 1, ans = -1;
//        while (l < r) {
//            int mid = l + (r - l) / 2;
//            if ((long) mid * mid <= x) {
//                ans = mid;
//                l = mid + 1;
//            } else {
//                r = mid;
//            }
//        }
        int l = 0, r = x, ans = -1;
        while (l <= r) {
            int mid = (l + r) >>> 1;
            if ((long) mid * mid <= x) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _69_x_的平方根 problem = new _69_x_的平方根();
    }
}