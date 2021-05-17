package leetcode.editor.cn;
//在二叉树中，根节点位于深度 0 处，每个深度为 k 的节点的子节点位于深度 k+1 处。 
//
// 如果二叉树的两个节点深度相同，但 父节点不同 ，则它们是一对堂兄弟节点。 
//
// 我们给出了具有唯一值的二叉树的根节点 root ，以及树中两个不同节点的值 x 和 y 。 
//
// 只有与值 x 和 y 对应的节点是堂兄弟节点时，才返回 true 。否则，返回 false。 
//
// 
//
// 示例 1： 
// 
//
// 
//输入：root = [1,2,3,4], x = 4, y = 3
//输出：false
// 
//
// 示例 2： 
// 
//
// 
//输入：root = [1,2,3,null,4,null,5], x = 5, y = 4
//输出：true
// 
//
// 示例 3： 
//
// 
//
// 
//输入：root = [1,2,3,null,4], x = 2, y = 3
//输出：false 
//
// 
//
// 提示： 
//
// 
// 二叉树的节点数介于 2 到 100 之间。 
// 每个节点的值都是唯一的、范围为 1 到 100 的整数。 
// 
//
// 
// Related Topics 树 广度优先搜索 
// 👍 180 👎 0

//https://leetcode-cn.com/problems/cousins-in-binary-tree/

import com.kevin.leetcode.TreeNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

class _993{
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
    class TreeNodeWithDeep {
        int deep;
        TreeNode node;
        TreeNode parent;

        public TreeNodeWithDeep(int deep, TreeNode node, TreeNode parent) {
            this.deep = deep;
            this.node = node;
            this.parent = parent;
        }

        public TreeNode getLeftNode() {
            return node.left;
        }

        public TreeNode getRightNode() {
            return node.right;
        }
    }
    public boolean isCousins(TreeNode root, int x, int y) {
        //解一：先找到两个节点，并返回深度值，然后再看父节点是否是同一个
        //解二：通过层序遍历确认x,y是否在同一层，如果在同一层，再确认父节点是否是同一个

        Deque<TreeNodeWithDeep> nodeDeque = new LinkedList<>();

        nodeDeque.addLast(new TreeNodeWithDeep(0, root, null));

        TreeNodeWithDeep xNode = null;
        TreeNodeWithDeep yNode = null;
        int currentDeep = 0;
        while (!nodeDeque.isEmpty()) {
            TreeNodeWithDeep treeNode = nodeDeque.removeFirst();
            currentDeep = treeNode.deep;
            if (treeNode.node.val == x) {
                xNode = treeNode;
            }

            if (treeNode.node.val == y) {
                yNode = treeNode;
            }

            if (xNode != null && yNode != null) {
                if (xNode.deep == yNode.deep) {
                    //check parent
                    return xNode.parent.val != yNode.parent.val;
                } else {
                    return false;
                }
            } else {
                if (xNode != null) {
                    if (xNode.deep != currentDeep) {
                        return false;
                    }
                }

                if (yNode != null) {
                    if (yNode.deep != currentDeep) {
                        return false;
                    }
                }
            }

            if (treeNode.getLeftNode() != null) {
                nodeDeque.addLast(new TreeNodeWithDeep(treeNode.deep + 1, treeNode.getLeftNode(), treeNode.node));
            }

            if (treeNode.getRightNode() != null) {
                nodeDeque.addLast(new TreeNodeWithDeep(treeNode.deep + 1, treeNode.getRightNode(), treeNode.node));
            }
        }

        return false;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _993 problem = new _993();
    }
}