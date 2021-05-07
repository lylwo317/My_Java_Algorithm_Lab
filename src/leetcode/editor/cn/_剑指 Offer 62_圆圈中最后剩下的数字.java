package leetcode.editor.cn;
//0,1,···,n-1这n个数字排成一个圆圈，从数字0开始，每次从这个圆圈里删除第m个数字（删除后从下一个数字开始计数）。求出这个圆圈里剩下的最后一个数字。
// 
//
// 例如，0、1、2、3、4这5个数字组成一个圆圈，从数字0开始每次删除第3个数字，则删除的前4个数字依次是2、0、4、1，因此最后剩下的数字是3。 
//
// 
//
// 示例 1： 
//
// 
//输入: n = 5, m = 3
//输出: 3
// 
//
// 示例 2： 
//
// 
//输入: n = 10, m = 17
//输出: 2
// 
//
// 
//
// 限制： 
//
// 
// 1 <= n <= 10^5 
// 1 <= m <= 10^6 
// 
// 👍 350 👎 0

//https://leetcode-cn.com/problems/yuan-quan-zhong-zui-hou-sheng-xia-de-shu-zi-lcof/

class _剑指_Offer_62_圆圈中最后剩下的数字{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 约瑟夫环问题
     *
     * 核心是找规律
     * f(n,m) = ( f(n-1,m) + m ) % n
     *
     * @param n
     * @param m
     * @return
     */
    public int lastRemaining(int n, int m) {
        //f(1,m) = 0
        int x = 0;

        //从f(2,m)递推到f(n,m)
        for (int i = 2; i <= n; i++) {
            x = (x + m) % i;
        }

        return x;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _剑指_Offer_62_圆圈中最后剩下的数字 problem = new _剑指_Offer_62_圆圈中最后剩下的数字();
    }
}