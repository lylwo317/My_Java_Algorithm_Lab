package com.kevin.datastructures.tree;

import com.kevin.datastructures.tree.printer.BinaryTreeInfo;
import com.sun.istack.internal.Nullable;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public abstract class BinaryTree<E> implements BinaryTreeInfo {
    protected static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean isChild() {
            return parent != null;
        }

        public boolean isLeftChild() {
            return parent != null && parent.left == this;
        }

        public boolean isRightChild() {
            return parent != null && parent.right == this;
        }

        public Node<E> sibling() {//兄弟
            if (isLeftChild()) {
                return parent.right;
            }

            if (isRightChild()) {
                return parent.left;
            }

            return null;
        }

        @Override
        public String toString() {
            String parentString = "null";
            if (parent != null) {
                parentString = parent.element.toString();
            }
            return element + "_p(" + parentString + ")";
        }
    }

    public static abstract class Visitor<T>{
        public boolean stop;

        public void setStop(boolean stop) {
            this.stop = stop;
        }

        abstract boolean visit(T element);
    }

    public Node<E> createNode(E element, Node<E> parent) {
        return new Node<>(element, parent);
    }

    protected int size;
    protected Node<E> root;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public void preorderRecursion(Visitor<E> visitor) {
        preorderRecursion(root, visitor);
    }

    /**
     * 前序遍历，递归版本
     * root , left , right
     * @param node
     * @return
     */
    private void preorderRecursion(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor == null) {
            return;
        }

