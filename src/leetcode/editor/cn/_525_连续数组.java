package leetcode.editor.cn;
//给定一个二进制数组 nums , 找到含有相同数量的 0 和 1 的最长连续子数组，并返回该子数组的长度。 
//
// 
//
// 示例 1: 
//
// 
//输入: nums = [0,1]
//输出: 2
//说明: [0, 1] 是具有相同数量0和1的最长连续子数组。 
//
// 示例 2: 
//
// 
//输入: nums = [0,1,0]
//输出: 2
//说明: [0, 1] (或 [1, 0]) 是具有相同数量0和1的最长连续子数组。 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 105 
// nums[i] 不是 0 就是 1 
// 
// Related Topics 哈希表 
// 👍 281 👎 0

//https://leetcode-cn.com/problems/contiguous-array/

import com.kevin.datastructures.map.HashMap;

class _525_连续数组{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 这个数组只有0和1
     * 1. 相同数量的0,1
     * 2. 最长
     *
     * 如果0,1数量相同，那么这些这个子数组的和的特征，sum[q] - sum[p] = (q - p) / 2
     * 上面这样不是很好转换出求解的方案。如果能完全通过前缀和来求解，并且能很简单的定位到边界就更好了。
     *
     * 仔细观察会发现，这里0,1数量相等，也就是说如果将0看做-1，sum[q] - sum[p] = 0， sum[q] = sum[p]
     *
     * 实际上就是看怎么快速定位到前缀和相等的两个index。这里可以使用哈希表来快速定位
     *
     * @param nums
     * @return
     */
    public int findMaxLength(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);//-1 index 的前缀和为 0
        int sum = 0;
        int maxLength = 0;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (num == 0) {
                sum++;
            } else {
                sum--;
            }
            Integer preIndex = map.get(sum);
            if (preIndex != null) {
                maxLength = Math.max(maxLength, i - preIndex);
            } else {
                map.put(sum, i);
            }
        }
        return maxLength;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _525_连续数组 problem = new _525_连续数组();
    }
}