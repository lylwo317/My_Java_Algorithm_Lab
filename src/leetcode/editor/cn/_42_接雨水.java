package leetcode.editor.cn;
//给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。 
//
// 
//
// 示例 1： 
//
// 
//
// 
//输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
//输出：6
//解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 
// 
//
// 示例 2： 
//
// 
//输入：height = [4,2,0,3,2,5]
//输出：9
// 
//
// 
//
// 提示： 
//
// 
// n == height.length 
// 0 <= n <= 3 * 104 
// 0 <= height[i] <= 105 
// 
// Related Topics 栈 数组 双指针 动态规划 
// 👍 2330 👎 0

//https://leetcode-cn.com/problems/trapping-rain-water/

class _42_接雨水{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 其实可以这样想象，决定每个柱子蓄水的多少，取决于柱子两边中的比当前柱子高的最短边
     *
     * |   |
     * | | |
     * @param height
     * @return
     */
    public int trap(int[] height) {
        int l = 0;
        int r = height.length - 1;
        int lower = 0;
        int lowerMax = 0;
        int water = 0;
        while (l < r) {
            if (height[l] <= height[r]) {
                lower = height[l];
                lowerMax = Math.max(lowerMax, lower);
                l++;
            } else {
                lower = height[r];
                lowerMax = Math.max(lowerMax, lower);
                r--;
            }
            water += lowerMax - lower;
        }
        return water;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _42_接雨水 problem = new _42_接雨水();
    }
}