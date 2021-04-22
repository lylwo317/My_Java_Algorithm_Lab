package leetcode.editor.cn;
//给定一个整数数组 nums，求出数组从索引 i 到 j（i ≤ j）范围内元素的总和，包含 i、j 两点。 
//
// 
// 
// 实现 NumArray 类： 
//
// 
// NumArray(int[] nums) 使用数组 nums 初始化对象 
// int sumRange(int i, int j) 返回数组 nums 从索引 i 到 j（i ≤ j）范围内元素的总和，包含 i、j 两点（也就是 s
//um(nums[i], nums[i + 1], ... , nums[j])） 
// 
//
// 
//
// 示例： 
//
// 
//输入：
//["NumArray", "sumRange", "sumRange", "sumRange"]
//[[[-2, 0, 3, -5, 2, -1]], [0, 2], [2, 5], [0, 5]]
//输出：
//[null, 1, -1, -3]
//
//解释：
//NumArray numArray = new NumArray([-2, 0, 3, -5, 2, -1]);
//numArray.sumRange(0, 2); // return 1 ((-2) + 0 + 3)
//numArray.sumRange(2, 5); // return -1 (3 + (-5) + 2 + (-1)) 
//numArray.sumRange(0, 5); // return -3 ((-2) + 0 + 3 + (-5) + 2 + (-1))
// 
//
// 
//
// 提示： 
//
// 
// 0 <= nums.length <= 104 
// -105 <= nums[i] <= 105 
// 0 <= i <= j < nums.length 
// 最多调用 104 次 sumRange 方法 
// 
// 
// 
// Related Topics 动态规划 
// 👍 327 👎 0

//https://leetcode-cn.com/problems/range-sum-query-immutable/

class _303_区域和检索_数组不可变{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class NumArray {
    int[] sums;

    public NumArray(int[] nums) {
        sums = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            sums[i + 1] = sums[i] + nums[i];
        }
    }
    
    public int sumRange(int left, int right) {
        return sums[right + 1] - sums[left];
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(left,right);
 */
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _303_区域和检索_数组不可变 problem = new _303_区域和检索_数组不可变();
    }
}