//            System.out.print(node.element + " ");
        if (visitor.stop) {
            return;
        }
        visitor.stop = visitor.visit(node.element);
        preorderRecursion(node.left, visitor);
        preorderRecursion(node.right, visitor);
    }

    public void preorderIteration(Visitor<E> visitor) {
        preorderIteration(root, visitor);
    }

    /**
     * 前序遍历，非递归版本
     * root , left , right
     * @param node
     * @return
     */
    private void preorderIteration(Node<E> node, Visitor<E> visitor) {
        if (visitor == null || node == null) {
            return;
        }

        Stack<Node<E>> nodeStack = new Stack<>();
        do {
            while (node != null) {
//                System.out.print(node.element + " ");
                visitor.stop = visitor.visit(node.element);
                if (visitor.stop) {
                    return;
                }
                nodeStack.push(node);
                node = node.left;
            }

            if (!nodeStack.isEmpty()) {
                Node<E> pop = nodeStack.pop();
                node = pop.right;
            }

        } while (node != null || !nodeStack.isEmpty());
    }

    public void inorderRecursion(Visitor<E> visitor) {
        inorderRecursion(root, visitor);
    }

    private void inorderRecursion(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor == null) {
            return;
        }

        if (visitor.stop) {
            return;
        }

        inorderRecursion(node.left, visitor);
        if (visitor.stop) {
            return;
        }
        visitor.stop = visitor.visit(node.element);
        inorderRecursion(node.right, visitor);
    }

    public void inorderIteration(Visitor<E> visitor) {
        inorderIteration(root, visitor);
    }

    private void inorderIteration(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor == null) {
            return;
        }

        Stack<Node<E>> nodeStack = new Stack<>();
        do {
            while (node != null) {
                nodeStack.push(node);
                node = node.left;
            }

            if (!nodeStack.isEmpty()) {
                Node<E> pop = nodeStack.pop();
                visitor.stop = visitor.visit(pop.element);
                if (visitor.stop) {
                    return;
                }
                node = pop.right;
            }

        } while (node != null || !nodeStack.isEmpty());
    }

    public void postorderRecursion(Visitor<E> visitor) {
        postorderRecursion(root, visitor);
    }

    private void postorderRecursion(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor == null) {
            return;
        }

        if (visitor.stop) {
            return;
        }

        postorderRecursion(node.left, visitor);
        postorderRecursion(node.right, visitor);

        if (visitor.stop) {
            return;
        }
        visitor.stop = visitor.visit(node.element);
    }

    public void postorderIteration(Visitor<E> visitor) {
        postorderIteration(root, visitor);
    }

    private void postorderIteration(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor == null) {
            return;
        }

        Stack<Node<E>> nodeStack = new Stack<>();

        Node<E> cur;
        Node<E> pre = null;
        nodeStack.push(node);

        while (!nodeStack.isEmpty()) {
            cur = nodeStack.peek();
            if (cur.isLeaf() || (pre != null && (cur.right == pre || cur.left == pre))) {
                visitor.stop = visitor.visit(cur.element);
                if (visitor.stop) {
                    return;
                }
                nodeStack.pop();
                pre = cur;
            } else {
                //还没有节点出栈过，或者前一个出栈的节点是当前节点的兄弟节点，并该节点有子节点，就尝试入栈
                if (cur.right != null) {
                    nodeStack.push(cur.right);
                }
                if (cur.left != null) {
                    nodeStack.push(cur.left);
                }
            }
        }
    }

    /**
     * 找前驱节点，找比当前节点小的所有节点中最大的节点
     *
     * node
     * if(有左子树）{
     *    node.left.right.right...（左子树最右(最大)节点）
     * } else {
     *    //当前节点没有左子树
     *    if(当前节点是父节点的右子节点) {//父节点就是前驱
     *       node.parent
     *    } else if(当前节点是父节点的左子节点){//就往祖父节点找，直到找到祖父节点的右子节点为止
     *       node.parent.parent....(parent.right == )
     *    } else { //没有前驱节点
     *       return null //也就是最左边的叶子节点
     *    }
     * }
     *
     * @param node
     * @return
     */
    protected Node<E> predecessor(Node<E> node) {
        if (node == null) {
            return null;
        }

        if (node.left != null) {
            node = node.left;
            while (node.right != null) {
                node = node.right;
            }
            return node;
        } else {
            while (node.parent != null && node.parent.left == node) {
                node = node.parent;
            }

//            if (node.parent != null) {
//                return node.parent;
//            } else {
//                return null;
//            }

            //简化
            return node.parent;

        }
    }

    /**
     * 后继节点,与前驱相反，改left 为 right 就行
     * @param node
     * @return
     */
    protected Node<E> successor(Node<E> node) {
        if (node == null) {
            return null;
        }

        if (node.right != null) {
            node = node.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        } else {
            while (node.parent != null && node.parent.right == node) {
                node = node.parent;
            }

//            if (node.parent != null) {
//                return node.parent;
//            } else {
//                return null;
//            }

            //简化
            return node.parent;

        }
    }

    /**
     * 计算树的高度
     * 也就是计算根节点的高度
     * @return
     */
    public int height() {
        return heightIteration(root);
    }

    /**
     * 计算树的高度，递归
     * 递归的思想就是, 大的问题分解成，小问题，小问题继续分解成更小的问题，类似于俄罗斯套娃
     * 这里要请求树的高度，最主要的点就是，分解成请求每个节点的高度
     * @return
     */
    private int heightRecursion(Node<E> node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(heightRecursion(node.left), heightRecursion(node.right));
    }

    /**
     * 计算节点的高度，非递归
     * @return
     */
    private int heightIteration(Node<E> node) {
        if (node == null) return 0;

        //可以使用层序，遍历计算树的高度

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(node);

        int levelSize = 1;

        int height = 0;

        while (!queue.isEmpty()) {
            Node<E> poll = queue.poll();
            levelSize--;

            if (poll.left != null) {
                queue.offer(poll.left);
            }

            if (poll.right != null) {
                queue.offer(poll.right);
            }

            if (levelSize == 0) {//访问完一层，就到下一层了，这个时候queuesize的数量就是下一层节点的数量
                levelSize = queue.size();
                height++;
            }
        }

        return height;
    }

    public boolean isCompleteTree() {
        return isCompleteTree(root);
    }

    /**
     * node作为根节点的树是否是完全二叉树
     * 思路
     * 层序遍历这棵树当发现第一个度为1的节点的时候（这个度在树的左边，前面所有节点的度都是2），需要确保，后面的节点的度都为0
     * @param node
     * @return
     */
    private boolean isCompleteTree(Node<E> node) {
        if (node == null) {
            return false;
        }
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(node);
        boolean afterNodeMustIsLeaf = false;
        while (!queue.isEmpty()) {
            Node<E> cur = queue.poll();

            if (afterNodeMustIsLeaf && !cur.isLeaf()) {
                return false;
            }

            if (cur.left != null) {
                queue.offer(cur.left);
            } else if (cur.right != null) {// cur.left == null && cur.right != null
                return false;
            }

            if (cur.right != null) {
                queue.offer(cur.right);
            } else {// cur.right == null
                afterNodeMustIsLeaf = true;
            }
        }
        return true;
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>)node).right;
    }

    @Override
    public Object string(Object node) {
        return node;
    }

    public abstract void add(E element);

    public abstract void remove(@Nullable E element);
}
