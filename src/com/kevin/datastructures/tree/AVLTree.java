package com.kevin.datastructures.tree;

import java.util.Comparator;

/**
 * <pre>
 * 在计算机科学中，AVL树是最先发明的自平衡二叉查找树。在AVL树中任何节点的两个子树的高度最大差别为1，所以它也被称为高度平衡树。
 * 增加和删除可能需要通过一次或多次树旋转来重新平衡这个树。
 * AVL树得名于它的发明者G. M. Adelson-Velsky和E. M. Landis，他们在1962年的论文《An algorithm for the organization of information》中发表了它。
 *
 * 平衡因子：节点的左右子树高度差
 *
 * 特点：
 * 1. 每个节点的平衡因子0,1,-1（绝对值<=1）。也就是左右子树高度差不超过1
 *
 * 如何恢复平衡：
 * 旋转。当平衡因子大于1的时候，通过旋转就可以让树恢复平衡。
 *
 * 旋转为什么能恢复平衡？
 * 1. 首先所谓的旋转其实本质就是调整节点相对位置，因添加的到节点导致失衡的子树重新恢复平衡。本质就是将左右
 * LL --> 右旋转。可以看到本来高度差（平衡因子）是1，但是由于添加了12， 导致子树的左右两边高度差变为2。通过右旋，使得子树的高度恢复成2。
 * 本质就是将导致的失衡的子树往上移动（降低1个高度，使得高度差恢复到1）,右边子树往下移动一位。这样假设原来的左右高度是2,0 --> 1,1
 *            34(2)           34(3)            23(2)
 *            /               /               /    \
 *           23(1)    --->   23(2)    --->   12(1) 34(1)
 *                           /
 *                          12(1)
 *
 *
 * RR --> 左旋转。可以看到本来高度差（平衡因子）是1，但是由于添加了72， 导致子树的左右两边高度差变为2。通过右旋，使得子树的高度恢复成2。
 *
 *          34(2)           34(3)               53(2)
 *              \               \              /    \
 *            53(1)  --->    53(2)    --->  34(1)  72(1)
 *                               \
 *                             72(1)
 *
 *
 * LR --> 20左旋转，40右旋转。可以看到本来高度差（平衡因子）是1，但是由于添加了30， 导致子树的左右两边高度差变为2。通过两次旋转，使得子树的高度恢复成2。
 * 高度变化（2,0->1,1）
 *          40(3)            40(3)             30(2)
 *          /                /                /    \
 *         20(2)    --->    30(2)    --->   20(1)  40(1)
 *          \              /
 *          30(1)         20(1)
 *
 *
 * RL --> 53右旋转，34左旋。可以看到本来高度差（平衡因子）是1，但是由于添加了40， 导致子树的左右两边高度差变为2。通过两次旋转，使得子树的高度恢复成2。
 *
 *          34(3)           34(3)               40(2)
 *             \               \               /    \
 *            53(2)  --->    40(2)    --->   34(1)  53(1)
 *            /                 \
 *          40(1)             53(1)
 *
 * RL 通过一次右旋 转成 RR ，然后再左旋恢复平衡
 * </pre>
 * 平衡二叉搜索树
 * @param <E>
 */
public class AVLTree<E> extends BinarySearchTree<E> {

    private Comparator<E> comparator;

    public AVLTree() {
    }

    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }

    private static class AVLNode<E> extends Node<E> {
        private int height = 1;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }


        public boolean isBalanced() {
            int leftHeight = left != null ? ((AVLNode<E>)left).getHeight() : 0;
            int rightHeight = right != null ? ((AVLNode<E>)right).getHeight() : 0;
            return Math.abs(leftHeight - rightHeight) <= 1;
        }

        public void updateHeight() {
            int leftHeight = left != null ? ((AVLNode<E>)left).getHeight() : 0;
            int rightHeight = right != null ? ((AVLNode<E>)right).getHeight() : 0;
            height = Math.max(leftHeight,rightHeight) + 1;
        }

        public Node<E> tallerChild() {
            int leftHeight = left != null ? ((AVLNode<E>)left).getHeight() : 0;
            int rightHeight = right != null ? ((AVLNode<E>)right).getHeight() : 0;
            return Math.max(leftHeight, rightHeight) == rightHeight ? right : left;
        }

        @Override
        public String toString() {
            String parentString = "null";
            if (parent != null) {
                parentString = parent.element.toString();
            }
            return element + "_p(" + parentString + ")_h(" + height + ")";
        }
    }

    @Override
    public Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<>(element, parent);
    }

    private boolean isBalanced(Node<E> node) {
        return ((AVLNode<E>) node).isBalanced();
    }

    @Override
    protected void afterAdd(Node<E> node) {
        //添加的节点都是叶子节点
        while (node.parent != null) {
            node = node.parent;
            if (isBalanced(node)) {//平衡的话，就只更新高度
                updateHeight(node);
            } else {//不平衡的话，要调整平衡，并更新高度
                reBalance(node);
            }
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {
        //添加的节点都是叶子节点
        while (node.parent != null) {
            node = node.parent;
            if (isBalanced(node)) {//平衡的话，就只更新高度
                updateHeight(node);
            } else {//不平衡的话，要调整平衡，并更新高度
                reBalance(node);
            }
        }
    }

    /**
     * 恢复平衡
     */
    private void reBalance(Node<E> grand) {
        AVLNode<E> parent = (AVLNode<E>) ((AVLNode<E>) grand).tallerChild();
        AVLNode<E> node = (AVLNode<E>) ((AVLNode<E>) parent).tallerChild();
        if (parent.isLeftChild()) {//L
            if (node.isLeftChild()) {//LL
                rotateRight(grand);
            } else {//LR
                rotateLeft(parent);
                rotateRight(grand);
            }
        } else if (parent.isRightChild()) {//R
            if (node.isRightChild()) {//RR
                rotateLeft(grand);
            } else {//RL
                rotateRight(parent);
                rotateLeft(grand);
            }
        }
    }

    private void rotateLeft(Node<E> grand) {
        Node<E> rootNode = grand.parent;
        Node<E> parent = grand.right;
        Node<E> subTree = parent.left;

        parent.left = grand;
        grand.right = subTree;

        afterRotate(rootNode, grand, parent, subTree);
    }

    private void rotateRight(Node<E> grand) {
        Node<E> rootNode = grand.parent;
        Node<E> parent = grand.left;
        Node<E> subTree = parent.right;

        parent.right = grand;
        grand.left = subTree;

        afterRotate(rootNode, grand, parent, subTree);
    }

    private void afterRotate(Node<E> rootNode, Node<E> grand, Node<E> parent, Node<E> subTree) {
        if (grand.isLeftChild()) {
            rootNode.left = parent;
        } else if (grand.isRightChild()) {
            rootNode.right = parent;
        } else {//根节点
            root = parent;
        }

        //update node.parent
        if (subTree != null) {
            subTree.parent = grand;
        }
        grand.parent = parent;
        parent.parent = rootNode;

        updateHeight(grand);
        updateHeight(parent);
    }


    private void updateHeight(Node<E> node) {
        ((AVLNode<E>) node).updateHeight();
    }
}
