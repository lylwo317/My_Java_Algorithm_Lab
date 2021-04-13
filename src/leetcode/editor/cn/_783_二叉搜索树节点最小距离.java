package leetcode.editor.cn;
//给你一个二叉搜索树的根节点 root ，返回 树中任意两不同节点值之间的最小差值 。 
//
// 注意：本题与 530：https://leetcode-cn.com/problems/minimum-absolute-difference-in-bs
//t/ 相同 
//
// 
//
// 
// 
// 示例 1： 
//
// 
//输入：root = [4,2,6,1,3]
//输出：1
// 
//
// 示例 2： 
//
// 
//输入：root = [1,0,48,null,null,12,49]
//输出：1
// 
//
// 
//
// 提示： 
//
// 
// 树中节点数目在范围 [2, 100] 内 
// 0 <= Node.val <= 105 
// 
// 
// 
// Related Topics 树 深度优先搜索 递归 
// 👍 177 👎 0

//https://leetcode-cn.com/problems/minimum-distance-between-bst-nodes/

import com.kevin.leetcode.TreeNode;
import com.sun.source.tree.Tree;

import java.util.Deque;
import java.util.LinkedList;

class _783_二叉搜索树节点最小距离{

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
    private TreeNode preNode = null;
    private int minDiff = Integer.MAX_VALUE;
    public int minDiffInBST(TreeNode root) {
        //中序遍历
        inorderRecursion(root);
        return minDiff;
    }

    /**
     * 递归
     * @param root
     */
    private void inorderRecursion(TreeNode root){
        if (root == null) {
            return;
        }
        inorderRecursion(root.left);
        if (preNode != null) {
            minDiff = Math.min(root.val - preNode.val, minDiff);
        }
        preNode = root;
        inorderRecursion(root.right);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}