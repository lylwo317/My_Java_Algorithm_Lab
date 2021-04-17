package leetcode.editor.cn;
//给你一个整数数组 nums 和两个整数 k 和 t 。请你判断是否存在 两个不同下标 i 和 j，使得 abs(nums[i] - nums[j]) <= 
//t ，同时又满足 abs(i - j) <= k 。 
//
// 如果存在则返回 true，不存在返回 false。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3,1], k = 3, t = 0
//输出：true 
//
// 示例 2： 
//
// 
//输入：nums = [1,0,1,1], k = 1, t = 2
//输出：true 
//
// 示例 3： 
//
// 
//输入：nums = [1,5,9,1,5,9], k = 2, t = 3
//输出：false 
//
// 
//
// 提示： 
//
// 
// 0 <= nums.length <= 2 * 104 
// -231 <= nums[i] <= 231 - 1 
// 0 <= k <= 104 
// 0 <= t <= 231 - 1 
// 
// Related Topics 排序 Ordered Map 
// 👍 363 👎 0

//https://leetcode-cn.com/problems/contains-duplicate-iii/

import java.util.*;

class _220_存在重复元素_III{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 桶
     *
     * 每个桶的size是t+1，然后nums[i] = (t+1) * a + b (0<=b<=t)，桶的编号是a。
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(min(n,k))
     *
     * @param nums
     * @param k
     * @param t
     * @return
     */
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        Map<Long, Long> map = new HashMap<>();
        long bucketSize = t + 1;

        for (int i = 0; i < nums.length; i++) {
            long id = getId(nums[i], bucketSize);
            //在同一个桶内
            if (map.containsKey(id)) {
                return true;
            }

            //在相邻的两个桶内
            //查找左边的桶有没有符合 abs(nums[i] - nums[j]) <= t
            if (map.containsKey(id - 1) && Math.abs(nums[i] - map.get(id - 1)) <= t) {
                return true;
            }

            //查找右边的桶有没有符合 abs(nums[i] - nums[j]) <= t
            if (map.containsKey(id + 1) && Math.abs(nums[i] - map.get(id + 1)) <= t) {
                return true;
            }

            map.put(id, (long) nums[i]);
            if (i>=k) {
                map.remove(getId(nums[i - k], bucketSize));
            }
        }

        return false;
    }

    private long getId(long value, long bucketSize){
        if (value >= 0) {
            return value / bucketSize;
        } else {
            return ((value + 1) / bucketSize) - 1;
        }
    }

    /**
     * 分析
     *
     * 两个条件：
     * 1.abs(i - j) <= k, 这个通过滑动窗口去满足，假设在当前索引x的前k个元素中[x-k,x-1]查找满足下面条件2的元素
     * 2.abs(nums[i] - nums[j]) <= t --> nums[j] 在 [nums[i] - t, nums[i] + t] 范围内
     *
     * 解法：滑动窗口+有序的Map
     *
     * 时间复杂度：O(nlog(min(n,k)))
     * 空间复杂度：O(min(n,k))
     *
     * @param nums
     * @param k
     * @param t
     * @return
     */
    public boolean containsNearbyAlmostDuplicate1(int[] nums, int k, int t) {
        TreeSet<Long> valueSet = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {//O(n)
            Long ceiling = valueSet.ceiling(((long)nums[i] - (long)t));//O(log(min(n,k)))
            if (ceiling != null && ceiling <= (long)nums[i] + (long)t) {
                return true;
            }
            valueSet.add((long) nums[i]);
            if (i >= k) {
                valueSet.remove((long)nums[i - k]);
            }
        }
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
public static void main(String[] args) {
    _220_存在重复元素_III problem = new _220_存在重复元素_III();
}
}