package leetcode.editor.cn;
//给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位
//。 
//
// 返回滑动窗口中的最大值。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
//输出：[3,3,5,5,6,7]
//解释：
//滑动窗口的位置                最大值
//---------------               -----
//[1  3  -1] -3  5  3  6  7       3
// 1 [3  -1  -3] 5  3  6  7       3
// 1  3 [-1  -3  5] 3  6  7       5
// 1  3  -1 [-3  5  3] 6  7       5
// 1  3  -1  -3 [5  3  6] 7       6
// 1  3  -1  -3  5 [3  6  7]      7
// 
//
// 示例 2： 
//
// 
//输入：nums = [1], k = 1
//输出：[1]
// 
//
// 示例 3： 
//
// 
//输入：nums = [1,-1], k = 1
//输出：[1,-1]
// 
//
// 示例 4： 
//
// 
//输入：nums = [9,11], k = 2
//输出：[11]
// 
//
// 示例 5： 
//
// 
//输入：nums = [4,-2], k = 2
//输出：[4] 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 105 
// -104 <= nums[i] <= 104 
// 1 <= k <= nums.length 
// 
// Related Topics 堆 Sliding Window 
// 👍 892 👎 0

import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;

class _239_滑动窗口最大值{

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 使用单调队列
     * 时间复杂度：O(n)
     * 空间复杂度：O(k) 队列中的元素不超过k+1个
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
//        return maxSlidingWindow1(nums, k);
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        Deque<Integer> dq = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            while (!dq.isEmpty()&&nums[dq.peekFirst()] <= nums[i]) {
                dq.pollFirst();
            }
            dq.offerFirst(i);
        }
        ans[0] = nums[dq.peekLast()];
        for (int i = k; i < n; i++) {
            while (!dq.isEmpty()&&nums[dq.peekFirst()] <= nums[i]) {
                dq.pollFirst();
            }
            while (!dq.isEmpty() && dq.peekLast() <= i - k) {
                dq.pollLast();
            }
            dq.offerFirst(i);
            ans[i - k + 1] = nums[dq.peekLast()];
        }
        return ans;
    }

    /**
     * 使用优先队列（堆）来存储，这样可以轻松获取最大值
     * 时间复杂度：O(nlog n)
     * 空间复杂度：O(n) pq的空间
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow1(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] != o2[0] ? o2[0] - o1[0] : o2[1] - o1[1];
            }
        });
        //初始化队列
        for (int i = 0; i < k; i++) {
            pq.offer(new int[]{nums[i], i});
        }

        ans[0] = pq.peek()[0];
        for (int i = k; i < n; i++) {
            pq.offer(new int[]{nums[i], i});
            while (pq.peek()[1] <= i - k) {//保证最大值是在(i-k,i]范围
                pq.poll();
            }
            ans[i - k + 1] = pq.peek()[0];
        }
        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}