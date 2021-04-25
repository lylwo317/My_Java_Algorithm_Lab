package leetcode.editor.cn;
//给你二叉搜索树的根节点 root ，该树中的两个节点被错误地交换。请在不改变其结构的情况下，恢复这棵树。 
//
// 进阶：使用 O(n) 空间复杂度的解法很容易实现。你能想出一个只使用常数空间的解决方案吗？ 
//
// 
//
// 示例 1： 
//
// 
//输入：root = [1,3,null,null,2]
//输出：[3,1,null,null,2]
//解释：3 不能是 1 左孩子，因为 3 > 1 。交换 1 和 3 使二叉搜索树有效。
// 
//
// 示例 2： 
//
// 
//输入：root = [3,1,4,null,null,2]
//输出：[2,1,4,null,null,3]
//解释：2 不能在 3 的右子树中，因为 2 < 3 。交换 2 和 3 使二叉搜索树有效。 
//
// 
//
// 提示： 
//
// 
// 树上节点的数目在范围 [2, 1000] 内 
// -231 <= Node.val <= 231 - 1 
// 
// Related Topics 树 深度优先搜索 
// 👍 455 👎 0

//https://leetcode-cn.com/problems/recover-binary-search-tree/

import com.kevin.leetcode.TreeNode;

class _99_恢复二叉搜索树{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    /**
     * 这里核心就是通过观察交换后的中序遍历的结果特征得出规律
     *
     * 这里得到的规律有：
     * 1. 如果是中序遍历中相邻的两个节点交换了，那么就会有一对逆序对，那么这个逆序对就是被错误交换的两个节点，交换就可以恢复
     * 2. 如果是中序遍历中不相邻的两个节点交换了，那么就会有两个逆序对，那么只要将这两个逆序对的头尾交换就可以恢复
     *
     * 例如：
     * 1 2 3 4 5 6 7 8 9 10
     *
     * 1 2 3 [5 4] 6 7 8 9 10
     *        - -
     *
     * 1 2 [8 4] 5 6 [7 3] 9 10
     *      -           -
     *
     * @param root
     */
    public void recoverTree(TreeNode root) {
        findWrongNodes(root);
        //交换两个错误节点的值
        if (first != null && second != null) {
            int tmp = first.val;
            first.val = second.val;
            second.val = tmp;
        }
    }

    private TreeNode first = null;
    private TreeNode second = null;
    private TreeNode pre = null;
    private void findWrongNodes(TreeNode root) {
        if (root == null) {
            return;
        }
        findWrongNodes(root.left);
        if (pre != null && pre.val > root.val) {
            if (first == null) {
                first = pre;
            }

            second = root;
        }
        pre = root;
        findWrongNodes(root.right);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _99_恢复二叉搜索树 problem = new _99_恢复二叉搜索树();
    }
}