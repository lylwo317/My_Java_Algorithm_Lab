package leetcode.editor.cn;
//给定一个整数数组和一个整数 k，判断数组中是否存在两个不同的索引 i 和 j，使得 nums [i] = nums [j]，并且 i 和 j 的差的 绝对值
// 至多为 k。 
//
// 
//
// 示例 1: 
//
// 输入: nums = [1,2,3,1], k = 3
//输出: true 
//
// 示例 2: 
//
// 输入: nums = [1,0,1,1], k = 1
//输出: true 
//
// 示例 3: 
//
// 输入: nums = [1,2,3,1,2,3], k = 2
//输出: false 
// Related Topics 数组 哈希表 
// 👍 255 👎 0

//https://leetcode-cn.com/problems/contains-duplicate-ii/

import java.util.*;

class _219_存在重复元素_II{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 滑动窗口 + 哈希表
     *
     * 最先出现的重复的两个数中间必定不含有重复的数字。换句话说，这道题的意思是在大小为k的滑动窗口中，是否包含重复的数字
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(min(n,k))
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (set.contains(nums[i])) {
                return true;
            }
            set.add(nums[i]);
            if (set.size() > k) {
                set.remove(nums[i - k]);
            }
        }
        return false;
    }

    /**
     * 哈希表
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean containsNearbyDuplicate1(int[] nums, int k) {
        Map<Integer, Integer> valueToIndex = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer integer = valueToIndex.get(nums[i]);
            if (integer == null) {
                valueToIndex.put(nums[i], i);
            } else {
                if (Math.abs(i - integer) <= k) {
                    return true;
                } else {
                    valueToIndex.put(nums[i], i);
                }
            }
        }
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _219_存在重复元素_II problem = new _219_存在重复元素_II();
        System.out.printf(String.valueOf(problem.solution.containsNearbyDuplicate(new int[]{1, 0, 1, 1}, 1)));
    }
}