package leetcode.editor.cn;
//给你一个由非负整数组成的数组 nums 。另有一个查询数组 queries ，其中 queries[i] = [xi, mi] 。 
//
// 第 i 个查询的答案是 xi 和任何 nums 数组中不超过 mi 的元素按位异或（XOR）得到的最大值。换句话说，答案是 max(nums[j] XOR
// xi) ，其中所有 j 均满足 nums[j] <= mi 。如果 nums 中的所有元素都大于 mi，最终答案就是 -1 。 
//
// 返回一个整数数组 answer 作为查询的答案，其中 answer.length == queries.length 且 answer[i] 是第 i 个
//查询的答案。 
//
// 
//
// 示例 1： 
//
// 输入：nums = [0,1,2,3,4], queries = [[3,1],[1,3],[5,6]]
//输出：[3,3,7]
//解释：
//1) 0 和 1 是仅有的两个不超过 1 的整数。0 XOR 3 = 3 而 1 XOR 3 = 2 。二者中的更大值是 3 。
//2) 1 XOR 2 = 3.
//3) 5 XOR 2 = 7.
// 
//
// 示例 2： 
//
// 输入：nums = [5,2,4,6,6,3], queries = [[12,4],[8,1],[6,3]]
//输出：[15,-1,5]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length, queries.length <= 105 
// queries[i].length == 2 
// 0 <= nums[j], xi, mi <= 109 
// 
// Related Topics 位运算 字典树 
// 👍 63 👎 0

//https://leetcode-cn.com/problems/maximum-xor-with-an-element-from-array/

import java.util.Arrays;
import java.util.Comparator;

class _1707_与数组中元素的最大异或值{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    Node root = new Node();
    public int[] maximizeXor(int[] nums, int[][] queries) {
        Arrays.sort(nums);
        int queriesLength = queries.length;
        int[][] newQueries = new int[queriesLength][3];
        for (int i = 0; i < queriesLength; i++) {
            newQueries[i][0] = queries[i][0];
            newQueries[i][1] = queries[i][1];
            newQueries[i][2] = i;
        }

        Arrays.sort(newQueries, Comparator.comparingInt(o -> o[1]));

        int[] ans = new int[queriesLength];
        int idx = 0;
        int n = nums.length;
        for (int[] newQuery : newQueries) {
            int x = newQuery[0];
            int m = newQuery[1];
            int qIdx = newQuery[2];
            while (idx < n && nums[idx] <= m) {
                root.addToTrie(nums[idx]);
                idx++;
            }

            if (idx == 0) {
                ans[qIdx] = -1;
            } else {
                int maxOXR = root.getMaxOXR(x);
                ans[qIdx] = maxOXR;
            }

        }

        return ans;
    }
    //Trie
    class Node {
        //如果是不知道数量的trie，可以用ArrayList。这里只会有0,1这两种
        private Node[] child = new Node[2];

        //30...0
        static final int HIGH_BIT = 30;

        private int getMaxOXR(int m) {
            int x = 0;
            Node cur = root;
            for (int k = HIGH_BIT; k >=0; k--) {
                int bit = (m >> k) & 1;
                //要使得结果最大，也就是x的位尽量为1。那么就需要与bit不同
                if (cur.child[bit ^ 1] != null) {
                    //说明能取1
                    x |= (1 << k);
                    bit ^= 1;//说明bit应该是0
                }
                cur = cur.child[bit];
            }
            return x;
        }

        private void addToTrie(int num) {
            Node cur = root;
            for (int k = HIGH_BIT; k >= 0; k--) {
                int bit = (num >> k) & 1;
                if (cur.child[bit] == null) {
                    cur.child[bit] = new Node();
                }
                cur = cur.child[bit];
            }
        }

    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _1707_与数组中元素的最大异或值 problem = new _1707_与数组中元素的最大异或值();
    }
}