package leetcode.editor.cn;
//给定一个包含 非负数 的数组和一个目标 整数 k ，编写一个函数来判断该数组是否含有连续的子数组，其大小至少为 2，且总和为 k 的倍数，即总和为 n * 
//k ，其中 n 也是一个整数。 
//
// 
//
// 示例 1： 
//
// 
//输入：[23,2,4,6,7], k = 6
//输出：True
//解释：[2,4] 是一个大小为 2 的子数组，并且和为 6。
// 
//
// 示例 2： 
//
// 
//输入：[23,2,6,4,7], k = 6
//输出：True
//解释：[23,2,6,4,7]是大小为 5 的子数组，并且和为 42。
// 
//
// 
//
// 说明： 
//
// 
// 数组的长度不会超过 10,000 。 
// 你可以认为所有数字总和在 32 位有符号整数范围内。 
// 
// Related Topics 数学 动态规划 
// 👍 237 👎 0

//https://leetcode-cn.com/problems/continuous-subarray-sum/

import java.util.HashMap;
import java.util.Map;

class _523_连续的子数组和{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 前缀和 +　哈希表
     *
     * 1. 且总和为 k 的倍数:
     *    首先要想办法快速求出子数组的和，然后就要想办法能快速判断是否是ｋ的倍数
     *      a. 快速求出子数组的和的办法：　通过前缀和来求，sum[q] - sum [p]
     *
     *      b. 快速判断ｋ的倍数: 同余定理，　(sum[q] - sum[p]) % k == 0, sum[q] % k == sum[p] % k。
     *         使用哈希表来存储该余数第一次出现的index
     * @param nums
     * @param k
     * @return
     */
    public boolean checkSubarraySum(int[] nums, int k) {
        int[] sum = new int[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }

        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);//特殊处理，因为sum[p]的p可能是<0的，也就是说这个子数组是从第0个元素开始的
        for (int i = 0; i < sum.length; i++) {
            int modK = sum[i] % k;
            Integer j = map.get(modK);
            if (j != null) {
                //找到同余的了
                int indexDirect = i - j;
                if (indexDirect >= 2) {
                    return true;
                }
            } else {
                map.put(modK, i);
            }
        }
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _523_连续的子数组和 problem = new _523_连续的子数组和();
        problem.solution.checkSubarraySum(new int[]{23, 2, 4, 6, 6}, 7);
    }
}