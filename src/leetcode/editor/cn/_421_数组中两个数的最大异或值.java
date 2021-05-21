package leetcode.editor.cn;
//给你一个整数数组 nums ，返回 nums[i] XOR nums[j] 的最大运算结果，其中 0 ≤ i ≤ j < n 。 
//
// 进阶：你可以在 O(n) 的时间解决这个问题吗？ 
//
// 
//
// 
// 
// 示例 1： 
//
// 
//输入：nums = [3,10,5,25,2,8]
//输出：28
//解释：最大运算结果是 5 XOR 25 = 28. 
//
// 示例 2： 
//
// 
//输入：nums = [0]
//输出：0
// 
//
// 示例 3： 
//
// 
//输入：nums = [2,4]
//输出：6
// 
//
// 示例 4： 
//
// 
//输入：nums = [8,10,2]
//输出：10
// 
//
// 示例 5： 
//
// 
//输入：nums = [14,70,53,83,49,91,36,80,92,51,66,70]
//输出：127
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 2 * 104 
// 0 <= nums[i] <= 231 - 1 
// 
// 
// 
// Related Topics 位运算 字典树 
// 👍 354 👎 0

//https://leetcode-cn.com/problems/maximum-xor-of-two-numbers-in-an-array/

import java.util.HashSet;
import java.util.Set;

class _421{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    // 最高位的二进制位编号为 30
    static final int HIGH_BIT = 30;

    /**
     * 要使得异或结果最大，那么异或的数的二进制位的高位要尽量为1，这样才能使得异或结果最大
     * 假设最大的异或结果是 x ，则有x = nums[i] ^ nums[j]。根据异或的运算性质可以得到
     * num[i] = x ^ nums[j]。
     * @param nums
     * @return
     */
    public int findMaximumXOR2(int[] nums) {
        int x = 0;
        for (int k = HIGH_BIT; k >=0; k--) {
            Set<Integer> set = new HashSet<>();
            for (Integer num : nums) {//pre
                set.add(num >> k);
            }

            //假设下一位为1
            int xNext = x * 2 + 1;

            boolean found = false;
            for (Integer num : nums){
                if (set.contains(xNext ^ (num >> k))) {
                    found = true;
                    break;
                }
            }

            if (found) {
                x = xNext;
            } else {
                x = xNext - 1;
            }
        }
        return x;
    }


    // 字典树的根节点
    Node root = new Node();

    public int findMaximumXOR(int[] nums) {
        int n = nums.length;
        int maxX = 0;
        for (int i = 1; i < n; i++) {
            addToTrie(nums[i-1]);
            int xNew = getX(nums[i]); //在nums[0,i-1]中找出与nums[j]异或的最大值
            maxX = Math.max(maxX, xNew);
        }
        return maxX;
    }

    public void addToTrie(int num){
        Node cur = root;
        for (int k = HIGH_BIT; k >= 0; k--) {
            int bit = (num >> k) & 1;//取第k位数
            if (bit == 0) {
                if (cur.child[0] == null) {
                    cur.child[0] = new Node();
                }
                cur = cur.child[0];
            } else {//==1
                if (cur.child[1] == null) {
                    cur.child[1] = new Node();
                }
                cur = cur.child[1];
            }
        }
    }

    private int getX(int num) {
        Node cur = root;
        int x = 0;
        for (int k = HIGH_BIT; k >= 0; k--) {
            int bit = (num >> k) & 1;//取第k位数
            if (bit == 0) {
                if (cur.child[1] != null) {
                    x = x * 2 + 1;
                    cur = cur.child[1];
                } else {
                    x = x * 2;
                    cur = cur.child[0];
                }
            } else {//==1
                if (cur.child[0] != null) {
                    x = x * 2 + 1;
                    cur = cur.child[0];
                } else {
                    x = x * 2;
                    cur = cur.child[1];
                }
            }
        }
        return x;
    }

    //Trie
    class Node {
        //如果是不知道数量的trie，可以用ArrayList。这里只会有0,1这两种
        private Node[] child = new Node[2];
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _421 problem = new _421();
    }
}