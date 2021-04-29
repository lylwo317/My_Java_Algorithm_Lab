package leetcode.editor.cn;
//给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重
//复的三元组。 
//
// 注意：答案中不可以包含重复的三元组。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [-1,0,1,2,-1,-4]
//输出：[[-1,-1,2],[-1,0,1]]
// 
//
// 示例 2： 
//
// 
//输入：nums = []
//输出：[]
// 
//
// 示例 3： 
//
// 
//输入：nums = [0]
//输出：[]
// 
//
// 
//
// 提示： 
//
// 
// 0 <= nums.length <= 3000 
// -105 <= nums[i] <= 105 
// 
// Related Topics 数组 双指针 
// 👍 3295 👎 0

//https://leetcode-cn.com/problems/3sum/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class _15_三数之和{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 要点：
     * 1. 不重复
     *
     * 基本思想：
     * 先选中一个数为c，然后这个数的右边查到是否有两个数的和等于-c
     *
     * 首先要对数组排序，排序有两个作用：
     * 1. 让搜索更简单，排序后，我们可以用l + r两个指针来查找a，b，通过不断缩小l r 来找到合适的a,b。如果不排序就只能暴力搜索了
     * 2. 可以很简单的排除重复的结果
     *
     * 时间复杂度：O(N^2)
     * 空间复杂度：O(1)
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            //跳过重复的nums[i]
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int remain = -nums[i];
            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum == remain) {
                    //找到了
                    List<Integer> selected = new ArrayList<>();
                    selected.add(nums[i]);
                    selected.add(nums[left]);
                    selected.add(nums[right]);
                    ans.add(selected);
                    //这里也有可能重复
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }

                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                } else if (sum > remain) {
                    right--;
                } else { // sum < remain
                    left++;
                }
            }
        }

        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _15_三数之和 problem = new _15_三数之和();
    }
}